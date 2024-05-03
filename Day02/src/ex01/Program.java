import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments have been submitted");
        } else {
            Set<String> set = new TreeSet<>();
            String[] strA = addToSet(args[0], set);
            String[] strB = addToSet(args[1], set);
            setToFile(set);

            int[] arrA = fillArrays(set, strA);
            int[] arrB = fillArrays(set, strB);

            double similarity = calculateSimilarity(arrA, arrB);
            System.out.printf("Similarity = %.2f", similarity);
        }
    }

    public static String[] addToSet(String filePath, Set<String> set) {
        String[] strArray = null;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fr);
            char[] buf = new char[1024];
            int numRead = 0;
            String readData;
            while ((numRead = reader.read(buf)) != -1) {
                readData = String.valueOf(buf, 0, numRead);
                buf = new char[1024];
                strArray = readData.split(" ");
                set.addAll(Arrays.asList(strArray));
            }
            reader.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return strArray;
    }

    public static int[] fillArrays(Set<String> set, String[] str) {
        int[] arr = new int[set.size()];
        int i = 0;
        for (String s : set) {
            for (String string : str) {
                if (s.equals(string)) {
                    arr[i]++;
                }
            }
            i++;
        }

        return arr;
    }

    public static double calculateSimilarity(int[] arrA, int[] arrB) {
        int numerator = 0;
        int lengthA = arrA.length;
        for (int i = 0; i < lengthA; i++) {
            numerator += arrA[i] * arrB[i];
        }
        double sqrtA = 0;
        double sqrtB = 0;
        for (int i : arrA) {
            sqrtA += i * i;
        }
        for (int i : arrB) {
            sqrtB += i * i;
        }
        double denominator = sqrt(sqrtA) * sqrt(sqrtB);

        return numerator / denominator;
    }

    public static void setToFile(Set<String> set) {
        try (PrintWriter writer = new PrintWriter("dictionary.txt")) {
            for (String s : set) {
                writer.write(s);
                writer.write(' ');
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }
    }
}
