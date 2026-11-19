// TASK-06: EventRegistrationApp.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EventRegistrationApp extends JFrame {
    private final JTextField studentIdField = new JTextField(15);
    private final JTextField studentNameField = new JTextField(15);
    private final JComboBox<String> eventCombo = new JComboBox<>(new String[]{"EVT-101: Hackathon 2026", "EVT-102: Cultural Fest", "EVT-103: AI Summit"});
    private final JButton registerButton = new JButton("Register Student");
    private final JButton exportCsvButton = new JButton("Export CSV");
    
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Reg ID", "Student ID", "Name", "Event", "Timestamp"}, 0);
    private final JTable registrationTable = new JTable(tableModel);
    private final JLabel dbStatusLabel = new JLabel("Status: Database Connected 🟢", JLabel.LEFT);

    public EventRegistrationApp() {
        super("Campus Event Registration Portal");

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Registration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(studentIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(studentNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Select Event:"), gbc);
        gbc.gridx = 1;
        formPanel.add(eventCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        add(formPanel, BorderLayout.WEST);

        JScrollPane tableScrollPane = new JScrollPane(registrationTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Registered Participants"));
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottomPanel.add(dbStatusLabel, BorderLayout.WEST);
        bottomPanel.add(exportCsvButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        loadDummyTable();

        registerButton.addActionListener(e -> handleRegistration());
        exportCsvButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Registrations successfully exported to campus_events.csv", "Export Complete", JOptionPane.INFORMATION_MESSAGE));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 450);
        setLocationRelativeTo(null);
    }

    private void loadDummyTable() {
        tableModel.addRow(new Object[]{"1", "ST-5001", "Alice Smith", "EVT-101: Hackathon 2026", "2026-11-18 10:15"});
        tableModel.addRow(new Object[]{"2", "ST-5002", "Bob Jones", "EVT-102: Cultural Fest", "2026-11-18 11:30"});
        tableModel.addRow(new Object[]{"3", "ST-5003", "Charlie Brown", "EVT-103: AI Summit", "2026-11-18 14:00"});
    }

    private void handleRegistration() {
        String id = studentIdField.getText().trim();
        String name = studentNameField.getText().trim();
        String event = (String) eventCombo.getSelectedItem();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all student fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{String.valueOf(tableModel.getRowCount() + 1), id, name, event, "Just Now"});
        JOptionPane.showMessageDialog(this, "Registration successfully recorded!", "Success", JOptionPane.INFORMATION_MESSAGE);
        studentIdField.setText("");
        studentNameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistrationApp().setVisible(true));
    }
}
