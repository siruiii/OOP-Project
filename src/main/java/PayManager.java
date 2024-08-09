import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class PayManager {

    private List<Discount> discounts;
    private Discount hasDiscount;

    public PayManager() {
        discounts = new ArrayList<>();
        readDiscounts("Discount.txt");
    }

    // Method to read the discount codes from the file
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

    // Method to validate and apply a discount code
    public double applyDiscount(String code) {
        for (Discount discount : discounts) {
            if (discount.getCode().equalsIgnoreCase(code) && discount.getUsageCount() > 0) {
                hasDiscount = discount;
                return discount.getDiscount();
            }
        }
        return 0;
    }

    // Method to save the updated discount codes back to the file
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

    // Method to print the receipt
    public void printReceipt(List<Item> cartItems, String paymentMethod, double totalPrice, double fee) {
        // Generate a unique filename using the current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "Receipt_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Receipt:\n\n");

            // Write the selected payment method
            writer.write("Payment Method: " + paymentMethod + "\n\n");

            // Write each item in the cart to the file
            for (Item item : cartItems) {
                writer.write(String.format("Item: %s, Size: %s, Quantity: %d, Price: $%.2f\n",
                        item.getName(), item.getSize(), item.getQuantity(), item.getItemPrice()));
            }

            // Include takeout fee if applicable
            if (startGUI.feeCheck()) {
                writer.write("\nTakeout Fee: $" + String.format("%.2f", fee));
            }

            // Write the total price to the file
            writer.write("\nTotal: $" + String.format("%.2f", totalPrice));

            // Show confirmation message
            JOptionPane.showMessageDialog(null, "The receipt has been printed!", "Receipt ", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            // Show error message if saving fails
            JOptionPane.showMessageDialog(null, "Error saving receipt.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class to represent a discount
    private static class Discount {
        private final String code;
        private final double discount;
        private int usageCount;

        public Discount(String code, double discount, int usageCount) {
            this.code = code;
            this.discount = discount;
            this.usageCount = usageCount;
        }

        public String getCode() {
            return code;
        }

        public double getDiscount() {
            return discount;
        }

        public int getUsageCount() {
            return usageCount;
        }

        public void decrementUsage() {
            if (usageCount > 0) {
                usageCount--;
            }
        }
    }
}