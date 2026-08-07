package com.stocksphere.ui;

import javax.swing.*;
import java.awt.*;

public class SalesReportFrame extends JFrame {

    public SalesReportFrame() {

        setTitle("Sales Report");
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Sales Report Module", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setText(
                "Sales Report\n\n" +
                        "• Today's Sales : ₹0\n" +
                        "• Monthly Sales : ₹0\n" +
                        "• Yearly Sales : ₹0\n\n" +
                        "Report feature can be extended later."
        );

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }
}
