package com.stocksphere.dao;

import com.stocksphere.database.DBConnection;
import com.stocksphere.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ================= ADD PRODUCT =================

    public boolean addProduct(Product product) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO products" +
                            "(product_name, category, price, quantity) " +
                            "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // ================= GET ALL PRODUCTS =================

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM products";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Product product = new Product();

                product.setId(
                        rs.getInt("id")
                );

                product.setProductName(
                        rs.getString("product_name")
                );

                product.setCategory(
                        rs.getString("category")
                );

                product.setPrice(
                        rs.getDouble("price")
                );

                product.setQuantity(
                        rs.getInt("quantity")
                );

                products.add(product);
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return products;
    }


    // ================= UPDATE PRODUCT =================

    public boolean updateProduct(Product product) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "UPDATE products SET " +
                            "product_name=?, " +
                            "category=?, " +
                            "price=?, " +
                            "quantity=? " +
                            "WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getId());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // ================= DELETE PRODUCT =================

    public boolean deleteProduct(int id) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "DELETE FROM products WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }


    // ================= SEARCH PRODUCTS =================

    public List<Product> searchProducts(String keyword) {

        List<Product> products = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM products " +
                            "WHERE product_name LIKE ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    "%" + keyword + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Product product = new Product();

                product.setId(
                        rs.getInt("id")
                );

                product.setProductName(
                        rs.getString("product_name")
                );

                product.setCategory(
                        rs.getString("category")
                );

                product.setPrice(
                        rs.getDouble("price")
                );

                product.setQuantity(
                        rs.getInt("quantity")
                );

                products.add(product);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return products;
    }


    // ================= PRODUCT COUNT =================

    public int getProductCount() {

        int count = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM products";

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

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


    // ================= REDUCE STOCK =================

    public boolean reduceStock(
            int productId,
            int quantity) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE products " +
                            "SET quantity = quantity - ? " +
                            "WHERE id = ? " +
                            "AND quantity >= ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            int rows =
                    ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}
