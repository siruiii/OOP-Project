import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class addItemGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private String itemName;
    private String category;
    private menuGUI menuFrame;
    private JCheckBox chckbxSmall, chckbxMedium, chckbxLarge;
    private double smallPrice, mediumPrice, largePrice;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    addItemGUI frame = new addItemGUI(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public addItemGUI(menuGUI menuFrame) {
        this.menuFrame = menuFrame;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Item: ");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblNewLabel.setBounds(74, 47, 300, 16);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Back");
        btnNewButton.setBounds(6, 6, 87, 29);
        contentPane.add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menuFrame.showMenu();
            }
        });

        JLabel lblSize = new JLabel("Size:");
        lblSize.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblSize.setBounds(84, 87, 61, 16);
        contentPane.add(lblSize);

        chckbxSmall = new JCheckBox("Small: $0.00");
        chckbxSmall.setBounds(134, 83, 150, 23);
        contentPane.add(chckbxSmall);
        chckbxSmall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chckbxSmall.isSelected()) {
                    chckbxMedium.setSelected(false);
                    chckbxLarge.setSelected(false);
                }
            }
        });

        chckbxMedium = new JCheckBox("Medium: $0.00");
        chckbxMedium.setBounds(134, 108, 150, 23);
        contentPane.add(chckbxMedium);
        chckbxMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chckbxMedium.isSelected()) {
                    chckbxSmall.setSelected(false);
                    chckbxLarge.setSelected(false);
                }
            }
        });

        chckbxLarge = new JCheckBox("Large: $0.00");
        chckbxLarge.setBounds(134, 133, 150, 23);
        contentPane.add(chckbxLarge);
        chckbxLarge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chckbxLarge.isSelected()) {
                    chckbxSmall.setSelected(false);
                    chckbxMedium.setSelected(false);
                }
            }
        });

        JButton btnNewButton_1 = new JButton("Add to Cart");
        btnNewButton_1.setBounds(167, 206, 117, 29);
        contentPane.add(btnNewButton_1);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblQuantity.setBounds(81, 162, 87, 16);
        contentPane.add(lblQuantity);

        textField = new JTextField();
        textField.setBounds(194, 157, 130, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCart();
            }
        });
    }
    private void addItemToCart() {
        String selectedSize = null;
        double price = 0.0;
        if (chckbxSmall.isSelected()) {
            selectedSize = "Small";
            price = smallPrice;
        } else if (chckbxMedium.isSelected()) {
            selectedSize = "Medium";
            price = mediumPrice;
        } else if (chckbxLarge.isSelected()) {
            selectedSize = "Large";
            price = largePrice;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedSize != null && quantity > 0) {
            Item newItem = new Item(itemName, category, selectedSize, price, smallPrice, mediumPrice, largePrice, quantity);
            CartManager.addItem(newItem);
            JOptionPane.showMessageDialog(null, "Added to cart: " + itemName + " - " + selectedSize + " x" + quantity, "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            menuFrame.showMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a size and enter a valid quantity.", "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setItemDetails(String itemName, String category, double smallPrice, double mediumPrice, double largePrice) {
        this.itemName = itemName;
        this.category = category;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;

        JLabel lblNewLabel = (JLabel) contentPane.getComponent(0);
        lblNewLabel.setText("Item: " + itemName);

        chckbxSmall.setText("Small: $" + smallPrice);
        chckbxMedium.setText("Medium: $" + mediumPrice);
        chckbxLarge.setText("Large: $" + largePrice);

        chckbxSmall.setSelected(false);
        chckbxMedium.setSelected(false);
        chckbxLarge.setSelected(false);
        textField.setText("");
    }
}
