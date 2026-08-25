package com.stocksphere.ui;

import com.stocksphere.dao.ProductDAO;
import com.stocksphere.model.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

        setTitle("StockSphere - Product Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= FORM PANEL =================

        JPanel formPanel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product Details"
                )
        );

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

        // ================= BUTTON PANEL =================

        JPanel buttonPanel = new JPanel();

        JButton btnAdd =
                new JButton("Add");

        JButton btnUpdate =
                new JButton("Update");

        JButton btnDelete =
                new JButton("Delete");

        JButton btnClear =
                new JButton("Clear");

        JButton btnRefresh =
                new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        JPanel northPanel =
                new JPanel(new BorderLayout(10, 10));

        northPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 0, 10
                )
        );

        northPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        northPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                northPanel,
                BorderLayout.NORTH
        );

        // ================= SEARCH PANEL =================

        JPanel searchPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 10, 0, 10
                )
        );

        JLabel searchLabel =
                new JLabel("Search Product:");

        txtSearch =
                new JTextField(25);

        JButton btnSearch =
                new JButton("Search");

        JButton btnShowAll =
                new JButton("Show All");

        searchPanel.add(searchLabel);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnShowAll);

        // ================= TABLE =================

        model =
                new DefaultTableModel() {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        model.setColumnIdentifiers(
                new String[]{
                        "ID",
                        "Product",
                        "Category",
                        "Price",
                        "Quantity"
                }
        );

        table =
                new JTable(model);

        table.setRowHeight(28);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        // ================= CENTER =================

        JPanel centerPanel =
                new JPanel(new BorderLayout(5, 5));

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 10, 10
                )
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        // ================= EVENTS =================

        btnAdd.addActionListener(
                e -> addProduct()
        );

        btnUpdate.addActionListener(
                e -> updateProduct()
        );

        btnDelete.addActionListener(
                e -> deleteProduct()
        );

        btnClear.addActionListener(
                e -> clearFields()
        );

        btnRefresh.addActionListener(
                e -> {
                    txtSearch.setText("");
                    loadProducts();
                }
        );

        btnSearch.addActionListener(
                e -> searchProducts()
        );

        btnShowAll.addActionListener(
                e -> {
                    txtSearch.setText("");
                    loadProducts();
                }
        );

        // Press Enter in search box

        txtSearch.addActionListener(
                e -> searchProducts()
        );

        // Search automatically while typing

        txtSearch.getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                searchProducts();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                searchProducts();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                searchProducts();
                            }
                        }
                );

        // Table selection

        table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (
                            !e.getValueIsAdjusting()
                                    && table.getSelectedRow() != -1
                    ) {

                        int row =
                                table.getSelectedRow();

                        txtId.setText(
                                model.getValueAt(
                                        row, 0
                                ).toString()
                        );

                        txtName.setText(
                                model.getValueAt(
                                        row, 1
                                ).toString()
                        );

                        txtCategory.setText(
                                model.getValueAt(
                                        row, 2
                                ).toString()
                        );

                        txtPrice.setText(
                                model.getValueAt(
                                        row, 3
                                ).toString()
                        );

                        txtQuantity.setText(
                                model.getValueAt(
                                        row, 4
                                ).toString()
                        );
                    }
                });

        // ================= LOAD PRODUCTS =================

        loadProducts();

        setVisible(true);
    }

    // =====================================================
    // ADD PRODUCT
    // =====================================================

    private void addProduct() {

        if (
                txtName.getText()
                        .trim()
                        .isEmpty()
                        ||
                        txtCategory.getText()
                                .trim()
                                .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Product Name and Category."
            );

            return;
        }

        try {

            double price =
                    Double.parseDouble(
                            txtPrice.getText().trim()
                    );

            int quantity =
                    Integer.parseInt(
                            txtQuantity.getText().trim()
                    );

            if (price < 0 || quantity < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price and quantity cannot be negative."
                );

                return;
            }

            Product product =
                    new Product();

            product.setProductName(
                    txtName.getText().trim()
            );

            product.setCategory(
                    txtCategory.getText().trim()
            );

            product.setPrice(price);
            product.setQuantity(quantity);

            ProductDAO dao =
                    new ProductDAO();

            if (dao.addProduct(product)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product Added Successfully!"
                );

                clearFields();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Product."
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Price and Quantity."
            );
        }
    }

    // =====================================================
    // UPDATE PRODUCT
    // =====================================================

    private void updateProduct() {

        if (
                txtId.getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first."
            );

            return;
        }

        try {

            double price =
                    Double.parseDouble(
                            txtPrice.getText().trim()
                    );

            int quantity =
                    Integer.parseInt(
                            txtQuantity.getText().trim()
                    );

            if (price < 0 || quantity < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price and quantity cannot be negative."
                );

                return;
            }

            Product product =
                    new Product();

            product.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            product.setProductName(
                    txtName.getText().trim()
            );

            product.setCategory(
                    txtCategory.getText().trim()
            );

            product.setPrice(price);
            product.setQuantity(quantity);

            ProductDAO dao =
                    new ProductDAO();

            if (dao.updateProduct(product)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Product Updated Successfully!"
                );

                clearFields();
                loadProducts();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Update Failed!"
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid Price and Quantity."
            );
        }
    }

    // =====================================================
    // DELETE PRODUCT
    // =====================================================

    private void deleteProduct() {

        if (
                txtId.getText()
                        .trim()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this product?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                confirm ==
                        JOptionPane.YES_OPTION
        ) {

            try {

                int id =
                        Integer.parseInt(
                                txtId.getText()
                        );

                ProductDAO dao =
                        new ProductDAO();

                if (dao.deleteProduct(id)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Product Deleted Successfully!"
                    );

                    clearFields();
                    loadProducts();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Delete Failed!"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Product ID."
                );
            }
        }
    }

    // =====================================================
    // LOAD ALL PRODUCTS
    // =====================================================

    private void loadProducts() {

        model.setRowCount(0);

        ProductDAO dao =
                new ProductDAO();

        List<Product> products =
                dao.getAllProducts();

        for (Product p : products) {

            model.addRow(
                    new Object[]{
                            p.getId(),
                            p.getProductName(),
                            p.getCategory(),
                            p.getPrice(),
                            p.getQuantity()
                    }
            );
        }
    }

    // =====================================================
    // SEARCH PRODUCTS
    // =====================================================

    private void searchProducts() {

        String keyword =
                txtSearch.getText()
                        .trim();

        if (keyword.isEmpty()) {

            loadProducts();
            return;
        }

        ProductDAO dao =
                new ProductDAO();

        List<Product> products =
                dao.searchProducts(keyword);

        model.setRowCount(0);

        for (Product p : products) {

            model.addRow(
                    new Object[]{
                            p.getId(),
                            p.getProductName(),
                            p.getCategory(),
                            p.getPrice(),
                            p.getQuantity()
                    }
            );
        }

        if (products.isEmpty()) {

            // Don't show a popup while typing.
            // The empty table itself indicates
            // that no matching product was found.
        }
    }

    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtCategory.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");

        table.clearSelection();
    }
}
