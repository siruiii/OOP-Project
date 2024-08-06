public class Item {
    private String name;
    private double smallPrice;
    private double mediumPrice;
    private double largePrice;
    private double totalRate;
    private int rateCount;

    // Constructor
    public Item(String name, double smallPrice, double mediumPrice, double largePrice) {
        this.name = name;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
        this.totalRate = 0.0; // Default rating
        this.rateCount = 0;
    }

    // Getters
    public String getName() {
        return name;
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