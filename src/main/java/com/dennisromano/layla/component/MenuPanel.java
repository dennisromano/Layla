package com.dennisromano.layla.component;

import com.dennisromano.layla.service.AzioniService;
import com.dennisromano.layla.service.AzioniServiceImpl;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static com.dennisromano.layla.util.Constants.generateFont;

public class MenuPanel extends JPanel {

    private final AzioniService azioniService = AzioniServiceImpl.getInstance();

    private JPanel pdfPanel;

    private JTextField pageNumberTextField;

    public MenuPanel(JPanel pdfPanel) throws IOException, FontFormatException {
        this.pdfPanel = pdfPanel;
        this.pageNumberTextField = customTextField();

        final JButton prevButton = customButton("<", azioniService::goToPreviousPage);
        final JButton nextButton = customButton(">", azioniService::goToNextPage);
        final JButton deleteButton = customButton("X", azioniService::removePage);
        final JButton deleteBlankButton = customButton("Elimina pagine bianche", azioniService::removeBlankPage);

        final Border border = new EmptyBorder(10, 0, 10, 0);

        setBorder(border);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(prevButton);
        add(nextButton);
        add(pageNumberTextField);
        add(deleteButton);
        add(deleteBlankButton);
    }

    private JTextField customTextField() throws IOException, FontFormatException {
        final Font bold = generateFont(Font.PLAIN, 14f);

        Border lineBorder = BorderFactory.createLineBorder(new Color(204, 204, 204), 1);
        Border emptyBorder = new EmptyBorder(8, 8, 8, 8);
        Border border = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);

        final JTextField textField = new JTextField(8);
        textField.setForeground(new Color(56, 58, 66));
        textField.setBorder(border);
        textField.setText(String.valueOf(azioniService.getCurrentPage()));
        textField.setFont(bold);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener(e -> {
            azioniService.goToSpecificPage(textField.getText());
            changePage(pdfPanel, textField);
        });

        return textField;
    }

    private JButton customButton(String text, Runnable action) throws IOException, FontFormatException {
        final Font bold = generateFont(Font.PLAIN, 14f);
        final Border border = new EmptyBorder(8, 8, 8, 8);

        final JButton button = new JButton(text);
        button.setFont(bold);
        button.setBorder(border);
        button.setForeground(new Color(56, 58, 66));
        button.setBackground(new Color(228, 192, 122));
        button.setFocusable(false);
        button.addActionListener(e -> {
            action.run();
            changePage(pdfPanel, pageNumberTextField);
        });

        return button;
    }

    private void changePage(JPanel panel, JTextField textField) {
        textField.setText(String.valueOf(azioniService.getCurrentPage()));

        final JLabel pdfPage = azioniService.generetePdfPage();

        panel.removeAll();
        panel.add(pdfPage);

        revalidate();
        repaint();
    }
}