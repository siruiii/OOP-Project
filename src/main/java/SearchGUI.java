package oopProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class SearchGUI {

  private JFrame frame;
  private JTextField textField;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          SearchGUI window = new SearchGUI();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public SearchGUI() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JLabel lblNewLabel = new JLabel("Search");
    lblNewLabel.setBounds(29, 20, 61, 16);
    frame.getContentPane().add(lblNewLabel);

    textField = new JTextField();
    textField.setBounds(92, 15, 200, 26);
    frame.getContentPane().add(textField);
    textField.setColumns(10);

    JTextPane textPane = new JTextPane();
    textPane.setBounds(29, 74, 392, 145);
    frame.getContentPane().add(textPane);

    JLabel lblFilter = new JLabel("Filter");
    lblFilter.setBounds(29, 46, 61, 16);
    frame.getContentPane().add(lblFilter);

    JComboBox comboBox = new JComboBox();
    comboBox.setBounds(77, 42, 52, 27);
    frame.getContentPane().add(comboBox);

    JComboBox comboBox_1 = new JComboBox();
    comboBox_1.setBounds(127, 42, 52, 27);
    frame.getContentPane().add(comboBox_1);
  }
}
