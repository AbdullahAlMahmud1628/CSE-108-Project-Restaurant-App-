package com.example.restaurantapp.restaurantOwner;

import com.example.restaurantapp.Part1.Restaurant;
import com.example.restaurantapp.util.OrderDTO;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.application.Platform;

import java.io.IOException;

public class ReadThreadRestaurantOwner implements Runnable{
    Thread thread;
    SocketWrapper socketWrapper;
    RestaurantOwner restaurantOwner;
    public ReadThreadRestaurantOwner(SocketWrapper socketWrapper,RestaurantOwner restaurantOwner) {
        this.socketWrapper=socketWrapper;
        this.restaurantOwner=restaurantOwner;
        thread=new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        try{
            while(true) {
                Object o=socketWrapper.read();
                if(o instanceof String) {
                    String s=(String) o;
                    if(s.equals("Invalid Login")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    restaurantOwner.showLoginFailedForRestaurant();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                        });
                    }
                }
                if(o instanceof Restaurant) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            restaurantOwner.setRestaurant((Restaurant) o);
                            try {
                                restaurantOwner.showRestaurantHome();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
                if(o instanceof OrderDTO) {
                    OrderDTO orderDTO=(OrderDTO) o;
                    for(var v:orderDTO.getFoodList()) {
                        v.showDetails();
                        restaurantOwner.addToOrderList(v);
                    }
                    restaurantOwner.setNewOrder(true);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(restaurantOwner.getHomePage()) {
                                try {
                                    restaurantOwner.showRestaurantHome();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                            else if(restaurantOwner.getOrderPage()) {
                                try {
                                    restaurantOwner.setNewOrder(false);
                                    restaurantOwner.showOrders();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                            else if(restaurantOwner.getInfoPage()) {
                                try {
                                    //restaurantOwner.setNewOrder(false);
                                    restaurantOwner.showRestaurantInfo();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                            else if(restaurantOwner.getSearchPage()) {
                                try {
                                    //restaurantOwner.setNewOrder(false);
                                    restaurantOwner.showFoodSearch();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                        }
                    });
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
