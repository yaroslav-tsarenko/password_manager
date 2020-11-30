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
}
