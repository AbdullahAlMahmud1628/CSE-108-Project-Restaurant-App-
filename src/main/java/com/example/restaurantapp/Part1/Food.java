package com.example.restaurantapp.Part1;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.io.Serializable;

public class Food implements Serializable {
    transient CheckBox select;
    private int restaurant_id;
    private String restaurant_name;
    private String category;
    private String name;
    private double price;

    public Food(int restaurant_id, String category, String name, Double price) {
        this.restaurant_id=restaurant_id;
        this.category=category;
        this.name=name;
        this.price=price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getRestaurant_id() {
        return restaurant_id;
    }
    public String getCategory() {
        return category;
    }
    public void showDetails() {
        System.out.print("Name: "+name);
        System.out.print(",  Category: "+category);
        System.out.print(",  Restaurant Id: "+restaurant_id);
        System.out.println(",  Price: "+price);
    }
    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name=restaurant_name;
    }
    public String getRestaurant_name() {
        return restaurant_name;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.select=checkBox;
    }
    public CheckBox getSelect() {
        return select;
    }
}
