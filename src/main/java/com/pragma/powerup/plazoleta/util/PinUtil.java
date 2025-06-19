package com.pragma.powerup.plazoleta.util;

import java.util.Random;

public class PinUtil {

    private static final Random random = new Random();

    private PinUtil() {
    }

    public static String generarPinAleatorio() {
        int pin = new Random().nextInt(9000) + 1000;
        return String.valueOf(pin);
    }
}