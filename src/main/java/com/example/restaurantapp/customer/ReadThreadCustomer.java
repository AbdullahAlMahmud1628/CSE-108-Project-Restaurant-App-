package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.RestaurantDatabase;
import com.example.restaurantapp.util.CustomerLoginDTO;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.application.Platform;

import java.io.IOException;
import java.security.spec.ECField;

public class ReadThreadCustomer implements Runnable{
    private Thread thread;
    private Customer customer;
    private SocketWrapper socketWrapper;
    public ReadThreadCustomer(SocketWrapper socketWrapper,Customer customer) {
        this.socketWrapper=socketWrapper;
        this.customer=customer;
        this.thread=new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                Object o=socketWrapper.read();
                if(o instanceof RestaurantDatabase) {
                    RestaurantDatabase restaurantDatabase=(RestaurantDatabase) o;
                    customer.setRestaurantDatabase(restaurantDatabase);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                customer.showCustomerHome();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    });
                }
                if(o instanceof CustomerLoginDTO) {
                    CustomerLoginDTO customerLoginDTO=(CustomerLoginDTO) o;
                    customer.setCustomerName(customerLoginDTO.getUserName());
                }
                if(o instanceof String) {
                    String s=(String) o;
                    if(s.equalsIgnoreCase("Invalid login")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    customer.showLoginFailed();
                                } catch (IOException e) {
                                    System.out.println(e);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);;
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}
