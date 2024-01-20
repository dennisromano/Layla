package com.dennisromano.layla.component;

import com.dennisromano.layla.service.ActionService;
import com.dennisromano.layla.service.ActionServiceImpl;

import javax.swing.*;
import java.awt.*;

import static com.dennisromano.layla.util.Style.*;

public class CustomButton extends JButton {
    private final ActionService actionService = ActionServiceImpl.getInstance();
    private static final int BUTTON_HEIGHT = 36;

    public CustomButton(String label, Runnable onClick) {
        super(label);

        setFont(PLAIN_FONT);
        setBackground(GOLD);
        setForeground(BLACK);
        setContentAreaFilled(false);
        setFocusPainted(false);

        addActionListener(e -> {
            onClick.run();
            actionService.changePage();
        });

        final int buttonWidth = label.length() == 1 ? 36 : 150;
        setPreferredSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

        g2d.setColor(getForeground());
        g2d.setFont(PLAIN_FONT);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
        g2d.dispose();
    }
}