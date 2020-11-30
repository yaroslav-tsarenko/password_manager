package com.password.manager;

import com.password.manager.window.BaseWindow;

import javax.swing.JFrame;

public class PasswordManagerApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Password Manager");
        frame.setContentPane(new BaseWindow().passwordManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
