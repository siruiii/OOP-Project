import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

class ShoppingCartGUI { 
    public void show() {
        JFrame jFrame = new JFrame("Shopping Cart");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(500, 360);
      
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}