package com.stocksphere.ui;

import com.stocksphere.dao.UserDAO;
import com.stocksphere.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("StockSphere - Inventory & Billing Management");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // LEFT SIDE
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(25, 118, 210));

        JLabel logo = new JLabel("📦");
        logo.setFont(new Font("Arial", Font.PLAIN, 70));
        logo.setForeground(Color.WHITE);
        logo.setBounds(160, 80, 100, 100);

        JLabel title = new JLabel("StockSphere");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(105, 190, 250, 40);

        JLabel subtitle = new JLabel("Inventory & Billing Management");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBounds(95, 235, 270, 30);

        JLabel welcome = new JLabel(
                "<html><center>Manage your products, customers<br>and billing in one place.</center></html>"
        );
        welcome.setFont(new Font("Arial", Font.PLAIN, 14));
        welcome.setForeground(Color.WHITE);
        welcome.setBounds(70, 300, 300, 60);

        leftPanel.add(logo);
        leftPanel.add(title);
        leftPanel.add(subtitle);
        leftPanel.add(welcome);

        // RIGHT SIDE
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        JLabel loginTitle = new JLabel("Welcome Back");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 26));
        loginTitle.setBounds(110, 75, 250, 40);

        JLabel loginSubtitle = new JLabel("Login to your account");
        loginSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        loginSubtitle.setForeground(Color.GRAY);
        loginSubtitle.setBounds(110, 115, 250, 25);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setBounds(80, 165, 250, 25);

        usernameField = new JTextField();
        usernameField.setBounds(80, 195, 260, 38);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setBounds(80, 250, 250, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(80, 280, 260, 38);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 345, 260, 42);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setFocusPainted(false);

        rightPanel.add(loginTitle);
        rightPanel.add(loginSubtitle);
        rightPanel.add(userLabel);
        rightPanel.add(usernameField);
        rightPanel.add(passLabel);
        rightPanel.add(passwordField);
        rightPanel.add(loginButton);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);

        // LOGIN BUTTON ACTION
        loginButton.addActionListener(e -> login());

        // PRESS ENTER TO LOGIN
        passwordField.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Username and Password."
            );
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.login(username, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful! Welcome to StockSphere."
            );

            new DashboardFrame();
            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!"
            );
        }
    }
}
