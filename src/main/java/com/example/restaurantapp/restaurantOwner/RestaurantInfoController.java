package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.Part1.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RestaurantInfoController {
    RestaurantOwner restaurantOwner;
    @FXML
    private ImageView imageView;
    @FXML
    private Button back;
    @FXML
    private Label name,price,categories,zipCode,score,id,refreshOrder;
    @FXML
    public void setBack(MouseEvent event) throws IOException {
        restaurantOwner.showRestaurantHome();
    }
    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner=restaurantOwner;
    }
    public void setLabels() {
        Restaurant restaurant=restaurantOwner.getRestaurant();
        name.setText(restaurant.getName());
        price.setText("Price category : "+restaurant.getPrice());
        score.setText("Rating : "+String.valueOf(restaurant.getScore()));
        zipCode.setText("Zip Code : "+restaurant.getZip_code());
        id.setText("ID : "+String.valueOf(restaurant.getId()));
        if(restaurant.getCategories().size()==1) {
            categories.setText(restaurant.getCategories().get(0));
        }
        else if(restaurant.getCategories().size()==2) {
            categories.setText(restaurant.getCategories().get(0)+",\n"+restaurant.getCategories().get(1));
        }
        else if(restaurant.getCategories().size()==3) {
            categories.setText(restaurant.getCategories().get(0)+",\n"+restaurant.getCategories().get(1)+",\n"+restaurant.getCategories().get(2));
        }
    }
    public void setImageView() {
        String filename=null;
        if(restaurantOwner.getRestaurant().getName().equalsIgnoreCase("KFC")) {
            filename="kfc.png";
        }
        else if(restaurantOwner.getRestaurant().getName().equalsIgnoreCase("IHOP")) {
            filename="ihop.png";
        }
        else if(restaurantOwner.getRestaurant().getName().equalsIgnoreCase("McDonalds")) {
            filename="mcdonalds.png";
        }
        else if(restaurantOwner.getRestaurant().getName().equalsIgnoreCase("Starbucks")) {
            filename="starbucks.png";
        }
        Image img =new Image(RestaurantOwner.class.getResourceAsStream(filename));
        imageView.setImage(img);
    }
    public void setNewOrderLabel() {
        refreshOrder.setText("You have new order(s)");
    }
}
