import java.util.Scanner;
import java.util.Random;
import java.util.regex.*;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Password Generator ===");

        System.out.print("Enter password length (8-64): ");
        int length = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Include uppercase letters? (Y/N): ");
        boolean useUppercase = scanner.nextLine().equalsIgnoreCase("Y");

        System.out.print("Include numbers? (Y/N): ");
        boolean useNumbers = scanner.nextLine().equalsIgnoreCase("Y");

        System.out.print("Include special characters? (Y/N): ");
        boolean useSpecials = scanner.nextLine().equalsIgnoreCase("Y");

        // Generate password
        String password = generatePassword(length, useUppercase, useNumbers, useSpecials);
        System.out.println("\nGenerated Password: " + password);

        // Validate strength using regex
        if (isPasswordStrong(password, useUppercase, useNumbers, useSpecials)) {
            System.out.println("Password is strong!");
        } else {
            System.out.println("Password does not meet requirements!");
        }
    }

    private static String generatePassword(int length, boolean useUppercase, boolean useNumbers, boolean useSpecials) {
        StringBuilder allowedChars = new StringBuilder(LOWERCASE);
        if (useUppercase) allowedChars.append(UPPERCASE);
        if (useNumbers) allowedChars.append(NUMBERS);
        if (useSpecials) allowedChars.append(SPECIALS);

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }

        return password.toString();
    }

    private static boolean isPasswordStrong(String password, boolean useUppercase, boolean useNumbers, boolean useSpecials) {
        String regex = "^(?=.*[a-z])"; // At least one lowercase
        if (useUppercase) regex += "(?=.*[A-Z])"; // At least one uppercase
        if (useNumbers) regex += "(?=.*\\d)"; // At least one digit
        if (useSpecials) regex += "(?=.*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?])"; // At least one special char
        regex += ".{" + password.length() + ",}$"; // Minimum length

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
