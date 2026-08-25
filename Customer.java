package com.stocksphere.model;

public class Customer {

    private int id;
    private String customerName;
    private String mobile;
    private String email;
    private String address;

    public Customer() {
    }

    public Customer(int id, String customerName, String mobile,
                    String email, String address) {
        this.id = id;
        this.customerName = customerName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
