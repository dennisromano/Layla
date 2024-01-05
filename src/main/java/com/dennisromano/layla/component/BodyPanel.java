package com.dennisromano.layla.component;

import javax.swing.*;
import java.awt.*;

public class BodyPanel extends JPanel {

    public BodyPanel() {
        final PdfPanel pdfPanel = new PdfPanel();
        final MenuPanel menu = new MenuPanel(pdfPanel);

        setLayout(new BorderLayout());
        add(pdfPanel, BorderLayout.CENTER);
        add(menu, BorderLayout.WEST);
    }
}