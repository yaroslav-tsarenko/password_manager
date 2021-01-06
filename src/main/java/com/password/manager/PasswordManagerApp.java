package com.password.manager;

import com.password.manager.cache.MemoryCache;
import com.password.manager.config.ConfigLoader;
import com.password.manager.repository.CredentialsRepository;
import com.password.manager.util.Generator;
import com.password.manager.window.BaseWindow;
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
        JFrame frame = new JFrame("Password Manager");
        frame.setContentPane(new BaseWindow().passwordManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        log.info("App successfully loaded");
    }
}
