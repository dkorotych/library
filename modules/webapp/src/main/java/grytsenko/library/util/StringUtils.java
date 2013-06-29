package grytsenko.library.util;

/**
 * Utilities for strings.
 */
public class StringUtils {

    /**
     * Determines that string is <code>null</code> or empty.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private StringUtils() {
    }

}
