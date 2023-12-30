package com.example.restaurantapp.server;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.Restaurant;
import com.example.restaurantapp.Part1.RestaurantDatabase;
import com.example.restaurantapp.customer.Customer;
import com.example.restaurantapp.util.CustomerLoginDTO;
import com.example.restaurantapp.util.RestaurantLoginDTO;
import com.example.restaurantapp.util.SocketWrapper;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private RestaurantDatabase restaurantDatabase;
    private static final String FILE_NAME_RESTAURANT="src\\main\\resources\\com\\example\\restaurantapp\\restaurant.txt";
    private static final String FILE_NAME_MENU="src\\main\\resources\\com\\example\\restaurantapp\\menu.txt";
    private static final String FILE_RESTAURANT_AUTH="src\\main\\resources\\com\\example\\restaurantapp\\restaurantAuth.txt";
    private static final String FILE_CUSTOMER_AUTH="src\\main\\resources\\com\\example\\restaurantapp\\customerAuth.txt";
    private HashMap<Integer,SocketWrapper> restaurantMap;
    private List<Pair<Integer,String>> restaurantAuth;
    private List<Pair<String,String>> customerAuth;
    public Server() throws IOException {
        this.readFiles();
        restaurantMap=new HashMap<>();
        new addThread(this,this.restaurantDatabase);
        try {
            serverSocket=new ServerSocket(33333);
            while (true) {
                Socket clientSocket=serverSocket.accept();
                SocketWrapper socketWrapper=new SocketWrapper(clientSocket);
                String s=(String) socketWrapper.read();
                if(s.equalsIgnoreCase("Customer"))
                    serveCustomer(socketWrapper);
                if(s.equalsIgnoreCase("Restaurant")) {
                    serveRestaurantOwner(socketWrapper);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void serveCustomer(SocketWrapper socketWrapper) throws IOException {
        //socketWrapper.write(restaurantDatabase);
        new ReadThreadServer(restaurantMap,socketWrapper,this);
    }
    private void serveRestaurantOwner(SocketWrapper socketWrapper) throws IOException, ClassNotFoundException {
        //socketWrapper.write(restaurantDatabase.getRestaurantById(id));
        new ReadThreadServerForRestaurant(socketWrapper,this);
    }
    private void readFiles() throws IOException {
        BufferedReader reader=new BufferedReader(new FileReader(FILE_NAME_RESTAURANT));
        BufferedReader reader2=new BufferedReader(new FileReader(FILE_NAME_MENU));
        BufferedReader reader1=new BufferedReader(new FileReader(FILE_RESTAURANT_AUTH));
        BufferedReader reader3=new BufferedReader(new FileReader(FILE_CUSTOMER_AUTH));
        restaurantDatabase=new RestaurantDatabase();
        while(true) {
            String line=reader.readLine();
            if(line==null) break;
            String [] splited=line.split(",",-1);
            List<String> categories=new ArrayList<>();
            int i=5;
            while(i<8) {
                categories.add(splited[i]);
                i++;
            }
            restaurantDatabase.addRestaurant(new Restaurant(Integer.valueOf(splited[0]),splited[1],Double.valueOf(splited[2]),splited[3],splited[4],categories));
        }
        while(true) {
            String line=reader2.readLine();
            if(line==null) break;
            String [] splited=line.split(",",-1);
            restaurantDatabase.addFood(new Food(Integer.valueOf(splited[0]),splited[1],splited[2],Double.valueOf(splited[3])));
            restaurantDatabase.addFoodToRestaurant(Integer.valueOf(splited[0]),new Food(Integer.valueOf(splited[0]),splited[1],splited[2],Double.valueOf(splited[3])));
        }
        restaurantAuth=new ArrayList<>();
        while(true) {
            String line=reader1.readLine();
            if(line==null) break;
            String [] splited=line.split(",",-1);
            restaurantAuth.add(new Pair<>(Integer.parseInt(splited[0]),splited[1]));
        }
        customerAuth=new ArrayList<>();
        while (true) {
            String line=reader3.readLine();
            if(line==null)
                break;
            String [] splited=line.split(",",-1);
            customerAuth.add(new Pair<>(splited[0],splited[1]));
        }
        reader.close();
        reader2.close();
        reader1.close();
        reader3.close();
    }

    public int isValidRestaurant(RestaurantLoginDTO restaurantLoginDTO) {
        int id=restaurantLoginDTO.getId();
        String password=restaurantLoginDTO.getPassword();
        for(var v:restaurantAuth) {
            if(v.getKey()==id && v.getValue().equals(password))
                return 1;
        }
        return -1;
    }
    public int isValidCustomer(CustomerLoginDTO customerLoginDTO) {
        String userName=customerLoginDTO.getUserName();
        String password=customerLoginDTO.getPassword();
        for(var v:customerAuth) {
            if(v.getValue().equals(password) && v.getKey().equals(userName))
                return  1;
        }
        return -1;
    }
    public RestaurantDatabase getRestaurantDatabase() {
        return restaurantDatabase;
    }

    public void addTorestaurantMap(Integer id,SocketWrapper socketWrapper) {
        restaurantMap.put(id,socketWrapper);
    }
    public static void main(String[] args) throws IOException {
        new Server();
    }
}
