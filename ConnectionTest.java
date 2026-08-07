package com.stocksphere.main;

import com.stocksphere.database.DBConnection;
import java.sql.Connection;

public class ConnectionTest {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("✅ Database Connected Successfully!");
        } else {
            System.out.println("❌ Connection Failed!");
        }
    }
}
