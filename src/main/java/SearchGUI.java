import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.text.*;


public class SearchGUI extends JFrame {

    private JComboBox<String> cbxField;
    private JTextPane textPane;
    private JLabel lblSearch;
    private JButton btnBack;
    private JLabel lblinstruction;
    private JCheckBox chckbxFilter;
    private JComboBox<String> cbxCategory;
    private JComboBox<String> cbxRating;
    private JButton btnEnter;
    private JScrollPane scrollPane;
    private static List<Item> items;

    private int hoverLine = -1;
    private int clickedLine = -1;
    private Style normalStyle, hoverStyle, clickedStyle;
    private static List<String> searchHistory = new ArrayList<String>();

    public SearchGUI(List<Item> menu_items) {
        items=menu_items;
        setTitle("Search Menu");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lblSearch = new JLabel("Search");
        lblSearch.setBounds(29, 65, 61, 16);
        getContentPane().add(lblSearch);

        textPane = new JTextPane();
        textPane.setEditable(false);

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(29, 95, 388, 150);
        getContentPane().add(scrollPane);

        chckbxFilter = new JCheckBox("Apply Filter");
        chckbxFilter.setBounds(29, 30, 109, 23);
        getContentPane().add(chckbxFilter);

        cbxCategory = new JComboBox<>();
        cbxCategory.setBounds(138, 30, 145, 27);
        cbxCategory.addItem("Food & Drink");
        cbxCategory.addItem("Food ONLY");
        cbxCategory.addItem("Drink ONLY");
        getContentPane().add(cbxCategory);

        cbxRating = new JComboBox<>();
        cbxRating.setBounds(287, 30, 130, 27);
        cbxRating.addItem("All Rating");
        cbxRating.addItem("Rating >4★ ");
        cbxRating.addItem("Rating >3★ ");
        cbxRating.addItem("Rating >2★ ");
        cbxRating.addItem("Rating >1★ ");
        getContentPane().add(cbxRating);

        cbxField = new JComboBox<>();
        cbxField.setBounds(92, 60, 215, 26);
        cbxField.setEditable(true);
        updateSearchHistory();
        getContentPane().add(cbxField);

        btnEnter = new JButton("Enter");
        btnEnter.setBounds(319, 60, 98, 29);
        getContentPane().add(btnEnter);

        // Go back to Menu GUI
        btnBack = new JButton("Back to Menu");
        btnBack.setBounds(6, 2, 117, 29);
        getContentPane().add(btnBack);
        btnBack.addActionListener(e -> {
            goBack();
        });

        // Show instruction to add item
        lblinstruction = new JLabel(" ");
        lblinstruction.setBounds(29, 245, 250, 16);
        Font currentFont = lblinstruction.getFont();
        lblinstruction.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblinstruction);

