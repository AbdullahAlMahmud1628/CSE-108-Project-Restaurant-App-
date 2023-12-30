package com.example.restaurantapp.util;

import java.io.Serializable;

public class CustomerLoginDTO implements Serializable {
    private String userName;
    private String password;
    public CustomerLoginDTO(String userName,String password) {
        this.userName=userName;
        this.password=password;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public void setUserName(String userName) {
        this.userName=userName;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return userName;
    }
}
