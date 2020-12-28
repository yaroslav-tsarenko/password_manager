package com.password.manager.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringFactory {
    public static String buildString(String... strings) {
        return Arrays.stream(strings)
                .map(string -> " " + string + " ")
                .collect(Collectors.joining());
    }
}
