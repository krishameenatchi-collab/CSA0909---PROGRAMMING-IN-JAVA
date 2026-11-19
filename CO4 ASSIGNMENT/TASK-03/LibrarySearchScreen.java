// TASK-03: LibrarySearchScreen.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {
    private final JTextField searchField = new JTextField(20);
    private final JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"All Categories", "Computer Science", "Mathematics", "Physics", "Literature"});
    private final JButton searchButton = new JButton("Search");
    private final JButton resetButton = new JButton("Reset");
    
    private final String[] columnNames = {"Book ID", "Title", "Author", "Category", "Availability"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private final JTable resultsTable = new JTable(tableModel);
    
    private final JLabel statusLabel = new JLabel("Status: Ready (5 books loaded)", JLabel.LEFT);

    public LibrarySearchScreen() {
        super("University Library Catalog Search");

        setLayout(new BorderLayout(10, 10));

        // Header Panel (North)
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Search Controls"));

        JPanel searchControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchControlPanel.add(new JLabel("Keywords:"));
        searchControlPanel.add(searchField);
        searchControlPanel.add(new JLabel("Category:"));
        searchControlPanel.add(categoryCombo);
        searchControlPanel.add(searchButton);
        searchControlPanel.add(resetButton);

        topPanel.add(searchControlPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Results Panel (Center)
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Search Results"));
        add(tableScrollPane, BorderLayout.CENTER);

        // Action & Status Panel (South)
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton borrowButton = new JButton("Reserve Selected Book");
        actionButtonPanel.add(borrowButton);

        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(actionButtonPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Dummy Data
        loadDummyData();

        // Event Handlers
        searchButton.addActionListener(e -> performSearch());
        resetButton.addActionListener(e -> loadDummyData());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void loadDummyData() {
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"BK-101", "Effective Java", "Joshua Bloch", "Computer Science", "Available"});
        tableModel.addRow(new Object[]{"BK-102", "Introduction to Algorithms", "Cormen et al.", "Computer Science", "Available"});
        tableModel.addRow(new Object[]{"BK-103", "Clean Code", "Robert C. Martin", "Computer Science", "Borrowed"});
        tableModel.addRow(new Object[]{"BK-104", "Linear Algebra Done Right", "Sheldon Axler", "Mathematics", "Available"});
        tableModel.addRow(new Object[]{"BK-105", "University Physics", "Sears and Zemansky", "Physics", "Available"});
        statusLabel.setText("Status: Ready (5 books loaded)");
    }

    private void performSearch() {
        String keyword = searchField.getText().trim().toLowerCase();
        String category = (String) categoryCombo.getSelectedItem();
        
        statusLabel.setText("Status: Searching for '" + keyword + "' in " + category + "...");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearchScreen().setVisible(true));
    }
}
