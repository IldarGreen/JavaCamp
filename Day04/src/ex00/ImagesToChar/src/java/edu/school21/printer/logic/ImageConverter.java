package src.java.edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    char white;
    char black;
    String path;
    private static char[][] image;
    static final int WHITE = Color.WHITE.getRGB();

    public ImageConverter(char white, char black, String path) {
        this.white = white;
        this.black = black;
        this.path = path;
        spellThePicture();
    }

    public char[][] spellThePicture() {
        File file = new File(path);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            int height = bufferedImage.getHeight();
            int wight = bufferedImage.getWidth();
            image = new char[height][wight];
            int color;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < wight; j++) {
                    color = bufferedImage.getRGB(j, i);//j->x, i->y
                    if (color == WHITE) {
                        image[i][j] = white;
                    } else {
                        image[i][j] = black;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public void printCharImage() {
        for (char[] line : image) {
            System.out.println(line);
        }
    }

}
