package com.stocksphere.main;

import javax.swing.SwingUtilities;
import com.stocksphere.ui.LoginFrame;

public class LoginApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
