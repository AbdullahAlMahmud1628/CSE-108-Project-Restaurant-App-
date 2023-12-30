module com.example.restaurantapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.restaurantapp to javafx.fxml;
    exports com.example.restaurantapp;
    exports com.example.restaurantapp.customer;
    opens com.example.restaurantapp.customer to javafx.fxml,javafx.graphics;
    exports com.example.restaurantapp.restaurantOwner;
    opens com.example.restaurantapp.restaurantOwner to javafx.graphics,javafx.fxml;
    opens com.example.restaurantapp.Part1 to javafx.base;
}