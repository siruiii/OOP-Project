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
    private List<Item> items;
    private JTextPane textPane;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("Search");
        btnNewButton.setBounds(16, 6, 117, 29);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchGUI sgui = new SearchGUI();
                setVisible(false);
                sgui.setVisible(true);
            }
        });


        JButton btnNewButton_1 = new JButton("Cart");
        btnNewButton_1.setBounds(16, 237, 117, 29);
        contentPane.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI cgui = new ShoppingCartGUI();
                setVisible(false);
                cgui.setVisible(true);
            }
        });

        textPane = new JTextPane();
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(83, 47, 294, 179);
        contentPane.add(scrollPane);


        addItemFrame = new addItemGUI(this);

        FileManager fileManager = new FileManager("itemfile.txt");
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

    public void showMenu() {
        setVisible(true);
    }
}
