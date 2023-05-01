package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.ProductMenu;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    private ProductService productService;


    @GetMapping("/create")
    public String showAddOrderForm(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        model.addAttribute("products", productService.getAllProducts());
        return "add-order";
    }
    @PostMapping("/add")
    public String createOrder(@ModelAttribute("order") Order order, @RequestParam("products") String products) {
        // Convert comma-separated string of product IDs to list of integers
        List<Integer> productList = Arrays.stream(products.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Set list of product IDs on order and check if product exists
        List<ProductMenu> allProducts = productService.getAllProducts();
        List<ProductMenu> productsForOrder = productService.findAndGetProducts(productList, allProducts);

        order.setProducts(productsForOrder);
        order.setTotalCost(orderService.calculateTotalCost(productsForOrder));
        // Save order to database
        if(!productsForOrder.isEmpty()){
            System.out.println(order.getProducts());
            orderService.createOrder(order);
        }
        //System.out.println(orderService.getAllOrders().toString());
        // Redirect to orders page
        return "redirect:/employee/orders/list";
    }


    /*
    @PostMapping("/add")
    public String createOrder(@ModelAttribute("order") @Validated Order order, BindingResult result,
                              @RequestParam("productIds") List<Integer> productIds) {
        if (result.hasErrors()) {
            System.out.println("err");
            System.out.println("Validation errors: " + result.getAllErrors());
            return "add-order";
        }

        List<ProductMenu> products = productService.getProductsByIds(productIds);
        order.setProducts(products);
        System.out.println(products.toString());
        order.setDate(LocalDate.now());
        order.setHour(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        order.setOrderStatus("New");
        orderService.createOrder(order);

        return "redirect:/employee/orders/list";
    }

    @PostMapping("/add")
    public String createOrder(@ModelAttribute("order") Order order) {

        orderService.createOrder(order);

        return "redirect:/employee/orders/list";
    }
     */
    @GetMapping("/update/{id}")
    public String showUpdateOrderForm(@PathVariable("id") int id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "update-order";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") int id, @ModelAttribute("order") Order order) {
        orderService.updateOrderStatus(id,order.getOrderStatus());
        return "redirect:/employee/orders/list";
    }
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Order> orders = orderService.getAllOrders();
        //List<ProductMenu> products = productService.getAllProducts();
        model.addAttribute("orders", orders);
       // model.addAttribute("products", productService.getAllProducts());
        return "list-orders";
    }
}
