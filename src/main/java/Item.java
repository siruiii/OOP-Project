// Item Class
public class Item {
    // Attributes
    private String name;
    private int quantity;
    private double price;
    private double totalRate;
    private int ratingCount;

    // Constructor
    public Item(String name, int quantity, double price, double rating) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalRate = 0.0;
        this.ratingCount = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Add a rating
    public void addRating(double rating) {
        // Assuming ratings are between 0 and 5
        if (rating >= 0.0 && rating <= 5.0) {
            this.totalRate += rating;
            this.ratingCount++;
        } else {
            System.out.println("Invalid rating. Please provide a rating between 0 and 5.");
        }
    }

    // Getter for average rating
    public double getAverageRating() {
        if (ratingCount == 0) {
            return 0.0;  // No ratings yet
        } else {
            return totalRate / ratingCount;
        }
    }
    
    // Method to get rating status as a string
    public String getRatingStatus() {
        if (ratingCount == 0) {
            return "No ratings yet";
        } else {
            return String.valueOf(getAverageRating());
        }
    }
}