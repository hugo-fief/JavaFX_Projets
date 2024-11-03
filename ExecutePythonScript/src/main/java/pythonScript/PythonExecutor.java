package pythonScript;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.Properties;
import java.util.Arrays;
import java.util.List;

public class PythonExecutor extends Application {

    // Chemin de l'interpreteur Python, lu depuis le fichier de configuration
    private static String pythonCommand;

    // Constantes pour les cles des proprietes et des versions requises
    private static final String REQUIRED_PYTHON_VERSION = "Python 3.11";
    private static final String PYTHON_COMMAND_KEY = "python.command";
    private static final String SCRIPT_PATH_KEY = "python.script.path";
    private static final String EXCEL_PATH_KEY = "python.script.excel";
    private static final String OUTPUT_PATH_KEY = "python.script.output";
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
            pythonCommand = properties.getProperty(PYTHON_COMMAND_KEY, "python");

            // Charger les chemins des ressources
            String scriptPath = getResourcePath("pythonScript/" + properties.getProperty(SCRIPT_PATH_KEY));
            String excelPath = getResourcePath("pythonScript/" + properties.getProperty(EXCEL_PATH_KEY));
            String outputPath = getResourcePath("pythonScript/" + properties.getProperty(OUTPUT_PATH_KEY));

            // Verifier l'environnement Python
            checkPythonEnvironment();

            // Executer le script Python avec les arguments fournis
            executePythonScript(scriptPath, excelPath, outputPath);

        } catch (ConfigurationException | PythonExecutionException e) {
            showErrorPopup(e.getMessage());
        } catch (IOException e) {
            showErrorPopup("Erreur de chargement ou d'execution : " + e.getMessage());
        }
    }

    // Verifier l'environnement Python : version et librairies
    private static void checkPythonEnvironment() throws ConfigurationException, IOException {
        if (!isCorrectPythonVersion()) {
            throw new ConfigurationException("Version de Python non compatible. Python 3.11 requis.");
        }
        checkPythonLibraries();
    }

    // Verifier la version de Python
    private static boolean isCorrectPythonVersion() throws IOException {
        String versionOutput = executeCommandAndGetOutput(List.of(pythonCommand, "--version"));
        return versionOutput != null && versionOutput.contains(REQUIRED_PYTHON_VERSION);
    }

    // Verifier les librairies Python requises
    private static void checkPythonLibraries() throws IOException, ConfigurationException {
        for (String library : REQUIRED_LIBRARIES) {
            String libraryInfo = executeCommandAndGetOutput(List.of("pip", "show", library));
            if (libraryInfo == null || !libraryInfo.contains("Name: " + library)) {
                throw new ConfigurationException("La librairie Python requise '" + library + "' n'est pas installee.");
            }
        }
    }

    // Methode utilitaire pour executer une commande et obtenir la sortie
    private static String executeCommandAndGetOutput(List<String> command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.readLine(); // Retourne la premiere ligne de sortie
        }
    }

    // Executer le script Python avec les arguments
    private static void executePythonScript(String scriptPath, String excelPath, String outputPath) throws PythonExecutionException, IOException {
        List<String> command = List.of(pythonCommand, scriptPath, excelPath, outputPath);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // Lire les sorties standard et erreur du script
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            System.out.println("Execution du script Python en cours...");

            reader.lines().forEach(System.out::println); // Afficher la sortie standard
            errorReader.lines().forEach(System.err::println); // Afficher la sortie d'erreur en rouge

        } catch (IOException e) {
            throw new PythonExecutionException("Erreur lors de l'execution du script Python : " + e.getMessage());
        }

        // Verifier le code de sortie du processus
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Les fichiers generes se trouvent dans le repertoire de sortie : \n" + outputPath);
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
    	private static final long serialVersionUID = 1L;
    	
        public ConfigurationException(String message) {
            super(message);
        }
    }

    private static class PythonExecutionException extends Exception {
    	private static final long serialVersionUID = 1L;
    	
        public PythonExecutionException(String message) {
            super(message);
        }
    }
}
