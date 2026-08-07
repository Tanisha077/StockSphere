package com.stocksphere.ui;

import javax.swing.*;
import java.awt.*;

public class InventoryFrame extends JFrame {

    public InventoryFrame() {

        setTitle("Inventory");
        setSize(500,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Inventory Module", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setText(
                "Inventory Information\n\n" +
                        "• Total Products\n" +
                        "• Available Stock\n" +
                        "• Low Stock Alert\n\n" +
                        "Inventory can be expanded later."
        );

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }
}
