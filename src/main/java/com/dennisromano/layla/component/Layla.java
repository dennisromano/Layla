package com.dennisromano.layla.component;

import com.dennisromano.layla.service.AzioniService;
import com.dennisromano.layla.service.AzioniServiceImpl;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Layla extends JFrame {
    private final AzioniService azioniService = AzioniServiceImpl.getInstance();

    public Layla(String filePath) {
        try {
            azioniService.startOperation(filePath);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    azioniService.closeOperation();
                }
            });

            final JPanel mainPanel = new BodyPanel();

            setTitle("Layla - A minimal PDF editor");
            setSize(700, 900);
            setLocationRelativeTo(null);
            setVisible(true);
            add(mainPanel);
        } catch (Exception e) {
            throw new RuntimeException("Errore in Layla!\n" + e.getMessage());
        }
    }
}