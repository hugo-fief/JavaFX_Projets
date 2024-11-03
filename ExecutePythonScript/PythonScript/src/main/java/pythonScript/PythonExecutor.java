package pythonScript;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PythonExecutor extends Application {

    // Chemin de l'interpreteur Python, lu depuis le fichier de configuration
    private static String pythonCommand;

    // Autres constantes
    private static final String REQUIRED_PYTHON_VERSION = "Python 3.11";
    private static final String SCRIPT_PATH_PROPERTY = "python.script.path";
    private static final String ARGUMENTS_PROPERTY = "python.script.arguments";
    private static final List<String> REQUIRED_LIBRARIES = Arrays.asList("pandas", "matplotlib", "openpyxl");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger les proprietes depuis le fichier config.properties
            Properties properties = loadProperties("config.properties");

            // Charger le chemin de l'interpreteur Python depuis config.properties
            pythonCommand = loadPythonCommand(properties);
            
            // Charger le chemin du script, du fichier Excel et du dossier de sortie
            String scriptPath = getResourcePath("pythonScript/" + properties.getProperty("python.script.path"));
            String excelPath = getResourcePath("pythonScript/" + properties.getProperty("python.script.excel"));
            String outputPath = getResourcePath("pythonScript/" + properties.getProperty("python.script.output"));

            
            // Verifier la version de Python
            if (!isCorrectPythonVersion()) {
                throw new ConfigurationException("Version de Python non compatible. Python 3.11 requis.");
            }

            // Verifier les librairies installees
            checkPythonLibraries();

            // Verifier que le chemin et les arguments sont valides
            if (scriptPath == null || scriptPath.isEmpty()) {
                throw new ConfigurationException("Chemin du script ou arguments non definis dans le fichier de configuration.");
            }

            // Executer le script Python avec les arguments fournis
            executePythonScript(scriptPath, excelPath, outputPath);

        } catch (ConfigurationException | PythonExecutionException e) {
            showErrorPopup(e.getMessage());
        } catch (IOException e) {
            showErrorPopup("Erreur de chargement ou d'execution.");
        }
    }

    // Charger le chemin de l'interpreteur Python
    private static String loadPythonCommand(Properties properties) {
        // Charger le chemin de l'executable Python du venv defini dans config.properties
        // Valeur par defaut "python3" si venv non specifiee
        return properties.getProperty("python.command", "python");
    }

    // Verifier la version de Python
    private static boolean isCorrectPythonVersion() throws IOException {
        // Utilisation de pythonCommand, qui peut pointer vers un venv
        ProcessBuilder versionCheck = new ProcessBuilder(pythonCommand, "--version");
        Process versionProcess = versionCheck.start();
        try (BufferedReader versionReader = new BufferedReader(new InputStreamReader(versionProcess.getInputStream()))) {
            String versionOutput = versionReader.readLine();
            return versionOutput != null && versionOutput.contains(REQUIRED_PYTHON_VERSION);
        }
    }

    // Verifier les librairies Python requises
    private static void checkPythonLibraries() throws IOException, ConfigurationException {
        for (String library : REQUIRED_LIBRARIES) {
            ProcessBuilder checkLibrary = new ProcessBuilder("pip", "show", library);
            Process checkProcess = checkLibrary.start();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()))) {
                if (reader.lines().noneMatch(l -> l.contains("Name: " + library))) {
                    throw new ConfigurationException("La librairie Python requise '" + library + "' n'est pas installee.");
                }
            }
        }
    }

    // Executer le script Python avec les arguments
    private static void executePythonScript(String scriptPath, String excelPath, String outputPath) throws PythonExecutionException, IOException {
    	// Creer une liste pour les arguments de ProcessBuilder
    	List<String> command = new ArrayList<>();
        command.add(pythonCommand);      // Interpreteur Python
        command.add(scriptPath);         // Script Python
        command.add(excelPath);          // Chemin du fichier Excel
        command.add(outputPath);         // Dossier de sortie
        
        // Initialiser ProcessBuilder avec la liste complete des arguments
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String line;
            System.out.println("Sortie standard du script Python :");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            if (errorReader.readLine() != null) {
            	System.out.println("Sortie d'erreur du script Python (si presente) :");
			}
            
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line); // Afficher la sortie d'erreur en rouge dans la console (si disponible)
            }
        } catch (IOException e) {
            throw new PythonExecutionException("Erreur lors de l'execution du script Python : " + e.getMessage());
        }
        
        // Verifier si le processus s'est termine correctement
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Les fichiers generes se trouvent dans le repertoire suivant : \n" + outputPath);
            } else {
            	throw new PythonExecutionException("Erreur lors de l'execution du script Python. Code de sortie : " + exitCode);
            }
        } catch (InterruptedException e) {
            throw new PythonExecutionException("Le processus a ete interrompu : " + e.getMessage());
        }
    }
    
    // Obtenir le chemin d'un fichier dans le classpath
    private static String getResourcePath(String relativePath) throws IOException {
        try {
            // Utilise getResource pour obtenir l'URL, puis Paths pour eviter les problemes de format
            if (PythonExecutor.class.getClassLoader().getResource(relativePath) == null) {
                throw new IOException("Ressource non trouvee : " + relativePath);
            }
            
            return Paths.get(PythonExecutor.class.getClassLoader().getResource(relativePath).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new IOException("Erreur lors de la conversion du chemin de la ressource : " + relativePath, e);
        }
    }

    // Charger les proprietes depuis un fichier
    private static Properties loadProperties(String filename) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = PythonExecutor.class.getClassLoader().getResourceAsStream("pythonScript/" + filename)) {
            if (input == null) {
                throw new IOException("Fichier de configuration '" + filename + "' non trouve dans le classpath.");
            }
            properties.load(input);
        }
        return properties;
    }

    // Afficher une pop-up d'erreur
    private void showErrorPopup(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Exceptions personnalisees
    private static class ConfigurationException extends Exception {
        public ConfigurationException(String message) {
            super(message);
        }
    }

    private static class PythonExecutionException extends Exception {
        public PythonExecutionException(String message) {
            super(message);
        }
    }
}
