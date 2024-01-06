package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.menuButton;

public class FunctionPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public FunctionPanel() {
        final JButton deleteButton = menuButton("Elimina pagina corrente");
        deleteButton.addActionListener(e -> {
            actionService.removePage();
            actionService.changePage();
        });

        final JButton deleteBlankButton = menuButton("Elimina pagine vuote");
        deleteBlankButton.addActionListener(e -> {
            actionService.removeBlankPage();
            actionService.changePage();
        });

        setBackground(Color.WHITE);
        setLayout(new GridLayout(12, 1));
        add(deleteButton);
        add(deleteBlankButton);
    }
}