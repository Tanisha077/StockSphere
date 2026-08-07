package com.stocksphere.ui;

import javax.swing.*;
import java.awt.*;

public class EmployeeFrame extends JFrame {

    public EmployeeFrame() {

        setTitle("Employee Management");
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Employee Module", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setText(
                "Employee Management\n\n" +
                        "• Add Employee\n" +
                        "• Update Employee\n" +
                        "• Delete Employee\n\n" +
                        "Feature can be implemented later."
        );

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }
}
