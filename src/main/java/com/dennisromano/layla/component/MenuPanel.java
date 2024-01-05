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

    private JPanel pdfPanel;

    private JTextField pageNumberTextField;

    public MenuPanel(JPanel pdfPanel) {
        this.pdfPanel = pdfPanel;
        final TitlePanel titlePanel = new TitlePanel();

        final JButton chooseFileButton = customButton("Scegli PDF...");
        chooseFileButton.addActionListener(e -> {
            actionService.openFileChooser(pdfPanel);
            //changePage(pdfPanel, pageNumberTextField);
        });

        final Border border = new EmptyBorder(10, 50, 10, 50);

        FunctionPanel functionPanel = new FunctionPanel();

        setBorder(border);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        //add(prevButton);
        //add(nextButton);
        //add(pageNumberTextField);
        add(functionPanel, BorderLayout.CENTER);
        //add(deleteBlankButton);
        add(chooseFileButton, BorderLayout.SOUTH);
    }
}