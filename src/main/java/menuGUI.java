import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.EmptyBorder;

// import javafx.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.ActionEvent;

import java.util.List;



public class menuGUI extends JFrame {

    private JPanel contentPane;
    private addItemGUI addItemFrame;

    /**
     * Launch the application.
     */
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

    /**
     * Create the frame.
     */
    public menuGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(16, 6, 117, 29);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchGUI sgui = new SearchGUI();
                setVisible(false);
                sgui.setVisible(true);
            }
        });

        JButton btnCart = new JButton("Cart");
        btnCart.setBounds(16, 237, 117, 29);
        contentPane.add(btnCart);
        btnCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI cgui = new ShoppingCartGUI();
                setVisible(false);
                cgui.setVisible(true);
            }
        });

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(83, 47, 294, 179);
        contentPane.add(scrollPane);

        // Load items from file
        FileManager fileManager = new FileManager("itemfile.txt");
        List<Item> items = fileManager.getItems();

        // Display items with categories and ratings
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
                        int offset = textPane.viewToModel(e.getPoint());
                        int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
                        String selectedItem = textPane.getDocument().getText(
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                                        textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                                .getStartOffset())
                                .trim();

                        for (Item item : items) {
                            if (selectedItem.startsWith(item.getName())) {
                                addItemFrame.setItemDetails(item.getName(), item.getcategory(), item.getSmallPrice(),
                                        item.getMediumPrice(), item.getLargePrice());
                                setVisible(false);
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
