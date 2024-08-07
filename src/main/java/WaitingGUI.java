import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingGUI extends JFrame {
    private JLabel status;
    private JLabel time;
    private JLabel load;
    private Timer timer;
    private int remainTime;
    private int total;
    private String[] stat = { "Received", "In Preparation", "Ready to Pick-Up" };
    // keep track of the current status
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

        load = new JLabel(new ImageIcon("loading.gif"), SwingConstants.CENTER);
        add(load);

        time = new JLabel("Estimated Time: " + remainTime + " seconds", SwingConstants.CENTER);
        add(time);

        // Start the simulation of the order process
        simulateOrder(quantity);

    }

    // Method to simulate the order process
    public void simulateOrder(int quantity) {
        if (timer != null) {
            timer.cancel(); // Cancel any existing timer
        }
        remainTime = 30 * quantity; // Calculate remaining time based on quantity
        total = remainTime;
        currStatInd = 0; // Reset the status to the first one

        timer = new Timer(); // Create a new timer

        // Schedule the timer task
        timer.scheduleAtFixedRate(createTTask(quantity), 0, 1000); // Start immediately, run every 1000 milliseconds (1
                                                                   // second)
    }

    //create a new time task with override method
    private TimerTask createTTask(final int quantity) {
        return new TimerTask() {
            @Override
            public void run() {
                handleTTask(quantity);
            }
        };
    }

    // Method to handle the override method
    private void handleTTask(int quantity) {
        // If there is remaining time, decrement it and update the UI
        if (remainTime > 0) {
            remainTime--;
            updateUI();

            // Determine the current phase based on the remaining time ratio
            double timeRatio = (double) remainTime / total;
            if (timeRatio <= 0.1 && currStatInd < 2) {
                currStatInd = 2; // "Ready to Pick-Up"
            } else if (timeRatio <= 0.75 && currStatInd < 1) {
                currStatInd = 1; // "In Preparation"
            }

        // If the status is complete, cancel the timer and open the rating page
        } else if (currStatInd == stat.length - 1) {
            timer.cancel();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Create and show the rating page
                    RatingGUI rating = new RatingGUI();
                    rating.setVisible(true);
                    // Close the waiting window
                    dispose();
                }
            });
        }
    }

    // Method to update the UI components
    private void updateUI() {
        status.setText("Status: " + stat[currStatInd]); // Update the status label
        time.setText("Estimated Time: " + remainTime + " seconds"); // Update the time label
    }

    public void showWait() {
        setVisible(true);
    }

}