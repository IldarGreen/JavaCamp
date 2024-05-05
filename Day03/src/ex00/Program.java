public class Program {
    public static void main(String[] args) {
        String[] parts = args[0].split("=");
        int num = Integer.parseInt(parts[1]);

        MyTread myTread1 = new MyTread(num, "Egg");
        MyTread myTread2 = new MyTread(num, "Hen");
        myTread1.start();
        myTread2.start();
        for (int i = 0; i < num; i++) {
            System.out.println("Human");
        }
    }
}

class MyTread extends Thread {
    String message;
    int num;
    MyTread(int num, String message) {
        this.message = message;
        this.num = num;
    }
    @Override
    public void run() {
        for (int i = 0; i < num; i++) {
            System.out.println(message);
        }
    }
}

//java Program --count=50