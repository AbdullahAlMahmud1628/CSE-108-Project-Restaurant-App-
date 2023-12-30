package com.example.restaurantapp.util;

import com.example.restaurantapp.Part1.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private String restaurant;
    private List<Food> foodList=new ArrayList<>();
    private int restaurantId;
    public void setRestaurant(String restaurant) {
        this.restaurant=restaurant;
    }
    public void setFoodList(List<Food> food) {
        this.foodList=food;
    }
    public String getRestaurant() {
        return restaurant;
    }
    public List<Food> getFoodList() {
        return foodList;
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId=restaurantId;
    }
    public int getRestaurantId() {
        return restaurantId;
    }
}
