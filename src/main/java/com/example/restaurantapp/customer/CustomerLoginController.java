package com.example.restaurantapp.customer;

import com.example.restaurantapp.util.CustomerLoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CustomerLoginController {
    Customer customer;
    @FXML
    private Button reset,login;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label userName,pass;
    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        userName.setText("");
        pass.setText("");
        String user=textField.getText();
        if(user.equals(null) || user.equals("")) {
            showUserNameAlert();
        }
        else {
            String password=passwordField.getText();
            if(password.equals("") || password.equals(null)) {
                showPasswordAlert();
            }
            else {
                CustomerLoginDTO customerLoginDTO=new CustomerLoginDTO(user,password);
                customer.getSocketWrapper().write(customerLoginDTO);
            }
        }
    }
    @FXML
    public void resetAction(ActionEvent event) {
        textField.setText("");
        passwordField.setText("");
        userName.setText("");
        pass.setText("");
    }
    private void showUserNameAlert() {
        this.userName.setText("Please enter username");
    }
    private void showPasswordAlert() {
        this.pass.setText("Please enter password");
    }
    public void setCustomer(Customer customer) {
        this.customer=customer;
    }
}
