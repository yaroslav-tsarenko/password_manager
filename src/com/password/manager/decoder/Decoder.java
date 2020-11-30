package com.password.manager.decoder;

import java.util.Base64;

public class Decoder {

    public String decodePassword(String src, String SECRET_KEY) {
        byte[] bytes = src.getBytes();
        byte[] decoded = Base64.getDecoder().decode(bytes);
        String decodedString = new String(decoded);
        String convertedToOriginal = decodedString.substring(0, decodedString.length() - SECRET_KEY.length());
        return convertedToOriginal;
    }

    public String decodePassword2(String src, String SECRET_KEY) {
        byte[] srcBytes = src.getBytes();
        byte[] keyBytes = SECRET_KEY.getBytes();

        for (int i = 0; i < srcBytes.length; i++) {
            for (int j = i; j < keyBytes.length && (j == i); j++) {
                byte temp = srcBytes[i];
                srcBytes[i] = (byte) (temp - keyBytes[j] / 10);
            }
        }
        return new String(srcBytes);
    }
}
