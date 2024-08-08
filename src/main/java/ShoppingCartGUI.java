import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.*;

public class ShoppingCartGUI extends JFrame {
    private JTextPane textPane;
    private JLabel lblTotalCount;
    private JLabel lblTotalPrice;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ShoppingCartGUI window = new ShoppingCartGUI();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ShoppingCartGUI() {
        setTitle("Shopping Cart");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        JButton btnBack = new JButton("Back to Menu");
        btnBack.setBounds(21, 2, 117, 29);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to proceed to checkout
                menuGUI mgui = new menuGUI();
                setVisible(false);
                mgui.setVisible(true);
            }
        });
        getContentPane().add(btnBack);

        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setBounds(346, 237, 98, 29);
        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to proceed to checkout
                System.out.println("Checkout button clicked");
            }
        });
        getContentPane().add(btnCheckout);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 40, 388, 170);
        getContentPane().add(scrollPane);

        textPane = new JTextPane();
        scrollPane.setViewportView(textPane);


        lblTotalPrice = new JLabel("Total Price: N/A");
        lblTotalPrice.setBounds(123, 242, 129, 16);
        getContentPane().add(lblTotalPrice);

        lblTotalCount = new JLabel("Total Count: N/A");
        lblTotalCount.setBounds(29, 242, 129, 16);
        getContentPane().add(lblTotalCount);

        JButton btnResetShoppingCart = new JButton("Reset");
        btnResetShoppingCart.setBounds(346, 2, 98, 29);
        getContentPane().add(btnResetShoppingCart);

        JLabel lblNewLabel_1 = new JLabel("Double Click to Edit");
        lblNewLabel_1.setBounds(29, 210, 223, 16);
        Font currentFont = lblNewLabel_1.getFont();
        lblNewLabel_1.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblNewLabel_1);

        displayCart();
    }
    private void displayCart(){
        StringBuilder displayText = new StringBuilder();
            for (Item item : CartManager.readCartItem()) {
                double unitprice=0;
                if(item.getSize() == "Small"){
                    unitprice=item.getSmallPrice();
                }else if(item.getSize() == "Medium"){
                    unitprice=item.getMediumPrice();
                }else if(item.getSize() == "Large"){
                    unitprice=item.getLargePrice();
                }
                displayText.append(item.getName())
                        .append(" - Size: ")
                        .append(item.getSize())
                        .append(" - Qty: ")
                        .append(item.getQuantity())
                        .append(" - Unit Price: ")
                        .append(unitprice)
                        .append("\n");
            }
            textPane.setText(displayText.toString());
            lblTotalCount.setText("Count: "+CartManager.getTotalCount());
            lblTotalPrice.setText("Total Price: "+CartManager.getTotalPrice());
    }
}