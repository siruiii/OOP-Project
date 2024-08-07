import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;

public class ShoppingCartGUI {

    private JFrame frame;
    private JTextPane textPane;
    private int hoverLine = -1;
    private int clickedLine = -1;  // Track the line that was clicked

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShoppingCartGUI window = new ShoppingCartGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ShoppingCartGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textPane = new JTextPane();  // Corrected variable assignment
        textPane.setBounds(33, 35, 377, 166);
        textPane.setEditable(false);
        frame.getContentPane().add(textPane);

        StyledDocument doc = textPane.getStyledDocument();  // Moved this line after creating textPane
        Style normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);
        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);

        textPane.setText("Selected item 1\n"
                + "Selected item 2\n"
                + "Selected item 3\n"
                + "Selected item 4");

        textPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int pos = textPane.viewToModel2D(e.getPoint());
                int line = getLineAtPosition(pos);

                if (line != hoverLine) {
                    hoverLine = line;
                    updateTextPaneStyles();
                }
            }
        });

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pos = textPane.viewToModel2D(e.getPoint());
                int line = getLineAtPosition(pos);
                clickedLine = line;
                updateTextPaneStyles();
            }
        });

        JLabel lblNewLabel = new JLabel("Shopping Cart");
        lblNewLabel.setBounds(16, 7, 200, 16);
        frame.getContentPane().add(lblNewLabel);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBounds(33, 218, 118, 29);
        frame.getContentPane().add(btnEdit);
        // btnEdit.addActionListener(ActionEvent e -> {
        //     EditItemGUI egui = new EditItemGUI();  // Create and show the edit window
        //     egui.setVisible(true);
        // });

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(163, 218, 118, 29);
        frame.getContentPane().add(btnDelete);

        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.setBounds(293, 218, 118, 29);
        frame.getContentPane().add(btnCheckout);
    }

    private int getLineAtPosition(int pos) {
        try {
            return textPane.getDocument().getDefaultRootElement().getElementIndex(pos);
        } catch (Exception ex) {
            return -1;
        }
    }

    private void updateTextPaneStyles() {
        StyledDocument doc = textPane.getStyledDocument();
        Style normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);
        Style clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        int numLines = textPane.getDocument().getDefaultRootElement().getElementCount();
        for (int i = 0; i < numLines; i++) {
            Element lineElem = textPane.getDocument().getDefaultRootElement().getElement(i);
            if (i == clickedLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), clickedStyle, false);
            } else if (i == hoverLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), hoverStyle, false);
            } else {
                doc.setCharacterAttributes(lineElem.getStartOffset(), lineElem.getEndOffset() - lineElem.getStartOffset(), normalStyle, false);
            }
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
