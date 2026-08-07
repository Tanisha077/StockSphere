package com.stocksphere.ui;

import com.stocksphere.dao.BillDAO;
import com.stocksphere.model.Bill;

import javax.swing.*;
import java.awt.*;

public class BillingFrame extends JFrame {

    private JTextField txtProduct;
    private JTextField txtQuantity;
    private JTextField txtTotal;

    public BillingFrame() {

        setTitle("StockSphere - Inventory & Billing Management System");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Product Name"));
        txtProduct = new JTextField();
        panel.add(txtProduct);

        panel.add(new JLabel("Quantity"));
        txtQuantity = new JTextField();
        panel.add(txtQuantity);

        panel.add(new JLabel("Total Amount"));
        txtTotal = new JTextField();
        panel.add(txtTotal);

        JButton btnSave = new JButton("Save Bill");

        panel.add(new JLabel(""));
        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> saveBill());

        setVisible(true);
    }

    private void saveBill() {

        try {

            Bill bill = new Bill();

            bill.setProductName(txtProduct.getText());
            bill.setQuantity(Integer.parseInt(txtQuantity.getText()));
            bill.setTotal(Double.parseDouble(txtTotal.getText()));

            BillDAO dao = new BillDAO();

            if (dao.saveBill(bill)) {

                JOptionPane.showMessageDialog(this,
                        "Bill Saved Successfully!");

                txtProduct.setText("");
                txtQuantity.setText("");
                txtTotal.setText("");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Failed!");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Invalid Input!");

        }
    }
}
