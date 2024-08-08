import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.text.*;
import java.util.ArrayList;

public class SearchGUI extends JFrame {

    private JTextField textField;
    private JTextPane textPane;
    private int hoverLine = -1;
    private int clickedLine = -1;
    private Style normalStyle, hoverStyle, clickedStyle;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SearchGUI window = new SearchGUI();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SearchGUI() {
        // Initialize JFrame properties
        setTitle("Search Menu");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel btnSearch = new JLabel("Search");
        btnSearch.setBounds(29, 65, 61, 16);
        getContentPane().add(btnSearch);

        JButton btnBack = new JButton("Back to Menu");
        btnBack.setBounds(6, 2, 117, 29);
        getContentPane().add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to proceed to checkout
                menuGUI mgui = new menuGUI();
                setVisible(false);
                mgui.setVisible(true);
            }
        });

        JLabel lblinstruction = new JLabel(" ");
        lblinstruction.setBounds(29, 245, 250, 16);
        Font currentFont = lblinstruction.getFont();
        lblinstruction.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblinstruction);

        textPane = new JTextPane();
        textPane.setEditable(false);
        StyledDocument doc = textPane.getStyledDocument();
        normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);

        hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);

        clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(29, 95, 388, 150);
        getContentPane().add(scrollPane);

        FileManager fileManager = new FileManager("itemfile.txt");
        List<Item> items = fileManager.getItems();


        JCheckBox chckbxFilter = new JCheckBox("Apply Filter");
        chckbxFilter.setBounds(29, 30, 109, 23);
        getContentPane().add(chckbxFilter);

        JComboBox<String> cbxCategory = new JComboBox<>();
        cbxCategory.setBounds(138, 30, 145, 27);
        cbxCategory.addItem("Food & Drink");
        cbxCategory.addItem("Food ONLY");
        cbxCategory.addItem("Drink ONLY");
        getContentPane().add(cbxCategory);

        JComboBox<String> cbxRating = new JComboBox<>();
        cbxRating.setBounds(287, 30, 130, 27);
        cbxRating.addItem("All Rating");
        cbxRating.addItem("Rating >4★ ");
        cbxRating.addItem("Rating >3★ ");
        cbxRating.addItem("Rating >2★ ");
        cbxRating.addItem("Rating >1★ ");
        getContentPane().add(cbxRating);

        textField = new JTextField();
        textField.setBounds(92, 60, 215, 26);
        getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnEnter = new JButton("Enter");
        btnEnter.setBounds(319, 60, 98, 29);
        getContentPane().add(btnEnter);

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the textField
                String input = textField.getText();

                // String selectedRating = (String) cbxRating.getSelectedItem();
                List<Item> result = searchByName(items, input);
                if (chckbxFilter.isSelected()) {
                    // Apply category filter
                    if (cbxCategory.getSelectedItem()=="Food ONLY"){
                        String selectedCategory = "Food";
                        result = filterByCategory(result, selectedCategory);
                    }else if(cbxCategory.getSelectedItem()=="Drink ONLY"){
                        String selectedCategory = "Drink";
                        result = filterByCategory(result, selectedCategory);
                    }

                    // Apply rating filter
                    // if (!selectedRating.equals("All Rating")) {
                    //     result = filterByRating(result, selectedRating);
                    // }
                }

                // Display the result list
                display(result);
            }
        });


        textPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int pos = textPane.viewToModel2D(e.getPoint());
                int line = getLineAtPosition(pos);

                if (line != hoverLine) {
                    hoverLine = line;
                    updateTextPaneStyles();
                    try {
                        int offset = textPane.viewToModel2D(e.getPoint());
                        int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
                        String selectedItem = textPane.getDocument().getText(
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                                        textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                                .getStartOffset())
                                .trim();

                                boolean found = false;
                                for (Item item : items) {
                                    if (selectedItem.startsWith(item.getName())) {
                                        lblinstruction.setText("Double Click to Add " + item.getName());
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    lblinstruction.setText(" ");
                                }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addItemGUI addCurrentLine = new addItemGUI(null);
                if (e.getClickCount() == 2) {
                    try {
                        int offset = textPane.viewToModel2D(e.getPoint());
                        int rowStart = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
                        String selectedItem = textPane.getDocument().getText(
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getStartOffset(),
                                textPane.getDocument().getDefaultRootElement().getElement(rowStart).getEndOffset() -
                                        textPane.getDocument().getDefaultRootElement().getElement(rowStart)
                                                .getStartOffset())
                                .trim();

                        for (Item item : items) {
                            if (selectedItem.startsWith(item.getName())) {
                                addCurrentLine.setItemDetails(item.getName(), item.getCategory(), item.getSmallPrice(),
                                        item.getMediumPrice(), item.getLargePrice());
                                setVisible(false);
                                addCurrentLine.setBtnBack("Back to Search");
                                addCurrentLine.setVisible(true);
                                break;
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private List<Item> filterByCategory(List<Item> items, String category) {
    return items.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // private List<Item> filterByRating(List<Item> items, String rating) {

    // }

    private List<Item> searchByName(List<Item> menu, String keyword){
        List<Item> search_results = new ArrayList<Item>();
        for (Item item : menu) {
            // Check if the item's name contains the search term
            if (item.getName() != null && item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                search_results.add(item);
            }
        }
        return search_results;
    }
    private void display(List<Item> items) {
        StringBuilder displayText = new StringBuilder();
        if(CartManager.readCartItem().size()==0){
            textPane.setText("\n\n                         ---Sorry, 0 results found---\n\n");
        }else{
        for (Item item : items) {
            displayText.append(item.getName())
                    .append(" - Rating: ")
                    .append(item.getRatingStatus())
                    .append(" ★")
                    .append("\n");
        }
        textPane.setText(displayText.toString());}
    }

    private int getLineAtPosition(int pos) {
        try {
            return textPane.getDocument().getDefaultRootElement().getElementIndex(pos);
        } catch (Exception e) {
            return -1;
        }
    }

    private void updateTextPaneStyles() {
        StyledDocument doc = textPane.getStyledDocument();
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
}
