package com.password.manager.window;

import com.password.manager.cache.MemoryCache;
import com.password.manager.ts_encrypt.TSEncrypt;
import org.apache.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PasswordForm {
    private static final Logger log = Logger.getLogger(PasswordForm.class);

    public JPanel passwordForm;
    public JTextField appPasswordField;
    public JTextField usernameField;
    public JButton confirmButton;

    public PasswordForm() {
        confirmButton.addActionListener(action -> {
            String password = appPasswordField.getText();
            if (TSEncrypt.doDecryption(MemoryCache.getProperty("admin_password")).equals(password)) {
                MemoryCache.setProperty("user_status", "authorized");
                JOptionPane.showMessageDialog(null, "Access opened!");
                appPasswordField.setText("");
                usernameField.setText("");
                log.info(String.format("user: %s is successfully authorised", usernameField.getText()));
            } else {
                JOptionPane.showMessageDialog(null, "Access denied! Try again!");
            }
        });

    }
}
