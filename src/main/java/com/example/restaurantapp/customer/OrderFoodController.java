package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFoodController implements Initializable {
    @FXML
    private Label resAlert,searchAlert,searchBarAlert,rangeAlert,optionsAlert,addAlert;
    private Customer customer;
    @FXML
    private Button search,back,cart,addToCart,showAll,showCostliest;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField resName,searchBar,lowerRangeBox,upperRangeBox;
    @FXML
    private TableView<Food> tableView;
    @FXML
    private TableColumn<Food,String> foodName;
    @FXML private TableColumn<Food,String> categoryName;
    @FXML private TableColumn<Food,Double> price;
    @FXML private TableColumn<Food,CheckBox> addToCartColumn;
    @FXML private TableColumn<Food,String> restaurantName;
    ObservableList<Food> foodObservableList;
    List<Food> order=new ArrayList<>();
    private String [] options={"By Name","By Category","By Name In A Restaurant","By Category In A Restaurant"};
    public void setChoiceBox() {
        choiceBox.setValue("Search options");
        choiceBox.getItems().addAll(options);
        if(customer.getCartList().size()>0)
            cart.setText("Cart ("+customer.getCartList().size()+ ")");
    }
    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Food> foodList = new ArrayList<>();
        foodObservableList = FXCollections.observableList(foodList);
        foodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("category"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addToCartColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        restaurantName.setCellValueFactory(new PropertyValueFactory<>("restaurant_name"));
        //table.getColumns().addAll(FoodItem,Category,Price);
        tableView.setItems(foodObservableList);
    }

    public void searchAction(ActionEvent event) {
        setlabel();
        order.clear();
        foodObservableList=FXCollections.observableList(order);
        tableView.setItems(foodObservableList);
        String option=choiceBox.getValue();
        if(option.equalsIgnoreCase("Search options")) {
            optionsAlert.setText("Choose an option");
        }
        else if(option.equalsIgnoreCase("By Name") || option.equalsIgnoreCase("By Category")) {
            String searchKey=searchBar.getText();
            if(searchKey.equalsIgnoreCase(null) || searchKey.equalsIgnoreCase("")) {
                searchBarAlert.setText("Enter food search key");
            }
            else {
                String r1=lowerRangeBox.getText();
                String r2=upperRangeBox.getText();
                if((r1.equals(null) || r1.equals("")) || (r2.equals(null) || r2.equals(""))) {
                    List<Integer> index=new ArrayList<>();
                    if(option.equals("By Name")) {
                        index=customer.restaurantDatabase.searchFoodByName(searchKey.trim());
                    }
                    else if(option.equals("By Category")) {
                        index=customer.restaurantDatabase.searchFoodByCategory(searchKey.trim());
                    }
                    if(index.isEmpty()) {
                        searchAlert.setText("No food found");
                    }
                    else {
                        for(var v:index) {
                            Food food=customer.restaurantDatabase.getFood(v);
                            food.setCheckBox(new CheckBox());
                            food.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(food.getRestaurant_id()).getName());
                            order.add(food);
                        }
                        foodObservableList=FXCollections.observableList(order);
                        tableView.setItems(foodObservableList);
                    }
                }
                else {
                    double range1=-1000,range2=-1000;
                    try{
                        range1=Double.valueOf(r1);
                        range2=Double.valueOf(r2);
                    } catch (Exception e) {
                        rangeAlert.setText("Price range must be some positive number");
                    }
                    if(range1>=0 && range2>=0) {
                        List<Integer> index=new ArrayList<>();
                        if(option.equals("By Name")) {
                            index=customer.restaurantDatabase.searchFoodByName(searchKey.trim());
                        }
                        else if(option.equals("By Category")) {
                            index=customer.restaurantDatabase.searchFoodByCategory(searchKey.trim());
                        }
                        if(index.isEmpty()) {
                            searchAlert.setText("No food fouund");
                        }
                        else {
                            for(var v:index) {
                                Food food=customer.restaurantDatabase.getFood(v);
                                if(food.getPrice()>=range1 && food.getPrice()<=range2) {
                                    food.setCheckBox(new CheckBox());
                                    food.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(food.getRestaurant_id()).getName());
                                    order.add(food);
                                }
                            }
                            if(order.isEmpty()) {
                                searchAlert.setText("No food found");
                            }
                            else {
                                foodObservableList=FXCollections.observableList(order);
                                tableView.setItems(foodObservableList);
                            }
                        }
                    }
                }
            }
        }
        else if(option.equalsIgnoreCase("By Name In A Restaurant")||option.equalsIgnoreCase("By Category In A Restaurant")) {
            String searchKey=searchBar.getText();
            if(searchKey.equalsIgnoreCase(null) || searchKey.equalsIgnoreCase("")) {
                searchBarAlert.setText("Enter food search key");
            }
            else {
                String res=resName.getText();
                if(res.equalsIgnoreCase("") || res.equalsIgnoreCase(null)) {
                    resAlert.setText("Enter Restaurant Name");
                }
                else{
                    if(!(customer.restaurantDatabase.restaurantExists(res.trim()))) {
                        resAlert.setText("No Restaurant Found");
                    }
                    else {
                        String r1 = lowerRangeBox.getText();
                        String r2 = upperRangeBox.getText();
                        if ((r1.equals(null) || r1.equals("")) && (r2.equals(null) || r2.equals(""))) {
                            List<Integer> index = new ArrayList<>();
                            if (option.equals("By Name In A Restaurant")) {
                                index = customer.restaurantDatabase.searchFoodByNameInRestaurant(searchKey.trim(),res.trim());
                            } else if (option.equals("By Category In A Restaurant")) {
                                index = customer.restaurantDatabase.searchFoodByCategoryInRestaurant(searchKey.trim(),res.trim());
                            }
                            if (index.isEmpty()) {
                                searchAlert.setText("No food found");
                            } else {
                                for (var v : index) {
                                    Food food = customer.restaurantDatabase.getRestaurant(res.trim()).getMenu().get(v);
                                    food.setCheckBox(new CheckBox());
                                    food.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(food.getRestaurant_id()).getName());
                                    order.add(food);
                                }
                                foodObservableList = FXCollections.observableList(order);
                                tableView.setItems(foodObservableList);
                            }
                        } else {
                            double range1 = -1000, range2 = -1000;
                            try {
                                range1 = Double.valueOf(r1);
                                range2 = Double.valueOf(r2);
                            } catch (Exception e) {
                                rangeAlert.setText("Price range must be some positive number");
                            }
                            if (range1 >= 0 && range2 >= 0) {
                                List<Integer> index = new ArrayList<>();
                                if (option.equals("By Name In A Restaurant")) {
                                    index = customer.restaurantDatabase.searchFoodByNameInRestaurant(searchKey.trim(),res.trim());
                                } else if (option.equals("By Category In A Restaurant")) {
                                    index = customer.restaurantDatabase.searchFoodByCategoryInRestaurant(searchKey.trim(),res.trim());
                                }
                                if (index.isEmpty()) {
                                    searchAlert.setText("No food fouund");
                                } else {
                                    for (var v : index) {
                                        Food food = customer.restaurantDatabase.getRestaurant(res.trim()).getMenu().get(v);
                                        if (food.getPrice() >= range1 && food.getPrice() <= range2) {
                                            food.setCheckBox(new CheckBox());
                                            food.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(food.getRestaurant_id()).getName());
                                            order.add(food);
                                        }
                                    }
                                    if (order.isEmpty()) {
                                        searchAlert.setText("No food found");
                                    } else {
                                        foodObservableList = FXCollections.observableList(order);
                                        tableView.setItems(foodObservableList);
                                    }
                                }
                            }
                            else {
                                rangeAlert.setText("Price must be some positive number");
                            }
                        }
                    }
                }
            }
        }
    }

    public void addToCartAction(ActionEvent event) {
        for(Food food :order) {
            if(food.getSelect().isSelected()) {
//                food.showDetails();
//                food.getSelect().setSelected(false);
                customer.getCartList().add(food);
                food.getSelect().setSelected(false);
            }
        }
        if(customer.getCartList().size()>0)
            cart.setText("Cart ("+customer.getCartList().size()+")");
        else
            cart.setText("Cart");
    }
    public void setlabel() {
        resAlert.setText("");
        searchBarAlert.setText("");
        addAlert.setText("");
        rangeAlert.setText("");
        searchAlert.setText("");
        optionsAlert.setText("");
    }
    public void showResAlert() {
        resAlert.setText("Enter restaurant name");
    }

    public void setRestaurantBox(ActionEvent event) {
        if(choiceBox.getValue().equalsIgnoreCase("By Name In A Restaurant") || choiceBox.getValue().equalsIgnoreCase("By Category In A Restaurant")) {
            resName.setVisible(true);
            if(choiceBox.getValue().equalsIgnoreCase("By Name In A Restaurant")) {
                searchBar.setPromptText("Enter Food Name");
            }
            else if(choiceBox.getValue().equalsIgnoreCase("By Category In A Restaurant")){
                searchBar.setPromptText("Enter Category");
            }
        }
        else if(choiceBox.getValue().equalsIgnoreCase("By Name")||choiceBox.getValue().equalsIgnoreCase("By category")){
            resName.setVisible(false);
            if(choiceBox.getValue().equalsIgnoreCase("By Name")) {
                searchBar.setPromptText("Enter Food Name");
            }
            else if(choiceBox.getValue().equalsIgnoreCase("By Category")){
                searchBar.setPromptText("Enter Category");
            }
        }
    }

    public void cartAction(ActionEvent event) throws IOException {
        customer.showCart();
    }

    public void setBack(ActionEvent event) throws IOException {
        customer.showCustomerHome();
    }

    public void setShowAll(ActionEvent event) {
        setlabel();
        order.clear();
        String r1=lowerRangeBox.getText();
        String r2=upperRangeBox.getText();
        if(r1.equals("") ||r1.equals(null) ||r2.equals("") || r2.equals(null)) {
            List<Food> foodList=customer.restaurantDatabase.getFoodList();
            for(var v:foodList) {
                order.add(v);
            }
            for(var v:order) {
                v.setCheckBox(new CheckBox());
                v.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(v.getRestaurant_id()).getName());
            }
            foodObservableList=FXCollections.observableList(order);
            tableView.setItems(foodObservableList);
        }
        else {
            double range1=-1000,range2=-1000;
            try {
                range1=Double.valueOf(r1);
                range2=Double.valueOf(r2);
            } catch(Exception e) {
                System.out.println(e);
                rangeAlert.setText("Price must be some positive number");
            }
            if(range1>=0 && range2>=0) {
                List<Food> foodList=customer.restaurantDatabase.getFoodList();
                for(var v:foodList) {
                    if(v.getPrice()>=range1 && v.getPrice()<=range2) {
                        order.add(v);
                    }
                }
                if(order.isEmpty()) {
                    searchAlert.setText("No food found");
                }
                else {
                    for(var v:order) {
                        v.setCheckBox(new CheckBox());
                        v.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(v.getRestaurant_id()).getName());
                    }
                    foodObservableList=FXCollections.observableList(order);
                    tableView.setItems(foodObservableList);
                }
            }
        }
    }

    public void setCostliest(ActionEvent event) {
        setlabel();
        order.clear();
        List<Integer> index=customer.restaurantDatabase.searchCostliest();
        for(var v: index) {
            Food food=customer.restaurantDatabase.getFood(v);
            food.setCheckBox(new CheckBox());
            food.setRestaurant_name(customer.restaurantDatabase.getRestaurantById(food.getRestaurant_id()).getName());
            order.add(food);
        }
        foodObservableList=FXCollections.observableList(order);
        tableView.setItems(foodObservableList);
    }
}
