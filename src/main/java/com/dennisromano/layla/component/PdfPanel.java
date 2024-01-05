package com.dennisromano.layla.component;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.PLAIN_FONT;

public class PdfPanel extends JPanel {

    public PdfPanel() {
        final JLabel chooseFile = new JLabel("Scegli il tuo file PDF!", SwingConstants.CENTER);
        chooseFile.setFont(PLAIN_FONT);

        setLayout(new BorderLayout());
        setBackground(new Color(250,250,250));
        add(chooseFile, BorderLayout.CENTER);
    }
}