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

public class addItemGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private String itemName;
    private menuGUI menuFrame;
    private JCheckBox chckbxSmall, chckbxMedium, chckbxLarge;

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
        this.itemName = itemName;

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

        chckbxSmall = new JCheckBox("Small");
        chckbxSmall.setBounds(134, 83, 73, 23);
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


        chckbxMedium = new JCheckBox("Medium");
        chckbxMedium.setBounds(205, 83, 87, 23);
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


        chckbxLarge = new JCheckBox("Large");
        chckbxLarge.setBounds(293, 83, 128, 23);
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
        btnNewButton_1.setBounds(175, 177, 117, 29);
        contentPane.add(btnNewButton_1);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblQuantity.setBounds(81, 125, 87, 16);
        contentPane.add(lblQuantity);

        textField = new JTextField();
        textField.setBounds(194, 120, 130, 26);
        contentPane.add(textField);
        textField.setColumns(10);
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
        JLabel lblNewLabel = (JLabel) contentPane.getComponent(0);
        lblNewLabel.setText("Item: " + itemName);
    }
}
