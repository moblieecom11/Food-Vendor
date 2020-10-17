package com.teamx.zapmeal;

import android.graphics.Bitmap;

public class ModelClass {
    private String price;
    private String rating;
    private String name;
    private int image;
    private String quantity;
    private String location;
    private String restaurantLocation;
    private String restaurantName;
    private String resturantPhoneNumber;
    private Boolean orderStatus;

    public String getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(String userChoice) {
        this.userChoice = userChoice;
    }

    private String userChoice;

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getResturantPhoneNumber() {
        return resturantPhoneNumber;
    }

    public void setResturantPhoneNumber(String resturantPhoneNumber) {
        this.resturantPhoneNumber = resturantPhoneNumber;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    private Boolean favourite;

    public ModelClass(String price, String rating, String name, int image) {
        this.price = price;
        this.rating = rating;
        this.name = name;
        this.image = image;
    }

    public ModelClass() {
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public ModelClass(int image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ModelClass(int image, String name, String price) {
        this.price = price;
        this.name = name;
        this.image = image;
    }
}
