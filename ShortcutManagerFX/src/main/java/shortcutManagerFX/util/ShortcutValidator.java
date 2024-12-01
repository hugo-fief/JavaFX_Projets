package shortcutManagerFX.util;

public class ShortcutValidator {

    public static boolean isValidShortcut(String shortcut) {
        if (shortcut == null || shortcut.trim().isEmpty()) {
            return false;
        }
        String formattedShortcut = shortcut.replace("Maj", "Shift");
        String regex = "^(Ctrl|Alt|Shift)(\\+(Ctrl|Alt|Shift))*\\+[A-Za-z0-9]$";
        return formattedShortcut.matches(regex);
    }
}
