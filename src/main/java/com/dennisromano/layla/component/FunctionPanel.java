package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

public class FunctionPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public FunctionPanel() {
        final JButton deleteBlankButton = new CustomButton("Elimina pagine vuote", actionService::removeBlankPage);

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(deleteBlankButton, BorderLayout.NORTH);
    }
}