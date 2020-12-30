package com.password.manager.file;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reader {
    public static String readInputFromFile(String path) throws IOException {
        String text = "";
        FileReader fileReader = new FileReader(path);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()) {
            text = text.concat(scanner.nextLine() + "\n");
        }
        fileReader.close();
        return text;
    }
}
