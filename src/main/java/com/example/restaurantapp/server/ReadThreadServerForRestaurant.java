package com.example.restaurantapp.server;

import com.example.restaurantapp.restaurantOwner.RestaurantOwner;
import com.example.restaurantapp.util.RestaurantLoginDTO;
import com.example.restaurantapp.util.SocketWrapper;

import java.security.spec.ECField;

public class ReadThreadServerForRestaurant implements Runnable {
    private Thread thread;
    private SocketWrapper socketWrapper;
    private Server server;
    private RestaurantOwner restaurantOwner;

    public ReadThreadServerForRestaurant(SocketWrapper socketWrapper,Server server) {
        this.socketWrapper=socketWrapper;
        this.server=server;
        this.thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while(true){
                Object o=socketWrapper.read();
                if(o instanceof RestaurantLoginDTO) {
                    RestaurantLoginDTO restaurantLoginDTO=(RestaurantLoginDTO) o;
                    int isValid=server.isValidRestaurant(restaurantLoginDTO);
                    if(isValid==1) {
                        socketWrapper.write(server.getRestaurantDatabase().getRestaurantById(restaurantLoginDTO.getId()));
                        server.addTorestaurantMap((Integer) restaurantLoginDTO.getId(),socketWrapper);
                    }
                    else {
                        socketWrapper.write("Invalid Login");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}
