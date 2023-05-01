package com.example.demo.controller;

import com.example.demo.model.ProductMenu;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        ProductMenu product = new ProductMenu();
        model.addAttribute("product", product);
        return "add-product";
    }

    @PostMapping("/add")
    public String createProduct(@ModelAttribute("product") ProductMenu product) {
        productService.createProduct(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable("id") int id, Model model) {
        ProductMenu product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") ProductMenu updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        List<ProductMenu> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "list-products";
    }
}
