package edu.school21.app;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        File folder = new File("src/main/java/edu/school21/classes");
        List<String> classNameList = processFilesFromFolder(folder);
        System.out.println("Classes:");
        for (String name : classNameList) {
            System.out.println("  — " + name);
        }
        System.out.println("—————————————————————");
        System.out.println("Enter class name:");
        String input = scanner.nextLine();
        Reflection reflection = new Reflection(input.toLowerCase() + "." + input);
        Class<?> currentClass = reflection.getClassInstance();
        reflection.showFieldList(currentClass);
        reflection.showMethodList(currentClass);
        System.out.println("—————————————————————");
        System.out.println("Let’s create an object.");
        Object currentObject = reflection.createObject(currentClass);
        System.out.print("Object created: ");
        reflection.showObject(currentObject);
        System.out.println("—————————————————————");
        System.out.println("Enter name of the field for changing:");
        currentObject = reflection.fieldChang(currentObject);
        System.out.print("Object updated: ");
        reflection.showObject(currentObject);
        System.out.println("—————————————————————");
        System.out.println("Enter name of the method for call:");
        reflection.runMethod(currentObject);
    }

    public static List<String> processFilesFromFolder(File folder) {
        List<String> strings = new ArrayList<>();
        File[] folderEntries = folder.listFiles();
        if (folderEntries != null) {
            for (File entry : folderEntries) {
                if (entry.isDirectory()) {
                    strings.addAll(processFilesFromFolder(entry));
                } else {
                    strings.add(entry.getName().split("\\.")[0]);
                }
            }
        }
        return strings;
    }
}
