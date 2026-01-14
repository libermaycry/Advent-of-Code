package advent_of_code.utils;

public class Strings {

    public static final String SEPARATOR = "§";

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String of(Object ... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object.toString()).append(SEPARATOR);
        }
        return sb.substring(0, sb.toString().length() - 1);
    }

}
