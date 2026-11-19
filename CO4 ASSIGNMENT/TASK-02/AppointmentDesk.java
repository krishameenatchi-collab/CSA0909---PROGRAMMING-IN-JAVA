// TASK-02: AppointmentDesk.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientIdField = new JTextField(15);
    private final JComboBox<String> departmentCombo = new JComboBox<>(new String[]{"Select Department", "Cardiology", "Neurology", "Orthopedics", "Pediatrics"});
    private final JComboBox<String> doctorCombo = new JComboBox<>();
    private final JComboBox<String> timeSlotCombo = new JComboBox<>();
    private final JButton bookButton = new JButton("Book Appointment");
    private final JButton clearButton = new JButton("Clear");
    private final JLabel statusLabel = new JLabel("Status: Select department to begin", JLabel.CENTER);

    private final Map<String, String[]> doctorMap = new HashMap<>();
    private final Map<String, String[]> slotMap = new HashMap<>();

    public AppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        initData();

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(patientIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(doctorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Time Slot:"), gbc);
        gbc.gridx = 1;
        formPanel.add(timeSlotCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(statusLabel, BorderLayout.NORTH);

        departmentCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateDoctors((String) departmentCombo.getSelectedItem());
            }
        });

        doctorCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateTimeSlots((String) doctorCombo.getSelectedItem());
            }
        });

        bookButton.addActionListener(e -> bookAppointment());
        clearButton.addActionListener(e -> resetForm());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initData() {
        doctorMap.put("Cardiology", new String[]{"Dr. Sarah Jenkins", "Dr. Robert Chen"});
        doctorMap.put("Neurology", new String[]{"Dr. Alan Grant", "Dr. Elena Rostova"});
        doctorMap.put("Orthopedics", new String[]{"Dr. Marcus Vance", "Dr. Priya Patel"});
        doctorMap.put("Pediatrics", new String[]{"Dr. Emily Watson", "Dr. David Kim"});

        slotMap.put("Dr. Sarah Jenkins", new String[]{"09:00 AM", "10:30 AM", "02:00 PM"});
        slotMap.put("Dr. Robert Chen", new String[]{"11:00 AM", "01:30 PM", "04:00 PM"});
        slotMap.put("Dr. Alan Grant", new String[]{"09:30 AM", "02:30 PM"});
        slotMap.put("Dr. Elena Rostova", new String[]{"10:00 AM", "03:00 PM"});
        slotMap.put("Dr. Marcus Vance", new String[]{"08:30 AM", "11:30 AM"});
        slotMap.put("Dr. Priya Patel", new String[]{"01:00 PM", "03:30 PM"});
        slotMap.put("Dr. Emily Watson", new String[]{"09:00 AM", "11:00 AM", "01:00 PM"});
        slotMap.put("Dr. David Kim", new String[]{"10:00 AM", "02:00 PM", "04:00 PM"});
    }

    private void updateDoctors(String dept) {
        doctorCombo.removeAllItems();
        timeSlotCombo.removeAllItems();
        if (doctorMap.containsKey(dept)) {
            for (String doc : doctorMap.get(dept)) {
                doctorCombo.addItem(doc);
            }
            statusLabel.setText("Status: Doctors loaded for " + dept);
        } else {
            statusLabel.setText("Status: Please select a valid department");
        }
    }

    private void updateTimeSlots(String doc) {
        timeSlotCombo.removeAllItems();
        if (doc != null && slotMap.containsKey(doc)) {
            for (String slot : slotMap.get(doc)) {
                timeSlotCombo.addItem(slot);
            }
        }
    }

    private void bookAppointment() {
        String patientId = patientIdField.getText().trim();
        String dept = (String) departmentCombo.getSelectedItem();
        String doc = (String) doctorCombo.getSelectedItem();
        String slot = (String) timeSlotCombo.getSelectedItem();

        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dept == null || dept.startsWith("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a department.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (doc == null || slot == null) {
            JOptionPane.showMessageDialog(this, "Please select doctor and slot.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        statusLabel.setText("Status: Appointment Booked for " + patientId);
        JOptionPane.showMessageDialog(this, "Appointment successfully confirmed for Patient " + patientId + "\nDoctor: " + doc + "\nSlot: " + slot, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        patientIdField.setText("");
        departmentCombo.setSelectedIndex(0);
        doctorCombo.removeAllItems();
        timeSlotCombo.removeAllItems();
        statusLabel.setText("Status: Select department to begin");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true));
    }
}
