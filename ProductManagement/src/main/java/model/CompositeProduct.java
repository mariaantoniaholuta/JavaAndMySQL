package model;

import bussinesslayer.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {

    private String name;
    private float price;
    private int cantity;
    private String ingredients;

    public CompositeProduct(String name, float price, int cantity, List<Product> productList) {

        this.name = name;
        this.price = price;
        this.cantity = cantity;
        this.productList = productList;
    }

    public CompositeProduct(String name, String ingredients, float price, int cantity) {
        this.name = name;
        this.price = price;
        this.cantity = cantity;
        this.ingredients = ingredients;
    }

    private List<Product> productList = new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCantity() {
        return cantity;
    }

    public void setCantity(int cantity) {
        this.cantity = cantity;
    }

    public String getIngredients() {

        return ingredients;
    }

    public void setIngredients1() {

        String str = "";
        for(Product p: productList) {
            str += p.getTitle() + ";";
        }
        //System.out.println(str);
        this.ingredients = str;
    }

    public void setIngredients2(String ingredients) {
        this.ingredients = ingredients;
    }

    public float computePrice() {
        float price = 0.0F;
        for(Product p: productList)
        {
            price += Float.parseFloat(p.getPrice());
        }
        return price;
    }
}
