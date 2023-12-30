package com.example.restaurantapp.util;

import java.io.Serializable;

public class RestaurantLoginDTO implements Serializable {
    private int id;
    private String password;
    public RestaurantLoginDTO() {

    }

    public void setId(int id) {
        this.id=id;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
}
