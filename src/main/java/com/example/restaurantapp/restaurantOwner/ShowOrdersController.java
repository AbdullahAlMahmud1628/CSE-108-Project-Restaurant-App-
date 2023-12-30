package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowOrdersController implements Initializable {
    private RestaurantOwner restaurantOwner;
    @FXML
    private Button back;
    @FXML
    private TableView<Food> tableView;
    @FXML
    private TableColumn<Food,String> foodColumn;
    @FXML
    private TableColumn<Food,String > categoryColumn;
    @FXML
    private TableColumn<Food,Double> priceColumn;
    ObservableList<Food> foodObservableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Food> foodList=new ArrayList<>();
        foodObservableList= FXCollections.observableList(foodList);
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(foodObservableList);
    }
    public void setTableView(List<Food> orderList) {
        foodObservableList=FXCollections.observableList(orderList);
        tableView.setItems(foodObservableList);
    }
    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner=restaurantOwner;
    }

    public void setBack(ActionEvent event) throws IOException {
        restaurantOwner.showRestaurantHome();
    }
}
