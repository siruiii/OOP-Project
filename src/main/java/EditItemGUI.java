import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class EditItemGUI {

  private JFrame frame;

  /**
   * Create the application.
   */
  public EditItemGUI() {
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

    JLabel lblNewLabel = new JLabel("Edit Item");
        lblNewLabel.setBounds(16, 7, 200, 16);
        frame.getContentPane().add(lblNewLabel);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(164, 218, 117, 29);
        frame.getContentPane().add(btnSave);
        // btnSave.addActionListener(e -> {
        //     ShoppingCartGUI cgui = new ShoppingCartGUI();  // Create and show the edit window
        //     cgui.setVisible(true);
        // });
  }
  public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
