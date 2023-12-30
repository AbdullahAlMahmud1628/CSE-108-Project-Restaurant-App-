package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.Part1.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RestaurantFoodController implements Initializable {
    private RestaurantOwner restaurantOwner;
    @FXML
    private Button back,costliest,searchButton,showAll;
    @FXML
    private TextField search;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TableView<Food> table;
    @FXML
    private TableColumn<Food,String> FoodItem;
    @FXML
    private TableColumn<Food,Double> Price;
    @FXML
    private TableColumn<Food,String> Category;
    @FXML
    private Label choiceAlert,searchAlert,refreshOrder;

    private String [] options={"By Name","By Category"};
    ObservableList<Food> foodObservableList;
    @FXML
    public void setBack(MouseEvent event) throws IOException {
        restaurantOwner.showRestaurantHome();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Food> foodList=new ArrayList<>();
        foodObservableList=FXCollections.observableList(foodList);
        FoodItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        //table.getColumns().addAll(FoodItem,Category,Price);
        table.setItems(foodObservableList);
    }
    @FXML
    public void setSearchButton(MouseEvent event) {
        List<Food> foodList=new ArrayList<>();
        foodObservableList=FXCollections.observableList(foodList);
        table.setItems(foodObservableList);
        choiceAlert.setText("");
        searchAlert.setText("");
        String choice=null;
        try {
            choice=choiceBox.getValue();
        } catch (Exception e) {
            e.printStackTrace();
            showChoiceAlert();
        }
        if(choice.equals("Search Options")) {
            showChoiceAlert();
        }
        else if(!choice.equals(null) || !choice.equals(""))
        {
            String s=search.getText();
            if(s.equals(null) || s.equals("")) {
                showSearchAlert();
            }
            else if(choice.equals("By Name")) {
                List<Integer> index=restaurantOwner.getRestaurant().searchFoodByNameInRestaurant(s.trim());
                if(index.isEmpty()) {
                    showSearchAlert2();
                }
                else {
                    for(var v:index) {
                        Food food=restaurantOwner.getRestaurant().getMenu().get(v);
                        foodList.add(food);
                       // System.out.println(food.getName());
                    }
                    foodObservableList=FXCollections.observableList(foodList);
                    table.setItems(foodObservableList);
                }
            }
            else if(choice.equals("By Category")) {
//                List<Food> foodList=new ArrayList<>();
//                foodObservableList=FXCollections.observableList(foodList);
//                table.setItems(foodObservableList);
                List<Integer> index=restaurantOwner.getRestaurant().searchFoodByCategoryInRestaurant(s.trim());
                if(index.isEmpty()) {
                    showSearchAlert2();
                }
                else {
                    for(var v:index) {
                        Food food=restaurantOwner.getRestaurant().getMenu().get(v);
                        foodList.add(food);
                        // System.out.println(food.getName());
                    }
                    foodObservableList=FXCollections.observableList(foodList);
                    table.setItems(foodObservableList);
                }
            }
        }
    }
    @FXML
    public void setCostliest(MouseEvent event){
        choiceAlert.setText("");
        searchAlert.setText("");
        List<Food> foodList=new ArrayList<>();
        foodObservableList=FXCollections.observableList(foodList);
        table.setItems(foodObservableList);
        List<Integer> index=restaurantOwner.getRestaurant().searchCostliestInRestaurant();
        if(index.isEmpty()){
            showSearchAlert2();
        }
        else {
            for(var v:index) {
                Food food=restaurantOwner.getRestaurant().getMenu().get(v);
                foodList.add(food);
            }
            foodObservableList=FXCollections.observableList(foodList);
            table.setItems(foodObservableList);
        }
    }
    @FXML
    public void setshowAll(MouseEvent event) {
        choiceAlert.setText("");
        searchAlert.setText("");
        List<Food> foodList=restaurantOwner.getRestaurant().getMenu();
        foodObservableList=FXCollections.observableList(foodList);
        table.setItems(foodObservableList);
    }


    private void showChoiceAlert() {
        choiceAlert.setText("Choose an option");
    }
    private void showSearchAlert() {
        searchAlert.setText("Search box is empty");
    }
    private void showSearchAlert2() {
        searchAlert.setText("No food found!");
    }
    public void setChoice() {
        choiceBox.setValue("Search Options");
        choiceBox.getItems().addAll(options);
    }
    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner=restaurantOwner;
    }
    public void setNewOrderLabel() {
        refreshOrder.setText("You have new order(s)");
    }
}
