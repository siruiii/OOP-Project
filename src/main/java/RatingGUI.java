import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class RatingGUI extends JFrame {
    private final List<Item> cartItems;
    private final FileManager save;
    private final JPanel panel;
    private final JButton finishButton;
    private JTextArea feedbackTextArea;
    private List<JComboBox<String>> ratingComboBoxes;

    // Constructor
    public RatingGUI() {
        save = new FileManager("itemfile.txt"); // Load items from file
        cartItems = CartManager.getSortedCart(); // Get sorted items from the cart
        ratingComboBoxes = new ArrayList<>();

        setTitle("Rating & Feedback");
        setBounds(100, 100, 450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with GridBagLayout for precise control
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Order complete label, centered and bold
        JLabel orderCompleteLabel = new JLabel("Yay! The Order is Complete!");
        orderCompleteLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(orderCompleteLabel, gbc);

        // Adding some space between the order complete label and the next section
        gbc.gridy++;
        panel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

        Font subtitleFont = new Font("Arial", Font.BOLD, 14); 

        // Instruction label before the rating spinners
        JLabel instructLabel = new JLabel("Enjoying what you have? Consider rating from 0 to 5 stars!");
        gbc.gridy++;
        gbc.gridwidth = 3;
        instructLabel.setFont(subtitleFont);
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instructLabel, gbc);

        // Adding each non-repeated item and its rating spinner to the panel
        gbc.gridwidth = 1;
        Set<String> addedItems = new HashSet<>(); // Set to track added item names
        for (Item item : cartItems) {
            if (!addedItems.contains(item.getName())) {
                gbc.gridy++;
                gbc.gridx = 0;

                JLabel itemLabel = new JLabel(item.getName());
                panel.add(itemLabel, gbc);

                gbc.gridx = 1;
                String[] ratings = {"", "0", "1", "2", "3", "4", "5"};
                JComboBox<String> ratingComboBox = new JComboBox<>(ratings);
                ratingComboBox.setPreferredSize(new Dimension(100, 25));
                ratingComboBoxes.add(ratingComboBox);
                panel.add(ratingComboBox, gbc);

                gbc.gridx = 2;
                JLabel starLabel = new JLabel("★");
                starLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                panel.add(starLabel, gbc);

                // Add the item name to the set to avoid duplicates
                addedItems.add(item.getName());
            }
        }

        // Feedback label, aligned to the left
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JLabel feedbackLabel = new JLabel("Questions? Complaints? Feel free to comment below:");
        feedbackLabel.setFont(subtitleFont);
        feedbackLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(feedbackLabel, gbc);

        // Feedback text area
        gbc.gridy++;
        feedbackTextArea = new JTextArea(7, 30); // Increased height for feedback box
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackTextArea);
        feedbackScrollPane.setPreferredSize(new Dimension(400, 100));
        panel.add(feedbackScrollPane, gbc);

        // Finish button, aligned to the bottom right
        gbc.gridy++;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;  // Prevent resizing the button
        gbc.weightx = 0;  // Do not grow horizontally
        gbc.weighty = 0;  // Do not grow vertically
        finishButton = new JButton("Finish");
        finishButton.setPreferredSize(new Dimension(100, 30));
        finishButton.addActionListener(this::clickFinish);
        panel.add(finishButton, gbc);

        // Wrap the panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scrollPane to the frame instead of the panel
        add(scrollPane);
    }

    // Method to handle the Finish button click
    private void clickFinish(ActionEvent e) {
        // Update ratings in FileManager
        for (int i = 0; i < cartItems.size(); i++) {
            Item cartItem = cartItems.get(i);
            String rating = (String) ratingComboBoxes.get(i).getSelectedItem();

            if (rating != null && !rating.isEmpty()) {
                double ratingValue = Double.parseDouble(rating);
                save.updateItemRating(cartItem.getName(), ratingValue);
            }
        }
        
        // Write available feedback using FileManager
        String feedbackText = feedbackTextArea.getText().trim();
        if (!feedbackText.isEmpty()) {
            save.writeFeedback(feedbackText);
        }
        
        // Save updated items back to menuitem.txt
        save.saveItems("itemfile.txt");

        // Show thank you message in a pop-up box
        JOptionPane.showMessageDialog(panel, "Thank you so much! Bye for Now!", "Gratitude Message", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    public void showRate() {
        setVisible(true);
    }

    /* Debugger
    public static void main(String[] args) {
        RatingGUI gui = new RatingGUI();
        gui.showRate(); // Show the payment window
    } */
}