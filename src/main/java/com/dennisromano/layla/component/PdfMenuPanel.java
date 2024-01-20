package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class PdfMenuPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    public PdfMenuPanel() {
        final JButton prevButton = new CustomButton("<", actionService::goToPreviousPage);
        final JButton nextButton = new CustomButton(">", actionService::goToNextPage);
        final JButton deleteButton = new CustomButton("x", actionService::removePage);

        final JTextField pageNumberTextField = customTextField(actionService.getCurrentPage());
        pageNumberTextField.addActionListener(e -> actionService.goToSpecificPage(pageNumberTextField.getText()));

        final JLabel totalPageLabel = new JLabel("/" + actionService.getTotalPage());
        totalPageLabel.setFont(PLAIN_FONT);

        setPreferredSize(new Dimension(getWidth(), 64));
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 14, 14));
        add(prevButton);
        add(pageNumberTextField);
        add(totalPageLabel);
        add(nextButton);
        add(deleteButton);
    }
}
