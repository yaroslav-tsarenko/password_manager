package com.password.manager.window;

import com.password.manager.cache.MemoryCache;
import com.password.manager.dto.CredentialsDto;
import com.password.manager.service.CredentialsServiceImpl;
import com.password.manager.service.exception.ArgumentRequiredException;
import com.password.manager.util.ResponseFactory;
import com.password.manager.util.StringFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BaseWindow {

    public JPanel passwordManager;
    public JTextField serviceNameField;
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton openStorage;
    public JButton saveCredentials;
    public JButton encryptCredentials;
    public JButton clearStatusDisplay;
    public JTextArea statusDisplay;

    public BaseWindow() {
        CredentialsServiceImpl service = new CredentialsServiceImpl();
        saveCredentials.addActionListener(event -> {
            int userChoice = JOptionPane.showConfirmDialog(null, "Please confirm to save");
            if (userChoice == 0) {
                try {
                    String response = service.saveCredentials(CredentialsDto.build(Map.of("service_name", serviceNameField.getText(),
                            "user_name", usernameField.getText(), "password", passwordField.getText())));
                    statusDisplay.setText(refreshStatus(statusDisplay.getText(), response));
                } catch (IOException | ArgumentRequiredException e) {
                    e.printStackTrace();
                }
            } else {
                serviceNameField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        openStorage.addActionListener(event -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                String filePath = MemoryCache.getProperty("storage_file_path");
                File file = new File(filePath);
                desktop.edit(file);
                statusDisplay.setText(refreshStatus(
                        statusDisplay.getText(), ResponseFactory.createResponse(StringFactory
                                .buildString("file:", file.getName(), "opened"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        encryptCredentials.addActionListener(event -> {
            String userChoice = JOptionPane.showInputDialog("enter password");
            if (userChoice.equals("12345")) {
                String serviceName = JOptionPane.showInputDialog("enter service name");
                statusDisplay.setText(service.getCredentialsByServiceName(serviceName));
            } else {
                statusDisplay.setText(refreshStatus(statusDisplay.getText(), ResponseFactory.createResponse("access denied!")));
            }
        });

        clearStatusDisplay.addActionListener(event -> {
            statusDisplay.setText("");
        });

        statusDisplay.setText(ResponseFactory.createResponse("Password Manager loaded"));
    }

    public String refreshStatus(String current, String newStatus) {
        if (current.length() >= 400) return newStatus;
        return current + "\n" + newStatus;

    }
}
