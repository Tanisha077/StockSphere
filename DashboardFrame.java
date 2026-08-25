package com.stocksphere.ui;

import com.stocksphere.dao.CustomerDAO;
import com.stocksphere.dao.ProductDAO;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JPanel centerPanel;

    public DashboardFrame() {

        setTitle("StockSphere - Inventory & Billing Management System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setPreferredSize(new Dimension(1000, 65));

        JLabel title = new JLabel("  StockSphere");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel adminLabel = new JLabel("Welcome, Admin  ");
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        header.add(title, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(45, 45, 45));
        sidebar.setPreferredSize(new Dimension(190, 650));
        sidebar.setLayout(new GridLayout(6, 1, 8, 8));

        JButton dashboardBtn = createMenuButton("Dashboard");
        JButton productBtn = createMenuButton("Products");
        JButton customerBtn = createMenuButton("Customers");
        JButton billingBtn = createMenuButton("Billing");
        JButton reportBtn = createMenuButton("Reports");
        JButton logoutBtn = createMenuButton("Logout");

        sidebar.add(dashboardBtn);
        sidebar.add(productBtn);
        sidebar.add(customerBtn);
        sidebar.add(billingBtn);
        sidebar.add(reportBtn);
        sidebar.add(logoutBtn);

        // ================= CENTER AREA =================

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));

        showDashboard();

        // ================= BUTTON EVENTS =================

        dashboardBtn.addActionListener(e -> showDashboard());

        productBtn.addActionListener(e -> {
            new ProductFrame();
        });

        customerBtn.addActionListener(e -> {
            new CustomerFrame();
        });

        billingBtn.addActionListener(e -> {
            new BillingFrame();
        });

        reportBtn.addActionListener(e -> {
            new SalesReportFrame();
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ================= CREATE MENU BUTTON =================

    private JButton createMenuButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);

        return button;
    }

    // ================= SHOW DASHBOARD =================

    private void showDashboard() {

        centerPanel.removeAll();

        ProductDAO productDAO = new ProductDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        int totalProducts = productDAO.getProductCount();
        int totalCustomers = customerDAO.getCustomerCount();

        JPanel dashboard = new JPanel();
        dashboard.setBackground(new Color(245, 245, 245));
        dashboard.setLayout(new BorderLayout());

        // Welcome section
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(245, 245, 245));
        welcomePanel.setPreferredSize(new Dimension(700, 130));

        JLabel welcome = new JLabel("Welcome to StockSphere");
        welcome.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitle = new JLabel(
                "Manage your inventory, customers and billing in one place."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(Color.GRAY);

        welcomePanel.add(welcome);
        welcomePanel.add(subtitle);

        // Cards panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 2, 25, 20));
        cardsPanel.setBackground(new Color(245, 245, 245));
        cardsPanel.setBorder(
                BorderFactory.createEmptyBorder(40, 80, 200, 80)
        );

        JPanel productCard = createCard(
                "📦",
                "Total Products",
                String.valueOf(totalProducts)
        );

        JPanel customerCard = createCard(
                "👥",
                "Total Customers",
                String.valueOf(totalCustomers)
        );

        cardsPanel.add(productCard);
        cardsPanel.add(customerCard);

        dashboard.add(welcomePanel, BorderLayout.NORTH);
        dashboard.add(cardsPanel, BorderLayout.CENTER);

        centerPanel.add(dashboard, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // ================= CREATE DASHBOARD CARD =================

    private JPanel createCard(String icon, String title, String value) {

        JPanel card = new JPanel();
        card.setLayout(new GridLayout(3, 1));
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 35));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(new Color(25, 118, 210));

        card.add(iconLabel);
        card.add(titleLabel);
        card.add(valueLabel);

        return card;
    }
}
