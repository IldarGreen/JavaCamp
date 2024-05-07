package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.printer.logic.ImageConverter;

@Parameters(separators = "=")
public class Program {
    @Parameter(names = {"--white"})
    private static String white;
    @Parameter(names = {"--black"})
    private static String black;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect number of arguments. Try again later.");
        } else {
            Program program = new Program();

            JCommander.newBuilder().addObject(program).build().parse(args);

            ImageConverter imageConverter = new ImageConverter(white, black, "/resources/it.bmp");
            imageConverter.printCharImage();
        }
    }
}
