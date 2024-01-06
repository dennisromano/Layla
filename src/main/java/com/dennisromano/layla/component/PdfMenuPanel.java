package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class PdfMenuPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public PdfMenuPanel() {
        final JTextField pageNumberTextField = customTextField(actionService.getCurrentPage());
        pageNumberTextField.addActionListener(e -> {
            actionService.goToSpecificPage(pageNumberTextField.getText());
            actionService.changePage();
        });

        final JButton prevButton = customButton("<");
        prevButton.addActionListener(e -> {
            actionService.goToPreviousPage();
            actionService.changePage();
        });

        final JButton nextButton = customButton(">");
        nextButton.addActionListener(e -> {
            actionService.goToNextPage();
            actionService.changePage();
        });

        setBackground(Color.WHITE);
        setLayout(new FlowLayout());
        add(prevButton);
        add(pageNumberTextField);
        add(nextButton);
    }
}
