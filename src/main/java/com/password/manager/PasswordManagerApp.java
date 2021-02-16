package com.password.manager;

import com.password.manager.cache.MemoryCache;
import com.password.manager.config.ConfigLoader;
import com.password.manager.repository.CredentialsRepository;
import com.password.manager.util.Generator;
import com.password.manager.window.BaseWindow;
import com.password.manager.window.PasswordForm;
import org.apache.log4j.Logger;

import javax.swing.JFrame;

import java.io.IOException;

import static com.password.manager.ts_encrypt.Const.GLOBAL_SYMBOLS;

public class PasswordManagerApp {

    private static final Logger log = Logger.getLogger(PasswordManagerApp.class);

    public static void main(String[] args) {
        Generator gen = new Generator();
        CredentialsRepository.checkRepo();
        if (ConfigLoader.checkProps()) {
            String propsItem = "secret_key=" + gen.getSecretKey(GLOBAL_SYMBOLS.length - 1);
            try {
                ConfigLoader.saveConfig(propsItem);
            } catch (IOException e) {
                log.error(e);
            }
        }
        MemoryCache.readProps(ConfigLoader.loadConfig());
        runBaseWindow();
        log.info("App successfully loaded");
    }

    public static void runBaseWindow() {
        JFrame mainFrame = new JFrame("Password Manager");
        mainFrame.setContentPane(new BaseWindow().passwordManager);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
    }

    public static void runLoginWindow() {
        JFrame loginFrame = new JFrame("Login Form");
        loginFrame.setContentPane(new PasswordForm().passwordForm);
        loginFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false);
    }
}
