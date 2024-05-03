import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You forgot to enter the argument");
        } else {
            String path = parser(args);
            System.out.println(path);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            while (!input.equals("exit")) {
                String[] parts = input.split(" ");
                switch (parts[0]) {
                    case "ls":
                        pushLs(path);
                        break;
                    case "cd":
                        path = pushCd(parts[1], path);
                        System.out.println(path);
                        break;
                    case "mv":
                        if (parts.length == 3) {
                            pushMv(parts[1], parts[2], path);
                        } else {
                            System.out.println("Wrong number of arguments");
                        }
                        break;
                    default:
                        System.out.println("There is no such command");
                        break;
                }
                input = scanner.nextLine();
            }
        }
    }

    public static String parser(String[] args) {
        String[] parts = args[0].split("=");
        return parts[1];
    }

    public static void pushLs(String path) {
        //first call of recursion
        double size = 0;
        File dir = new File(path);
        for (File item : dir.listFiles()) {
            if (item.isDirectory()) {
                size = getSize(item.getPath());
            } else {
                size = getFileSize(item);
            }
            System.out.printf("%s  %.2f KB\n", item.getName(), size);
        }
    }

    public static double getSize(String path) {
        double size = 0;
        File dir = new File(path);
        if (dir.isDirectory()) {
            //get all nested objects
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    size += getSize(item.getPath());
                } else {
                    size += getFileSize(item);
                }
            }
        } else {
            size += getFileSize(dir);
        }

        return size;
    }

    private static Double getFileSize(File file) {
        return (double) file.length() / 1000; // kb not a KiB
    }

    public static String pushCd(String input, String path) {
        String fileSeparator = File.separator;
        String newPath = path + fileSeparator + input;
        File dir = new File(newPath);
        System.out.println(input + " " + dir.isDirectory());
        if (dir.isDirectory()) {
            path = newPath;
        } else {
            System.out.println("cd: no such file or directory: " + input);
        }
        return path;
    }

    private static void pushMv(String src, String dest, String path) {
        String fileSeparator = File.separator;
        Path pathSrc = Paths.get(src);
        Path pathDest = Paths.get(dest);
        if (!pathSrc.isAbsolute()) {
            pathSrc = Paths.get(path + fileSeparator + src);
        }
        if (!pathDest.isAbsolute()) {
            pathDest = Paths.get(path + fileSeparator + dest);
        }
        try {
            Files.move(pathSrc, pathDest);
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }
    }

}

/*
$ java Program --current-folder=/Users/vanesabo/Desktop/javatest
C:/MAIN
-> ls
folder1 60 KB
folder2 90 KB
-> cd folder1
C:/MAIN/folder1
-> ls
image.jpg 10 KB
animation.gif 50 KB
-> mv image.jpg image2.jpg
-> ls
image2.jpg 10 KB
animation.gif 50 KB
-> mv animation.gif ../folder2
-> ls
image2.jpg 10 KB
-> cd ../folder2
C:/MAIN/folder2
-> ls
text.txt 10 KB
Program.java 80 KB
animation.gif 50 KB
-> exit
*/
