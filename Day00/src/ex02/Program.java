import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        long n = 0;
        while (n != 42) {
            n = scanner.nextInt();
            if (Program.checkForPrime(getSum(n)))
                count++;
        }
        System.out.println("Count of coffee-request – " + count);
    }

    public static long getSum(long n) {
        long sum = 0;
        n = (n < 0) ? -n : n;
        while (n > 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    public static boolean checkForPrime(long n) {
        boolean result = true;
        if (n > 1) {
            for (long i = 2; i * i <= n && i <= Math.sqrt(Long.MAX_VALUE); i++) {
                if (n % i == 0) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}
