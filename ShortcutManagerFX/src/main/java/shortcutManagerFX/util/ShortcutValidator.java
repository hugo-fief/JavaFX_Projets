package shortcutManagerFX.util;

public class ShortcutValidator {

    public static boolean isValidShortcut(String shortcut) {
        // Vérifie si le raccourci est nul ou vide
        if (shortcut == null || shortcut.trim().isEmpty()) {
            return false;
        }

        // Vérifie si le format du raccourci est valide (par exemple : Ctrl+S ou Alt+Shift+1)
        String regex = "^(Ctrl|Alt|Shift|Cmd)\\+[A-Za-z0-9]+(\\+(Ctrl|Alt|Shift|Cmd))?$";
        return shortcut.matches(regex);
    }
}

