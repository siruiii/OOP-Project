import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class RatingGUI extends JFrame {
    private final JPanel panel;
    private final JButton finishButton;
    private final List<Item> cartItems;
    private JTextArea feedbackTextArea;
    private List<JComboBox<String>> ratingComboBoxes;
    private final FileManager file;


    public RatingGUI() {
        file = new FileManager("itemfile.txt"); // Load items from file
        cartItems = CartManager.readCartItem(); // Get items from the cart
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
        JLabel orderCompleteLabel = new JLabel("Yay! The order is complete!");
        orderCompleteLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(orderCompleteLabel, gbc);

        // Adding some space between the order complete label and the next section
        gbc.gridy++;
        panel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

        // Instruction label before the rating spinners
        JLabel instructionLabel = new JLabel("Enjoying what you have? Please consider rating from 0 to 5 stars!");
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(instructionLabel, gbc);

        // Adding each item and its rating spinner to the panel
        gbc.gridwidth = 1;
        for (Item item : cartItems) {
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
        }

        // Feedback label, aligned to the left
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        JLabel feedbackLabel = new JLabel("Questions? Complaints? Feel free to comment below:");
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
        finishButton.addActionListener(new FinishButtonListener());
        panel.add(finishButton, gbc);

        add(panel);
    }

    // Listener for the Finish button
    private class FinishButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update ratings in FileManager
            for (int i = 0; i < cartItems.size(); i++) {
                Item cartItem = cartItems.get(i);
                String rating = (String) ratingComboBoxes.get(i).getSelectedItem();

                if (rating != null && !rating.isEmpty()) {
                    double ratingValue = Double.parseDouble(rating);
                    file.updateItemRating(cartItem.getName(), ratingValue);
                }
            }
            // Save updated items back to menuitem.txt
            file.saveItems("itemfile.txt");

            // Write feedback to Feedback.txt
            try (BufferedWriter feedbackWriter = new BufferedWriter(new FileWriter("Feedback.txt", true))) {
                String feedback = feedbackTextArea.getText().trim();
                if (!feedback.isEmpty()) {
                    feedbackWriter.write(feedback + "\n\n");
                }
            } catch (IOException ioException) {}

            // Show thank you message in a pop-up box
            JOptionPane.showMessageDialog(panel, "Thank you so much! Bye for Now!", "Message", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the GUI
        }
    }

    public void showRate() {
        setVisible(true);
    }
}