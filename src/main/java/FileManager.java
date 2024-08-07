import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private List<Item> items;

    public FileManager(String fileName) {
        items = new ArrayList<>();
        readFile(fileName);
    }

    private void readFile(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String name = parts[0];
                    String category = parts[1];
                    double smallPrice = Double.parseDouble(parts[2]);
                    double mediumPrice = Double.parseDouble(parts[3]);
                    double largePrice = Double.parseDouble(parts[4]);
                    double totalRate = Double.parseDouble(parts[5]);
                    int rateCount = Integer.parseInt(parts[6]);
                    items.add(new Item(name, category, smallPrice, mediumPrice, largePrice, totalRate, rateCount));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getItems() {
        return items;
    }
}