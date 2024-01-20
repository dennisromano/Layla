package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class MenuPanel extends JPanel {

    private final ActionService actionService = ActionServiceImpl.getInstance();

    public MenuPanel() {
        final JLabel logo = logo();
        final Border border = new EmptyBorder(10, 50, 10, 50);
        final FunctionPanel functionPanel = new FunctionPanel();
        final JButton chooseFileButton = new CustomButton("Scegli PDF...", actionService::openFileChooser);

        setBorder(border);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(logo, BorderLayout.NORTH);
        add(functionPanel, BorderLayout.CENTER);
        add(chooseFileButton, BorderLayout.SOUTH);
    }
}