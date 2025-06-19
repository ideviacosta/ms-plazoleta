package com.pragma.powerup.plazoleta.util;

import java.util.Random;

public class PinUtil {

    private static final Random random = new Random();

    private PinUtil() {
    }

    public static int generarPinAleatorio() {
        return random.nextInt(9000) + 1000;
    }
}