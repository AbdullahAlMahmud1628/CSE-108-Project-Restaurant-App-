package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.Restaurant;
import com.example.restaurantapp.customer.CustomerHomeController;
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
import java.util.Scanner;

public class RestaurantOwner extends Application {

    private boolean homePage,searchPage,infoPage,orderPage,newOrder;
    List<Food> orderList=new ArrayList<>();
    Stage stage;
    private Restaurant restaurant;
    private SocketWrapper socketWrapper;
    /*public RestaurantOwner(String serverAddress,int serverPort) throws IOException, ClassNotFoundException {
        Scanner scanner=new Scanner(System.in);
        socketWrapper=new SocketWrapper(serverAddress,serverPort);
        socketWrapper.write("Restaurant");
        //restaurant=(Restaurant) socketWrapper.read();
        new ReadThreadRestaurantOwner(socketWrapper);
        restaurant.showDetailes();
        functions();
    }*/
    private void functions() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("1.Search food");
        int option=scanner.nextInt();
        while(true) {
            if(option==1) {
                System.out.println("Enter food name");
                scanner.nextLine();
                String s=scanner.nextLine();
                List<Integer> index=restaurant.searchFoodByNameInRestaurant(s.trim());
                for(var v:index) {
                    restaurant.getMenu().get(v).showDetails();
                }
            }
            System.out.println("1.Search food");
            option=scanner.nextInt();
        }
    }
    public SocketWrapper getSocketWrapper() {
        return this.socketWrapper;
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant=restaurant;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        this.connectToServer();
        this.showRestaurantLogin();
    }
    private void connectToServer() throws IOException {
        String serverAddress="127.0.0.1";
        int serverPort=33333;
        this.socketWrapper = new SocketWrapper(serverAddress, serverPort);
        this.socketWrapper.write("Restaurant");
        new ReadThreadRestaurantOwner(socketWrapper,this);
    }

    public void showRestaurantLogin() throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantLogin.fxml"));
        Parent root=loader.load();
        RestaurantLoginController controller=loader.getController();
        controller.setRestaurantOwner(this);
        controller.setLabel();
        stage.setTitle("Restaurant Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showLoginFailedForRestaurant() throws IOException {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("Id and password you provided are incorrect");
        alert.showAndWait();
    }

    public void showRestaurantHome() throws IOException {
        homePage=true;
        infoPage=false;
        orderPage=false;
        searchPage=false;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantHome.fxml"));
        Parent root=loader.load();
        RestaurantHomeController controller=loader.getController();
        controller.setRestaurantOwner(this);
        //controller.setImageView();
        if(newOrder) {
            controller.setNewOrderLabel();
            newOrder=false;
        }
        stage.setTitle("Restaurant Home");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void showRestaurantInfo() throws IOException {
        homePage=false;
        infoPage=true;
        orderPage=false;
        searchPage=false;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantInfo.fxml"));
        Parent root=loader.load();
        RestaurantInfoController controller=loader.getController();
        controller.setRestaurantOwner(this);
        controller.setLabels();
        controller.setImageView();
        if(newOrder) {
            controller.setNewOrderLabel();
            newOrder=false;
        }
        stage.setTitle("Restaurant Info");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void showFoodSearch() throws IOException {
        homePage=false;
        infoPage=false;
        orderPage=false;
        searchPage=true;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("RestaurantFood.fxml"));
        Parent root=loader.load();
        RestaurantFoodController controller=loader.getController();
        controller.setRestaurantOwner(this);
        controller.setChoice();
        if(newOrder) {
            controller.setNewOrderLabel();
            newOrder=false;
        }
        stage.setTitle("Food Search");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addToOrderList(Food food) {
        orderList.add(0,food);
    }

    public void showOrders() throws IOException {
        homePage=false;
        infoPage=false;
        orderPage=true;
        searchPage=false;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("ShowOrders.fxml"));
        Parent root=loader.load();
        ShowOrdersController controller=loader.getController();
        controller.setRestaurantOwner(this);
        controller.setTableView(orderList);
        //controller.setChoice();
        stage.setTitle("Orders");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setHomePage(boolean b) {
        homePage=b;
    }
    public boolean getHomePage() {
        return homePage;
    }

    public void setInfoPage(boolean b) {
        infoPage=b;
    }
    public boolean getInfoPage() {
        return infoPage;
    }
    public void setSearchPage(boolean b) {
        searchPage=b;
    }
    public boolean getSearchPage() {
        return searchPage;
    }
    public void setOrderPage(boolean b) {
        orderPage=b;
    }
    public boolean getOrderPage() {
        return orderPage;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //new RestaurantOwner("127.0.0.1",16281);
        launch(args);
    }

    public void setNewOrder(boolean b) {
        newOrder=b;
    }
}
