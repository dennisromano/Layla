package com.dennisromano.layla.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Constants {

    public static String getPdfPath(String resourcePath) {
        URL resource = Constants.class.getClassLoader().getResource(resourcePath);

        if (resource != null) {
            return resource.getPath();

        }

        throw new IllegalArgumentException("File not found!");
    }

    public static Font generateFont(int type, float size) throws IOException, FontFormatException {
        final String fontPath = getPdfPath("font/DMSans.ttf");
        return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(type, size);
    }
}