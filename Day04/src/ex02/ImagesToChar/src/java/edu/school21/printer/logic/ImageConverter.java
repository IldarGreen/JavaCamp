package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

public class ImageConverter {
    private final Attribute white;
    private final Attribute black;

    String path;
    private static String[][] image;
    static final int WHITE = Color.WHITE.getRGB();

    public ImageConverter(String white, String black, String path) {
        this.white = getColor(white);
        this.black = getColor(black);
        this.path = path;
        spellThePicture();
    }

    private Attribute getColor(String color) {
        switch (color) {
            case "WHITE":
                return Attribute.WHITE_BACK();
            case "BLACK":
                return Attribute.BLACK_BACK();
            case "RED":
                return Attribute.RED_BACK();
            case "GREEN":
                return Attribute.GREEN_BACK();
            case "BLUE":
                return Attribute.BLUE_BACK();
            case "YELLOW":
                return Attribute.YELLOW_BACK();
            case "BRIGHT_BLACK":
                return Attribute.BRIGHT_BLACK_BACK();
            case "BRIGHT_WHITE":
                return Attribute.BRIGHT_WHITE_BACK();
                case "BRIGHT_RED":
                return Attribute.BRIGHT_RED_BACK();
            case "BRIGHT_GREEN":
                return Attribute.BRIGHT_GREEN_BACK();
            case "BRIGHT_BLUE":
                return Attribute.BRIGHT_BLUE_BACK();
            case "BRIGHT_YELLOW":
                return Attribute.BRIGHT_YELLOW_BACK();
            case "CYAN":
                return Attribute.CYAN_BACK();
            case "MAGENTA":
                return Attribute.MAGENTA_BACK();
            case "BRIGHT_MAGENTA":
                return Attribute.BRIGHT_MAGENTA_BACK();
            default:
                throw new RuntimeException("Color not found");
        }
    }

    public String[][] spellThePicture() {
        try {
            URL url = ImageConverter.class.getResource(path);
            if (url != null) {
                BufferedImage bufferedImage = ImageIO.read(url);
                int height = bufferedImage.getHeight();
                int wight = bufferedImage.getWidth();
                image = new String[height][wight];
                int color;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < wight; j++) {
                        color = bufferedImage.getRGB(j, i);//j->x, i->y
                        if (color == WHITE) {
                            image[i][j] = Ansi.colorize("  ", white);
                        } else {
                            image[i][j] = Ansi.colorize("  ", black);
                        }
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public void printCharImage() {
        for (String[] line : image) {
            for (int i = 0; i < line.length; i++) {
                System.out.print(line[i]);
            }
            System.out.println();
        }
    }

}
