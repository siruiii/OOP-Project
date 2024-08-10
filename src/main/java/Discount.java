public class Discount {
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