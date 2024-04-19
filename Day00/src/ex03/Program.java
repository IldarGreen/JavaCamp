import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int weekCounter = 0;
        int weekNumber = 0;
        int grade = 0;
        int minGrade = 9;
        long asArray = 0;
        String check;
        while (weekCounter < 19) {
            try {
            check = scanner.next();
                if (check.equals("42"))
                    break;

                if (check.equals("Week")) {
                    weekNumber = scanner.nextInt();
                    if (weekNumber > 19) {
                        System.out.println("Illegal Argument");
                        break;
                    }
                    for (int i = 0; i < 5; i++) {
                        grade = scanner.nextInt();
                        if (minGrade > grade)
                            minGrade = grade;
                    }
                    asArray += minGrade * Math.pow(10, weekNumber - 1);
                    minGrade = 9;
                } else {
                    System.out.println("Illegal Argument");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Illegal Argument");
                break;
            }
            weekCounter++;
        }
        scanner.close();
        print(asArray);
    }

    public static void print(long asArray) {
        int n = 1;
        long level = 0;
        String week = "Week ";

        while(asArray > 0) {
            level = asArray % 10;
            asArray = asArray / 10;
            if (level > 0) {
                System.out.print(week);
                System.out.print(n);
                System.out.print(' ');
                for (int i = 0; i < level; i++) {
                    System.out.print('=');
                }
                System.out.print(">\n");
            }
            n++;
        }
    }
}
