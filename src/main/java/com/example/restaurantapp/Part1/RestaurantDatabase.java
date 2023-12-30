package com.example.restaurantapp.Part1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabase implements Serializable {
    private List<Restaurant> restaurantList;
    private List<Food> foodList;
    public RestaurantDatabase() {
        restaurantList=new ArrayList<>();
        foodList=new ArrayList<>();
    }
    public void addRestaurant(Restaurant r) {
        restaurantList.add(r);
    }
    public void addFood(Food f) {
        foodList.add(f);
    }
    public void showRestaurants() {
        for(Restaurant r:restaurantList) {
            r.showDetailes();
            System.out.println();
        }
    }
    public void showFoods() {
        for(Food f:foodList)
            f.showDetails();
    }

    public int getRestaurantId(String name) {
        if(name.equals(""))
            return -1;
        for(Restaurant r:restaurantList) {
            if(r.getName().equalsIgnoreCase(name))
                return r.getId();
        }
        return -1;
    }
    public Restaurant getRestaurant(int index) {
        return restaurantList.get(index);
    }
    public Food getFood(int index) {
        return foodList.get(index);
    }
    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
    public void mainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1) Search Restaurants");
        System.out.println("2) Search Food Items");
        System.out.println("3) Add Restaurant");
        System.out.println("4) Add Food Item to the Menu");
        System.out.println("5) Exit System");
    }
    public void restaurantSearchingOptions() {
        System.out.println("Restaurant Searching Options:");
        System.out.println("1) By Name");
        System.out.println("2) By Score");
        System.out.println("3) By Category");
        System.out.println("4) By Price");
        System.out.println("5) By Zip Code");
        System.out.println("6) Different Category Wise List of Restaurants");
        System.out.println("7) Back to Main Menu");
    }
    public List<Integer> searchRestaurantByName(String name) {
        List<Integer> index=new ArrayList<>();
        if(name.equals(""))
            return index;
        for(int i=0;i<restaurantList.size();i++) {
            if(restaurantList.get(i).getName().toLowerCase().contains(name.toLowerCase()))
                index.add(i);
        }
        return index;
    }
    public List<Integer> searchRestaurantByScore(double score1, double score2) {
        List<Integer> index=new ArrayList<>();
        for(int i=0;i<restaurantList.size();i++) {
            if(restaurantList.get(i).getScore()>=score1 && restaurantList.get(i).getScore()<=score2) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchRestaurantByCategory(String c) {
        List<Integer> index=new ArrayList<>();
        if(c.equals(""))
            return index;
        for(int i=0;i<restaurantList.size();i++) {
            List<String> cat=restaurantList.get(i).getCategories();
            for(String s:cat) {
                if(s.toLowerCase().contains(c.toLowerCase())) {
                    index.add(i);
                }
            }
        }
        return index;
    }
    public List<Integer> searchRestaurantByPrice(String p) {
        List<Integer> index=new ArrayList<>();
        if(p.equals(""))
            return index;
        for(int i=0;i<restaurantList.size();i++) {
            if(restaurantList.get(i).getPrice().equalsIgnoreCase(p)) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchRestaurantByZip_code(String z) {
        List<Integer> index=new ArrayList<>();
        if(z.equals(""))
            return index;
        for(int i=0;i<restaurantList.size();i++) {
            if(restaurantList.get(i).getZip_code().equalsIgnoreCase(z)) {
                index.add(i);
            }
        }
        return index;
    }
    public void categoryWiseRestaurants() {
        List<String> cat=new ArrayList<>();
        for(int i=0;i<restaurantList.size();i++) {
            for(String ss:restaurantList.get(i).getCategories()) {
                if(!cat.contains(ss))
                    cat.add(ss);
            }
        }
        for(String s:cat) {
            if(s!="") {
            List<String> name=new ArrayList<>();
            for(Restaurant r:restaurantList) {
                if(r.getCategories().contains(s)) {
                    name.add(r.getName());
                }
            }
            System.out.print(s+": ");
            if(!name.isEmpty()) {
                System.out.print(name.get(0));
                for(int i=1;i<name.size();i++) {
                    System.out.print(","+name.get(i));
                }
            }
            System.out.print("\n");}
        }
    }
    public void foodItemSearchingOptions() {
        System.out.println("Food Item Searching Options:");
        System.out.println("1) By Name");
        System.out.println("2) By Name in a Given Restaurant");
        System.out.println("3) By Category");
        System.out.println("4) By Category in a Given Restaurant");
        System.out.println("5) By Price Range");
        System.out.println("6) By Price Range in a Given Restaurant");
        System.out.println("7) Costliest Food Item(s) on the Menu in a Given Restaurant");
        System.out.println("8) List of Restaurants and Total Food Item on the Menu");
        System.out.println("9) Back to Main Menu");
    }
    public List<Integer> searchFoodByName(String name) {
        List<Integer> index=new ArrayList<>();
        if(name.equals(""))
            return index;
        for(int i=0;i<foodList.size();i++) {
            if(foodList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchFoodByNameInRestaurant(String food,String restaurant) {
        List<Integer> index=new ArrayList<>();
        if(food.equals(""))
            return index;
        Restaurant r=getRestaurant(restaurant);
        index=r.searchFoodByNameInRestaurant(food);
        return index;
    }
    public List<Integer> searchFoodByCategory(String c) {
        List<Integer> index=new ArrayList<>();
        if(c.equals(""))
            return index;
        for(int i=0;i<foodList.size();i++) {
            if(foodList.get(i).getCategory().toLowerCase().contains(c.toLowerCase())) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchFoodByCategoryInRestaurant(String category,String restaurant) {
        List<Integer> index=new ArrayList<>();
        if(category.equals(""))
            return index;
        Restaurant r=getRestaurant(restaurant);
        index=r.searchFoodByCategoryInRestaurant(category);
        return index;
    }
    public List<Integer> searchFoodByPriceRange(double range1,double range2) {
        List<Integer> index=new ArrayList<>();
        for(int i=0;i<foodList.size();i++) {
            if(foodList.get(i).getPrice()>=range1 && foodList.get(i).getPrice()<=range2) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchFoodByPriceRangeInRestaurant(double range1,double range2,String restaurant) {
        List<Integer> index=new ArrayList<>();
        int id=getRestaurantId(restaurant);
        if(id==-1) {
            return index;
        }
        index=getRestaurantById(id).searchFoodByPriceRangeInRestaurant(range1,range2);
        return index;
    }
    public List<Integer> searchCostliestInRestaurant(String restaurant) {
        List<Integer> index=new ArrayList<>();
        Restaurant r=getRestaurant(restaurant);
        index=r.searchCostliestInRestaurant();
        return index;
    }
    public void restaurantsWithTotalFood() {
        for(Restaurant r:restaurantList) {
            System.out.println(r.getName()+": "+r.getMenu().size());
        }
    }
    public boolean restaurantExists(String name) {
        if(name.equals(""))
            return false;
        for(Restaurant r:restaurantList) {
            if(r.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean restaurantExists(int id) {
        for(Restaurant r:restaurantList) {
            if(r.getId()==id) {
                return true;
            }
        }
        return false;
    }
    public Restaurant getRestaurant(String name) {
        if(name.equals(""))
            return null;
        Restaurant restaurant=null;
        for(Restaurant r: restaurantList) {
            if(r.getName().equalsIgnoreCase(name)) {
                restaurant=r;
                break;
            }
        }
        return restaurant;
    }
    public int restaurantListSize() {
        return restaurantList.size();
    }
    public int foodListSize() {
        return foodList.size();
    }
    public boolean foodExistsInTheRestaurant(String ff,String r,String c) {
        Restaurant restaurant=getRestaurant(r);
        return restaurant.foodExistsInTheRestaurant(ff,c);
    }
    public void addFoodToRestaurant(int resId,Food f) {
        getRestaurantById(resId).addFood(f);
    }
    public Restaurant getRestaurantById(int id) {
        Restaurant res=null;
        for(Restaurant r:restaurantList) {
            if(r.getId()==id) {
                res=r;
                break;
            }
        }
        return res;
    }
    public List<Food> getFoodList() {
        return foodList;
    }
    public List<Integer> searchCostliest() {
        List<Integer> index=new ArrayList<>();
        double max=-1;
        for(var v:foodList) {
            if(v.getPrice()>max)
                max=v.getPrice();
        }
        for(int i=0;i<foodList.size();i++) {
            if(foodList.get(i).getPrice()==max) {
                index.add(i);
            }
        }
        return index;
    }
}
