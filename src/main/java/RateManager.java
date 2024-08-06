import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class RateManager {

    private File menuFile;
    private File feedbackFile;

    public RateManager() {
        this.menuFile = new File("itemfile.txt");
        this.feedbackFile = new File("Feedback.txt");
    }


    public void processRatings(Map<String, Integer> newRatings) throws IOException {
    }
    public void saveFeedback(String feedback) throws IOException {
        if (feedback.trim().isEmpty()) {
            return; // No feedback to save
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(feedbackFile, true));
        writer.write(feedback);
        writer.newLine();
        writer.close();
    }
}