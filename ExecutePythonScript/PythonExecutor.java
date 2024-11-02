import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.List;
import java.util.Arrays;

public class PythonExecutor extends Application {

    // Constantes
    private static final String PYTHON_COMMAND = "python3";
    private static final String REQUIRED_PYTHON_VERSION = "Python 3.11";
    private static final String SCRIPT_PATH_PROPERTY = "python.script.path";
    private static final String ARGUMENTS_PROPERTY = "python.script.arguments";
    private static final List<String> REQUIRED_LIBRARIES = Arrays.asList("pandas", "matplotlib");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger les propriétés depuis un fichier de configuration
            Properties properties = loadProperties("config.properties");

            // Vérifier la version de Python
            if (!isCorrectPythonVersion()) {
                throw new ConfigurationException("Version de Python non compatible. Python 3.11 requis.");
            }

            // Vérifier les librairies installées
            checkPythonLibraries();

            // Récupérer le chemin du script et les arguments
            String scriptPath = properties.getProperty(SCRIPT_PATH_PROPERTY);
            String arguments = properties.getProperty(ARGUMENTS_PROPERTY);

            // Vérifier que le chemin et les arguments sont valides
            if (scriptPath == null || scriptPath.isEmpty()) {
                throw new ConfigurationException("Chemin du script ou arguments non définis dans le fichier de configuration.");
            }

            // Exécuter le script Python
            executePythonScript(scriptPath, arguments);

        } catch (ConfigurationException | PythonExecutionException e) {
            showErrorPopup(e.getMessage());
        } catch (IOException e) {
            showErrorPopup("Erreur de chargement du fichier de configuration ou d'exécution du script.");
        }
    }

    // Vérifier la version de Python
    private static boolean isCorrectPythonVersion() throws IOException {
        ProcessBuilder versionCheck = new ProcessBuilder(PYTHON_COMMAND, "--version");
        Process versionProcess = versionCheck.start();
        try (BufferedReader versionReader = new BufferedReader(new InputStreamReader(versionProcess.getInputStream()))) {
            String versionOutput = versionReader.readLine();
            return versionOutput != null && versionOutput.contains(REQUIRED_PYTHON_VERSION);
        }
    }

    // Vérifier les librairies Python requises
    private static void checkPythonLibraries() throws IOException, ConfigurationException {
        ProcessBuilder listLibraries = new ProcessBuilder(PYTHON_COMMAND, "-m", "pip", "list");
        Process listProcess = listLibraries.start();
        
        System.out.println("Librairies Python installées :");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(listProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        for (String library : REQUIRED_LIBRARIES) {
            ProcessBuilder checkLibrary = new ProcessBuilder(PYTHON_COMMAND, "-m", "pip", "show", library);
            Process checkProcess = checkLibrary.start();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(checkProcess.getInputStream()))) {
                if (reader.lines().noneMatch(l -> l.contains("Name: " + library))) {
                    throw new ConfigurationException("La librairie Python requise '" + library + "' n'est pas installée.");
                }
            }
        }
    }

    // Exécuter le script Python avec les arguments
    private static void executePythonScript(String scriptPath, String arguments) throws PythonExecutionException, IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(PYTHON_COMMAND, scriptPath, arguments.split(" "));
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new PythonExecutionException("Erreur lors de l'exécution du script Python : " + e.getMessage());
        }
    }

    // Charger les propriétés depuis un fichier
    private static Properties loadProperties(String filename) throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(filename)) {
            properties.load(reader);
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

    // Exceptions personnalisées
    static class ConfigurationException extends Exception {
        public ConfigurationException(String message) {
            super(message);
        }
    }

    static class PythonExecutionException extends Exception {
        public PythonExecutionException(String message) {
            super(message);
        }
    }
}
