package com.dennisromano.layla.component;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BodyPanel extends JPanel {

    public BodyPanel() throws IOException, FontFormatException {
        final TitlePanel titlePanel = new TitlePanel();
        final PdfPanel pdfPanel = new PdfPanel();
        final MenuPanel menu = new MenuPanel(pdfPanel);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.PAGE_START);
        add(pdfPanel, BorderLayout.CENTER);
        add(menu, BorderLayout.PAGE_END);
    }
}