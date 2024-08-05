import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class startGUI extends JFrame {

  private JPanel contentPane;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          startGUI frame = new startGUI();
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
  public startGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblNewLabel = new JLabel("Restaurant Orders");
    lblNewLabel.setBounds(138, 17, 174, 60);
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
    contentPane.add(lblNewLabel);

    JButton btnNewButton = new JButton("In Store");
    btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      }
    });
    btnNewButton.setBounds(91, 141, 101, 37);
    contentPane.add(btnNewButton);

    JButton btnNewButton_1 = new JButton("Takeouts");
    btnNewButton_1.setBounds(268, 141, 101, 37);
    contentPane.add(btnNewButton_1);
  }
}
