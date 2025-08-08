package util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Validate if a string is not null and not empty
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Validate email format using regex
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    // Validate Sri Lankan phone numbers (basic pattern)
    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^(0\\d{9})$";
        return phone != null && Pattern.matches(phoneRegex, phone);
    }

    // Validate numeric string
    public static boolean isNumeric(String input) {
        return input != null && input.matches("\\d+");
    }

    // Validate name (letters and spaces only)
    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z\\s]+$");
    }
}
