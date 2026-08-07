package com.stocksphere.ui;

import com.stocksphere.dao.CustomerDAO;
import com.stocksphere.model.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerFrame extends JFrame {

    private JTextField txtName;
    private JTextField txtMobile;
    private JTextField txtEmail;
    private JTextField txtAddress;

    public CustomerFrame() {

        setTitle("StockSphere - Inventory & Billing Management System");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Customer Name"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Mobile"));
        txtMobile = new JTextField();
        panel.add(txtMobile);

        panel.add(new JLabel("Email"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Address"));
        txtAddress = new JTextField();
        panel.add(txtAddress);

        JButton btnSave = new JButton("Save Customer");

        panel.add(new JLabel(""));
        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> saveCustomer());

        setVisible(true);
    }

    private void saveCustomer() {

        try {

            Customer customer = new Customer();

            customer.setCustomerName(txtName.getText());
            customer.setMobile(txtMobile.getText());
            customer.setEmail(txtEmail.getText());
            customer.setAddress(txtAddress.getText());

            CustomerDAO dao = new CustomerDAO();

            if(dao.addCustomer(customer)){

                JOptionPane.showMessageDialog(this,
                        "Customer Added Successfully!");

                txtName.setText("");
                txtMobile.setText("");
                txtEmail.setText("");
                txtAddress.setText("");

            }else{

                JOptionPane.showMessageDialog(this,
                        "Failed!");

            }

        }catch(Exception ex){

            JOptionPane.showMessageDialog(this,
                    "Invalid Data!");

        }

    }
}
