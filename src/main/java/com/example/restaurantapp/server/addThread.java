package com.example.restaurantapp.server;

import com.example.restaurantapp.Part1.Food;
import com.example.restaurantapp.Part1.Restaurant;
import com.example.restaurantapp.Part1.RestaurantDatabase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class addThread implements Runnable {
    private Thread thread;
    private Server server;
    private RestaurantDatabase restaurantDatabase;

    public addThread(Server server,RestaurantDatabase restaurantDatabase) {
        this.server = server;
        this.restaurantDatabase=restaurantDatabase;
        this.thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        System.out.println("Hello Admin! \nEnter password");
        Scanner scanner = new Scanner(System.in);
        //scanner.nextLine();
        String pass = scanner.nextLine();
        while (!pass.equals("abdullah")) {
            System.out.println("Enter correct password");
            pass=scanner.nextLine();
        }
        System.out.println("Welcome");
        while (true) {
            System.out.println("1.Add food to a restaurant");
            System.out.println("2.Add restaurant");
            System.out.println("Enter option");
            int option = -1;
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (option == 1) {
                System.out.println("Enter restaurant name in which you want to add food item: ");
                scanner.nextLine();
                String restaurantName = scanner.nextLine();
                if (!server.getRestaurantDatabase().restaurantExists(restaurantName.trim())) {
                    System.out.println("No such restaurant with this name");
                } else {
                    System.out.println("Enter food item name: ");
                    String name = scanner.nextLine();
                    while (name.trim().equals("")) {
                        System.out.println("Please enter valid name : ");
                        name = scanner.nextLine();
                    }
                    System.out.println("Enter food category: ");
                    String category = scanner.nextLine();
                    while (category.trim().equals("")) {
                        System.out.println("Please enter valid category : ");
                        category = scanner.nextLine();
                    }
                    while (server.getRestaurantDatabase().foodExistsInTheRestaurant(name.trim(), restaurantName.trim(), category.trim())) {
                        System.out.println("Food exists with this name and category in the restaurant. Please enter name again: ");
                        name = scanner.nextLine();
                        System.out.println("Please enter category again : ");
                        category = scanner.nextLine();
                    }
                    System.out.println("Enter price: ");
                    double price = scanner.nextDouble();
                    Restaurant r = server.getRestaurantDatabase().getRestaurant(restaurantName.trim());
                    restaurantDatabase.addFood(new Food(r.getId(), category.trim(), name.trim(), price));
                    restaurantDatabase.addFoodToRestaurant(r.getId(), new Food(r.getId(), category.trim(), name.trim(), price));
                    System.out.println("Food item added successfully!");
                    BufferedWriter bufferedWriter = null;
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter("menu.txt"));
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    List<Food> foodList = restaurantDatabase.getFoodList();
                    for (int i = 0; i < foodList.size(); i++) {
                        Food f = foodList.get(i);
                        try {
                            bufferedWriter.write(f.getRestaurant_id() + "," + f.getCategory() + "," + f.getName() + "," + f.getPrice());
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                        try {
                            bufferedWriter.write(System.lineSeparator());
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            } else if (option == 2) {
                System.out.println("Enter restaurant name: ");
                scanner.nextLine();
                String name=scanner.nextLine();
                while(name.trim().equals("")) {
                    System.out.println("Please enter valid name: ");
                    name=scanner.nextLine();
                }
                while(server.getRestaurantDatabase().restaurantExists(name.trim())) {
                    System.out.println("Restaurant already exists with the name. Please enter another name: ");
                    name=scanner.nextLine();
                }
                System.out.println("Enter restaurant id: ");
                int id=scanner.nextInt();
                while(server.getRestaurantDatabase().restaurantExists(id)) {
                    System.out.println("Restaurant already exists with the id. Please enter another id: ");
                    id=scanner.nextInt();
                }
                System.out.println("Enter restaurant price ( $ , $$ or $$$ ): ");
                scanner.nextLine();
                String price=scanner.nextLine();
                while(!price.trim().equals("$") && !price.trim().equals("$$") && !price.trim().equals("$$$")) {
                    System.out.println("Invalid price. Please enter valid ( $ ,$ $ or $$$ ) price: ");
                    price=scanner.nextLine();
                }
                System.out.println("Enter restaurant score: ");
                double score=scanner.nextDouble();
                System.out.println("Enter restaurant zip code: ");
                scanner.nextLine();
                String zip_code=scanner.nextLine();
                List<String> categories=new ArrayList<>();
                System.out.println("Enter a category: (You can add up to 3 categories)");
                String category1=scanner.nextLine();
                while(category1.trim().equals("")) {
                    System.out.println("Please enter valid category: ");
                    category1=scanner.nextLine();
                }
                categories.add(category1.trim());
                System.out.println("Enter another category?(Optional)");
                System.out.println("1.Yes");
                System.out.println("2.No");
                option=scanner.nextInt();
                if(option==1) {
                    System.out.println("Enter category: ");
                    scanner.nextLine();
                    String category2=scanner.nextLine();
                    categories.add(category2.trim());
                    System.out.println("Enter another category?(Optional)");
                    System.out.println("1.Yes");
                    System.out.println("2.No");
                    option=scanner.nextInt();
                    if(option==1) {
                        System.out.println("Enter category: ");
                        scanner.nextLine();
                        String category3=scanner.nextLine();
                        categories.add(category3.trim());
                    }
                    else {
                        categories.add("");
                    }
                }
                else {
                    categories.add("");
                    categories.add("");
                }
                restaurantDatabase.addRestaurant(new Restaurant(id,name.trim(),score,price.trim(),zip_code.trim(),categories));
                System.out.println("Restaurant added successfully!");
                BufferedWriter bufferedWriter=null;
                try {
                    bufferedWriter=new BufferedWriter(new FileWriter("restaurant.txt"));
                } catch (IOException e) {
                    System.out.println(e);
                }
                List<Restaurant> restaurantList=restaurantDatabase.getRestaurantList();
                for(int i=0;i<restaurantList.size();i++) {
                    Restaurant r=restaurantList.get(i);
                    List<String> c=r.getCategories();
                    try {
                        bufferedWriter.write(r.getId()+","+r.getName()+","+r.getScore()+","+r.getPrice()+","+r.getZip_code()+","+c.get(0)+","+c.get(1)+","+c.get(2));
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    try {
                        bufferedWriter.write(System.lineSeparator());
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            else {
                System.out.println("Invalid option");
            }
        }
    }
}
