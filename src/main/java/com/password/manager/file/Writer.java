package com.password.manager.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Writer {

    public static void writeOutputToFile(String output, String path) throws IOException {
            FileWriter writer = new FileWriter(path);
            writer.write(output);
            writer.flush();
            writer.close();
    }

    public static void writeOutputToFileSoftMode(String output, String path) throws IOException {
        String data = "";
        if (Files.isRegularFile(Paths.get(path))) data = Reader.readInputFromFile(path);
            FileWriter writer = new FileWriter(path);
            writer.write(data.concat(output));
            writer.flush();
            writer.close();
    }

    public static String outputEditor(String src, int lineLength) {
        char[] arr = src.toCharArray();
        int length = arr.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineLength; i++) {
            if (length != 0) {
                builder.append(arr[i]);
                if (i == lineLength - 1) {
                    i = 0;
                    builder.append("\n");
                }
            } else {
                break;
            }
            length--;
        }
        return builder.toString();
    }
}
