import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.*;

public class ShoppingCartGUI extends JFrame {
    private JTextPane textPane;
    private JLabel lblTotalCount;
    private JLabel lblTotalPrice;
    private int hoverLine = -1;
    private int clickedLine = -1;
    private Style normalStyle, hoverStyle, clickedStyle;

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
                PaymentGUI paymentGUI = new PaymentGUI();
                setVisible(false);
                paymentGUI.showPay();
            }
        });
        getContentPane().add(btnCheckout);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 40, 388, 170);
        getContentPane().add(scrollPane);

        textPane = new JTextPane();
        scrollPane.setViewportView(textPane);
        StyledDocument doc = textPane.getStyledDocument();
        normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);

        hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);

        clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);


        lblTotalPrice = new JLabel("Total Price: N/A");
        lblTotalPrice.setBounds(150, 242, 129, 16);
        getContentPane().add(lblTotalPrice);

        lblTotalCount = new JLabel("Item Count: N/A");
        lblTotalCount.setBounds(29, 242, 129, 16);
        getContentPane().add(lblTotalCount);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(346, 2, 98, 29);
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartManager.resetCart();
                displayCart();
            }
        });
        getContentPane().add(btnReset);

        JLabel lblNewLabel_1 = new JLabel(" ");
        lblNewLabel_1.setBounds(29, 210, 300, 16);
        Font currentFont = lblNewLabel_1.getFont();
        lblNewLabel_1.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblNewLabel_1);

        displayCart();

        textPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int pos = textPane.viewToModel2D(e.getPoint());
                int line = getLineAtPosition(pos);

                if (line != hoverLine) {
                    hoverLine = line;
                    updateTextPaneStyles();
                    try {
                        int offset = textPane.viewToModel2D(e.getPoint());
                        int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
                        String selectedItem = textPane.getDocument().getText(
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                                        textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                                .getStartOffset())
                                .trim();

                                boolean found = false;
                                for (Item item : CartManager.readCartItem()) {
                                    if (selectedItem.startsWith(item.getName())) {
                                        lblNewLabel_1.setText("Double Click to Edit " + item.getName());
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    lblNewLabel_1.setText(" ");
                                }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditItemGUI editCurrentLine = new EditItemGUI(null);
                if (e.getClickCount() == 2) {
                    try {
                        int offset = textPane.viewToModel2D(e.getPoint());
                        int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
                        String selectedItem = textPane.getDocument().getText(
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                                        textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                                .getStartOffset())
                                .trim();

                        for (Item item : CartManager.readCartItem()) {
                            if (selectedItem.startsWith(item.getName())) {
                                editCurrentLine.setItemDetails(item);
                                setVisible(false);
                                editCurrentLine.setVisible(true);
                                break;
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    private void displayCart(){
        StringBuilder displayText = new StringBuilder();
        if(CartManager.readCartItem().size()==0){
            lblTotalCount.setText("Item Count: 0");
            lblTotalPrice.setText("Total Price: 0");
            textPane.setText("\n\n                    ---Shopping Cart is empty now---\n\n                    ---Back to Menu to add items---");
        }else{
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
            lblTotalCount.setText("Item Count: "+CartManager.getTotalCount());
            lblTotalPrice.setText("Total Price: "+CartManager.getTotalPrice());
        }  
    }
    private int getLineAtPosition(int pos) {
        try {
            return textPane.getDocument().getDefaultRootElement().getElementIndex(pos);
        } catch (Exception e) {
            return -1;
        }
    }

    private void updateTextPaneStyles() {
        StyledDocument doc = textPane.getStyledDocument();
        int numLines = textPane.getDocument().getDefaultRootElement().getElementCount();
        for (int i = 0; i < numLines; i++) {
            Element lineElem = textPane.getDocument().getDefaultRootElement().getElement(i);
            if (i == clickedLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), clickedStyle, false);
            } else if (i == hoverLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), hoverStyle, false);
            } else {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), normalStyle, false);
            }
        }
    }
}