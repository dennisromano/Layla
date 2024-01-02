package com.dennisromano.layla.component;

import com.dennisromano.layla.service.AzioniService;
import com.dennisromano.layla.service.AzioniServiceImpl;

import javax.swing.*;
import java.awt.*;

public class PdfPanel extends JPanel {
    private final AzioniService azioniService = AzioniServiceImpl.getInstance();

    public PdfPanel() {
        final JLabel pdfPage = azioniService.generetePdfPage();

        setLayout(new BorderLayout());
        setBackground(new Color(250,250,250));
        add(pdfPage, BorderLayout.CENTER);
    }
}