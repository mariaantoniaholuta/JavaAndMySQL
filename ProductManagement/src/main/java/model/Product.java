package model;

import bussinesslayer.MenuItem;

public class Product extends MenuItem {

    private String title;
    private String rating;
    private String calories;
    private String protein;
    private String fat;
    private String sodium;
    private String price;

    public Product(String title, String rating, String calories, String protein, String fat, String sodium, String price) {

        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void printInfo() {
        System.out.println("title: " + title);
        System.out.println("rating: " + rating);
        System.out.println("calories: " + calories);
        System.out.println("protein: " + protein);
        System.out.println("fat: " + fat);
        System.out.println("sodium: " + sodium);
        System.out.println("price: " + price);
    }

    public float computePrice() {
        return Float.parseFloat(price);
    }

}
