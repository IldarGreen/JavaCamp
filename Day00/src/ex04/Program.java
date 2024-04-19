import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        char[] charArray = scanner.next().toCharArray();
        System.out.println();
        int length = charArray.length;
        int[] totalArray = new int[65536];
        int[] tenMaxCode = new int[999];
        int[] tenMaxCount = new int[999];

        for (int i = 0; i < length; i++) {
            totalArray[charArray[i]]++;
        }
        for (int i = 0; i < 65536; i++) {
            if (totalArray[i] > 0) {
                for (int j = 0; j < 999; j++) {
                    if (totalArray[i] > tenMaxCount[j]) {
                        tenMaxCount = insertX(tenMaxCount, totalArray[i], j);
                        tenMaxCode = insertX(tenMaxCode, i, j);
                        break;
                    }
                }
            }
        }

        printGistogramm(tenMaxCount, tenMaxCode);
    }

    public static int[] insertX(int[] arr, int x, int index) {
        int length = arr.length;
        int[] newArr = new int[length];
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (i == index) {
                newArr[i] = x;
                i++;
                if (i >= length)
                    break;
            }
            newArr[i] = arr[j];
        }

        return newArr;
    }

    public static void printGistogramm(int[] tenMaxCount, int[] tenMaxCode) {
        double divider = (float) tenMaxCount[0] / 10;
        for (int i = 10; i > -1; i--) {
            for (int j = 0; j < 10 & tenMaxCount[j] > 0; j++) {
                if ((int) (tenMaxCount[j] / divider) > i) {
                    System.out.printf("%4s", '#');
                }
                if ((int) (tenMaxCount[j] / divider) == i) {
                    System.out.printf("%4s", tenMaxCount[j]);
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            if (tenMaxCount[i] > 0)
                System.out.printf("%4s", (char) tenMaxCode[i]);
        }
        System.out.println();
    }
}
