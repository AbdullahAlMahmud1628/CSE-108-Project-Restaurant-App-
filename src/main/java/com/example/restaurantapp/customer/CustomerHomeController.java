package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class CustomerHomeController {
    @FXML
    private Button myOrders,logOut,searchRestaurant,myCart,order;
    @FXML
    private Customer customer;
    @FXML
    private Label welcome,name;

    public void setCustomer(Customer customer) {
        this.customer=customer;
    }
    @FXML
    public void logOutAction(ActionEvent event) throws IOException {
        customer.showCustomerLogin();
    }
//    public void searchFoodAction(ActionEvent event) throws IOException {
//        customer.showFoodSearch();
//    }

    public void searchResAction(ActionEvent event) throws IOException {
        customer.showCustomerResSearch();
    }

    public void setOrder(ActionEvent event) throws IOException {
        customer.showOrderPage();
    }

    public void setMyOrders(ActionEvent event) throws IOException {
        customer.showMyOrders();
    }
    public void setThings(String n) {
        welcome.setText("Welcome");
        name.setText(n);
        if(customer.getCartList().size()>0)
            myCart.setText("My Cart ("+customer.getCartList().size()+")");
        else {
            myCart.setText("My Cart");
        }
    }

    public void setCart(ActionEvent event) throws IOException {
        customer.showCart();
    }
}
