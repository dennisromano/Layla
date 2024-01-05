package com.dennisromano.layla;

import com.dennisromano.layla.component.Layla;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Layla::new);

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.SEVERE);
    }
}