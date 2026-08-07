package com.stocksphere.ui;

import com.stocksphere.dao.ProductDAO;
import com.stocksphere.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductFrame extends JFrame {

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtCategory;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JTextField txtSearch;

    private JTable table;
    private DefaultTableModel model;

    public ProductFrame() {

        setTitle("StockSphere - Inventory & Billing Management System");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= FORM PANEL =================

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        formPanel.add(new JLabel("Product ID"));
        txtId = new JTextField();
        txtId.setEditable(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Product Name"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Category"));
        txtCategory = new JTextField();
        formPanel.add(txtCategory);

        formPanel.add(new JLabel("Price"));
        txtPrice = new JTextField();
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Quantity"));
        txtQuantity = new JTextField();
        formPanel.add(txtQuantity);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        // ================= SEARCH PANEL =================

        JPanel searchPanel = new JPanel();

        searchPanel.add(new JLabel("Search Product:"));

        txtSearch = new JTextField(20);

        searchPanel.add(txtSearch);

        add(searchPanel, BorderLayout.SOUTH);

// ================= TABLE =================

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "ID",
                "Product",
                "Category",
                "Price",
                "Quantity"
        });

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);



        // ================= EVENTS =================

        btnAdd.addActionListener(e -> addProduct());

        btnUpdate.addActionListener(e -> updateProduct());

        btnDelete.addActionListener(e -> deleteProduct());

        btnClear.addActionListener(e -> clearFields());

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {

                int row = table.getSelectedRow();

                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtCategory.setText(model.getValueAt(row, 2).toString());
                txtPrice.setText(model.getValueAt(row, 3).toString());
                txtQuantity.setText(model.getValueAt(row, 4).toString());
            }
        });

        loadProducts();

        setVisible(true);
    }

    // ================= ADD =================

    private void addProduct() {

        try {

            Product product = new Product();

            product.setProductName(txtName.getText());
            product.setCategory(txtCategory.getText());
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setQuantity(Integer.parseInt(txtQuantity.getText()));

            ProductDAO dao = new ProductDAO();

            if (dao.addProduct(product)) {

                JOptionPane.showMessageDialog(this, "Product Added Successfully!");

                clearFields();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(this, "Failed to Add Product");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    // ================= UPDATE =================

    private void updateProduct() {

        if (txtId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please select a product");
            return;
        }

        try {

            Product product = new Product();

            product.setId(Integer.parseInt(txtId.getText()));
            product.setProductName(txtName.getText());
            product.setCategory(txtCategory.getText());
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setQuantity(Integer.parseInt(txtQuantity.getText()));

            ProductDAO dao = new ProductDAO();

            if (dao.updateProduct(product)) {

                JOptionPane.showMessageDialog(this, "Product Updated Successfully!");

                clearFields();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(this, "Update Failed!");
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    // ================= DELETE =================

    private void deleteProduct() {

        if (txtId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please select a product");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this product?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            ProductDAO dao = new ProductDAO();

            if (dao.deleteProduct(Integer.parseInt(txtId.getText()))) {

                JOptionPane.showMessageDialog(this, "Product Deleted Successfully!");

                clearFields();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(this, "Delete Failed!");
            }
        }
    }

    // ================= LOAD TABLE =================

    private void loadProducts() {

        model.setRowCount(0);

        ProductDAO dao = new ProductDAO();

        List<Product> products = dao.getAllProducts();

        for (Product p : products) {

            model.addRow(new Object[]{
                    p.getId(),
                    p.getProductName(),
                    p.getCategory(),
                    p.getPrice(),
                    p.getQuantity()
            });
        }
    }

// ================= SEARCH =================

    private void searchProducts() {

        String keyword = txtSearch.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            table.clearSelection();
            return;
        }

        for (int i = 0; i < table.getRowCount(); i++) {

            String name = table.getValueAt(i, 1).toString().toLowerCase();

            if (name.contains(keyword)) {

                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                return;
            }
        }

        table.clearSelection();
    }

// ================= CLEAR =================

    private void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");

        table.clearSelection();
    }

}
