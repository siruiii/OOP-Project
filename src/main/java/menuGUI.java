import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;

public class menuGUI extends JFrame {

  private JPanel contentPane;

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

    JButton btnNewButton = new JButton("Search");
    btnNewButton.setBounds(16, 6, 117, 29);
    contentPane.add(btnNewButton);

    JButton btnNewButton_1 = new JButton("Cart");
    btnNewButton_1.setBounds(16, 237, 117, 29);
    contentPane.add(btnNewButton_1);

    JTextPane textPane = new JTextPane();
    textPane.setBounds(83, 47, 294, 179);
    contentPane.add(textPane);

    JScrollBar scrollBar = new JScrollBar();
    scrollBar.setBounds(377, 47, 14, 179);
    contentPane.add(scrollBar);
  }
}
