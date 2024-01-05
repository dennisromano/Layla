package com.dennisromano.layla.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class TitlePanel extends JPanel {

    public TitlePanel() {
        final JLabel title = new JLabel("Layla", SwingConstants.CENTER);
        title.setForeground(new Color(228, 192, 122));
        title.setFont(BOLD_FONT);

        final JLabel subtitle = new JLabel("A minimal PDF editor", SwingConstants.CENTER);
        subtitle.setForeground(new Color(56, 58, 66));
        subtitle.setFont(PLAIN_FONT);

        final Border border = new EmptyBorder(10, 0, 16, 0);

        setBorder(border);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(subtitle, BorderLayout.SOUTH);
    }
}