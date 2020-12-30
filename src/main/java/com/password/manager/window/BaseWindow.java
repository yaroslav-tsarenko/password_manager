package com.password.manager.window;

import com.password.manager.cache.MemoryCache;
import com.password.manager.config.ConfigLoader;
import com.password.manager.dto.CredentialsDto;
import com.password.manager.service.CredentialsServiceImpl;
import com.password.manager.service.exception.ArgumentRequiredException;
import com.password.manager.ts_encrypt.TSEncrypt;
import com.password.manager.util.ResponseFactory;
import com.password.manager.util.StringFactory;
import org.apache.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.password.manager.util.Constants.REPO_FILE_NAME;

public class BaseWindow {

    private static final Logger log = Logger.getLogger(BaseWindow.class);

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
        if (!MemoryCache.hasProperty("storage_file_path")) {
            String path = MemoryCache.getProperty("resource_dir_path") + REPO_FILE_NAME;
            try {
                Files.createFile(Path.of(path));
            } catch (IOException e) {
                log.error(e);
            }
            MemoryCache.setProperty("storage_file_path", path);
        }
        CredentialsServiceImpl service = new CredentialsServiceImpl();
        saveCredentials.addActionListener(event -> {
            if (serviceNameField.getText().equals("") && usernameField.getText().equals("") && passwordField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Enter credentials please");
            } else {
                int userChoice = JOptionPane.showConfirmDialog(null, "Please confirm to save");
                if (userChoice == 0) {
                    try {
                        String response = service.saveCredentials(CredentialsDto.build(Map.of("service_name", serviceNameField.getText(),
                                "user_name", usernameField.getText(), "password", passwordField.getText())));
                        statusDisplay.setText(refreshStatus(statusDisplay.getText(), response));
                        serviceNameField.setText("");
                        usernameField.setText("");
                        passwordField.setText("");
                    } catch (ArgumentRequiredException e) {
                        log.error(e);
                    }
                } else {
                    serviceNameField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        openStorage.addActionListener(event -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                if (Files.isRegularFile(Path.of(MemoryCache.getProperty("storage_file_path")))) {
                    String filePath = MemoryCache.getProperty("storage_file_path");
                    File file = new File(filePath);
                    desktop.edit(file);
                    statusDisplay.setText(refreshStatus(
                            statusDisplay.getText(), ResponseFactory.createResponse(StringFactory
                                    .buildString("file:", file.getName(), "opened"))));
                } else {
                    statusDisplay.setText(refreshStatus(statusDisplay.getText(),
                            ResponseFactory.createResponse("storage not found, save at least one record")));
                }
            } catch (IOException e) {
                log.error(e);
            }
        });

        encryptCredentials.addActionListener(event -> {
            String userChoice = JOptionPane.showInputDialog("enter password");
            if (TSEncrypt.doDecryption(MemoryCache.getProperty("admin_password")).equals(userChoice)) {
                String serviceName = JOptionPane.showInputDialog("enter service name");
                statusDisplay.setText(service.getCredentialsByServiceName(serviceName));
            } else {
                statusDisplay.setText(refreshStatus(statusDisplay.getText(), ResponseFactory.createResponse("access denied!")));
            }
        });

        clearStatusDisplay.addActionListener(event -> {
            statusDisplay.setText("");
        });

        if (!ConfigLoader.checkPass()) {
            String pass = JOptionPane.showInputDialog("create password");
            String passFinal = TSEncrypt.doEncryption(pass);
            ConfigLoader.saveConfig("admin_password=" + passFinal);
            MemoryCache.setProperty("admin_password", passFinal);

        }
        statusDisplay.setText(ResponseFactory.createResponse("Password Manager loaded"));
    }

    public String refreshStatus(String current, String newStatus) {
        if (current.length() >= 400) return newStatus;
        return current + "\n" + newStatus;
    }
}
