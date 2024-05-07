package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments. Try again later.");
        } else {
            ImageConverter imageConverter = new ImageConverter(args[0].charAt(0), args[1].charAt(0), "/resources/it.bmp");
            imageConverter.printCharImage();
        }
    }
}
