package com.example.restaurantapp.restaurantOwner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RestaurantHomeController {
    @FXML
    private Button myInfo,orders,foods,logOut;
    @FXML
    private ImageView imageView;
    @FXML
    private Label refreshOrder;
    private RestaurantOwner restaurantOwner;
    @FXML
    public void setMyInfo(MouseEvent event) throws IOException {
        restaurantOwner.showRestaurantInfo();
    }
    @FXML
    public void setOrders(MouseEvent event) throws IOException {
        restaurantOwner.showOrders();
    }
    @FXML
    public void setFoods(MouseEvent event) throws IOException {
        restaurantOwner.showFoodSearch();
    }
    @FXML
    public void setLogOut(MouseEvent event) throws IOException {
        restaurantOwner.showRestaurantLogin();
    }
    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner=restaurantOwner;
    }

    public void setNewOrderLabel() {
        refreshOrder.setText("You have new order(s)");
    }
}
