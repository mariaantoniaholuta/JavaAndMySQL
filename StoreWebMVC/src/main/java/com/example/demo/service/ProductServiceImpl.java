package com.example.demo.service;

import com.example.demo.model.ProductMenu;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductMenu> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductMenu getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ProductMenu createProduct(ProductMenu product) {
        return productRepository.save(product);
    }

    @Override
    public ProductMenu updateProduct(int id, ProductMenu updatedProduct) {
        ProductMenu existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(int id) {
        ProductMenu product = getProductById(id);
        productRepository.delete(product);
    }
    @Override
    public List<ProductMenu> getProductsByIds(List<Integer> productIds) {
        return productRepository.findAllById(productIds);
    }
    @Override
    public List<ProductMenu> findAndGetProducts(List<Integer> productList, List<ProductMenu> allProducts){
        List<ProductMenu> productsForOrder = new ArrayList<>();
        Integer newQuantity = 0;
        for(Integer i: productList){
            for(ProductMenu p: allProducts ){
                if(p.getId() == i && p.getQuantity() != 0){
                    productsForOrder.add(p);
                    newQuantity = p.getQuantity()-1;
                    p.setQuantity(newQuantity);
                }
            }
        }
        return productsForOrder;
    }

}
