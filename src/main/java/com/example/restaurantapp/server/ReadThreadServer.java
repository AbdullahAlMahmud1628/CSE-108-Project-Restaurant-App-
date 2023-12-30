package com.example.restaurantapp.server;

import com.example.restaurantapp.util.CustomerLoginDTO;
import com.example.restaurantapp.util.OrderDTO;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.application.Platform;

import java.io.IOException;
import java.util.HashMap;

public class ReadThreadServer implements Runnable{
    private Server server;
    private Thread thread;
    private HashMap<Integer, SocketWrapper> restaurantMap;
    private SocketWrapper socketWrapper;
    public ReadThreadServer(HashMap<Integer,SocketWrapper> restaurantMap,SocketWrapper socketWrapper,Server server) {
        this.restaurantMap=restaurantMap;
        this.server=server;
        this.socketWrapper=socketWrapper;
        this.thread=new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        try {
            while(true) {
                Object o=socketWrapper.read();
                if(o instanceof OrderDTO) {
                    OrderDTO order=(OrderDTO) o;
                    SocketWrapper socketWrapper1=restaurantMap.get(order.getRestaurantId());
                    socketWrapper1.write(order);
                }
                if(o instanceof CustomerLoginDTO) {
                    CustomerLoginDTO customerLoginDTO=(CustomerLoginDTO) o;
                    int isValidCustomer=server.isValidCustomer(customerLoginDTO);
                    if(isValidCustomer==1) {
                        socketWrapper.write(server.getRestaurantDatabase());
                        socketWrapper.write(customerLoginDTO);
                    }
                    else {
                        socketWrapper.write("Invalid login");
                    }
                }
            }
        } catch (Exception e) {
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
