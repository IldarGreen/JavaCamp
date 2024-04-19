public class Program {
    public static void main(String[] args) {
        int x = 479598;
        int sum = 0;

        while(x > 0) {
            sum += x % 10;
            x = x / 10;
        }

        System.out.println(sum);
    }
}
