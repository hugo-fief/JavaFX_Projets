package shortcutManagerFX.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShortcutManager {
    private static final String CONFIG_PATH = System.getProperty("user.home") + "\\Trapil_Analyse_Shortcuts.txt";

    private Map<String, String> shortcuts = new LinkedHashMap<>();

    public ShortcutManager() {
        loadShortcuts();
    }

    public String getShortcut(String action) {
        return shortcuts.getOrDefault(action, "");
    }

    public void setShortcut(String action, String shortcut) {
        shortcuts.put(action, shortcut);
    }

    public void saveShortcuts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
            for (Map.Entry<String, String> entry : shortcuts.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadShortcuts() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        shortcuts.put(parts[0], parts[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createDefaultConfig();
        }
    }

    private void createDefaultConfig() {
        shortcuts.put("action1", "Ctrl+S");
        shortcuts.put("action2", "Ctrl+O");
        shortcuts.put("config", "Ctrl+H");
        saveShortcuts();
    }

    public Map<String, String> getAllShortcuts() {
        return new LinkedHashMap<>(shortcuts);
    }
}
