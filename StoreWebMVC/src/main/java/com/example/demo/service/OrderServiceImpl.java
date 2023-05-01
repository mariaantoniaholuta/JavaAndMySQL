package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.ProductMenu;
import com.example.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateOrderStatus(int id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(status);
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public void createOrder(Order order) {
        order.setDate(LocalDate.now());
        order.setHour(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        order.setOrderStatus("New");
        orderRepository.save(order);
    }

    @Override
    public double calculateTotalCost(List<ProductMenu> products) {
        double totalCost = 0;
        for (ProductMenu product : products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }
    @Override
    public List<Order> findOrders(List<Order> orders, String begin, String end) {
        List<Order> ordersFound = new ArrayList<>();

        LocalDate dateStart = LocalDate.parse(begin);
        LocalDate dateEnd = LocalDate.parse(end);

        for(Order o: orders){
            if(o.getDate().isAfter(dateStart) && o.getDate().isBefore(dateEnd)) {
                ordersFound.add(o);
            }
        }

        return ordersFound;
    }

    @Override
    public List<String> findMostOrdered(List<Order> orders) {
        Map<ProductMenu, Integer> productCounts = new HashMap<>();

        for (Order order : orders) {
            for (ProductMenu product : order.getProducts()) {
                int count = productCounts.getOrDefault(product, 0);
                productCounts.put(product, count + 1);
            }
        }

        List<Map.Entry<ProductMenu, Integer>> sortedEntries = new ArrayList<>(productCounts.entrySet());
        sortedEntries.sort(Map.Entry.<ProductMenu, Integer>comparingByValue().reversed());

        List<String> mostOrdered = new ArrayList<>();
        int limit = Math.min(3, sortedEntries.size());
        for (int i = 0; i < limit; i++) {
            ProductMenu product = sortedEntries.get(i).getKey();
            mostOrdered.add(product.getName());
        }

        return mostOrdered;

    }

    @Override
    public String getProductsList(Order order){
        String productsString = "";
        List<ProductMenu> productMenus = order.getProducts();
        for(ProductMenu p: productMenus){
            productsString += p.getName();
            productsString += " ";
        }
        return productsString;
    }

}
