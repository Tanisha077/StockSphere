package com.stocksphere.dao;

import com.stocksphere.database.DBConnection;
import com.stocksphere.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDAO {

    public boolean addCustomer(Customer customer) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO customers(customer_name,mobile,email,address) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getMobile());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getAddress());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public int getCustomerCount() {

        int count = 0;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT COUNT(*) FROM customers";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
