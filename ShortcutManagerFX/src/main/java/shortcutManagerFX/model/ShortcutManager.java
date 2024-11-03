package shortcutManagerFX.model;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import shortcutManagerFX.util.ShortcutValidator;

public class ShortcutManager {
    // Chemin générique pour le fichier JSON dans le disque C:
    private static final String CONFIG_DIRECTORY = "C:\\TrapilAnalyse_Preferences";
    private static final String CONFIG_PATH = CONFIG_DIRECTORY + "\\shortcuts.json";

    private Map<String, String> shortcuts = new HashMap<>();

    public ShortcutManager() {
        loadShortcuts();
    }

    public String getShortcut(String action) {
        return shortcuts.getOrDefault(action, "");
    }
    
    public void setShortcut(String action, String shortcut) {
        if (!ShortcutValidator.isValidShortcut(shortcut)) {
            throw new IllegalArgumentException("Le raccourci n'est pas valide : " + shortcut);
        }
        shortcuts.put(action, shortcut);
    }

    public void saveShortcuts() {
        try {
            // Crée le dossier si nécessaire
            File directory = new File(CONFIG_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Sauvegarde les raccourcis dans le fichier JSON
            try (FileWriter file = new FileWriter(CONFIG_PATH)) {
                JSONObject json = new JSONObject();
                json.put("shortcuts", shortcuts);
                file.write(json.toString(4));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des raccourcis : " + e.getMessage());
        }
    }

    private void loadShortcuts() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JSONObject json = new JSONObject(new JSONTokener(reader));
                JSONObject shortcutJson = json.getJSONObject("shortcuts");
                shortcutJson.keySet().forEach(key -> shortcuts.put(key, shortcutJson.getString(key)));
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des raccourcis : " + e.getMessage());
            }
        } else {
            createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        // Raccourcis par défaut
        shortcuts.put("save", "Ctrl+S");
        shortcuts.put("open", "Ctrl+O");
        shortcuts.put("exit", "Ctrl+Q");
        shortcuts.put("config", "Ctrl+H");
        saveShortcuts();
    }
}
