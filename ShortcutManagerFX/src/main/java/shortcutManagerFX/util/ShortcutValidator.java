package shortcutManagerFX.util;

/**
 * Classe utilitaire pour valider les raccourcis clavier.
 * Cette classe fournit des méthodes pour vérifier si un raccourci est valide
 * selon des règles spécifiques et pour générer des messages d'erreur pertinents.
 */
public class ShortcutValidator {

    /**
     * Vérifie si un raccourci est valide selon les règles spécifiées :
     * <ul>
     *   <li>Le raccourci doit commencer par "Ctrl".</li>
     *   <li>Le raccourci doit contenir entre 2 et 3 niveaux, séparés par "+".</li>
     *   <li>Les niveaux suivants doivent être "Alt", "Maj" ou une lettre (ex. : A, B, Z).</li>
     * </ul>
     *
     * Exemples de raccourcis valides :
     * <ul>
     *   <li>Ctrl+A</li>
     *   <li>Ctrl+Maj+A</li>
     *   <li>Ctrl+Alt+O</li>
     * </ul>
     *
     * @param shortcut Le raccourci à valider (sous forme de chaîne de caractères).
     * @return {@code true} si le raccourci est valide, {@code false} sinon.
     */
    public static boolean isValidShortcut(String shortcut) {
        if (shortcut == null || shortcut.trim().isEmpty()) {
            // Si le raccourci est vide ou null, il est considéré comme invalide
            return false;
        }

        // Expression régulière pour valider les raccourcis :
        // - Doit commencer par "Ctrl".
        // - Doit être suivi de 1 à 2 segments, séparés par "+".
        // - Chaque segment doit être "Alt", "Maj", ou une lettre (A-Z).
        String regex = "^Ctrl(\\+(Alt|Maj|[A-Za-z])){1,2}$";

        // Retourne true si le raccourci correspond au format spécifié par l'expression régulière
        return shortcut.matches(regex);
    }

    /**
     * Génère un message d'erreur expliquant pourquoi un raccourci est invalide.
     * Ce message est destiné à l'utilisateur et inclut des exemples de raccourcis valides.
     *
     * @return Une chaîne de caractères contenant le message d'erreur.
     */
    public static String getInvalidShortcutMessage() {
        return "Raccourci invalide ! Un raccourci valide doit avoir ce format : \n"
             + "'Ctrl+A', 'Ctrl+Maj+A', 'Ctrl+Alt+A'.";
    }
}
