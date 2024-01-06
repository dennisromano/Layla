package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

public class BodyPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public BodyPanel() {
        final MenuPanel menu = new MenuPanel();

        setLayout(new BorderLayout());
        add(actionService.getPdfPanel(), BorderLayout.CENTER);
        add(menu, BorderLayout.WEST);
    }
}