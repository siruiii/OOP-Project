import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class PaymentGUI extends JFrame {
    private int totalCount = 0;

    public PaymentGUI() {
        setTitle("Payment");
        setBounds(100, 100, 450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main panel with BoxLayout for vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Header labels
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(new LineBorder(Color.BLACK));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setMaximumSize(new Dimension(450, 30));
        JLabel itemLabel = new JLabel("ITEM");
        JLabel sizeLabel = new JLabel("SIZE");
        JLabel quantityLabel = new JLabel("QUANTITY");
        JLabel priceLabel = new JLabel("PRICE");

        Font headerFont = new Font("Arial", Font.BOLD, 16);
        itemLabel.setFont(headerFont);
        sizeLabel.setFont(headerFont);
        quantityLabel.setFont(headerFont);
        priceLabel.setFont(headerFont);

        headerPanel.add(itemLabel);
        headerPanel.add(Box.createHorizontalStrut(100));  // Space between columns
        headerPanel.add(sizeLabel);
        headerPanel.add(Box.createHorizontalStrut(40));  // Space between columns
        headerPanel.add(quantityLabel);
        headerPanel.add(Box.createHorizontalStrut(40));  // Space between columns
        headerPanel.add(priceLabel);
        mainPanel.add(headerPanel);

        // Cart items panel
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        mainPanel.add(itemsPanel);

        // Cart items
        for (Item item : CartManager.readCartItem()) {
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            itemPanel.setBorder(new LineBorder(Color.GRAY));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setMaximumSize(new Dimension(453, 45));  // Adjust the height to accommodate wrapping

            JTextArea itemName = new JTextArea(item.getName());
            JTextArea itemSize = new JTextArea(item.getSize());
            JTextArea itemQuantity = new JTextArea(String.valueOf(item.getQuantity()));
            JTextArea itemPrice = new JTextArea("$" + String.format("%.2f", getItemPrice(item)));

            totalCount += item.getQuantity();

            itemName.setLineWrap(true);
            itemSize.setLineWrap(true);
            itemQuantity.setLineWrap(true);
            itemPrice.setLineWrap(true);

            itemName.setWrapStyleWord(true);
            itemSize.setWrapStyleWord(true);
            itemQuantity.setWrapStyleWord(true);
            itemPrice.setWrapStyleWord(true);

            itemName.setEditable(false);
            itemSize.setEditable(false);
            itemQuantity.setEditable(false);
            itemPrice.setEditable(false);

            itemName.setOpaque(false);
            itemSize.setOpaque(false);
            itemQuantity.setOpaque(false);
            itemPrice.setOpaque(false);

            itemName.setPreferredSize(new Dimension(143, 40));  // Set preferred size for wrapping
            itemSize.setPreferredSize(new Dimension(82, 40));
            itemQuantity.setPreferredSize(new Dimension(125, 40));
            itemPrice.setPreferredSize(new Dimension(60, 40));

            itemPanel.add(itemName);
            itemPanel.add(itemSize);
            itemPanel.add(itemQuantity);
            itemPanel.add(itemPrice);

            itemsPanel.add(itemPanel);
        }

        // Total price in one line
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBorder(new LineBorder(Color.BLACK));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setMaximumSize(new Dimension(450, 30));  // Limit the height of the total panel
        JTextArea totalTLb = new JTextArea("Total:");
        JTextArea totalPLb = new JTextArea("$" + String.format("%.2f", CartManager.getTotalPrice()));

        totalTLb.setFont(headerFont);
        totalPLb.setFont(headerFont);

        totalTLb.setLineWrap(true);
        totalPLb.setLineWrap(true);

        totalTLb.setWrapStyleWord(true);
        totalPLb.setWrapStyleWord(true);

        totalTLb.setEditable(false);
        totalPLb.setEditable(false);

        totalTLb.setOpaque(false);
        totalPLb.setOpaque(false);

        totalTLb.setPreferredSize(new Dimension(361, 30));
        totalPLb.setPreferredSize(new Dimension(64, 30));

        totalPanel.add(totalTLb);
        totalPanel.add(totalPLb);

        mainPanel.add(totalPanel);

        // Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener((ActionEvent e) -> {
            ShoppingCartGUI cgui = new ShoppingCartGUI();
            setVisible(false);
            cgui.setVisible(true);
        });
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.addActionListener((ActionEvent e) -> {
            WaitingGUI gui = new WaitingGUI(totalCount);
            gui.showWait();
            dispose();
        });
        buttonPanel.add(btnReturn, BorderLayout.WEST);
        buttonPanel.add(btnConfirm, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private double getItemPrice(Item item) {
        return switch (item.getSize()) {
            case "Small" -> item.getSmallPrice() * item.getQuantity();
            case "Medium" -> item.getMediumPrice() * item.getQuantity();
            case "Large" -> item.getLargePrice() * item.getQuantity();
            default -> 0;
        };
    }

    public void showPay() {
        setVisible(true);
    }

}