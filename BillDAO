package com.stocksphere.dao;

import com.stocksphere.database.DBConnection;
import com.stocksphere.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillDAO {

    public boolean saveBill(Bill bill) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO bill(product_name, quantity, total) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bill.getProductName());
            ps.setInt(2, bill.getQuantity());
            ps.setDouble(3, bill.getTotal());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
