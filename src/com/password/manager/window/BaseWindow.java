package com.password.manager.window;

import com.password.manager.decoder.Decoder;
import com.password.manager.encoder.Encoder;
import com.password.manager.util.Const;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BaseWindow {
    public JPanel passwordManager;
    public JTextField textDisplay;

    public JButton encodeBtn;
    public JButton decodeBtn;

    public BaseWindow() {
        encodeBtn.addActionListener(event -> {
            Encoder encoder = new Encoder();
            String encoded = encoder.encodePassword(textDisplay.getText(), Const.SECRET_KEY);
            textDisplay.setText(encoded);
        });

        decodeBtn.addActionListener(event -> {
            Decoder decoder = new Decoder();
            String encoded = decoder.decodePassword(textDisplay.getText(), Const.SECRET_KEY);
            textDisplay.setText(encoded);
        });
    }
}
