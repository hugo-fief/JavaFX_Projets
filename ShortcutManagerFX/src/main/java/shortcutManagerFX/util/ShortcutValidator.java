package shortcutManagerFX.util;

public class ShortcutValidator {

    /**
     * Vérifie si un raccourci est valide selon les règles spécifiées.
     * Règles :
     * 1. Le raccourci doit commencer par "Ctrl".
     * 2. Le raccourci doit contenir 2 à 3 niveaux (exemple : Ctrl+A, Ctrl+Maj+A).
     *
     * @param shortcut Le raccourci à valider.
     * @return true si le raccourci est valide, false sinon.
     */
    public static boolean isValidShortcut(String shortcut) {
        if (shortcut == null || shortcut.trim().isEmpty()) {
            return false; // Raccourci vide ou null invalide
        }

        // Règle : doit commencer par "Ctrl" et suivre un format valide avec 2 à 3 niveaux
        String regex = "^Ctrl(\\+(Alt|Maj|[A-Za-z])){1,2}$";
        return shortcut.matches(regex);
    }

    /**
     * Message d'erreur à afficher si le raccourci est invalide.
     * Le message inclut un exemple pour guider l'utilisateur.
     *
     * @return Le message d'erreur.
     */
    public static String getInvalidShortcutMessage() {
        return "Raccourci invalide ! Un raccourci valide doit contenir 2 à 3 niveaux (exemples : 'Ctrl+A', 'Ctrl+Maj+A').";
    }
}
