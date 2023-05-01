package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.ProductMenu;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(int id);
    void updateOrderStatus(int id, String status);
    void createOrder(Order order);
    double calculateTotalCost(List<ProductMenu> products);
     List<Order> findOrders(List<Order> orders, String begin, String end);
     List<String> findMostOrdered(List<Order> orders);
     String getProductsList(Order orders);
}
