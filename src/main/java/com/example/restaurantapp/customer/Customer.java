package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.RestaurantDatabase;
import com.example.restaurantapp.server.ReadThreadServer;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Application {
    private List<Food> cartList=new ArrayList<>();
    private List<Food> orderList=new ArrayList<>();
    private String customerName;
    private Stage stage;
    public RestaurantDatabase restaurantDatabase;
    private SocketWrapper socketWrapper;

    /*public Customer(String serverAddress,int serverPort) throws IOException, ClassNotFoundException {
        SocketWrapper socketWrapper=new SocketWrapper(serverAddress,serverPort);
        socketWrapper.write("Customer");
        this.restaurantDatabase=(RestaurantDatabase)socketWrapper.read();
        restaurantDatabase.showRestaurants();
        new WriteThreadCustomer(socketWrapper,restaurantDatabase);
    }*/

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        this.stage=stage;
        this.connectToServer();
        this.showCustomerLogin();
    }
    private void connectToServer() throws IOException, ClassNotFoundException {
        String serverAddress="127.0.0.1";
        int serverPort=33333;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        socketWrapper.write("Customer");
        //this.restaurantDatabase=(RestaurantDatabase) socketWrapper.read();
        //new WriteThreadCustomer(socketWrapper,restaurantDatabase);
        new ReadThreadCustomer(socketWrapper,this);
    }
    public void showCustomerHome() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerHome.fxml"));
        Parent root=loader.load();
        CustomerHomeController controller=loader.getController();
        controller.setCustomer(this);
        controller.setThings(customerName);
        stage.setTitle("Customer Home");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void showCustomerLogin() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerLogin.fxml"));
        Parent root=loader.load();
        CustomerLoginController controller=loader.getController();
        controller.setCustomer(this);
        stage.setTitle("Customer Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public SocketWrapper getSocketWrapper() {
        return this.socketWrapper;
    }
    public void setRestaurantDatabase(RestaurantDatabase restaurantDatabase) {
        this.restaurantDatabase=restaurantDatabase;
    }
    public void setCustomerName(String customerName) {
        this.customerName=customerName;
    }
    public void showLoginFailed() throws IOException{
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect credentials");
        alert.setContentText("Username and password you provided are incorrect");
        alert.showAndWait();
    }
    public void showCustomerResSearch() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerResSearch.fxml"));
        Parent root=loader.load();
        CustomerResSearchController controller=loader.getController();
        controller.setCustomer(this);
        controller.setChoiceBox();
        stage.setTitle("Search Restaurant");
        stage.setScene(new Scene(root));
        stage.show();
    }
//    public void showFoodSearch() throws IOException {
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("CustomerFoodSearch.fxml"));
//        Parent root=loader.load();
//        CustomerFoodSearchController controller=loader.getController();
//        controller.setCustomer(this);
//        controller.setChoiceBox();
//        stage.setTitle("Search Food");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
    public void showOrderPage() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderFood.fxml"));
        Parent root=loader.load();
        OrderFoodController controller=loader.getController();
        controller.setCustomer(this);
        controller.setChoiceBox();
        stage.setTitle("Search Foods");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void setCartList(List<Food> cartList) {
        this.cartList=cartList;
    }
    public List<Food> getCartList() {
        return cartList;
    }
    public List<Food> getOrderList() {
        return orderList;
    }
    public void setOrderList(List<Food> list) {
        this.orderList=list;
    }
    public void addToOrderList(Food food) {
        orderList.add(0,food);
    }
    public void showCart() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("showCart.fxml"));
        Parent root=loader.load();
        ShowCartController controller=loader.getController();
        controller.setCustomer(this);
        controller.setSocketWrapper(this.socketWrapper);
        controller.setCartList(cartList);
        controller.setTableView(cartList);
        //controller.setChoiceBox();
        stage.setTitle("Order");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void showMyOrders() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowMyOrder.fxml"));
        Parent root=loader.load();
        ShowMyOrderController controller=loader.getController();
        controller.setCustomer(this);
        //controller.setSocketWrapper(this.socketWrapper);
        //controller.setCartList(cartList);
        controller.setTableView(orderList);
        //controller.setChoiceBox();
        stage.setTitle("Order");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //new Customer("127.0.0.1",16281);
        launch(args);
    }
}
