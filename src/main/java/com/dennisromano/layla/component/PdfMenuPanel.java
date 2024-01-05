package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class PdfMenuPanel extends JPanel {
    private final ActionService actionService = ActionServiceImpl.getInstance();

    private JPanel pdfPanel;

    private JTextField pageNumberTextField;

    public PdfMenuPanel(JPanel pdfPanel) {
        this.pdfPanel = pdfPanel;
        this.pageNumberTextField = customTextField();

        final JButton prevButton = customButton("<");
        prevButton.addActionListener(e -> {
            actionService.goToPreviousPage();
            changePage(pdfPanel, pageNumberTextField);
        });

        final JButton nextButton = customButton(">");
        nextButton.addActionListener(e -> {
            actionService.goToNextPage();
            changePage(pdfPanel, pageNumberTextField);
        });

        setBackground(Color.RED);
        setLayout(new FlowLayout());
        add(prevButton);
        add(pageNumberTextField);
        add(nextButton);
    }

    private JTextField customTextField() {
        Border lineBorder = BorderFactory.createLineBorder(new Color(204, 204, 204), 1);
        Border emptyBorder = new EmptyBorder(8, 8, 8, 8);
        Border border = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

        final JTextField textField = new JTextField(8);
        textField.setForeground(new Color(56, 58, 66));
        textField.setBorder(border);
        textField.setText(String.valueOf(actionService.getCurrentPage()));
        textField.setFont(PLAIN_FONT);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener(e -> {
            actionService.goToSpecificPage(textField.getText());
            changePage(pdfPanel, textField);
        });

        return textField;
    }

    private void changePage(JPanel pdfPanel, JTextField textField) {
        textField.setText(String.valueOf(actionService.getCurrentPage()));

        final JLabel pdfPage = actionService.generetePdfPage();

        pdfPanel.removeAll();
        pdfPanel.add(pdfPage, BorderLayout.CENTER);
        pdfPanel.add(this, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
