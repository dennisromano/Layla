package com.dennisromano.layla;

import com.dennisromano.layla.component.Layla;
import com.dennisromano.layla.util.Constants;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        final String PDF_PATH = Constants.getPdfPath("example/bitcoin.pdf");
        SwingUtilities.invokeLater(() -> new Layla(PDF_PATH));

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.SEVERE);
    }
}