package com.dennisromano.layla.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static com.dennisromano.layla.util.Constants.generateFont;

public class TitlePanel extends JPanel {

    public TitlePanel() throws IOException, FontFormatException {
        final Font bold = generateFont(Font.BOLD, 44f);
        final Font plain = generateFont(Font.PLAIN, 14f);

        final JLabel title = new JLabel("Layla", SwingConstants.CENTER);
        title.setForeground(new Color(228, 192, 122));
        title.setFont(bold);

        final JLabel subtitle = new JLabel("A minimal PDF editor", SwingConstants.CENTER);
        subtitle.setForeground(new Color(56, 58, 66));
        subtitle.setFont(plain);

        final Border border = new EmptyBorder(10, 0, 16, 0);

        setBorder(border);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(subtitle, BorderLayout.SOUTH);
    }
}