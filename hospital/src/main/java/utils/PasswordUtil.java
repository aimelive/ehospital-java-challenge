package utils;

public class PasswordUtil {
    public static boolean isValidPassword(String password, int minLength, int maxLength) {
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }
        return true;
    }
}
