package com.stocksphere;

import javax.swing.SwingUtilities;
import com.stocksphere.ui.LoginFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
