package com.example.restaurantapp.customer;

import com.example.restaurantapp.Part1.RestaurantDatabase;
import com.example.restaurantapp.util.OrderDTO;
import com.example.restaurantapp.util.SocketWrapper;

import java.util.List;
import java.util.Scanner;

public class WriteThreadCustomer implements Runnable{
    Thread thread;
    SocketWrapper socketWrapper;
    RestaurantDatabase restaurantDatabase;
    public WriteThreadCustomer(SocketWrapper socketWrapper,RestaurantDatabase restaurantDatabase) {
        this.socketWrapper=socketWrapper;
        this.restaurantDatabase=restaurantDatabase;
        this.thread=new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        Scanner scanner=new Scanner(System.in);
        try {
            System.out.println("1.Search\n2.Order");
            int option=scanner.nextInt();
            while(true) {
                if(option==1) {
                    System.out.println("Enter restaurant name: ");
                    scanner.nextLine();
                    String name=scanner.nextLine();
                    List<Integer> index=restaurantDatabase.searchRestaurantByName(name);
                    for(var v:index) {
                        restaurantDatabase.getRestaurant(v).showDetailes();
                    }
                }
                if(option==2) {
                    System.out.println("Enter restaurant name: ");
                    scanner.nextLine();
                    String restaurant=scanner.nextLine();
                    System.out.println("Enter food name: ");
                    String food=scanner.nextLine();
                    OrderDTO order=new OrderDTO();
                    order.setRestaurant(restaurant);
                    //order.setFoodList(food);
                    order.setRestaurantId(restaurantDatabase.getRestaurantId(restaurant));
                    try {
                        socketWrapper.write(order);
                        System.out.println("Order has been placed successfully");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                System.out.println("1.Search\n2.Order");
                option=scanner.nextInt();
            }
        } catch(Exception e) {
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
