package com.password.manager.ts_encrypt;

import com.password.manager.cache.MemoryCache;
import com.password.manager.ts_encrypt.exception.UnsupportedSymbolException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.password.manager.ts_encrypt.Const.GLOBAL_SYMBOLS;
import static com.password.manager.ts_encrypt.Const.SYMBOLS;

public class TSEncrypt {

    public static String doEncryption(String src) {
        int[] code = srcToCode(src.toCharArray());
        for (int i = 0; i < code.length; i++) {
            int temp = code[i];
            code[i] = roundUp(modifyOne(temp));
        }
        return codeToText(code);
    }

    public static String doDecryption(String src) {
        String[] symbols = src.split("\\$");
        int[] code = finalSrcToCode(symbols);
        for (int i = 0; i < code.length; i++) {
            int temp = code[i];
            code[i] = roundUp(modifyTwo(temp));
        }
        return finalCodeToText(code);
    }

    private static int[] srcToCode(char[] source) {
        int[] code = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            int exists = exists(source[i], SYMBOLS);
            if (exists >= 0) {
                code[i] = exists;
            } else {
                throw new UnsupportedSymbolException("symbol: " + source[i] + " unsupported by app");
            }
        }
        return code;
    }

    private static int[] finalSrcToCode(String[] source) {
        if (MemoryCache.notEmpty()) {
            GLOBAL_SYMBOLS = MemoryCache.getProperty("secret_key").split("\\$");
        }
        int[] code = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            int exists = exists(source[i], GLOBAL_SYMBOLS);
            if (exists >= 0) {
                code[i] = exists;
            } else {
                throw new UnsupportedSymbolException("symbol: " + source[i] + " unsupported by app");
            }
        }
        return code;
    }

    private static String codeToText(int[] code) {
        if (MemoryCache.notEmpty()) {
            GLOBAL_SYMBOLS = MemoryCache.getProperty("secret_key").split("\\$");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length; i++) {
            builder.append(GLOBAL_SYMBOLS[code[i]]);
            builder.append("$");
        }
        return builder.toString();
    }

    private static String finalCodeToText(int[] code) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length; i++) {
            builder.append(SYMBOLS[code[i]]);
        }
        return builder.toString();
    }

    private static int exists(char value, char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (value == arr[i]) {
                return i;
            }
        }
        return -1;
    }

    private static int exists(String value, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (value.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }

    private static double modifyOne(int value) {
        return (value + (value * 0.7));
    }

    private static double modifyTwo(int value) {
        return (value / 1.7);
    }

    private static int roundUp(double value) {
        BigDecimal decimal = new BigDecimal(value);
        BigDecimal scale = decimal.setScale(0, RoundingMode.HALF_UP);
        return scale.intValueExact();
    }

}
