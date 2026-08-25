package com.stocksphere.ui;

import com.stocksphere.dao.BillDAO;
import com.stocksphere.dao.ProductDAO;
import com.stocksphere.model.Bill;
import com.stocksphere.model.Product;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BillingFrame extends JFrame {

    private JComboBox<Product> productComboBox;
    private JLabel priceLabel;
    private JTextField quantityField;
    private JLabel subtotalLabel;

    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel grandTotalLabel;

    private final List<Product> products = new ArrayList<>();

    public BillingFrame() {

        setTitle("StockSphere - Billing / Point of Sale");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // ================= TITLE =================

        JLabel title =
                new JLabel("Billing / Point of Sale");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        title.setForeground(
                new Color(25, 118, 210)
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // ================= PRODUCT INPUT =================

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(4, 2, 10, 10)
                );

        inputPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Product Details"
                )
        );

        // Product

        inputPanel.add(
                new JLabel("Product")
        );

        productComboBox =
                new JComboBox<>();

        inputPanel.add(
                productComboBox
        );

        // Price

        inputPanel.add(
                new JLabel("Price")
        );

        priceLabel =
                new JLabel("₹0.00");

        priceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        inputPanel.add(
                priceLabel
        );

        // Quantity

        inputPanel.add(
                new JLabel("Quantity")
        );

        quantityField =
                new JTextField();

        inputPanel.add(
                quantityField
        );

        // Subtotal

        inputPanel.add(
                new JLabel("Subtotal")
        );

        subtotalLabel =
                new JLabel("₹0.00");

        subtotalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        inputPanel.add(
                subtotalLabel
        );

        JButton addButton =
                new JButton("Add to Cart");

        JPanel productPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        productPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );

        productPanel.add(
                addButton,
                BorderLayout.SOUTH
        );

        // ================= CART TABLE =================

        tableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Product",
                                "Price",
                                "Quantity",
                                "Subtotal"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        cartTable =
                new JTable(tableModel);

        cartTable.setRowHeight(28);

        // Hide ID column because it is only
        // required internally

        cartTable
                .getColumnModel()
                .getColumn(0)
                .setMinWidth(0);

        cartTable
                .getColumnModel()
                .getColumn(0)
                .setMaxWidth(0);

        cartTable
                .getColumnModel()
                .getColumn(0)
                .setWidth(0);

        JScrollPane cartScrollPane =
                new JScrollPane(cartTable);

        cartScrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Shopping Cart"
                )
        );

        // ================= BUTTONS =================

        JButton removeButton =
                new JButton("Remove Selected");

        JButton clearButton =
                new JButton("Clear Cart");

        JButton saveButton =
                new JButton("Save Bill");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                removeButton
        );

        buttonPanel.add(
                clearButton
        );

        buttonPanel.add(
                saveButton
        );

        // ================= GRAND TOTAL =================

        grandTotalLabel =
                new JLabel(
                        "Grand Total: ₹0.00"
                );

        grandTotalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.add(
                grandTotalLabel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.EAST
        );

        // ================= CENTER =================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        centerPanel.add(
                productPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                cartScrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // ================= LOAD PRODUCTS =================

        loadProducts();

        // ================= EVENTS =================

        productComboBox.addActionListener(
                e -> updatePrice()
        );

        quantityField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                calculateSubtotal();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                calculateSubtotal();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                calculateSubtotal();
                            }
                        }
                );

        addButton.addActionListener(
                e -> addToCart()
        );

        removeButton.addActionListener(
                e -> removeSelectedItem()
        );

        clearButton.addActionListener(
                e -> clearCart()
        );

        saveButton.addActionListener(
                e -> saveBill()
        );

        setVisible(true);
    }

    // =====================================================
    // LOAD PRODUCTS
    // =====================================================

    private void loadProducts() {

        ProductDAO dao =
                new ProductDAO();

        products.clear();

        products.addAll(
                dao.getAllProducts()
        );

        productComboBox.removeAllItems();

        for (Product product : products) {

            productComboBox.addItem(
                    product
            );
        }

        updatePrice();
    }

    // =====================================================
    // UPDATE PRICE
    // =====================================================

    private void updatePrice() {

        Product product =
                (Product)
                        productComboBox
                                .getSelectedItem();

        if (product == null) {

            priceLabel.setText(
                    "₹0.00"
            );

            subtotalLabel.setText(
                    "₹0.00"
            );

            return;
        }

        priceLabel.setText(
                String.format(
                        "₹%.2f",
                        product.getPrice()
                )
        );

        calculateSubtotal();
    }

    // =====================================================
    // CALCULATE SUBTOTAL
    // =====================================================

    private void calculateSubtotal() {

        Product product =
                (Product)
                        productComboBox
                                .getSelectedItem();

        if (product == null) {

            subtotalLabel.setText(
                    "₹0.00"
            );

            return;
        }

        try {

            int quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

            if (quantity <= 0) {

                subtotalLabel.setText(
                        "₹0.00"
                );

                return;
            }

            double subtotal =
                    product.getPrice()
                            * quantity;

            subtotalLabel.setText(
                    String.format(
                            "₹%.2f",
                            subtotal
                    )
            );

        } catch (NumberFormatException e) {

            subtotalLabel.setText(
                    "₹0.00"
            );
        }
    }

    // =====================================================
    // ADD TO CART
    // =====================================================

    private void addToCart() {

        Product product =
                (Product)
                        productComboBox
                                .getSelectedItem();

        if (product == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product."
            );

            return;
        }

        int quantity;

        try {

            quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity."
            );

            return;
        }

        if (quantity <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than 0."
            );

            return;
        }

        // ================= STOCK CHECK =================

        if (quantity > product.getQuantity()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Insufficient stock.\n\n"
                            + "Available stock: "
                            + product.getQuantity(),
                    "Stock Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double subtotal =
                product.getPrice()
                        * quantity;

        // ================= ADD ROW =================

        tableModel.addRow(
                new Object[]{
                        product.getId(),
                        product.getProductName(),
                        String.format(
                                "₹%.2f",
                                product.getPrice()
                        ),
                        quantity,
                        String.format(
                                "₹%.2f",
                                subtotal
                        )
                }
        );

        updateGrandTotal();

        quantityField.setText("");
    }

    // =====================================================
    // CALCULATE GRAND TOTAL
    // =====================================================

    private double calculateGrandTotal() {

        double total = 0;

        for (
                int i = 0;
                i < tableModel.getRowCount();
                i++
        ) {

            String amount =
                    tableModel
                            .getValueAt(i, 4)
                            .toString()
                            .replace("₹", "")
                            .trim();

            total +=
                    Double.parseDouble(
                            amount
                    );
        }

        return total;
    }

    // =====================================================
    // UPDATE GRAND TOTAL
    // =====================================================

    private void updateGrandTotal() {

        double total =
                calculateGrandTotal();

        grandTotalLabel.setText(
                String.format(
                        "Grand Total: ₹%.2f",
                        total
                )
        );
    }

    // =====================================================
    // REMOVE SELECTED ITEM
    // =====================================================

    private void removeSelectedItem() {

        int selectedRow =
                cartTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item first."
            );

            return;
        }

        tableModel.removeRow(
                selectedRow
        );

        updateGrandTotal();
    }

    // =====================================================
    // CLEAR CART
    // =====================================================

    private void clearCart() {

        if (tableModel.getRowCount() == 0) {

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Clear all items from cart?",
                        "Clear Cart",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice ==
                        JOptionPane.YES_OPTION
        ) {

            tableModel.setRowCount(0);

            updateGrandTotal();
        }
    }

    // =====================================================
    // SAVE BILL
    // =====================================================

    private void saveBill() {

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cart is empty.\n"
                            + "Add a product first."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to save this bill?",
                        "Confirm Bill",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }

        BillDAO billDAO =
                new BillDAO();

        ProductDAO productDAO =
                new ProductDAO();

        boolean allSaved = true;

        // ================= SAVE EACH ITEM =================

        for (
                int i = 0;
                i < tableModel.getRowCount();
                i++
        ) {

            int productId =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(i, 0)
                                    .toString()
                    );

            String productName =
                    tableModel
                            .getValueAt(i, 1)
                            .toString();

            int quantity =
                    Integer.parseInt(
                            tableModel
                                    .getValueAt(i, 3)
                                    .toString()
                    );

            String totalText =
                    tableModel
                            .getValueAt(i, 4)
                            .toString()
                            .replace("₹", "")
                            .trim();

            double total =
                    Double.parseDouble(
                            totalText
                    );

            // ================= CREATE BILL =================

            Bill bill =
                    new Bill();

            bill.setProductName(
                    productName
            );

            bill.setQuantity(
                    quantity
            );

            bill.setTotal(
                    total
            );

            // ================= SAVE BILL =================

            if (
                    !billDAO.saveBill(bill)
            ) {

                allSaved = false;

                break;
            }

            // ================= REDUCE STOCK =================

            if (
                    !productDAO.reduceStock(
                            productId,
                            quantity
                    )
            ) {

                allSaved = false;

                break;
            }
        }

        // ================= RESULT =================

        if (allSaved) {

            double grandTotal =
                    calculateGrandTotal();

            JOptionPane.showMessageDialog(
                    this,
                    String.format(
                            "Bill Saved Successfully!\n\n"
                                    + "Grand Total: ₹%.2f\n"
                                    + "Stock Updated Successfully.",
                            grandTotal
                    ),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            tableModel.setRowCount(0);

            updateGrandTotal();

            quantityField.setText("");

            loadProducts();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to complete the bill.\n"
                            + "Please check the stock and database.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
