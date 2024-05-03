import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Map<String, List<String>> mapOfSignatures = getMapOfSignatures();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("42")) {
            List<String> listHex = new LinkedList<>();
            input = scanner.next();
            if (!input.equals("42")) {
                File file = new File(input);
                try {
                    InputStream is = new FileInputStream(file);
                    int value;
                    int i = 0;
                    while ((value = is.read()) != -1 && i < 24) {
                        String str = String.format("%02X ", value);
                        listHex.add(str.substring(0, str.length() - 1));
                        i++;
                    }
                    check(listHex, mapOfSignatures);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void check(List<String> listHex, Map<String, List<String>> mapOfSignatures) {
        boolean flag = false;
        for (Map.Entry<String, List<String>> entry : mapOfSignatures.entrySet()) {
            int i = 0;
            for (; i < entry.getValue().size(); i++) {
                if (!entry.getValue().get(i).equals(listHex.get(i))) {
                    break;
                }
            }
            if (i == entry.getValue().size()) {
                flag = true;
                System.out.println("PROCESSED");
                writeResultToFile(entry.getKey());
            }
        }
        if (!flag) {
            System.out.println("UNDEFINED");
        }
    }

    public static Map<String, List<String>> getMapOfSignatures() {
        Map<String, List<String>> map = new HashMap<>();
        String path = "signatures.txt";
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            String fileExtension = "";
            String temp = "";
            List<String> fileSignature = new ArrayList<>();
            while (scanner.hasNext()) {
                temp = scanner.next();
                if (temp.length() > 2) {
                    map.put(fileExtension, fileSignature);
                    fileExtension = temp.substring(0, temp.length() - 1);
                    fileSignature = new ArrayList<>();
                } else {
                    fileSignature.add(temp);
                }
            }
            map.put(fileExtension, fileSignature);
            map.remove("");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return map;
    }

    public static void writeResultToFile(String extention) {
        try {
            FileWriter writer = new FileWriter("result.txt", true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(extention);
            bufferWriter.write("\n");
            bufferWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}