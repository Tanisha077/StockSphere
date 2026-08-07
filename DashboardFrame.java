package com.stocksphere.ui;

import com.stocksphere.dao.CustomerDAO;
import com.stocksphere.dao.ProductDAO;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("StockSphere - Inventory & Billing Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================

        JPanel header = new JPanel();
        header.setBackground(new Color(25, 118, 210));
        header.setPreferredSize(new Dimension(900, 60));

        JLabel title = new JLabel("StockSphere Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        header.add(title);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));

        JButton productBtn = new JButton("📦 Products");
        JButton customerBtn = new JButton("👥 Customers");
        JButton billingBtn = new JButton("🧾 Billing");
        JButton salesBtn = new JButton("Sales Report");
        JButton inventoryBtn = new JButton("Inventory");
        JButton employeeBtn = new JButton("Employees");
        JButton settingsBtn = new JButton("Settings");
        JButton logoutBtn = new JButton("🚪 Logout");

        sidebar.add(productBtn);
        sidebar.add(customerBtn);
        sidebar.add(billingBtn);
        sidebar.add(salesBtn);
        sidebar.add(inventoryBtn);
        sidebar.add(employeeBtn);
        sidebar.add(settingsBtn);
        sidebar.add(logoutBtn);

        // ================= CENTER =================

        JPanel center = new JPanel();
        center.setBackground(new Color(245, 245, 245));

        ProductDAO productDAO = new ProductDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        int totalProducts = productDAO.getProductCount();
        int totalCustomers = customerDAO.getCustomerCount();

        JLabel welcome = new JLabel(
                "<html><center>"
                        + "<h1>Welcome Admin</h1>"
                        + "<h2>StockSphere</h2>"
                        + "<br>"
                        + "Inventory & Billing Management System"
                        + "<br><br>"
                        + "📦 Total Products : <b>" + totalProducts + "</b>"
                        + "<br><br>"
                        + "👥 Total Customers : <b>" + totalCustomers + "</b>"
                        + "</center></html>"
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 20));

        center.add(welcome);

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);

        // ================= BUTTON EVENTS =================

        productBtn.addActionListener(e -> new ProductFrame());

        customerBtn.addActionListener(e -> new CustomerFrame());

        billingBtn.addActionListener(e -> new BillingFrame());

        salesBtn.addActionListener(e -> new SalesReportFrame());

        inventoryBtn.addActionListener(e -> new InventoryFrame());

        employeeBtn.addActionListener(e -> new EmployeeFrame());

        settingsBtn.addActionListener(e -> new SettingsFrame());

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
