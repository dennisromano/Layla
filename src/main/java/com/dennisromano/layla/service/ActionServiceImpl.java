package com.dennisromano.layla.service;

import com.dennisromano.layla.component.PdfMenuPanel;
import com.dennisromano.layla.component.PdfPanel;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionServiceImpl implements ActionService {

    /*
     *
     * Singleton Design Pattern for initialization
     *
     */
    private static ActionServiceImpl instance;

    private ActionServiceImpl() {
    }

    public static ActionServiceImpl getInstance() {
        if (instance == null) {
            instance = new ActionServiceImpl();
        }
        return instance;
    }


    /*
     *
     * Implementation variabiles
     *
     */
    private static final String TXT_PATH = "/Layla/Conversion/PDFtoTXT_";

    private static final String AUTOSAVE_PATH = "/Layla/Autosave/PDFautosave_";

    private int currentPage = 1;

    private int totalPage = 1;

    private final List<String> pdfAutoSaveList = new ArrayList<>();

    private PDDocument document;

    private String DESKTOP_PATH = "";


    private final PdfPanel pdfPanel = new PdfPanel();

    @Override
    public PdfPanel getPdfPanel() {
        return pdfPanel;
    }


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

            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            final int originalHeight = (int) document.getPage(0).getMediaBox().getHeight();
            final int desiredHeight = screenSize.height - (64 * 3);
            final float scaleY = (float) desiredHeight / originalHeight;

            final BufferedImage pageImage = pdfRenderer.renderImage(currentPage - 1, scaleY);
            final ImageIcon icon = new ImageIcon(pageImage);

            return new JLabel(icon);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella generazione della pagina pdf!\n" + e.getMessage());
        }
    }

    @Override
    public void removePage() {
        try {
            if (totalPage > 1 && currentPage >= 1 && currentPage <= totalPage) {
                document.removePage(currentPage - 1);

                final String autosaveFullPath = new StringBuilder(DESKTOP_PATH)
                        .append(AUTOSAVE_PATH)
                        .append(System.currentTimeMillis())
                        .append(".pdf")
                        .toString();

                createFolderAndFile(autosaveFullPath);

                document.save(autosaveFullPath);
                pdfAutoSaveList.add(autosaveFullPath);

                document = Loader.loadPDF(new File(autosaveFullPath));

                if (currentPage > 1) {
                    currentPage--;
                    totalPage = getTotalPage();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nell'eliminazione della pagina!\n" + e.getMessage());
        }
    }

    @Override
    public void removeBlankPage() {
        try {
            List<Integer> blankPages = new ArrayList<>();

            for (int pageIndex = 1; pageIndex <= totalPage; pageIndex++) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                pdfStripper.setStartPage(pageIndex);
                pdfStripper.setEndPage(pageIndex);
                String pageText = pdfStripper.getText(document);

                if (pageText.trim().isEmpty()) {
                    blankPages.add(pageIndex);
                }
            }

            for (int i = 0; i < blankPages.size(); i++) {
                int value = blankPages.get(i) - i - 1;
                document.removePage(value);
            }

            final String autosaveFullPath = new StringBuilder(DESKTOP_PATH)
                    .append(AUTOSAVE_PATH)
                    .append(System.currentTimeMillis())
                    .append(".pdf")
                    .toString();

            createFolderAndFile(autosaveFullPath);

            document.save(autosaveFullPath);
            pdfAutoSaveList.add(autosaveFullPath);

            document = Loader.loadPDF(new File(autosaveFullPath));
            totalPage = getTotalPage();
            currentPage = 1;
        } catch (Exception e) {
            throw new RuntimeException("Errore nel salvataggio del nuovo file, dopo eliminazione delle pagine bianche!\n" + e.getMessage());
        }
    }

    @Override
    public void convertPDFtoTXT() {
        final List<String> textLines = new ArrayList<>();
        final PDFTextStripper pdfTextStripper = new PDFTextStripper();

        try {
            final String text = pdfTextStripper.getText(document);
            final String[] lines = text.split(System.lineSeparator());

            Collections.addAll(textLines, lines);

            writeTXTfile(textLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeTXTfile(List<String> lines) throws IOException {
        final String txtFullPath = new StringBuilder(DESKTOP_PATH)
                .append(TXT_PATH)
                .append(System.currentTimeMillis())
                .append(".txt")
                .toString();

        createFolderAndFile(txtFullPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFullPath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void createFolderAndFile(String newPath) {
        try {
            final Path path = Paths.get(newPath);
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDesktopPath() {
        final String userHome = System.getProperty("user.home");
        final String os = System.getProperty("os.name").toLowerCase();

        return os.contains("win")
                ? userHome + "\\Desktop"
                : userHome + "/Desktop";
    }

    @Override
    public int getTotalPage() {
        totalPage = document.getNumberOfPages() - 1;
        return totalPage;
    }

    @Override
    public void openFileChooser() {
        final FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(pdfFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        final int result = fileChooser.showOpenDialog(pdfPanel);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                final String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                final JLabel pdfPage = startOperation(selectedFilePath);

                final PdfMenuPanel pdfMenuPanel = new PdfMenuPanel();

                pdfPanel.removeAll();
                pdfPanel.add(pdfPage, BorderLayout.CENTER);
                pdfPanel.add(pdfMenuPanel, BorderLayout.SOUTH);
                pdfPanel.revalidate();
                pdfPanel.repaint();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("File selection canceled.");
        }
    }

    private JLabel startOperation(String filePath) throws IOException {
        document = loadDocument(filePath);
        totalPage = getTotalPage();
        pdfAutoSaveList.add(filePath);
        DESKTOP_PATH = getDesktopPath();

        return generetePdfPage();
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
    public void goToNextPage() {
        if (currentPage >= 1 && currentPage < totalPage) {
            currentPage++;
        }
    }

    @Override
    public void goToSpecificPage(String textFieldValue) {
        if (!textFieldValue.isEmpty() && textFieldValue.matches("^\\d+$")) {
            final int textFieldValueAsInt = Integer.parseInt(textFieldValue);

            if (textFieldValueAsInt > 0 && textFieldValueAsInt <= totalPage) {
                currentPage = textFieldValueAsInt;
            }
        }
    }

    @Override
    public void goToPreviousPage() {
        if (currentPage > 1 && currentPage <= totalPage) {
            currentPage--;
        }
    }

    @Override
    public void changePage() {
        final JLabel pdfPage = generetePdfPage();
        final PdfMenuPanel pdfMenuPanel = new PdfMenuPanel();

        pdfPanel.removeAll();
        pdfPanel.add(pdfPage, BorderLayout.CENTER);
        pdfPanel.add(pdfMenuPanel, BorderLayout.SOUTH);

        pdfPanel.revalidate();
        pdfPanel.repaint();
    }
}