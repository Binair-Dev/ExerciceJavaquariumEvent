package be.tftic.mobile.javaquarium.enums;

import java.security.SecureRandom;

public enum Sexe {
    MALE,
    FEMELLE;

    public static Sexe getRandomSexe() {
        SecureRandom rng = new SecureRandom();
        return rng.nextBoolean() ? FEMELLE : MALE;
    }
}
