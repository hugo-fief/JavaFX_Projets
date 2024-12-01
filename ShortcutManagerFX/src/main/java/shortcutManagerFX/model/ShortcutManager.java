package shortcutManagerFX.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestionnaire de raccourcis.
 * Cette classe gère la configuration des raccourcis utilisateur, leur sauvegarde dans un fichier,
 * et leur récupération au démarrage de l'application.
 */
public class ShortcutManager {

    // Chemin du fichier de configuration des raccourcis (dans le répertoire utilisateur)
    private static final String CONFIG_PATH = System.getProperty("user.home") + "\\Trapil_Analyse_Shortcuts.txt";

    // Stockage des raccourcis sous forme de paires clé-valeur (action, raccourci)
    private Map<String, String> shortcuts = new LinkedHashMap<>();

    /**
     * Constructeur par défaut.
     * Charge les raccourcis depuis le fichier de configuration ou crée une configuration par défaut si le fichier n'existe pas.
     */
    public ShortcutManager() {
        loadShortcuts();
    }

    /**
     * Récupère le raccourci associé à une action donnée.
     *
     * @param action Le nom de l'action.
     * @return Le raccourci associé, ou une chaîne vide si aucun raccourci n'est défini pour cette action.
     */
    public String getShortcut(String action) {
        return shortcuts.getOrDefault(action, ""); // Retourne une chaîne vide si aucune clé n'est trouvée
    }

    /**
     * Définit ou met à jour un raccourci pour une action donnée.
     *
     * @param action   Le nom de l'action.
     * @param shortcut Le raccourci à associer.
     */
    public void setShortcut(String action, String shortcut) {
        shortcuts.put(action, shortcut); // Met à jour ou ajoute une nouvelle paire clé-valeur
    }

    /**
     * Sauvegarde les raccourcis dans un fichier de configuration.
     * Chaque raccourci est sauvegardé sous la forme "action=raccourci".
     */
    public void saveShortcuts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
            for (Map.Entry<String, String> entry : shortcuts.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue()); // Écrit la paire clé-valeur
                writer.newLine(); // Passe à la ligne suivante
            }
        } catch (IOException e) {
            // Gère les erreurs d'écriture dans le fichier
            e.printStackTrace();
        }
    }

    /**
     * Charge les raccourcis depuis le fichier de configuration.
     * Si le fichier n'existe pas, une configuration par défaut est créée.
     */
    private void loadShortcuts() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            // Le fichier existe, lire son contenu
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2); // Sépare la ligne en action et raccourci
                    if (parts.length == 2) { // Vérifie que la ligne est bien formatée
                        shortcuts.put(parts[0], parts[1]); // Ajoute la paire clé-valeur au Map
                    }
                }
            } catch (IOException e) {
                // Gère les erreurs de lecture
                e.printStackTrace();
            }
        } else {
            // Le fichier n'existe pas, créer une configuration par défaut
            createDefaultConfig();
        }
    }

    /**
     * Crée une configuration par défaut si aucun fichier de configuration n'existe.
     * Les raccourcis par défaut sont définis et sauvegardés dans le fichier.
     */
    private void createDefaultConfig() {
        shortcuts.put("save", "Ctrl+S"); // Exemple : raccourci pour sauvegarder
        shortcuts.put("open", "Ctrl+O"); // Exemple : raccourci pour ouvrir
        shortcuts.put("config", "Ctrl+H"); // Exemple : raccourci pour ouvrir la configuration
        saveShortcuts(); // Sauvegarde immédiatement la configuration par défaut dans le fichier
    }

    /**
     * Récupère tous les raccourcis sous forme d'une copie du Map.
     * Cette méthode est utilisée pour fournir une vue non modifiable des raccourcis.
     *
     * @return Une copie des raccourcis enregistrés.
     */
    public Map<String, String> getAllShortcuts() {
        return new LinkedHashMap<>(shortcuts); // Retourne une copie pour protéger l'original
    }
    
    /**
     * Vérifie si un raccourci est déjà utilisé.
     *
     * @param shortcut Le raccourci à vérifier.
     * @param action   L'action pour laquelle on vérifie (l'action actuelle est exclue).
     * @return true si le raccourci est déjà utilisé, false sinon.
     */
    public boolean isShortcutDuplicate(String shortcut, String action) {
        return shortcuts.values().stream()
                .anyMatch(existingShortcut -> existingShortcut.equals(shortcut) && !shortcuts.get(action).equals(shortcut));
    }
}
