import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RatingGUI extends JFrame {
  private List<Item> products;
  private JTextArea feedbackArea;

  public RatingGUI(List<Item> products) {
    setTitle("Rating Page"); // Set the title of the window
    setSize(300, 200); // Set the size of the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define the close operation
    setLayout(new FlowLayout()); // Set the layout manager

    feedbackArea = new JTextArea(5, 30);
    feedbackArea.setBorder(BorderFactory.createTitledBorder("Additional Feedback"));
    add(feedbackArea, BorderLayout.NORTH);

    JButton submitButton = new JButton("Submit"); // Create the submit button
    add(submitButton, BorderLayout.SOUTH); // Add the button to the bottom

    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Step 1: Collect the new ratings from the UI
        Map<String, Integer> newRatings = collectRatings();

        // Step 2: Get the feedback from the text area
        String feedback = feedbackArea.getText();

        try {
          // Step 3: Process the ratings and save feedback
          RateManager.processRatings(newRatings);
          RateManager.saveFeedback(feedback)

          // Step 4: Show a thank you message
          JOptionPane.showMessageDialog(RatingGUI.this, "Thank you for your feedback!");

          // Step 5: Exit the application
          System.exit(0);
        } catch (IOException ioException) {
          // Handle any IO exceptions that occur
          JOptionPane.showMessageDialog(RatingGUI.this, "Error updating the menu file.", "Error",
              JOptionPane.ERROR_MESSAGE);
          ioException.printStackTrace();
        }
      }
    });

    setVisible(true); // Make the rating page visible
  }
}
