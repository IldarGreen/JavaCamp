import java.util.Random;

public class Program {
    public static void main(String[] args) {

        String[] parts = args[0].split("=");
        int arraySize = Integer.parseInt(parts[1]);
        parts = args[1].split("=");
        int threadsCount = Integer.parseInt(parts[1]);

        Short[] array = new Short[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = (short) (random.nextInt(2000) - 1000);
        }
        System.out.println("Sum: " + calcSum(array));

        Thread[] threads = new Processor[threadsCount];
        int part = arraySize / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            if (i != threadsCount - 1) {
                threads[i] = new Processor(i, array, i * part, (i + 1) * part);
            } else {
                threads[i] = new Processor(i, array, i * part, arraySize);
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Sum by threads: " + Processor.getSum());
    }

    public static long calcSum(Short[] array) {
        long sum = 0;
        for (short i : array) {
            sum += i;
        }
        return sum;
    }
}

class Processor extends Thread implements Runnable {
    private static long processorSum;
    private int id;
    private Short[] array;
    private int start;
    private int end;

    public Processor(int id, Short[] array, int start, int end) {
        this.id = id;
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        System.out.println("Thread " + (id + 1) + ": from " + start + " to " + (end - 1) + " sum is " + sum);
        processorSum += sum;
    }

    public static long getSum() {
        return processorSum;
    }
}

/*
java Program --arraySize=13 --threadsCount=3
*/