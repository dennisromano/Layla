package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Layla extends JFrame {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public Layla() {
        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    actionService.closeOperation();
                }
            });

            final BodyPanel bodyPanel = new BodyPanel();

            setTitle("Layla - OpenSource PDF Editor");
            setSize(700, 900);
            setLocationRelativeTo(null);
            setVisible(true);
            add(bodyPanel);
        } catch (Exception e) {
            throw new RuntimeException("Errore in Layla!\n" + e.getMessage());
        }
    }
}