import java.awt.EventQueue;

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

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          addItemGUI frame = new addItemGUI();
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
  public addItemGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblNewLabel = new JLabel("Item: ");
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
    lblNewLabel.setBounds(74, 47, 61, 16);
    contentPane.add(lblNewLabel);

    JButton btnNewButton = new JButton("Back");
    btnNewButton.setBounds(6, 6, 87, 29);
    contentPane.add(btnNewButton);

    JLabel lblSize = new JLabel("Size:");
    lblSize.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
    lblSize.setBounds(84, 83, 61, 16);
    contentPane.add(lblSize);

    JCheckBox chckbxNewCheckBox = new JCheckBox("Small");
    chckbxNewCheckBox.setBounds(134, 83, 73, 23);
    contentPane.add(chckbxNewCheckBox);

    JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Medium");
    chckbxNewCheckBox_1.setBounds(205, 83, 87, 23);
    contentPane.add(chckbxNewCheckBox_1);

    JButton btnNewButton_1 = new JButton("Add to Cart");
    btnNewButton_1.setBounds(175, 177, 117, 29);
    contentPane.add(btnNewButton_1);

    JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Large");
    chckbxNewCheckBox_2.setBounds(293, 83, 128, 23);
    contentPane.add(chckbxNewCheckBox_2);

    JLabel lblQuantity = new JLabel("Quantity:");
    lblQuantity.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
    lblQuantity.setBounds(84, 120, 87, 16);
    contentPane.add(lblQuantity);

    textField = new JTextField();
    textField.setBounds(194, 120, 130, 26);
    contentPane.add(textField);
    textField.setColumns(10);
  }
}
