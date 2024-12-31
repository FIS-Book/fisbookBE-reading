package us.es.reading.utils;

import java.util.regex.Pattern;

public class ISBNValidator {

    // Patrón para validar ISBN-10 y ISBN-13
    private static final Pattern ISBN_10_PATTERN = Pattern.compile("\\d{9}[\\dX]");
    private static final Pattern ISBN_13_PATTERN = Pattern.compile("\\d{13}");

    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }
        // Limpia guiones y espacios
        var normalizedIsbn = isbn.replace("-", "").replace(" ", "");
        return switch (normalizedIsbn.length()) {
            case 10 -> isValidISBN10(normalizedIsbn);
            case 13 -> isValidISBN13(normalizedIsbn);
            default -> false; // ISBN con longitud inválida
        };
    }

    private static boolean isValidISBN10(String isbn) {
        if (!ISBN_10_PATTERN.matcher(isbn).matches()) {
            return false; // Verifica formato
        }
        var sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(isbn.charAt(i)) * (i + 1);
        }
        // Verifica el último carácter (puede ser 'X')
        var lastChar = isbn.charAt(9);
        sum += (lastChar == 'X') ? 10 * 10 : Character.getNumericValue(lastChar) * 10;
        return sum % 11 == 0;
    }

    private static boolean isValidISBN13(String isbn) {
        if (!ISBN_13_PATTERN.matcher(isbn).matches()) {
            return false; // Verifica formato
        }
        var sum = 0;
        for (int i = 0; i < 13; i++) {
            var digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3; // Alterna factores 1 y 3
        }
        return sum % 10 == 0;
    }

    public static void main(String[] args) {
        var isbn10 = "0-306-40615-2";
        var isbn13 = "978-3-16-148410-0";
        var invalidIsbn = "1234567890";

        System.out.println(isbn10 + " is valid: " + isValidISBN(isbn10)); // true
        System.out.println(isbn13 + " is valid: " + isValidISBN(isbn13)); // true
        System.out.println(invalidIsbn + " is valid: " + isValidISBN(invalidIsbn)); // false
    }
}
