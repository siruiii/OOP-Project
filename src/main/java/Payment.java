public class Payment{
    private String orderSummary;
    private String payMethod;
    private String promoCode;
    private boolean payConfirm;

    // constructor
    public Payment() {
        this.orderSummary = "";
        this.payMethod = "";
        this.promoCode = "";
        this.payConfirm = false;
    }

    // Getters & Setters
    public String getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(String orderSummary) {
        this.orderSummary = orderSummary;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.payMethod = payMethod;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public boolean isPayConfirm() {
        return payConfirm;
    }

    public void confirmPay() {
        this.payConfirm = true;
    }

    // Method to display order summary
    public void displayOrderSummary() {
        System.out.println("Order Summary: " + this.orderSummary);
    }

    // Method to select payment method
    public void selectPaymentMethod(String paymentMethod) {
        setPaymentMethod(paymentMethod);
        System.out.println("Payment Method Selected: " + this.payMethod);
    }

    // Method to apply promo code
    public void applyPromoCode(String promoCode) {
        setPromoCode(promoCode);
        System.out.println("Promo Code Applied: " + this.promoCode);
    }

    // Method to confirm payment
    public void confirmPaymentProcess() {
        confirmPay();
        System.out.println("Payment Confirmed: " + this.payConfirm);
    }
}

