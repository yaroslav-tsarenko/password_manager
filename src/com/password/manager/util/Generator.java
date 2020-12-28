package com.password.manager.util;

import java.util.Random;

import static com.password.manager.ts_encrypt.Const.GLOBAL_SYMBOLS;

public class Generator {

//TODO: create this method
//    public String getPassword(int length)

    public String getSecretKey(int length) {
        int[] token = generateRandomCode(length);
        return codeToString(token);
    }

    private int[] generateRandomCode(int length) {
        int[] token = new int[length];
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int newInt = 0;
            while (duplicateExists(token, newInt)) {
                newInt = random.nextInt(GLOBAL_SYMBOLS.length);
            }
            token[i] = newInt;
        }
        return token;
    }

    private boolean duplicateExists(int[] arr, int value) {
        for (int item : arr) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }

    private String codeToString(int[] token) {
        StringBuilder builder = new StringBuilder();
        for (int value : token) {
            builder.append(GLOBAL_SYMBOLS[value]);
            builder.append("$");
        }
        return builder.toString();
    }
}