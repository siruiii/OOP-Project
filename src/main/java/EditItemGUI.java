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
import javax.swing.JOptionPane;

public class EditItemGUI extends JFrame {

    private JPanel contentPane;
    private JButton btnBack;
    private JTextField textField;
    private String itemName;
    private String category;
    private menuGUI menuFrame;
    private JCheckBox chckbxSmall, chckbxMedium, chckbxLarge;
    private double smallPrice, mediumPrice, largePrice;
    private JButton btnDelete;
    private int i;
    private Item selectedItem;
    private JLabel lblItem;
    private JLabel lblSize;
    private JButton btnSave;
    private JLabel lblQuantity;


    public EditItemGUI(menuGUI menuFrame) {
        this.menuFrame = menuFrame;
        setTitle("Edit item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblItem = new JLabel("Item: ");
        lblItem.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblItem.setBounds(84, 47, 300, 16);
        contentPane.add(lblItem);

        btnBack = new JButton("Back to Cart");
        btnBack.setBounds(21, 2, 117, 29);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        getContentPane().add(btnBack);

        lblSize = new JLabel("Size:");
        lblSize.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblSize.setBounds(84, 92, 61, 16);
        contentPane.add(lblSize);

        chckbxSmall = new JCheckBox("Small: $0.00");
        chckbxSmall.setBounds(166, 88, 150, 23);
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

        chckbxMedium = new JCheckBox("Medium: $0.00");
        chckbxMedium.setBounds(166, 113, 150, 23);
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

        chckbxLarge = new JCheckBox("Large: $0.00");
        chckbxLarge.setBounds(166, 138, 150, 23);
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

        btnSave = new JButton("Save Change");
        btnSave.setBounds(315, 226, 117, 29);
        contentPane.add(btnSave);

        lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblQuantity.setBounds(84, 186, 87, 16);
        contentPane.add(lblQuantity);

        textField = new JTextField();
        textField.setBounds(176, 181, 117, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            clickDelete();
          }
        });
        btnDelete.setBounds(186, 226, 117, 29);
        contentPane.add(btnDelete);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickSave(null,0);
            }
        });
    }

    public void setItemDetails(int index) {
        i=index;
        selectedItem=CartManager.readCartItem().get(i);
        this.itemName = selectedItem.getName();
        this.category = selectedItem.getCategory();
        this.smallPrice = selectedItem.getSmallPrice();
        this.mediumPrice = selectedItem.getMediumPrice();
        this.largePrice = selectedItem.getLargePrice();

        JLabel lblNewLabel = (JLabel) contentPane.getComponent(0);
        lblNewLabel.setText("Item: " + itemName);

        chckbxSmall.setText("Small: $" + smallPrice);
        chckbxMedium.setText("Medium: $" + mediumPrice);
        chckbxLarge.setText("Large: $" + largePrice);
        if(selectedItem.getSize()=="Small"){
            chckbxSmall.setSelected(true);
        chckbxMedium.setSelected(false);
        chckbxLarge.setSelected(false);
        }else if(selectedItem.getSize()=="Medium"){
            chckbxSmall.setSelected(false);
        chckbxMedium.setSelected(true);
        chckbxLarge.setSelected(false);
        }else if(selectedItem.getSize()=="Large"){
            chckbxSmall.setSelected(false);
        chckbxMedium.setSelected(false);
        chckbxLarge.setSelected(true);
        }
        textField.setText(selectedItem.getQuantity()+ "");
    }
    private void goBack(){
        setVisible(false);
                ShoppingCartGUI cgui= new ShoppingCartGUI();
                cgui.setVisible(true);
    }
    private void showCart(){
        setVisible(false);
        ShoppingCartGUI cgui= new ShoppingCartGUI();
        cgui.setVisible(true);
    }
    private void clickDelete(){
        CartManager.deleteItem(i);
        setVisible(false);
        ShoppingCartGUI cgui= new ShoppingCartGUI();
        cgui.setVisible(true);
    }
    private void clickSave(String selectedSize, int quantity){
        double price = 0.0; 

        if (chckbxSmall.isSelected()) {
            selectedSize = "Small";
            price = smallPrice;
        } else if (chckbxMedium.isSelected()) {
            selectedSize = "Medium";
            price = mediumPrice;
        } else if (chckbxLarge.isSelected()) {
            selectedSize = "Large";
            price = largePrice;
        }

        try {
            quantity = Integer.parseInt(textField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid quantity.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate selection and quantity
        if (selectedSize != null && quantity > 0) {

            CartManager.editItem(i,quantity,selectedSize);
            JOptionPane.showMessageDialog(null,
                    "Saved: " + itemName + " - " + selectedSize + " x" + quantity, "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            showCart();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a size and enter a valid quantity.",
                    "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
