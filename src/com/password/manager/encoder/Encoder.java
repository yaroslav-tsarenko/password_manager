package com.password.manager.encoder;

import java.util.Base64;

public class Encoder {

    public String encodePassword(String src, String SECRET_KEY) {
        byte[] bytes = src.concat(SECRET_KEY).getBytes();

       String encoded = Base64.getEncoder().withoutPadding().encodeToString(bytes);

        return encoded;
    }
}
