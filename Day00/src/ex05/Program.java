import java.util.Scanner;


public class Program {
    static String[] arrDayOfTheWeek = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
    static int correction = 1;// 1 date is the Tuesday

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] scheduleWeek = new int[7][6];
        String[] students = new String[10];
        int[][][] studentAttendance = new int[10][31][6];//[student name][date][hour] -> attendance(1/-1)

        String inputLine;
        String dayOfTheWeek;

        //read: students name
        for (int i = 0; i < 10; i++) {
            inputLine = scanner.next();
            if (inputLine.equals(".")) {
                break;
            } else {
                students[i] = inputLine;
            }
        }
        //read: 2 MO
        for (int i = 0; i < 10; i++) {
            inputLine = scanner.next();
            if (inputLine.equals(".")) {
                break;
            } else {
                int time =  Integer.parseInt(inputLine);
                dayOfTheWeek = scanner.next();
                scheduleWeek[getIndexDayOfTheWeek(dayOfTheWeek)][time] = 1;
            }
        }
        //read: Mike 2 28 NOT_HERE ...
        for (int i = 0; i < 10; i++) {
            inputLine = scanner.next();
            if (inputLine.equals(".")) {
                break;
            } else {
                int hour = scanner.nextInt();
                int date = scanner.nextInt();
                int attendanceMark = -1;// NOT_HERE
                if (scanner.next().equals("HERE"))
                    attendanceMark = 1;//
                studentAttendance[getIndexStudent(students, inputLine)][date][hour] = attendanceMark;
            }
        }
        // output header
        System.out.printf("%10s", "");
        for (int date = 1; date < 31; date++) {
            for (int hour = 0; hour < 6; hour++) {
                if (scheduleWeek[(date - 1 + correction) % 7][hour] == 1) {
                    System.out.printf(hour + ":00 " + getNameDayOfTheWeek((date - 1 + correction) % 7) + " " +
                            "%2d" + "|", date);
                }
            }
        }
        System.out.println();
        //output student attendance
        for (int studentNameIndex = 0; studentNameIndex < 10; studentNameIndex++) {
            if (students[studentNameIndex] != null) {
                System.out.printf("%10s", students[studentNameIndex]);
                for (int date = 1; date < 31; date++) {
                    for (int hour = 0; hour < 6; hour++) {
                        if (scheduleWeek[(date -1 + correction) % 7][hour] == 1) {
                            System.out.printf("%8s", "");
                            if (studentAttendance[studentNameIndex][date][hour] == 1)
                                System.out.print(" " + studentAttendance[studentNameIndex][date][hour]);
                            else if (studentAttendance[studentNameIndex][date][hour] == -1)
                                System.out.print(studentAttendance[studentNameIndex][date][hour]);
                            else
                                System.out.print("  ");
                            System.out.print("|");
                        }
                    }
                }
                System.out.println();
            }
        }

    }

    public static String getNameDayOfTheWeek(int date) {
        String nameDayOfTheWeek = "MO";
        nameDayOfTheWeek = arrDayOfTheWeek[(date - 1 + correction) % 7];

        return nameDayOfTheWeek;
    }

    public static int getIndexDayOfTheWeek(String nameDayOfTheWeek) {
        int numDayOfTheWeek = 0;
        for (String day : arrDayOfTheWeek) {
            if (nameDayOfTheWeek.equals(day))
                break;
            else
                numDayOfTheWeek++;
        }

        return numDayOfTheWeek;
    }

    public static int getIndexStudent(String[] students, String studentName) {
        int IndexStudent = 0;
        for (String name : students) {
            if (studentName.equals(name))
                break;
            else
                IndexStudent++;
        }

        return IndexStudent;
    }
}
