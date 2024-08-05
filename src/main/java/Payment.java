import java.util.List;

public class Payment {
    private List<Item> orderSummary;
    private PaymentMethod payMethod;
    private String promoCode;
    private double totalCost;
    private boolean payConfirm;
    private String receipt;

    public Payment(List<Item> orderSummary) {
        this.orderSummary = orderSummary;
        this.totalCost = calculateTotalCost();
        this.payConfirm = false;
        this.receipt = "";
    }

    private double calculateTotalCost() {
        double total = 0;
        for (Item item : orderSummary) {
            total += item.getPrice();
        }
        return total;
    }

    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (Item item : orderSummary) {
            System.out.println(item);
        }
        System.out.println("Total Cost: $" + totalCost);
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        this.payMethod = paymentMethod;
    }

    public void applyPromotionCode(String promotionCode) {
        // all promo discounts are 10% off
        if (promotionCode.equals("DISCOUNT10")) {
            this.totalCost *= 0.9;
            this.promoCode = promotionCode;
            System.out.println("Promotion code applied. New total cost: $" + totalCost);
        } else {
            System.out.println("Invalid promotion code.");
        }
    }

    public void confirmPay() {
        if (payMethod == null) {
            System.out.println("Please select a payment method.");
            return;
        }
        this.payConfirm = true;
        this.receipt = generateReceipt();
        System.out.println("Payment confirmed. Receipt: \n" + receipt);
    }

    private String generateReceipt() {
        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("Receipt:\n");
        for (Item item : orderSummary) {
            receiptBuilder.append(item).append("\n");
        }
        receiptBuilder.append("Total Cost: $").append(totalCost).append("\n");
        receiptBuilder.append("Payment Method: ").append(paymentMethod).append("\n");
        if (promoCode != null) {
            receiptBuilder.append("Promotion Code: ").append(promotionCode).append("\n");
        }
        return receiptBuilder.toString();
    }

    public void printReceipt() {
        if (!payConfirm) {
            System.out.println("Payment not confirmed. Cannot print receipt.");
            return;
        }
        System.out.println("Printing receipt...");
        System.out.println(receipt);
    }

    public void emailReceipt(String email) {
        if (!payConfirm) {
            System.out.println("Payment not confirmed. Cannot email receipt.");
            return;
        }
        System.out.println("Emailing receipt to " + email + "...");
    }

    // Nested classes for demonstration purposes
    public static class Item {
        private String name;
        private double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return name + " - $" + price;
        }
    }

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, MOBILE_PAYMENT, GIFT_CARD, CASH
    }

}
