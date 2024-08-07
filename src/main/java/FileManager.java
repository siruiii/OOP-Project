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
                if (parts.length == 4) {
                    String name = parts[0];
                    double smallPrice = Double.parseDouble(parts[1]);
                    double mediumPrice = Double.parseDouble(parts[2]);
                    double largePrice = Double.parseDouble(parts[3]);
                    items.add(new Item(name, smallPrice, mediumPrice, largePrice));
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