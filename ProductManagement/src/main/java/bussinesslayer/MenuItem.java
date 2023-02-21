package bussinesslayer;

import model.CompositeProduct;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    private List<CompositeProduct> compositeProductList = new ArrayList<>();

    public MenuItem(List<CompositeProduct> compositeProductList) {
        this.compositeProductList = compositeProductList;
    }

    public MenuItem() {

    }

    public List<CompositeProduct> getCompositeProductList() {
        return compositeProductList;
    }

    public void setCompositeProductList(List<CompositeProduct> compositeProductList) {
        this.compositeProductList = compositeProductList;
    }

    public float computePrice() {
        float aux = 0.0F;

        for(CompositeProduct c: compositeProductList) {
            aux += c.getPrice();
        }
        return aux;
    }
}
