package com.stocksphere.ui;
import com.stocksphere.ui.DashboardFrame;
import com.stocksphere.dao.UserDAO;
import com.stocksphere.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("StockSphere - Inventory & Billing Management");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("StockSphere");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(25, 118, 210));
        title.setBounds(130, 20, 250, 30);

        JLabel subtitle = new JLabel("Inventory & Billing Management");
        subtitle.setBounds(105, 50, 250, 20);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(60, 100, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(160, 100, 200, 30);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(60, 145, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 145, 200, 30);

        loginButton = new JButton("Login");
        loginButton.setBounds(160, 205, 120, 35);

        panel.add(title);
        panel.add(subtitle);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Please enter Username and Password."
                    );
                    return;
                }

                UserDAO dao = new UserDAO();
                User user = dao.login(username, password);

                if (user != null) {

                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Login Successful!"
                    );

                    new DashboardFrame();
                    dispose();

                    // TODO: Open Dashboard
                    // new DashboardFrame();
                    // dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Invalid Username or Password!"
                    );
                }
            }
        });

        setVisible(true);
    }
}
