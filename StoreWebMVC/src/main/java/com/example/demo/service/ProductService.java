package com.example.demo.service;

import com.example.demo.model.ProductMenu;
import com.example.demo.model.User;

import java.util.List;

public interface ProductService {
    public List<ProductMenu> getAllProducts();
    public ProductMenu getProductById(int id);
    public ProductMenu createProduct(ProductMenu product);
    public ProductMenu updateProduct(int id, ProductMenu updatedProduct);
    public void deleteProduct(int id);
    public List<ProductMenu> getProductsByIds(List<Integer> productIds);
    public List<ProductMenu> findAndGetProducts(List<Integer> productList, List<ProductMenu> allProducts);
}
