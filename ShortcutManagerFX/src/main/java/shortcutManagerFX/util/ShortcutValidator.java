package shortcutManagerFX.util;

public class ShortcutValidator {

    public static boolean isValidShortcut(String shortcut) {
        if (shortcut == null || shortcut.trim().isEmpty()) {
            return false;
        }

        // Remplacer "Maj" par "Shift" dans la chaîne pour rendre le raccourci compatible
        String formattedShortcut = shortcut.replace("Maj", "Shift");

        // Expression reguliere pour des combinaisons telles que Ctrl+Alt+Shift+H
        String regex = "^(Ctrl|Alt|Shift)(\\+(Ctrl|Alt|Shift))*\\+[A-Za-z0-9]$";
        return formattedShortcut.matches(regex);
    }
}
