public class Program {
    public static void main(String[] args) throws InterruptedException {
        String[] parts = args[0].split("=");
        int num = Integer.parseInt(parts[1]);

        final Object lock = new Object();
        ProducerConsumer pc = new ProducerConsumer(lock);

        Thread threadProduceEgg = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < num; i++) {
                    try {
                        pc.produce("Egg");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread threadProduceHen = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < num; i++) {
                    try {
                        pc.produce("Hen");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        threadProduceEgg.start();
        threadProduceHen.start();

        threadProduceEgg.join();
        threadProduceHen.join();
    }
}

class ProducerConsumer {
    final Object lock;
    private static String flag = "Hen";

    public ProducerConsumer(Object lock) {
        this.lock = lock;
    }

    public void produce(String string) throws InterruptedException {
        synchronized (lock) {
            if (flag.equals(string)) {
                lock.wait();
            }
            System.out.println(string);
            flag = change(flag);
            lock.notify();
        }
    }

    private String change(String string) {
        if (string.equals("Egg"))
            string = "Hen";
        else
            string = "Egg";
        return string;
    }
}

/*
java Program --count=50
*/