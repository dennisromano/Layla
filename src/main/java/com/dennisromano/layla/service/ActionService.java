package com.dennisromano.layla.service;

import com.dennisromano.layla.component.PdfPanel;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.IOException;

public interface ActionService {
    PdfPanel getPdfPanel();
    void openFileChooser();

    PDDocument loadDocument(String filePath) throws IOException;

    void removePage();

    void removeBlankPage();

    void closeOperation();

    int getTotalPage();

    int getCurrentPage();

    void goToNextPage();

    void goToPreviousPage();

    void goToSpecificPage(String textFieldValue);

    JLabel generetePdfPage();

    void changePage();
}