        // Update pane style with mouse action
        StyledDocument doc = textPane.getStyledDocument();
        normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);

        hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);

        clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);


        // Filter
        // Click to show search result with/without filter
        btnEnter.addActionListener(e -> {
            String input = (String) cbxField.getSelectedItem();
            clickEnter(input);
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

    public SearchGUI(){
        setTitle("Search Menu");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lblSearch = new JLabel("Search");
        lblSearch.setBounds(29, 65, 61, 16);
        getContentPane().add(lblSearch);

        textPane = new JTextPane();
        textPane.setEditable(false);

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBounds(29, 95, 388, 150);
        getContentPane().add(scrollPane);

        chckbxFilter = new JCheckBox("Apply Filter");
        chckbxFilter.setBounds(29, 30, 109, 23);
        getContentPane().add(chckbxFilter);

        cbxCategory = new JComboBox<>();
        cbxCategory.setBounds(138, 30, 145, 27);
        cbxCategory.addItem("Food & Drink");
        cbxCategory.addItem("Food ONLY");
        cbxCategory.addItem("Drink ONLY");
        getContentPane().add(cbxCategory);

        cbxRating = new JComboBox<>();
        cbxRating.setBounds(287, 30, 130, 27);
        cbxRating.addItem("All Rating");
        cbxRating.addItem("Rating >4★ ");
        cbxRating.addItem("Rating >3★ ");
        cbxRating.addItem("Rating >2★ ");
        cbxRating.addItem("Rating >1★ ");
        getContentPane().add(cbxRating);

        cbxField = new JComboBox<>();
        cbxField.setBounds(92, 60, 215, 26);
        cbxField.setEditable(true);
        updateSearchHistory();
        getContentPane().add(cbxField);

        btnEnter = new JButton("Enter");
        btnEnter.setBounds(319, 60, 98, 29);
        getContentPane().add(btnEnter);

        // Go back to Menu GUI
        btnBack = new JButton("Back to Menu");
        btnBack.setBounds(6, 2, 117, 29);
        getContentPane().add(btnBack);
        btnBack.addActionListener(e -> {
            goBack();
        });

        // Show instruction to add item
        lblinstruction = new JLabel(" ");
        lblinstruction.setBounds(29, 245, 250, 16);
        Font currentFont = lblinstruction.getFont();
        lblinstruction.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), 12));
        getContentPane().add(lblinstruction);

        // Update pane style with mouse action
        StyledDocument doc = textPane.getStyledDocument();
        normalStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setBackground(normalStyle, Color.WHITE);

        hoverStyle = doc.addStyle("hoverStyle", null);
        StyleConstants.setBackground(hoverStyle, Color.LIGHT_GRAY);

        clickedStyle = doc.addStyle("clickedStyle", null);
        StyleConstants.setBackground(clickedStyle, Color.YELLOW);

        doc.setCharacterAttributes(0, doc.getLength(), normalStyle, true);


        // Filter
        // Click to show search result with/without filter
        btnEnter.addActionListener(e -> {
            String input = (String) cbxField.getSelectedItem();
            clickEnter(input);
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
    private void updateSearchHistory() {
        cbxField.removeAllItems();
        // Reverse the order of search history
        List<String> reversedHistory = new ArrayList<String>(searchHistory);
        Collections.reverse(reversedHistory);

        // Add items in reversed order
        for (String historyItem : reversedHistory) {
            cbxField.addItem(historyItem);
        }
    }

    private List<Item> filter(List<Item> items, String selectedCategory, String selectedRating) {
        List<Item> filteredCategory = new ArrayList<Item>();
        List<Item> filteredRating = new ArrayList<Item>();
        double sRating = Double.parseDouble(selectedRating);

        // Filter by category
        if ("0".equals(selectedCategory)) {
            filteredCategory = new ArrayList<Item>(items); // Copy all items if no specific category is selected
        } else {
            for (Item item : items) {
                if (selectedCategory.equals(item.getCategory())) {
                    filteredCategory.add(item);
                }
            }
        }

        // Filter by rating
        if ("0".equals(selectedRating)) {
            filteredRating = new ArrayList<Item>(items); // Copy all items if no specific rating is selected
        } else {
            for (Item item : items) {
                String r = item.getRatingStatus();
                double rating = 0;
                if (!"No ratings yet".equals(r)) {
                    rating = Double.parseDouble(r);
                }
                if (rating >= sRating) {
                    filteredRating.add(item);
                }
            }
        }

        // Retain only items that are in both filteredCategory and filteredRating
        filteredCategory.retainAll(filteredRating);
        return filteredCategory;
    }

    private List<Item> searchByName(String keyword) {
        List<Item> search_results = new ArrayList<Item>();
        for (Item item : items) {
            // Check if the item's name contains the search term
            if (item.getName() != null && item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                search_results.add(item);
            }
        }
        return search_results;
    }

    private void display(List<Item> items) {
        StringBuilder displayText = new StringBuilder();
        if (items.size() == 0) {
            textPane.setText("\n\n                         ---Sorry, 0 results found---\n\n");
        } else {
            for (Item item : items) {
                displayText.append(item.getName())
                        .append(" - Rating: ")
                        .append(item.getRatingStatus())
                        .append(" ★")
                        .append("\n");
            }
            textPane.setText(displayText.toString());
        }
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
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), clickedStyle, false);
            } else if (i == hoverLine) {
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), hoverStyle, false);
            } else {
                doc.setCharacterAttributes(lineElem.getStartOffset(),
                        lineElem.getEndOffset() - lineElem.getStartOffset(), normalStyle, false);
            }
        }
    }

    private void goBack() {
        menuGUI mgui = new menuGUI();
        setVisible(false);
        mgui.setVisible(true);
    }

    private void clickEnter(String input) {
        List<Item> result = new ArrayList<Item>();

        if (input != null && !input.trim().isEmpty()) {
            // Store the search history
            if (!searchHistory.contains(input)) {
                searchHistory.add(input);
                updateSearchHistory();
            }

            result = searchByName(input);

            if (chckbxFilter.isSelected()) {
                // Apply category filter
                String selectedCategory = (String) cbxCategory.getSelectedItem();
                if ("Food ONLY".equals(selectedCategory)) {
                    selectedCategory = "Food";
                } else if ("Drink ONLY".equals(selectedCategory)) {
                    selectedCategory = "Drink";
                } else {
                    selectedCategory = "0";
                }

                String selectedRating = (String) cbxRating.getSelectedItem();
                if ("Rating >4★ ".equals(selectedRating)) {
                    selectedRating = "4";
                } else if ("Rating >3★ ".equals(selectedRating)) {
                    selectedRating = "3";
                } else if ("Rating >2★ ".equals(selectedRating)) {
                    selectedRating = "2";
                } else if ("Rating >1★ ".equals(selectedRating)) {
                    selectedRating = "1";
                } else {
                    selectedRating = "0";
                }

                result = filter(result, selectedCategory, selectedRating);
            }
        } else {
            // If input is empty, display all items
            result = items;

            if (chckbxFilter.isSelected()) {
                // Apply filters even if no search term is provided
                String selectedCategory = (String) cbxCategory.getSelectedItem();
                if ("Food ONLY".equals(selectedCategory)) {
                    selectedCategory = "Food";
                } else if ("Drink ONLY".equals(selectedCategory)) {
                    selectedCategory = "Drink";
                } else {
                    selectedCategory = "0";
                }

                String selectedRating = (String) cbxRating.getSelectedItem();
                if ("Rating >4★ ".equals(selectedRating)) {
                    selectedRating = "4";
                } else if ("Rating >3★ ".equals(selectedRating)) {
                    selectedRating = "3";
                } else if ("Rating >2★ ".equals(selectedRating)) {
                    selectedRating = "2";
                } else if ("Rating >1★ ".equals(selectedRating)) {
                    selectedRating = "1";
                } else {
                    selectedRating = "0";
                }

                result = filter(result, selectedCategory, selectedRating);
            }
        }

        // Display the result list
        display(result);
    }
}