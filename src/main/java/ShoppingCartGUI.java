import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class ShoppingCartGUI extends JFrame {
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JLabel lblTotalCount;
    private JLabel lblTotalPrice;
    private JLabel lblInstruction;
    private JButton btnBack;
    private JButton btnCheckout;
    private JButton btnReset;
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
        // GUI settings
        setTitle("Shopping Cart");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        btnBack = new JButton("Back to Menu");
        btnBack.setBounds(21, 2, 117, 29);
        btnBack.addActionListener(e -> {
            goBack();
        });
        getContentPane().add(btnBack);

        btnCheckout = new JButton("Checkout");
        btnCheckout.setBounds(346, 237, 98, 29);
        btnCheckout.addActionListener(e -> {
            // Add logic to proceed to checkout
            clickCheckout();
        });
        getContentPane().add(btnCheckout);

        scrollPane = new JScrollPane();
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
        lblTotalPrice.setBounds(150, 242, 200, 16);
        getContentPane().add(lblTotalPrice);

        lblTotalCount = new JLabel("Item Count: N/A");
        lblTotalCount.setBounds(29, 242, 129, 16);
        getContentPane().add(lblTotalCount);

        btnReset = new JButton("Reset");
        btnReset.setBounds(346, 2, 98, 29);
        btnReset.addActionListener(e -> {
            clickReset();
        });
        getContentPane().add(btnReset);

        lblInstruction = new JLabel(" ");
        lblInstruction.setBounds(29, 210, 300, 16);
        Font currentFont = lblInstruction.getFont();
        lblInstruction.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblInstruction);

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
                        if (!selectedItem.isEmpty()) {
                            char index = selectedItem.charAt(0);
                            int i = Character.getNumericValue(index) - 1;
                            if (i >= 0 && i < CartManager.readCartItem().size()) {
                                lblInstruction
                                        .setText("Double Click to Edit " + CartManager.readCartItem().get(i).getName());
                                found = true;
                            }
                        }
                        if (!found) {
                            lblInstruction.setText(" ");
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

                        if (!selectedItem.isEmpty()) {
                            char index = selectedItem.charAt(0);
                            int i = Character.getNumericValue(index) - 1;
                            if (i >= 0 && i < CartManager.readCartItem().size()) {
                                showEdit(i);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void displayCart() {
        StringBuilder displayText = new StringBuilder();

        if (CartManager.readCartItem().isEmpty()) {
            lblTotalCount.setText("Item Count: 0");
            lblTotalPrice.setText("Total Price: 0");
            textPane.setText(
                    "\n\n                    ---Shopping Cart is empty now---\n\n                    ---Back to Menu to add items---");
        } else {
            for (int i = 0; i < CartManager.readCartItem().size(); i++) {
                Item item = CartManager.readCartItem().get(i);
                double unitprice = 0;
                if ("Small".equals(item.getSize())) {
                    unitprice = item.getSmallPrice();
                } else if ("Medium".equals(item.getSize())) {
                    unitprice = item.getMediumPrice();
                } else if ("Large".equals(item.getSize())) {
                    unitprice = item.getLargePrice();
                }
                displayText.append(i + 1)
                        .append(" - ")
                        .append(item.getName())
                        .append(" - Size: ")
                        .append(item.getSize())
                        .append(" - Qty: ")
                        .append(item.getQuantity())
                        .append(" - Unit Price: ")
                        .append(unitprice)
                        .append("\n");
            }
            textPane.setText(displayText.toString());
            lblTotalCount.setText("Item Count: " + CartManager.getTotalCount());
            lblTotalPrice.setText("Total Price: " + CartManager.getTotalPrice());
        }
    }

    private int getLineAtPosition(int pos) {
        try {
            return textPane.getDocument().getDefaultRootElement().getElementIndex(pos);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void updateTextPaneStyles() {
        StyledDocument doc = textPane.getStyledDocument();
        int numLines = textPane.getDocument().getDefaultRootElement().getElementCount();
        for (int i = 0; i < numLines; i++) {
            Element lineElem = textPane.getDocument().getDefaultRootElement().getElement(i);
            if (i == clickedLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), clickedStyle, false);
            } else if (i == hoverLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), hoverStyle, false);
            } else {
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), normalStyle, false);
            }
        }
    }

    private void clickReset() {
        CartManager.resetCart();
        displayCart();
    }

    private void clickCheckout(){
        System.out.println("Checkout button clicked");
            PaymentGUI paymentGUI = new PaymentGUI();
            setVisible(false);
            paymentGUI.showPay();
    }

    private void showEdit(int i){
        EditItemGUI editCurrentLine = new EditItemGUI(null);
        editCurrentLine.setItemDetails(i);
        setVisible(false);
        editCurrentLine.setVisible(true);
    }

    private void goBack() {
        menuGUI mgui = new menuGUI();
        setVisible(false);
        mgui.setVisible(true);
    }
}