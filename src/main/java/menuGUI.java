import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.List;

public class menuGUI extends JFrame {

    private JPanel contentPane;
    private addItemGUI addItemFrame;
    private JButton btnSearch;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    menuGUI frame = new menuGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public menuGUI() {
        // GUI settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Go to Search GUI
        btnSearch = new JButton("Search");
        btnSearch.setBounds(16, 6, 117, 29);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchGUI sgui = new SearchGUI();
                setVisible(false);
                sgui.setVisible(true);
            }
        });

        //Go to Shopping Cart
        JButton btnCart = new JButton("View Shopping Cart");
        btnCart.setBounds(16, 237, 200, 29);
        contentPane.add(btnCart);
        btnCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI cgui = new ShoppingCartGUI();
                setVisible(false);
                cgui.setVisible(true);
            }
        });

        // Pane to display menu
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(29, 40, 388, 179);
        contentPane.add(scrollPane);

        // Load menu from file
        FileManager fileManager = new FileManager("itemfile.txt", false);
        List<Item> items = fileManager.getItems();

        // Display items with ratings
        StringBuilder displayText = new StringBuilder();
        for (Item item : items) {
            displayText.append(item.getName())
                    .append(" - Rating: ")
                    .append(item.getRatingStatus())
                    .append(" ★")
                    .append("\n");
        }
        textPane.setText(displayText.toString());

        addItemFrame = new addItemGUI(this);
        textPane.addMouseListener(new MouseAdapter() {
        @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
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
            }
        });

    }

    public void showMenu() {
        setVisible(true);
    }
}
