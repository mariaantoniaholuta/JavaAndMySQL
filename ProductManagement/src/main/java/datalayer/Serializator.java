package datalayer;

import bussinesslayer.Order;
import model.CompositeProduct;
import model.Product;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serializator {

    static final String filePath = "user.txt";

    public static void serialize(User user) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static User deserialize() {
        User savedUser = null;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            savedUser = (User) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }

        return savedUser;
    }

    public static List<User> readForUsers() throws IOException {
        String file = "users.txt";

        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {

                List<String> data = Arrays.asList(line.split(" "));
               // System.out.println(data.get(1));
                User newUser = new User(data.get(0), data.get(1), data.get(2));
                users.add(newUser);
            }
        }

        return users;
    }
    public static List<Product> readForProducts() throws IOException {
        String file = "products.csv";
        List<Product> products = new ArrayList<>();
        boolean check = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                check = false;
                List<String> data = Arrays.asList(line.split(","));

                Product newProduct = new Product(data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),data.get(5),data.get(6));
                for(Product p: products) {
                    if(p.getTitle().equals(newProduct.getTitle())) {
                        check = true;
                    }
                }
                if(!check) {
                    //newProduct.printInfo();
                    products.add(newProduct);
                }
            }
        }

        return products;
    }

    public static List<CompositeProduct> readForCompositeProducts() throws IOException {
        String file = "menu.txt";
        List<CompositeProduct> compositeProducts = new ArrayList<>();
        boolean check = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                check = false;
                List<String> data = Arrays.asList(line.split(","));
               // System.out.println(data);

                CompositeProduct newCompositeProduct = new CompositeProduct(data.get(0),data.get(1),Float.parseFloat(data.get(2)),Integer.parseInt(data.get(3)));
                for(CompositeProduct  c: compositeProducts ) {
                    if(c.getName().equals(newCompositeProduct.getName())) {
                        check = true;
                    }
                }
                if(!check) {
                    //newProduct.printInfo();
                    compositeProducts.add(newCompositeProduct );
                }
            }
        }

        return compositeProducts;
    }
    public static List<Order> readForOrders() throws IOException {
        String file = "orders.txt";
        List<Order> orders = new ArrayList<>();
        boolean check = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                check = false;
                List<String> data = Arrays.asList(line.split(","));
                //System.out.println(data);

                Order newOrder = new Order();
                newOrder.setOrderID(Integer.parseInt(data.get(0)));
                newOrder.setClientID(Integer.parseInt(data.get(1)));
                newOrder.setDateOrder(data.get(2));
                newOrder.setProducts(data.get(4));
                newOrder.setPrice(data.get(3));
                orders.add(newOrder);
            }
        }

        return orders;
    }

    public static String readForOrdersComposite() throws IOException {
        String file = "orders.txt";
        String result = "";
        List<Order> orders = new ArrayList<>();
        boolean check = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                check = false;
                List<String> data = Arrays.asList(line.split(","));
                //System.out.println(data);

                Order newOrder = new Order();
                newOrder.setOrderID(Integer.parseInt(data.get(0)));
                newOrder.setClientID(Integer.parseInt(data.get(1)));
                newOrder.setDateOrder(data.get(2));
                newOrder.setProducts(data.get(4));
                orders.add(newOrder);
            }
        }

        return result;
    }

}
