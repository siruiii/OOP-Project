import javax.swing.*;
import java.awt.*;

public class RatingGUI extends JFrame {

  public RatingGUI() {
    setTitle("Rating Page"); // Set the title of the window
    setSize(300, 200); // Set the size of the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define the close operation
    setLayout(new FlowLayout()); // Set the layout manager

    JLabel rateLabel = new JLabel("Please rate your experience:"); // Create the label
    add(rateLabel); // Add the label to the frame

    JButton submitButton = new JButton("Submit"); // Create the submit button
    add(submitButton); // Add the button to the frame

    // Add an action listener to the submit button
    submitButton.addActionListener(e -> {
      JOptionPane.showMessageDialog(this, "Thank you for your feedback!"); // Show a thank you message
      System.exit(0); // Close the application after submitting the feedback
    });

    setVisible(true); // Make the rating page visible
  }
}
