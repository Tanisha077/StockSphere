package com.stocksphere.ui;

import com.stocksphere.dao.BillDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SalesReportFrame extends JFrame {

    private DefaultTableModel tableModel;

    public SalesReportFrame() {

        setTitle("StockSphere - Sales Report");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        BillDAO billDAO = new BillDAO();

        // ================= HEADER =================

        JPanel header = new JPanel();
        header.setBackground(new Color(25, 118, 210));
        header.setPreferredSize(new Dimension(850, 70));

        JLabel title = new JLabel("Sales Report");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        header.add(title);

        add(header, BorderLayout.NORTH);


        // ================= SUMMARY DATA =================

        int totalBills =
                billDAO.getTotalBills();

        double totalSales =
                billDAO.getTotalSales();

        int totalItems =
                billDAO.getTotalItemsSold();

        double averageBill =
                billDAO.getAverageBill();


        // ================= SUMMARY CARDS =================

        JPanel summaryPanel =
                new JPanel(new GridLayout(1, 4, 15, 15));

        summaryPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        summaryPanel.setBackground(
                new Color(245, 245, 245)
        );


        summaryPanel.add(
                createCard(
                        "Total Bills",
                        String.valueOf(totalBills)
                )
        );

        summaryPanel.add(
                createCard(
                        "Total Sales",
                        String.format(
                                "₹ %.2f",
                                totalSales
                        )
                )
        );

        summaryPanel.add(
                createCard(
                        "Items Sold",
                        String.valueOf(totalItems)
                )
        );

        summaryPanel.add(
                createCard(
                        "Average Bill",
                        String.format(
                                "₹ %.2f",
                                averageBill
                        )
                )
        );


        // ================= SALES TABLE =================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Product",
                                "Quantity Sold",
                                "Revenue"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };


        JTable table =
                new JTable(tableModel);

        table.setRowHeight(28);

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Product-wise Sales"
                )
        );


        // ================= LOAD PRODUCT SALES =================

        List<BillDAO.ProductSale> sales =
                billDAO.getProductSales();

        for (BillDAO.ProductSale sale : sales) {

            tableModel.addRow(
                    new Object[]{
                            sale.getProductName(),
                            sale.getQuantity(),
                            String.format(
                                    "₹ %.2f",
                                    sale.getTotal()
                            )
                    }
            );
        }


        // ================= REFRESH BUTTON =================

        JButton refreshButton =
                new JButton("Refresh Report");

        refreshButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        refreshButton.addActionListener(e -> {

            dispose();

            new SalesReportFrame();
        });


        JPanel bottomPanel =
                new JPanel();

        bottomPanel.add(refreshButton);


        // ================= CENTER PANEL =================

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setBackground(
                new Color(245, 245, 245)
        );

        centerPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        setVisible(true);
    }


    // ================= CREATE CARD =================

    private JPanel createCard(
            String title,
            String value
    ) {

        JPanel card =
                new JPanel(new GridLayout(2, 1));

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 10, 10, 10
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );


        JLabel valueLabel =
                new JLabel(
                        value,
                        SwingConstants.CENTER
                );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        valueLabel.setForeground(
                new Color(25, 118, 210)
        );


        card.add(titleLabel);
        card.add(valueLabel);

        return card;
    }
}
