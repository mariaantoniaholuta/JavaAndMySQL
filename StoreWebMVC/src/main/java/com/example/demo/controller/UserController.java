package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String admin() {
        return "admin";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add-user";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("admin") User user) {
        userService.saveUser(user);
        return "redirect:/admin/list";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "list-users";
    }

    @GetMapping("/reports")
    public String showReportsPage(Model model) {
        model.addAttribute("startDate", LocalDate.now().minusDays(7));
        model.addAttribute("endDate", LocalDate.now());

        return "reports";
    }

    @PostMapping("/reports")
    public String generateReports(Model model, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        List<Order> allOrders = orderService.getAllOrders();
        List<Order> filteredOrders = orderService.findOrders(allOrders, startDate, endDate);
        model.addAttribute("orders", filteredOrders);
        try {
            PrintWriter writer = new PrintWriter("reports.txt");
            for (Order order : filteredOrders) {
                writer.println("Order ID: " + order.getId());
                writer.println("Total Cost: " + order.getTotalCost());
                writer.println("Order date: " + order.getDate());
                writer.println("Products: " + orderService.getProductsList(order));
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "reports";
    }

    @PostMapping("/reports/mostOrdered")
    public String mostOrdered(Model model) {
        List<String> mostOrdered = orderService.findMostOrdered(orderService.getAllOrders());
        model.addAttribute("mostOrdered", mostOrdered);
        return "reports";
    }



}
