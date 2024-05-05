import java.io.*;
import java.net.URL;
import java.util.*;

public class Program {
    private static Queue<String[]> urls = getListOfURL();

    public static void main(String[] args) {
        String[] parts = args[0].split("=");
        int threadsCount = Integer.parseInt(parts[1]);

        MyThread[] threads = new MyThread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new MyThread(i + 1);
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
    }

    public static synchronized String[] getFromUrls() {
        String[] numUrl = urls.element();
        urls.poll();
        return numUrl;
    }

    public static Queue<String[]> getListOfURL() {
        Queue<String[]> urls = new LinkedList<>();
        String path = "files_urls.txt";
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            String[] temps = null;
            while (scanner.hasNext()) {
                temps = (scanner.nextLine().split(" "));
                urls.add(temps);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return urls;
    }

    public static void downloadFile(URL url) throws IOException {
        String[] fileNames = url.toString().split("/");
        try (InputStream is = url.openStream();
             BufferedInputStream bis = new BufferedInputStream(is);
             FileOutputStream fos = new FileOutputStream(fileNames[fileNames.length - 1])) {
            byte[] data = new byte[1024];
            int count;
            while ((count = bis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
            }
        }
    }

    public static boolean isUrls() {
        return !urls.isEmpty();
    }
}


class MyThread extends Thread implements Runnable {
    private long id;

    public MyThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (Program.isUrls()) {
            String[] numUrl = Program.getFromUrls();
            System.out.println("Thread-" + id + " started downloading file number " + numUrl[0]);
            try {
                URL url = new URL(numUrl[1]);
                Program.downloadFile(url);
                System.out.println("Thread-" + id + " finished downloading file number " + numUrl[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public long getId() {
        return id;
    }
}

/*
java Program --threadsCount=3
*/

/*
5 http://tldp.org/LDP/intro-linux/intro-linux.pdf
*/
