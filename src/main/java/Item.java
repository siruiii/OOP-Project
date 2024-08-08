public class Item {
    private String name;
    private String category;
    private String size;
    private double smallPrice;
    private double mediumPrice;
    private double largePrice;
    private double totalRate;
    private int rateCount;
    private int quantity;

    // Default Constructor
    public Item(String name, String category, double smallPrice, double mediumPrice, double largePrice,
            double totalRate, int rateCount) {
        this.name = name;
        this.category = category;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.totalRate = totalRate;
        this.rateCount = rateCount;
    }

    public Item(String name, String category, String size, double Price, double smallPrice, double mediumPrice,
            double largePrice, int quantity) {
        this.name = name;
        this.size = size;
        this.category = category;
        this.quantity = quantity;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
    }

    // Getters
    public String getSize() {
        return size;
    }
    public void setSize(String s) {
        this.size=s;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int q) {
        this.quantity=q;
    }

    public double getSmallPrice() {
        return smallPrice;
    }

    public double getMediumPrice() {
        return mediumPrice;
    }

    public double getLargePrice() {
        return largePrice;
    }

    // Add a rating to total sum
    public void addRating(double rating) {
        this.totalRate += rating;
        this.rateCount++;
    }

    // Method to get rating status/average rating as a string
    public String getRatingStatus() {
        if (rateCount == 0) {
            return "No ratings yet";
        } else {
            double average = totalRate / rateCount;
            return String.valueOf(average);
        }
    }

}