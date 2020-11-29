package com.company;

import java.util.Scanner;

public class PasswordManagerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        byte[] inputBytes = input.getBytes();
        String SECRET_KEY = "sskudgawhkjbbkxuyshlxbqwxbwilucblidubclsabxclqbcliwbsce";
        byte[] keyBytes = SECRET_KEY.getBytes();

        for (int i = 0; i < inputBytes.length; i++) {
            byte bytes = (byte) (inputBytes[i] + keyBytes[i]);
            if (bytes<0){
                inputBytes[i] = (byte) (bytes - bytes * 2);
            } else {
                inputBytes[i] = bytes;
            }

        }
        System.out.println(new String(inputBytes));


    }
}
