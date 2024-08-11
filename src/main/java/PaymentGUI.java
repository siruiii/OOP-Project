import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PaymentGUI extends JFrame {
    private final JPanel mainPanel;
    private JPanel discountPanel;
    private final FileManager pay;
    private JTextArea totalPLb;
    private JTextField couponField;
    private JComboBox<String> payComboBox;
    private JCheckBox receiptCheckbox;

    private double totalPrice = CartManager.getTotalPrice();
    private final double fee = 1.99;

    public PaymentGUI() {
        // Initialize PayManager
        pay = new FileManager("itemfile.txt", true);

        // Set up UI components
        setTitle("Payment");
        setBounds(100, 100, 465, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main content panel
        mainPanel = createMainPanel(); 
        add(mainPanel, BorderLayout.CENTER);

        // Add the mainPanel to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

    }

    // Creates the main content panel containing the order summary, payment method, coupon code input, etc
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout for stacking components

        mainPanel.add(createTitlePanel());
        mainPanel.add(Box.createVerticalStrut(10)); // Adds vertical space

        // Receipt
        mainPanel.add(createHeaderPanel()); 
        mainPanel.add(createItemsPanel()); 
        if (startGUI.feeCheck()) {
            mainPanel.add(createFeePanel());
        }

        mainPanel.add(createTotalPanel());

        mainPanel.add(Box.createVerticalStrut(30)); // Adds vertical space

        mainPanel.add(createPayPanel()); // pay
        mainPanel.add(createCouponPanel()); // coupon
        mainPanel.add(createReceiptPanel()); // receipt

        return mainPanel; // Return the fully assembled main panel
    }

    // Title panel
    private JPanel createTitlePanel() {
        JLabel TitleLabel = new JLabel("Order Summary"); // "Order Summary" label
        TitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        TitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ensure the label spans the full width
        JPanel TitlePanel = new JPanel(new BorderLayout());
        TitlePanel.add(TitleLabel, BorderLayout.WEST);
        TitlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        return TitlePanel;
    }

    // Header panel with column labels
    private JPanel createHeaderPanel() {
        JPanel headerPanel = setUpPanel();

        Font headerFont = new Font("Arial", Font.BOLD, 16); 
        headerPanel.add(createLabel("ITEM", headerFont));
        headerPanel.add(Box.createHorizontalStrut(100));
        headerPanel.add(createLabel("SIZE", headerFont));
        headerPanel.add(Box.createHorizontalStrut(40)); 
        headerPanel.add(createLabel("QUANTITY", headerFont));
        headerPanel.add(Box.createHorizontalStrut(40));
        headerPanel.add(createLabel("PRICE", headerFont));

        return headerPanel;
    }

    // Item Panel that displays all things in the cart
    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        for (Item item : CartManager.getSortedCart()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
            itemPanel.setBackground(Color.WHITE);

            Dimension size = new Dimension(450, 50);
            itemPanel.setPreferredSize(size);
            itemPanel.setMinimumSize(size);
            itemPanel.setMaximumSize(size);

            Font font = new Font("Arial", Font.PLAIN, 14); 
            itemPanel.add(createTextArea(item.getName(), 129, 40, font, true));
            itemPanel.add(createTextArea(item.getSize(), 70, 40, font, true));
            itemPanel.add(createTextArea(String.valueOf(item.getQuantity()), 114,40, font, true));
            itemPanel.add(createTextArea("$" + String.format("%.2f", item.getItemPrice()), 60, 40, font, true));

            itemsPanel.add(itemPanel);
        }

        return itemsPanel;
    }

    // Optional Takeout fee panel
    private JPanel createFeePanel() {
        JPanel feePanel = setUpPanel();

        JTextArea feetxtLb = createTextArea("Takeout Fee", 361, 30, new Font("Arial", Font.PLAIN, 14));
        JTextArea feeLb = createTextArea("$" + String.format("%.2f", fee), 64, 30, new Font("Arial", Font.PLAIN, 14));

        feePanel.add(feetxtLb);
        feePanel.add(feeLb);

        return feePanel;
    }

    private void showDiscountPanel(double discount) {
        if (discountPanel != null) {
            mainPanel.remove(discountPanel); // Remove the existing discount panel if present
        }

        discountPanel = setUpPanel();

        JLabel discountLabel = new JLabel("Discount:");
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel percentLabel = new JLabel(String.format("%.0f%% off", discount)); // Show percentage off
        percentLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        discountPanel.add(discountLabel);
        discountPanel.add(Box.createHorizontalStrut(300)); // Adjust spacing as needed
        discountPanel.add(percentLabel);

        mainPanel.add(discountPanel, mainPanel.getComponentCount() - 5); // Add before totalPanel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Total price panel
    private JPanel createTotalPanel() {
        JPanel totalPanel = setUpPanel();

        if(startGUI.feeCheck()){
            totalPrice += fee;
        }

        Font font = new Font("Arial", Font.BOLD, 16); // Font for total price
        JTextArea totalTLb = createTextArea("Total:", 361, 30, font);
        totalPLb = createTextArea("$" + String.format("%.2f", totalPrice), 64, 30, font);

        totalPanel.add(totalTLb);
        totalPanel.add(totalPLb);

        return totalPanel;
    }


    // Payment method selection panel
    private JPanel createPayPanel() {
        JPanel payPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel payLabel = new JLabel("Select Payment Method:");
        payComboBox = new JComboBox<>(new String[] { "Select...", "Credit/Debit Card", "Apple Pay", "Cash" });

        payPanel.add(payLabel);
        payPanel.add(payComboBox);

        return payPanel;
    }

    // Coupon code input panel
    private JPanel createCouponPanel() {
        JPanel couponPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel couponLabel = new JLabel("Apply Coupon Code (once only):");
        couponPanel.add(couponLabel);

        couponField = new JTextField(10);
        couponPanel.add(couponField);

        JButton applyCouponButton = new JButton("Apply"); 
        applyCouponButton.addActionListener(e -> {
            String code = couponField.getText().trim();
            if (!code.isEmpty()) {
                double discount = pay.applyDiscount(code);
                if (discount > 0) {
                    double percentageOff = (1 - discount) * 100; // Calculate percentage off
                    totalPrice *= discount;
                    totalPLb.setText("$" + String.format("%.2f", totalPrice)); 
                    JOptionPane.showMessageDialog(PaymentGUI.this, "Coupon applied Successfully!", "Coupon Applied", JOptionPane.INFORMATION_MESSAGE);
                    showDiscountPanel(percentageOff); // Show the discount panel
                } else if (discount == 0) {
                    JOptionPane.showMessageDialog(PaymentGUI.this, "Expired coupon code.", "Coupon Error", JOptionPane.ERROR_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(PaymentGUI.this, "Invalid coupon code.", "Coupon Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
        });
        couponPanel.add(applyCouponButton); 

        return couponPanel;
    }

    // Receipt checkbox Panel
    private JPanel createReceiptPanel() {
        // Save order summary checkbox (aligned to left)
        receiptCheckbox = new JCheckBox("Get Receipt as text file");
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.add(receiptCheckbox, BorderLayout.WEST);
        receiptPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));  // Allow full width
        return receiptPanel;
    }

    // Buttons at the bottom of the Page
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout()); 
        JButton btnReturn = new JButton("Return"); 
        // "Return" button to go back to Cart
        btnReturn.addActionListener(e -> {
            ShoppingCartGUI cgui = new ShoppingCartGUI(); 
            setVisible(false);
            cgui.setVisible(true); 
        });

        JButton btnConfirm = new JButton("Confirm");
        // "Confirm" button to go to the Waiting Page
        btnConfirm.addActionListener(e -> {
            if (payComboBox.getSelectedIndex() == 0) {
                // Show warning if no payment method is selected
                JOptionPane.showMessageDialog(PaymentGUI.this, "Please select a payment method.", "Payment Method Required", JOptionPane.WARNING_MESSAGE);
            } else {
                 // Print Receipt if the checkbox is selected
                if (receiptCheckbox.isSelected()) {
                    String couponCode = couponField.getText().trim();
                    double discountMulti = pay.applyDiscount(couponCode);
                    pay.printReceipt(
                        CartManager.readCartItem(), // List of items in the cart
                        (String) payComboBox.getSelectedItem(), // Selected payment method
                        totalPrice, // Total price of the order
                        fee, // Takeout fee amount
                        couponCode.isEmpty() ? null : couponCode,
                        discountMulti
                    );
                }
                pay.saveDiscounts("Discount.txt");
                WaitingGUI gui = new WaitingGUI(CartManager.getTotalCount());
                gui.showWait();
                dispose();
            }
        });

        buttonPanel.add(btnReturn, BorderLayout.WEST);
        buttonPanel.add(btnConfirm, BorderLayout.EAST);

        return buttonPanel; // Return the assembled button panel
    }
    
     // Utility Method to Create JLabel with specified text, font, and alignment.
    private JLabel createLabel(String text, Font font, float alignment) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(alignment);
        return label;
    }

    // Overloaded method to create a JLabel with default center alignment
    private JLabel createLabel(String text, Font font) {
        return createLabel(text, font, Component.CENTER_ALIGNMENT);
    }

    // Utility Method to Creates a JTextArea with specified text, width, height, and font
    private JTextArea createTextArea(String text, int width, int height, Font font) {
        JTextArea textArea = new JTextArea(text);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setPreferredSize(new Dimension(width, height));
        textArea.setFont(font);

        return textArea;
    }

    // Overloaded Method to make sure the text is center alignment in Y-axis
    private JTextArea createTextArea(String text, int width, int height, Font font, boolean centered) {
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true); 
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setPreferredSize(new Dimension(width, height));
        textArea.setFont(font); 

        if (centered) {
            // Set alignment and adjust margins for vertical centering
            textArea.setAlignmentY(Component.CENTER_ALIGNMENT);
            textArea.setMargin(new Insets(5, 5, 5, 5));
        }

        return textArea;
    }

    // Utility Method for default Panel setting
    private JPanel setUpPanel(){
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBorder(new LineBorder(Color.BLACK));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(450, 30));

        return p;
    }

    // Display Method
    public void showPay() {
        setVisible(true);
    }

     /*Debugger
    public static void main(String[] args) {
        PaymentGUI gui = new PaymentGUI();
        gui.showPay(); // Show the payment window
    }*/
}