package com.stocksphere.dao;

import com.stocksphere.database.DBConnection;
import com.stocksphere.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // ================= SAVE BILL =================

    public boolean saveBill(Bill bill) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO bill(product_name, quantity, total) " +
                            "VALUES (?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

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


    // ================= TOTAL BILLS =================

    public int getTotalBills() {

        int count = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM bill";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return count;
    }


    // ================= TOTAL SALES =================

    public double getTotalSales() {

        double totalSales = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COALESCE(SUM(total), 0) FROM bill";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                totalSales = rs.getDouble(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return totalSales;
    }


    // ================= TOTAL ITEMS SOLD =================

    public int getTotalItemsSold() {

        int totalItems = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COALESCE(SUM(quantity), 0) FROM bill";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                totalItems = rs.getInt(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return totalItems;
    }


    // ================= AVERAGE BILL =================

    public double getAverageBill() {

        double average = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COALESCE(AVG(total), 0) FROM bill";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                average = rs.getDouble(1);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return average;
    }


    // ================= PRODUCT-WISE SALES =================

    public List<ProductSale> getProductSales() {

        List<ProductSale> sales =
                new ArrayList<>();

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT product_name, " +
                            "SUM(quantity) AS total_quantity, " +
                            "SUM(total) AS total_sales " +
                            "FROM bill " +
                            "GROUP BY product_name " +
                            "ORDER BY total_sales DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                String productName =
                        rs.getString("product_name");

                int quantity =
                        rs.getInt("total_quantity");

                double total =
                        rs.getDouble("total_sales");

                sales.add(
                        new ProductSale(
                                productName,
                                quantity,
                                total
                        )
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return sales;
    }


    // ================= PRODUCT SALE MODEL =================

    public static class ProductSale {

        private String productName;
        private int quantity;
        private double total;

        public ProductSale(
                String productName,
                int quantity,
                double total
        ) {

            this.productName = productName;
            this.quantity = quantity;
            this.total = total;
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotal() {
            return total;
        }
    }
}
