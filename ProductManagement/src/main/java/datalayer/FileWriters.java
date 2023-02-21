package datalayer;

import bussinesslayer.MenuItem;
import bussinesslayer.Order;
import model.CompositeProduct;
import model.Product;
import model.User;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWriters {

    public static void writeNewUser(User user) {
        try {
            Files.write(Paths.get("users.txt"),(user.getUsername() + " " + user.getPassword() + " " + user.getType() + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("Successfully added client");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in file");
            e.printStackTrace();
        }
    }

    public static void writeNewProduct(Product product) {
        try {
            Files.write(Paths.get("products.csv"),(product.getTitle() + " " + "," + product.getRating() + "," + product.getCalories()  + "," + product.getProtein() +
                    "," + product.getFat() + "," + product.getSodium() + "," + product.getPrice() + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("Successfully added product");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in file products.csv");
            e.printStackTrace();
        }
    }

    public static void deleteProduct(Product product, List<Product> products) throws IOException {
        String file = "products.csv";
        String str = "";

        File f1 = new File("products.csv");
        if(!f1.exists()) {
            f1.createNewFile();
        }

        for(Product p : products) {
            if(!p.getTitle().equals(product.getTitle())) {
              //  Files.write(Paths.get("products1.csv"),(p.getTitle() + " " + "," + p.getRating() + "," + p.getCalories()  + "," + p.getProtein() +
               //         "," + p.getFat() + "," + p.getSodium() + "," + p.getPrice() + "\n").getBytes(), StandardOpenOption.APPEND);
                str += p.getTitle() + " " + "," + p.getRating() + "," + p.getCalories()  + "," + p.getProtein() +
                        "," + p.getFat() + "," + p.getSodium() + "," + p.getPrice() + "\n";

            }
        }
        FileWriter fileWritter = new FileWriter(f1.getName(),false);
        BufferedWriter writer = new BufferedWriter(fileWritter);
        writer.write(str);

        writer.close();

    }

    public static void updateProduct(Product product, List<Product> products, JTable table) throws IOException {
        String str = "";

        File f1 = new File("products.csv");
        if(!f1.exists()) {
            f1.createNewFile();
        }

        for(Product p : products) {
            if(!p.getTitle().equals(product.getTitle())) {
                //  Files.write(Paths.get("products1.csv"),(p.getTitle() + " " + "," + p.getRating() + "," + p.getCalories()  + "," + p.getProtein() +
                //         "," + p.getFat() + "," + p.getSodium() + "," + p.getPrice() + "\n").getBytes(), StandardOpenOption.APPEND);
                str += p.getTitle() + " " + "," + p.getRating() + "," + p.getCalories()  + "," + p.getProtein() +
                        "," + p.getFat() + "," + p.getSodium() + "," + p.getPrice() + "\n";

            }
            else {
                str += table.getModel().getValueAt(table.getSelectedRow(), 0) + " " + "," + table.getModel().getValueAt(table.getSelectedRow(), 1)+ "," +
                        table.getModel().getValueAt(table.getSelectedRow(), 2)  + "," +  table.getModel().getValueAt(table.getSelectedRow(), 3) +
                        "," +  table.getModel().getValueAt(table.getSelectedRow(), 4) + "," +  table.getModel().getValueAt(table.getSelectedRow(), 5) + "," +  table.getModel().getValueAt(table.getSelectedRow(), 6) + "\n";
            }
        }
        FileWriter fileWritter = new FileWriter(f1.getName(),false);
        BufferedWriter writer = new BufferedWriter(fileWritter);
        writer.write(str);

        writer.close();
    }

    public static void writeNewComposedProduct(CompositeProduct c) {
        try {
            Files.write(Paths.get("menu.txt"),(c.getName() + "," + c.getIngredients() + "," + c.getPrice() + "," + c.getCantity()  + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("Successfully added to the menu");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in menu file");
            e.printStackTrace();
        }
    }

    public static void writeNewOrder(Order o, MenuItem m) {
        List<CompositeProduct> list = new ArrayList<>();
        String str="";
        String str1="";
        String str2="";
        list = m.getCompositeProductList();
        for(CompositeProduct c: list) {
            str += c.getIngredients();
            str1 += c.getName()+"-";
            str2 += c.getName() + "......" + c.getPrice() + "\n";
        }
        try {
            Files.write(Paths.get("file.txt"),(o.getOrderID() + "," + o.getClientID() + "," + o.getDateOrder() + "," + m.computePrice() + "," + str1 + "," + str + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("Successfully added to the orders");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in orders file");
            e.printStackTrace();
        }
        try {
            Files.write(Paths.get("orders.txt"),(o.getOrderID() + "," + o.getClientID() + "," + o.getDateOrder() + "," + m.computePrice() + "," + str1 + "," + str + "\n").getBytes(), StandardOpenOption.APPEND);
            System.out.println("Successfully added to the orders");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in orders file");
            e.printStackTrace();
        }
        try {
            Files.write(Paths.get("bill.txt"),("Products: \n" + str2 + "\n" + "Total Price: ......" + m.computePrice() + "\n").getBytes(), StandardOpenOption.CREATE);
            System.out.println("Successfully added to the orders");
        } catch (IOException e) {
            System.out.println("An error occurred while writing in orders file");
            e.printStackTrace();
        }
    }

    public static void writeOrdersFoundFromDate(List<Order> orders) throws IOException {
        String str = "";
        for(Order o : orders) {
            str += "ID: " + o.getOrderID() + " " + o.getDateOrder()+"\n";
        }
        Files.write(Paths.get("ordersFound.txt"),("Orders Found: \n" + str + "\n").getBytes(), StandardOpenOption.CREATE);
        System.out.println("Orders Found: \n" + str + "\n");
    }
}
