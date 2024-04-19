import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Program.checkForPrime(n);
    }

    public static void checkForPrime(int n) {
        boolean result = true;
        if (n > 1) {
            int i = 2;
            for (; i * i <= n && i <= Math.sqrt(Integer.MAX_VALUE); i++) {
                if (n % i == 0) {
                    result = false;
                    break;
                }
            }
            if (result)
                System.out.println("true " + i);
            else
                System.out.println("false " + i);
        } else {
            System.out.println("Illegal Argument");
            System.exit(-1);
        }
    }
}
