package be.tftic.mobile.javaquarium.utils;

import java.security.SecureRandom;

public final class StringGeneratorUtils {

    public static char getLowerChar() {
        SecureRandom rng = new SecureRandom();
        int val = rng.nextInt('a', 'z' + 1);

        return (char) val;
    }

    public static char getUpperChar() {
        SecureRandom rng = new SecureRandom();
        int val = rng.nextInt('A', 'Z' + 1);

        return (char) val;
    }

    public static String getString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getUpperChar());

        int longeur = (new SecureRandom()).nextInt(4, 7);
        for (int i = 0; i < longeur; i++) {
            sb.append(getLowerChar());
        }

        return sb.toString();
    }
}
