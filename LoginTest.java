package com.stocksphere.main;

import com.stocksphere.dao.UserDAO;
import com.stocksphere.model.User;

public class LoginTest {

    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        User user = dao.login("admin", "admin123");

        if (user != null) {
            System.out.println("Login Successful");
            System.out.println("Welcome " + user.getUsername());
            System.out.println("Role : " + user.getRole());
        } else {
            System.out.println("Invalid Username or Password");
        }
    }
}
