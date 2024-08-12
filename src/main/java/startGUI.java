import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class startGUI extends JFrame {
  private static boolean isTakeOut = false;
  private JPanel contentPane;
  private JLabel orderLabel;
  private JButton btnins;
  private JButton btntake;

  public startGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    orderLabel = new JLabel("Restaurant Orders");
    orderLabel.setBounds(138, 17, 174, 60);
    orderLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
    contentPane.add(orderLabel);

    btnins = new JButton("In Store");
    btnins.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
    btnins.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        entermenu();
      }
    });
    btnins.setBounds(91, 141, 101, 37);
    contentPane.add(btnins);

    btntake = new JButton("Takeouts");
    btntake.setBounds(268, 141, 101, 37);
    btntake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        isTakeOut = true;
        entermenu();
      }
    });
    contentPane.add(btntake);
  }

  public static boolean feeCheck(){
    return isTakeOut;
  }

  private void entermenu() {
    menuGUI menuFrame = new menuGUI();
    this.setVisible(false);
    menuFrame.setVisible(true);
  }
}
