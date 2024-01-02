package com.dennisromano.layla.service;

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.IOException;

public interface AzioniService {
    void startOperation(String filePath) throws IOException;

    PDDocument loadDocument(String filePath) throws IOException;

    void removePage();

    void removeBlankPage();

    void closeOperation();

    int getTotalPage();

    int getCurrentPage();

    int goToNextPage();

    int goToPreviousPage();

    int goToSpecificPage(String textFieldValue);

    JLabel generetePdfPage();
}
