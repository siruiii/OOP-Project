import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class WaitingGUI extends JFrame {
    private JLabel status;
    private final JLabel time;
    private JLabel load;
    private Timer timer;
    private int remainTime;
    private int total;
    private String[] stat = { "Payment Processing", "Received", "In Preparation", "Ready to Pick-Up" }; // keep track of the current status
    private int currStatInd = 0;

    // Constructor to set up the GUI
    public WaitingGUI(int quantity) {
        // Setting up the frame
        setTitle("Order Status");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Initialize & Add label
        status = new JLabel("Status: " + stat[currStatInd], SwingConstants.CENTER);
        status.setBounds(50, 20, 200, 30);
        add(status);

        // Load and resize the GIF using ImageIcon
        try {
            ImageIcon originalIcon = new ImageIcon("/Users/icyfloaty/Documents/CS_coding/OOP/Final_Project/loading.gif");
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(96, 96, Image.SCALE_DEFAULT);
            load = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);
        } catch (Exception e) {
            load = new JLabel("Loading...", SwingConstants.CENTER);
        }
        add(load);

        time = new JLabel("Estimated Time: " + remainTime + " seconds", SwingConstants.CENTER);
        add(time);

        // Start the simulation of the order process
        simulateWait(quantity);

    }

    // Method to simulate the waiting process
    private void simulateWait(int quantity) {
        // Cancel any existing timer
        if (timer != null) {
            timer.cancel();
        }
        currStatInd = 0; // Reset the status to the first one
        remainTime = 15 * quantity; // Estimate remaining time based on quantity

        // default processing time is 20 seconds
        if (remainTime < 20){
            remainTime = 20;
        }

        total = remainTime;
        timer = new Timer(); // Create a new timer

        // Schedule the timer task
        timer.scheduleAtFixedRate(createTTask(), 0, 1000); // Start immediately, run every 1000 milliseconds (1 second)
    }

    // Method to Create a new time task with override method
    private TimerTask createTTask() {
        return new TimerTask() {
            @Override
            public void run() {
                handleTTask();
            }
        };
    }

    // Method to handle the override method
    private void handleTTask() {
        // If there is remaining time, decrement it and update the UI
        if (remainTime > 0) {
            remainTime--;
            updateUI();

            // Determine the current phase based on the remaining time ratio
            double timeRatio = (double) remainTime / total;
            if (timeRatio <= 0.15 && currStatInd < 3) {
                currStatInd = 3; // "Ready to Pick-Up"
            } else if (timeRatio <= 0.75 && currStatInd < 2) {
                currStatInd = 2; // "In Preparation"
            } else if (timeRatio <= 0.85 && currStatInd < 1) {
                currStatInd = 1; // "Received"
            }

        // If the status is complete, cancel the timer and open the rating page
        } else if (currStatInd == stat.length - 1) {
            timer.cancel();
            SwingUtilities.invokeLater(() -> {
                RatingGUI rating = new RatingGUI();
                // Close the waiting window before opening the rating page
                dispose();
                rating.showRate();
            });
        }
    }

    // Method to update the UI components
    private void updateUI() {
        status.setText("Status: " + stat[currStatInd]); // Update the status label
        time.setText("Estimated Time: " + remainTime + " seconds"); // Update the time label
    }

    // Display this GUI
    public void showWait() {
        setVisible(true);
    }

    /* Debugger
    public static void main(String[] args) {
        // Create an instance of the WaitingGUI with a sample quantity
        WaitingGUI gui = new WaitingGUI(1);
        gui.showWait();
    }*/
}