package com.password.manager.encoder;

import java.util.Base64;

public class Encoder {

    public String encodePassword(String src, String SECRET_KEY) {
        byte[] bytes = src.concat(SECRET_KEY).getBytes();

        String encoded = Base64.getEncoder().withoutPadding().encodeToString(bytes);

        return encoded;
    }

    public String encodePassword2(String src, String SECRET_KEY) {
        byte[] srcBytes = src.getBytes();
        byte[] keyBytes = SECRET_KEY.getBytes();

        for (int i = 0; i < srcBytes.length; i++) {
            for (int j = i; j < keyBytes.length && (j == i); j++) {
                byte temp = srcBytes[i];
                srcBytes[i] = (byte) (keyBytes[j] / 10 + temp);
            }
        }
        return new String(srcBytes);
    }
}
