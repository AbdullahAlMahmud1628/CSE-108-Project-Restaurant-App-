package com.example.restaurantapp.Part1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String price;
    private double score;
    private String zip_code;
    private List<String> categories=new ArrayList<>();
    private List<Food> menu=new ArrayList<>();

    public Restaurant() {

    }

    public Restaurant(int id, String name, Double score, String price , String zip_code, List<String> categories) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.score=score;
        this.zip_code=zip_code;
        this.categories=categories;
    }

    public void showDetailes() {
        System.out.print("Name: "+name+",  Restaurant Id: "+id+",  Price category: "+price+",  Score: "+score+",  Zip Code: "+zip_code+",  Categories: ");
        if(categories.size()==1)
            System.out.print(categories.get(0));
        else if(categories.size()==2)
            System.out.print(categories.get(0)+","+categories.get(1));
        else if(categories.size()==3)
            System.out.print(categories.get(0)+","+categories.get(1)+","+categories.get(2));
        System.out.print("\n");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public double getScore() {
        return score;
    }
    public String getZip_code() {
        return zip_code;
    }
    public List<String> getCategories() {
        return categories;
    }
    public int menuSize() {
        return menu.size();
    }
    public List<Food> getMenu() {
        return menu;
    }
    public void addFood(Food f) {
        menu.add(f);
    }
    public List<Integer> searchFoodByNameInRestaurant(String food) {
        List<Integer> index=new ArrayList<>();
        for(int i=0;i<menuSize();i++) {
            if(menu.get(i).getName().toLowerCase().contains(food.toLowerCase()))
                index.add(i);
        }
        return index;
    }

    public List<Integer> searchFoodByCategoryInRestaurant(String category) {
        List<Integer> index=new ArrayList<>();
        for(int i=0;i<menuSize();i++) {
            if(menu.get(i).getCategory().toLowerCase().contains(category.toLowerCase()))
                index.add(i);
        }
        return index;
    }
    public List<Integer> searchFoodByPriceRangeInRestaurant(double range1,double range2) {
        List<Integer> index=new ArrayList<>();
        for(int i=0;i<menuSize();i++) {
            if(menu.get(i).getPrice()>=range1 && menu.get(i).getPrice()<=range2) {
                index.add(i);
            }
        }
        return index;
    }
    public List<Integer> searchCostliestInRestaurant() {
        List<Integer> index=new ArrayList<>();
        double maxPrice=-1;
        for(Food f:menu) {
            if(f.getPrice()>maxPrice) {
                maxPrice=f.getPrice();
            }
        }
        for(int i=0;i<menuSize();i++) {
            if(menu.get(i).getPrice()==maxPrice) {
                index.add(i);
            }
        }
        return index;
    }
    public boolean foodExistsInTheRestaurant(String ff,String c) {
        for(Food f:menu) {
            if(f.getName().equalsIgnoreCase(ff) && f.getCategory().equalsIgnoreCase(c)) {
                    return true;
            }
        }
        return false;
    }
}