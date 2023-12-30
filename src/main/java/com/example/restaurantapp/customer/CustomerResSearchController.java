package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerResSearchController implements Initializable {
    @FXML
    private Button back,showAll,search,logOut;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBar;
    @FXML
    private Label choiceAlert,searchAlert;
    private Customer customer;
    @FXML
    private TableView<Restaurant> tableView;
    @FXML
    private TableColumn<Restaurant,String > resName;
    @FXML private TableColumn<Restaurant,List<String>> resCategory;
    @FXML private TableColumn<Restaurant,String > resPrice;
    @FXML private TableColumn<Restaurant,Double> resScore;

    @FXML private TextField lowerRating,upperRating;
    @FXML private Label ratingAlert;
    ObservableList<Restaurant> restaurantObservableList;

    private String [] options={"By Name","By Category","By Price Category"};

    public void setBack(ActionEvent event) throws IOException {
        customer.showCustomerHome();
    }

    public void setLogOut(ActionEvent event) throws IOException {
        customer.showCustomerLogin();
    }

    public void setSearch(ActionEvent event) {
        choiceAlert.setText("");
        searchAlert.setText("");
        ratingAlert.setText("");
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurantObservableList=FXCollections.observableList(restaurantList);
        tableView.setItems(restaurantObservableList);
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
            String s=searchBar.getText();
            if(s.equals(null) || s.equals("")) {
                showSearchAlert();
            }
            else {
                String r1=lowerRating.getText();
                String r2=upperRating.getText();
                if(r1.equals("") || r1.equals(null) || r2.equals("") || r2.equals(null)) {
                    if(choice.equals("By Name")) {
                        List<Integer> index=customer.restaurantDatabase.searchRestaurantByName(s.trim());
                        if(index.isEmpty()) {
                            showSearchAlert2();
                        }
                        else {
                            for(var v:index) {
                                Restaurant restaurant=customer.restaurantDatabase.getRestaurantList().get(v);
                                restaurantList.add(restaurant);
                                // System.out.println(food.getName());
                            }
                            restaurantObservableList=FXCollections.observableList(restaurantList);
                            tableView.setItems(restaurantObservableList);
                        }
                     }
                else if(choice.equals("By Category")) {
                    List<Integer> index=customer.restaurantDatabase.searchRestaurantByCategory(s.trim());
                    if(index.isEmpty()) {
                        showSearchAlert2();
                    }
                    else {
                        for(var v:index) {
                            Restaurant restaurant=customer.restaurantDatabase.getRestaurantList().get(v);
                            restaurantList.add(restaurant);
                            // System.out.println(food.getName());
                        }
                        restaurantObservableList=FXCollections.observableList(restaurantList);
                        tableView.setItems(restaurantObservableList);
                    }
                }
                else if(choice.equals("By Price Category")) {
                    if(!(s.equals("$")|| s.equals("$$") ||s.equals("$$$"))) {
                        showSearchAlert3();
                    }
                    else {
                        List<Integer> index=customer.restaurantDatabase.searchRestaurantByPrice(s.trim());
                        if(index.isEmpty()) {
                            showSearchAlert2();
                        }
                        else {
                            for(var v:index) {
                                Restaurant restaurant=customer.restaurantDatabase.getRestaurantList().get(v);
                                restaurantList.add(restaurant);
                                // System.out.println(food.getName());
                            }
                            restaurantObservableList=FXCollections.observableList(restaurantList);
                            tableView.setItems(restaurantObservableList);
                        }
                    }
                }
            }
            else {
                    double range1 = -1, range2 = -2;
                    try {
                        range1 = Double.valueOf(r1);
                        range2 = Double.valueOf(r2);
                    } catch (Exception e) {
                        System.out.println(e);
                        ratingAlert.setText("Rating must be some number between 0 to 5");
                    }
                    if(range1<0 || range1>5 || range2>5 || range1<0) {
                        ratingAlert.setText("Rating must be some number between 0 to 5");
                    }
                    else if (range1 >= 0 && range2 >= 0) {
                        if (choice.equals("By Name")) {
                            List<Integer> index = customer.restaurantDatabase.searchRestaurantByName(s.trim());
                            if (index.isEmpty()) {
                                showSearchAlert2();
                            } else {
                                for (var v : index) {
                                    Restaurant restaurant = customer.restaurantDatabase.getRestaurantList().get(v);
                                    if (restaurant.getScore()>=range1 && restaurant.getScore()<=range2)
                                        restaurantList.add(restaurant);
                                    // System.out.println(food.getName());
                                }
                                if(restaurantList.isEmpty()) {
                                    searchAlert.setText("No restaurant found");
                                }
                                else {
                                    restaurantObservableList = FXCollections.observableList(restaurantList);
                                    tableView.setItems(restaurantObservableList);
                                }
                            }
                        } else if (choice.equals("By Category")) {
//                List<Restaurant> restaurantList=new ArrayList<>();
//                restaurantObservableList=FXCollections.observableList(restaurantList);
//                tableView.setItems(restaurantObservableList);
                            List<Integer> index = customer.restaurantDatabase.searchRestaurantByCategory(s.trim());
                            if (index.isEmpty()) {
                                showSearchAlert2();
                            } else {
                                for (var v : index) {
                                    Restaurant restaurant = customer.restaurantDatabase.getRestaurantList().get(v);
                                    if (restaurant.getScore()>=range1 && restaurant.getScore()<=range2)
                                         restaurantList.add(restaurant);
                                    // System.out.println(food.getName());
                                }
                                if(restaurantList.isEmpty()) {
                                    searchAlert.setText("No restaurant found");
                                }
                                else {
                                    restaurantObservableList = FXCollections.observableList(restaurantList);
                                    tableView.setItems(restaurantObservableList);
                                }
                            }
                        } else if (choice.equals("By Price Category")) {
//                List<Restaurant> restaurantList=new ArrayList<>();
//                restaurantObservableList=FXCollections.observableList(restaurantList);
//                tableView.setItems(restaurantObservableList);
                            if (!(s.equals("$") || s.equals("$$") || s.equals("$$$"))) {
                                showSearchAlert3();
                            } else {
                                List<Integer> index = customer.restaurantDatabase.searchRestaurantByPrice(s.trim());
                                if (index.isEmpty()) {
                                    showSearchAlert2();
                                } else {
                                    for (var v : index) {
                                        Restaurant restaurant = customer.restaurantDatabase.getRestaurantList().get(v);
                                        if (restaurant.getScore()>=range1 && restaurant.getScore()<=range2)
                                            restaurantList.add(restaurant);
                                        // System.out.println(food.getName());
                                    }
                                    if(restaurantList.isEmpty()) {
                                        searchAlert.setText("No restaurant found");
                                    }
                                    else {
                                        restaurantObservableList = FXCollections.observableList(restaurantList);
                                        tableView.setItems(restaurantObservableList);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void showSearchAlert3() {
        searchAlert.setText("Price category must be $ , $$ or $$$");
    }

    private void showSearchAlert2() {
        searchAlert.setText("No restaurant found");
    }

    private void showSearchAlert() {
        searchAlert.setText("Search box is empty");
    }

    private void showChoiceAlert() {
        choiceAlert.setText("Choose an option");
    }

    public void setShowAll(ActionEvent event) {
        choiceAlert.setText("");
        searchAlert.setText("");
        ratingAlert.setText("");
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurantObservableList=FXCollections.observableList(restaurantList);
        tableView.setItems(restaurantObservableList);
        String r1=lowerRating.getText();
        String r2=upperRating.getText();
        if(r1.equals("") || r1.equals(null) || r2.equals("") || r2.equals(null)) {
            for(var v:customer.restaurantDatabase.getRestaurantList()) {
                restaurantList.add(v);
            }
            restaurantObservableList=FXCollections.observableList(restaurantList);
            tableView.setItems(restaurantObservableList);
        }
        else {
            double range1=-1;
            double range2=-2;
            try {
                range1=Double.valueOf(r1);
                range2=Double.valueOf(r2);
            } catch (Exception e) {
                System.out.println(e);
                ratingAlert.setText("Rating must be some number between 0 to 5");
            }
            if(range1<0 || range1>5 || range2>5 || range1<0) {
                ratingAlert.setText("Rating must be some number between 0 to 5");
            }
            else {
                for(var v:customer.restaurantDatabase.getRestaurantList()) {
                    if(v.getScore()>=range1 && v.getScore()<=range2)
                        restaurantList.add(v);
                }
                if(restaurantList.isEmpty()) {
                    searchAlert.setText("No restaurant found");
                }
                else {
                    restaurantObservableList=FXCollections.observableList(restaurantList);
                    tableView.setItems(restaurantObservableList);
                }
            }
        }
    }
    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Restaurant> restaurantList=new ArrayList<>();
        restaurantObservableList= FXCollections.observableList(restaurantList);
        resName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
        resPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        resScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        //table.getColumns().addAll(FoodItem,Category,Price);
        tableView.setItems(restaurantObservableList);
    }
    public void setChoiceBox() {
        choiceBox.setValue("Search Options");
        choiceBox.getItems().addAll(options);
    }
}
