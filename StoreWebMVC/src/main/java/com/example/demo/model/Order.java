package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "hour")
    private LocalTime hour;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<ProductMenu> products;

    public Order() {
    }

    public Order(Integer id, String orderStatus, double totalCost, LocalDate date, LocalTime hour, List<ProductMenu> products) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalCost = totalCost;
        this.date = date;
        this.hour = hour;
        this.products = products;
    }


}
