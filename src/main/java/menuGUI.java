import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;

public class menuGUI extends JFrame {

    private JPanel contentPane;
    private addItemGUI addItemFrame;
    private List<Item> items;
    private JTextPane textPane;
    private JButton btnSearch;
    private JButton btnCart;
    private JScrollPane scrollPane;
    private JLabel lblinstruction;
    private int hoverLine = -1;
    private int clickedLine = -1;
    private Style normalStyle, hoverStyle, clickedStyle;


    public menuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(16, 6, 117, 29);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToSearch();
            }
        });

        btnCart = new JButton("View Shopping Cart");
        btnCart.setBounds(16, 237, 200, 29);
        contentPane.add(btnCart);
        btnCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });

        textPane = new JTextPane();
        textPane.setEditable(false);

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(29, 47, 388, 160);
        contentPane.add(scrollPane);

        addItemFrame = new addItemGUI(this);

        FileManager fileManager = new FileManager("/Users/annabella/Desktop/Eclipse/OOP team project/src/itemfile.txt");
        items = fileManager.getItems();
        showItems();

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewItem(e);
                }
            }
        });

        lblinstruction = new JLabel(" ");
        lblinstruction.setBounds(29, 210, 300, 16);
        Font currentFont = lblinstruction.getFont();
        lblinstruction.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblinstruction);

        StyledDocument doc = textPane.getStyledDocument();
        normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);

        hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);

        clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);

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
                        for (Item item : items) {
                            if (selectedItem.startsWith(item.getName())) {
                                lblinstruction.setText("Double Click to Add " + item.getName());
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            lblinstruction.setText(" ");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

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

    private void showItems() {
        StringBuilder displayText = new StringBuilder();
        for (Item item : items) {
            displayText.append(item.getName())
                       .append(" - Rating: ")
                       .append(item.getRatingStatus())
                       .append(" ★")
                       .append("\n");
        }
        textPane.setText(displayText.toString());
    }

    private void viewItem(MouseEvent e) {
        try {
            Point2D point2D = new Point2D.Double(e.getPoint().getX(), e.getPoint().getY());
            int offset = textPane.viewToModel2D(point2D);
            int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
            String selectedItem = textPane.getDocument().getText(
                    textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                    textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                            textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                    .getStartOffset())
                    .trim();

            for (Item item : items) {
                if (selectedItem.startsWith(item.getName())) {
                    addItemFrame.setItemDetails(item.getName(), item.getCategory(), item.getSmallPrice(),
                            item.getMediumPrice(), item.getLargePrice());
                    setVisible(false);
                    addItemFrame.setBtnBack("Back to Menu");
                    addItemFrame.setVisible(true);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void goToSearch(){
        SearchGUI sgui = new SearchGUI(items);
        setVisible(false);
        sgui.setVisible(true);
    }

    private void viewCart(){
        ShoppingCartGUI cgui = new ShoppingCartGUI();
        setVisible(false);
        cgui.setVisible(true);
    }

    public void showMenu() {
        setVisible(true);
    }
}
