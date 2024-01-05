package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.customButton;
import static com.dennisromano.layla.util.Style.menuButton;

public class FunctionPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();
    public FunctionPanel() {
        final JButton deleteButton = menuButton("Elimina pagina corrente");
        deleteButton.addActionListener(e -> {
            actionService.removePage();
            //changePage(pdfPanel, pageNumberTextField);
        });

        final JButton deleteBlankButton = menuButton("Elimina pagine vuote");
        deleteBlankButton.addActionListener(e -> {
            actionService.removeBlankPage();
            //changePage(pdfPanel, pageNumberTextField);
        });

        final JButton chooseFileButton = customButton("Scegli PDF...");
        //chooseFileButton.addActionListener(e -> {
            //actionService.openFileChooser(pdfPanel);
            //changePage(pdfPanel, pageNumberTextField);
        //});

        setBackground(Color.WHITE);
        setLayout(new GridLayout(12, 1));
        add(deleteButton);
        add(deleteBlankButton);
    }

    private void changePage(JPanel panel) {
        final JLabel pdfPage = actionService.generetePdfPage();

        panel.removeAll();
        panel.add(pdfPage);

        revalidate();
        repaint();
    }
}