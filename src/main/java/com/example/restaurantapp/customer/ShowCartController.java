package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.util.OrderDTO;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowCartController implements Initializable {
    List<Food> cartList=new ArrayList<>();
    private Customer customer;
    private SocketWrapper socketWrapper;
    @FXML
    private Button back,removeFromCart,orderButton;
    @FXML
    private Label totalField,totalPrice;
    @FXML
    private TableView<Food> tableView;
    @FXML
    private TableColumn<Food,String> foodColumn;
    @FXML
    private TableColumn<Food,String> restaurantColumn;
    @FXML
    private TableColumn<Food,String> categoryColumn;
    @FXML
    private TableColumn<Food,Double> priceColumn;
    @FXML
    private TableColumn<Food, CheckBox> selectColumn;
    ObservableList<Food> foodObservableList;
    public void backAction(ActionEvent event) throws IOException {
        customer.showOrderPage();
    }

    public void orderAction(ActionEvent event) throws IOException {
        if(!cartList.isEmpty()) {
            for (var v : cartList) {
                customer.addToOrderList(v);
            }
            Set<Integer> set = new HashSet<>();
            for (var v : cartList) {
                set.add(v.getRestaurant_id());
            }
            for (var v : set) {
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setRestaurantId(v);
                List<Food> foodList = new ArrayList<>();
                for (var food : cartList) {
                    if (food.getRestaurant_id() == v) {
                        foodList.add(food);
                    }
                }
                orderDTO.setFoodList(foodList);
                socketWrapper.write(orderDTO);
            }
            cartList.clear();
            customer.setCartList(cartList);
            setTableView(cartList);
        }
    }

    public void removeFromCartAction(ActionEvent event) {
        /*for(Food food:cartList) {
            if(food.getSelect().isSelected()) {
                cartList.remove(food);
            }
        }
        setTableView(cartList);
        customer.setCartList(cartList);*/
        for(int i=0;i<cartList.size();i++) {
            if(cartList.get(i).getSelect().isSelected()) {
                cartList.get(i).showDetails();
                var remove = cartList.remove(i);
                i--;
            }
        }
        setCartList(cartList);
        setTableView(cartList);
        customer.setCartList(cartList);
    }
    public void setCustomer(Customer customer) {
        this.customer=customer;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {
        this.socketWrapper=socketWrapper;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Food> foodList=new ArrayList<>();
        foodObservableList= FXCollections.observableList(foodList);
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant_name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        tableView.setItems(foodObservableList);
    }
    public void setTableView(List<Food> list) {
        List<Food> cartList=list;
        foodObservableList= FXCollections.observableList(cartList);
//        foodColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        restaurantColumn.setCellValueFactory(new PropertyValueFactory<>("restaurant_name"));
//        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//        selectColumn.setCellValueFactory(new PropertyValueFactory<>(""));
        tableView.setItems(foodObservableList);
        totalField.setText("Total item(s) : "+cartList.size());
        setTotalPrice();
    }
    public void setCartList(List<Food> cartList) {
        this.cartList=cartList;
    }
    public List<Food> getCartList() {
        return cartList;
    }
    public void setTotalPrice() {
        double prc=0;
        for(var v:cartList) {
            prc+=v.getPrice();
        }
        totalPrice.setText("Total price : " +String.valueOf(prc));
    }
}
