package com.dennisromano.layla.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Style {

    public static final Font PLAIN_FONT = generateFont(13f);
    public static final Font BOLD_FONT = generateFont(44f);

    public static final Color GOLD = new Color(228, 192, 122);
    public static final Color BLACK = new Color(56, 58, 66);

    private static Font generateFont(float size) {
        try {
            final URL resource = Style.class.getClassLoader().getResource("font/NotoSans.ttf");

            if (resource != null && resource.getPath() != null) {
                final String fontPath = resource.getPath().replace("%20", " ");
                final File fontFile = new File(fontPath);
                return Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, size);
            }
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Errore nel generateFont!");
    }

    public static JTextField customTextField(int pageNumber) {
        Border lineBorder = BorderFactory.createLineBorder(new Color(204, 204, 204), 1);
        Border emptyBorder = new EmptyBorder(8, 8, 8, 8);
        Border border = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

        final JTextField textField = new JTextField(8);
        textField.setForeground(BLACK);
        textField.setBorder(border);
        textField.setText(String.valueOf(pageNumber));
        textField.setFont(PLAIN_FONT);
        textField.setHorizontalAlignment(SwingConstants.CENTER);

        return textField;
    }

    public static JLabel logo() {
        final Border border = new EmptyBorder(10, 0, 16, 0);
        final JLabel logo = new JLabel("Layla", SwingConstants.CENTER);
        logo.setForeground(GOLD);
        logo.setFont(BOLD_FONT);
        logo.setBorder(border);

        return logo;
    }
}