import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class FileManager {

    private List<Item> items;
    private List<Discount> discounts;
    private Discount hasDiscount;

    // Constructor
    public FileManager(String fileName) {
        items = new ArrayList<>();
        readFile(fileName);
    }
    
    public FileManager(String fileName, boolean useDiscount) {
        items = new ArrayList<>();
        readFile(fileName);
        if (useDiscount){
            discounts = new ArrayList<>();
            readDiscounts("Discount.txt");
        }
    }

    // Read Menu info
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

    // Read the discount codes
    private void readDiscounts(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String code = parts[0];
                    double discount = Double.parseDouble(parts[1]);
                    int usageCount = Integer.parseInt(parts[2]);
                    discounts.add(new Discount(code, discount, usageCount));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public List<Item> getItems() {
        return items;
    }

    // Update all Items' Rating
    public void updateItemRating(String itemName, double newRating) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                item.addRating(newRating);
                break;
            }
        }
    }

    // Write updated Rating info
    public void saveItems(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Item item : items) {
                writer.write(item.getName() + "," + item.getCategory() + "," + item.getSmallPrice() + "," + item.getMediumPrice() + ","
                        + item.getLargePrice() + "," + item.getTotalRate() + "," + item.getRateCount() + "\n");
            }
        } catch (IOException e) {
        }
    }


    // Validate and apply a discount code
    public double applyDiscount(String code) {
        for (Discount discount : discounts) {
            if (discount.getCode().equalsIgnoreCase(code) && discount.getUsageCount() > 0) {
                hasDiscount = discount;
                return discount.getDiscount();
            }
        }
        return 0;
    }

    // Write the updated discount codes availibility back to the file
   public void saveDiscounts(String fileName) {
        if (hasDiscount != null) {
            hasDiscount.decrementUsage(); // Decrement the usage count
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Discount discount : discounts) {
                writer.write(discount.getCode() + "," + discount.getDiscount() + "," + discount.getUsageCount() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Print the receipt
    public void printReceipt(List<Item> cartItems, String paymentMethod, double totalPrice, double fee) {
        // Generate a unique filename using the current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "Receipt_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Receipt:\n\n");

            // Write the selected payment method
            writer.write("Payment Method: " + paymentMethod + "\n\n");
            for (Item item : cartItems) {
                writer.write(String.format("Item: %s, Size: %s, Quantity: %d, Price: $%.2f\n",
                        item.getName(), item.getSize(), item.getQuantity(), item.getItemPrice()));
            }

            // Include takeout fee if applicable
            if (startGUI.feeCheck()) {
                writer.write("\nTakeout Fee: $" + String.format("%.2f", fee));
            }

            writer.write("\nTotal: $" + String.format("%.2f", totalPrice));
            writer.write("\n\nThank you so much for Shopping here!");

            // Show confirmation message
            JOptionPane.showMessageDialog(null, "The receipt has been printed!", "Receipt ", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // Show error message if saving fails
            JOptionPane.showMessageDialog(null, "Error saving receipt.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save Feedback to a new file
    public void writeFeedback(String feedback) {
        try (BufferedWriter feedbackWriter = new BufferedWriter(new FileWriter("Feedback.txt", true))) {
            if (feedback != null && !feedback.trim().isEmpty()) {
                feedbackWriter.write(feedback.trim() + "\n\n");
            }
        } catch (IOException e) {}
    }

}