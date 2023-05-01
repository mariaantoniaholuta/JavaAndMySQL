package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.ProductMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

}
