package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.util.RestaurantLoginDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class RestaurantLoginController {
    private RestaurantOwner restaurantOwner;
    @FXML
    private Button reset;
    @FXML
    private Button login;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label id,pass;
    @FXML
    public void resetAction(MouseEvent event) {
        textField.setText("");
        passwordField.setText("");
        id.setText("");
        pass.setText("");
    }
    @FXML
    public void loginAction(MouseEvent event) throws IOException {
        id.setText("");
        pass.setText("");
        String i=textField.getText();
        if(i.equals(null) || i.equals(""))
            showIdAlert();
        else{
            String password=passwordField.getText();
            if(password.equals(null)||password.equals("")) {
                showPasswordAlert();
            }
            else {
                int id=-1000;
                try {
                    id=Integer.parseInt(i);
                } catch(NumberFormatException e) {
                    System.out.println(e);
                    showIdAlert2();
                }
                if(id>=0) {
                    RestaurantLoginDTO restaurantLoginDTO=new RestaurantLoginDTO();
                    restaurantLoginDTO.setId(id);
                    restaurantLoginDTO.setPassword(password);
                    try {
                        restaurantOwner.getSocketWrapper().write(restaurantLoginDTO);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }
    public void setLabel() {
        id.setText("");
        pass.setText("");
    }
    private void showIdAlert() {
        id.setText("Please enter Restaurant Id");
    }
    private void showPasswordAlert() {
        pass.setText("Please enter password");
    }
    private void showIdAlert2() {
        id.setText("Restaurant Id must be of integer value");
    }
    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner=restaurantOwner;
    }
}
