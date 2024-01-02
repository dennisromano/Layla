package com.dennisromano.layla.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AzioniServiceImpl implements AzioniService {

    /*
     *
     * Singleton Design Pattern for initialization
     *
     */
    private static AzioniServiceImpl instance;

    private AzioniServiceImpl() {
    }

    public static AzioniServiceImpl getInstance() {
        if (instance == null) {
            instance = new AzioniServiceImpl();
        }
        return instance;
    }


    /*
     *
     * Implementation variabiles
     *
     */
    private int currentPage = 0;

    private int totalPage = 0;
    private final List<String> pdfAutoSaveList = new ArrayList<>();

    private PDDocument document;

    private String PDF_PATH = "";


    /*
     *
     * Implementation methods
     *
     */
    @Override
    public PDDocument loadDocument(String filePath) throws IOException {
        document = Loader.loadPDF(new File(filePath));
        return document;
    }

    @Override
    public JLabel generetePdfPage() {
        try {
            final PDFRenderer pdfRenderer = new PDFRenderer(document);
            final Image pageImage = pdfRenderer.renderImage(currentPage);
            final ImageIcon icon = new ImageIcon(pageImage);

            return new JLabel(icon);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella generazione della pagina pdf!\n" + e.getMessage());
        }
    }

    @Override
    public void removePage() {
        try {
            if (currentPage >= 0 && currentPage <= totalPage) {
                document.removePage(currentPage);

                String lastPdfAutoSave = PDF_PATH
                        .replace(".pdf", "")
                        .concat(String.valueOf(System.currentTimeMillis()))
                        .concat(".pdf");

                document.save(lastPdfAutoSave);
                pdfAutoSaveList.add(lastPdfAutoSave);

                document = Loader.loadPDF(new File(lastPdfAutoSave));
                totalPage = getTotalPage();

                if(currentPage > 0) {
                    currentPage--;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nell'eliminazione della pagina!\n" + e.getMessage());
        }
    }

    @Override
    public void removeBlankPage() {
        try {
            System.out.println("Inizio eliminazione pagine bianche");
            long start = System.currentTimeMillis();

            List<Integer> blankPages = new ArrayList<>();

            for (int pageIndex = 1; pageIndex < document.getNumberOfPages(); pageIndex++) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                pdfStripper.setStartPage(pageIndex);
                pdfStripper.setEndPage(pageIndex);
                String pageText = pdfStripper.getText(document);

                if (pageText.trim().isEmpty()) {
                    blankPages.add(pageIndex);
                    System.out.println("Pagina bianca " + (pageIndex) + " di " + document.getNumberOfPages());
                }
            }

            for(int i=0; i < blankPages.size(); i++) {
                int value = blankPages.get(i) - i - 1;
                System.out.println("Rimuovo bianca " + value + " di " + document.getNumberOfPages());
                document.removePage(value);
            }

            blankPages.forEach(blankPage -> document.removePage(blankPage));

            String lastPdfAutoSave = PDF_PATH
                    .replace(".pdf", "")
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".pdf");

            document.save(lastPdfAutoSave);
            pdfAutoSaveList.add(lastPdfAutoSave);

            document = Loader.loadPDF(new File(lastPdfAutoSave));
            totalPage = getTotalPage();
            currentPage = 0;

            long end = System.currentTimeMillis();
            double diff = (end - start) / 1000D;
            System.out.println("-------------------------------");
            System.out.println(diff + "s - Fine eliminazione pagine bianche");
        } catch (Exception e) {
            throw new RuntimeException("Errore nel salvataggio del nuovo file, dopo eliminazione delle pagine bianche!\n" + e.getMessage());
        }
    }

    @Override
    public int getTotalPage() {
        totalPage = document.getNumberOfPages() - 1;
        return totalPage;
    }

    @Override
    public void startOperation(String filePath) throws IOException {
        PDF_PATH = filePath;
        document = loadDocument(filePath);
        totalPage = getTotalPage();
        pdfAutoSaveList.add(PDF_PATH);
    }

    @Override
    public void closeOperation() {
        if (pdfAutoSaveList.size() > 1) {
            pdfAutoSaveList.removeLast();
            pdfAutoSaveList.forEach(pdfAutoSave -> {
                Path percorsoFile = Paths.get(pdfAutoSave);

                try {
                    Files.delete(percorsoFile);
                } catch (IOException e) {
                    throw new RuntimeException("Errore nelle operazioni di chiusura di Layla!\n" + e.getMessage());
                }
            });
        }
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int goToNextPage() {
        if (currentPage >= 0 && currentPage < totalPage) {
            return currentPage++;
        }

        return currentPage;
    }

    @Override
    public int goToSpecificPage(String textFieldValue) {
        if (!textFieldValue.isEmpty() && textFieldValue.matches("^\\d+$")) {
            final int textFieldValueAsInt = Integer.parseInt(textFieldValue);

            if (textFieldValueAsInt >= 0 && textFieldValueAsInt <= totalPage) {
                currentPage = textFieldValueAsInt;
                return currentPage;
            }
        }

        return currentPage;
    }

    @Override
    public int goToPreviousPage() {
        if (currentPage > 0 && currentPage <= totalPage) {
            return currentPage--;
        }

        return currentPage;
    }
}