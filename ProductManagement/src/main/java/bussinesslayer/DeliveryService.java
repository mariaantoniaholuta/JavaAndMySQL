package bussinesslayer;

import model.CompositeProduct;
import model.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    public HashMap<Order, MenuItem> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<Order, MenuItem> orders) {
        this.orders = orders;
    }

    public HashMap<Order, MenuItem> orders = new HashMap<>();

    public DeliveryService(HashMap<Order, MenuItem> orders) {
        this.orders = orders;
    }

    public static void addRowsFromDataComposed(Object[] rows, CompositeProduct c) {
        rows[0] = c.getName();
        rows[1] = c.getIngredients();
        rows[2] = c.getCantity();
        rows[3] = c.getPrice();
    }

    public static void addRowsFromData(Object[] rows, Product p) {
        rows[0] = p.getTitle();
        rows[1] = p.getRating();
        rows[2] = p.getCalories();
        rows[3] = p.getProtein();
        rows[4] = p.getFat();
        rows[5] = p.getSodium();
        rows[6] = p.getPrice();
    }

    public static void addRowsFromDataOrders(Object[] rows, Order o) {
        rows[0] = o.getOrderID();
        rows[1] = o.getClientID();
        rows[2] = o.getProducts();
        rows[3] = o.getDateOrder();
    }

    public static List<Product> findProducts(List<Product> products, String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        List<Product> productsFound = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();

        products.forEach((p) -> { if(p.getTitle().contains(title) || p.getFat().contains(fat) ||
                p.getSodium().contains(sodium) || p.getPrice().contains(price) ||
                p.getProtein().contains( protein)|| p.getCalories().contains(calories) || p.getRating().contains(rating)) {
            count.getAndIncrement();
            productsFound.add(p);
        }
            if(p.getTitle().equals("Title")) {
                productsFound.add(p);
            }});

        System.out.println("found " + count + " products alike");

        return productsFound;
    }

    public static List<Order> findOrders(List<Order> orders, String begin, String end) {
        List<Order> ordersFound = new ArrayList<>();
        int hour = Integer.parseInt(end)-Integer.parseInt(begin);
        assert hour >= 0;

        orders.forEach((o) -> {for( int i = Integer.parseInt(begin); i <= Integer.parseInt(end); i++) {
                       //System.out.println(o.getDateOrder().substring(11,13));
                       if(o.getDateOrder().substring(11,13).equals(String.valueOf(i)) || o.getDateOrder().substring(11,13).equals("0"+i)) {
                          ordersFound.add(o);
                       }
                   } });


        return ordersFound;
    }

    public static String findProductsMoreThanX(List<Order> orders, String nr) {
        String result ="";
        List<CompositeProduct> compositeProducts = new ArrayList<>();
        int x = Integer.parseInt(nr);
        assert x >= 0;
        AtomicReference<String> str = new AtomicReference<>("");
        AtomicInteger index = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        Integer[] found1 = new Integer[300];
        for(int i = 0; i < 300; i++) {
            found1[i] = 0;
        }
        AtomicInteger k = new AtomicInteger();
        orders.forEach((o) -> {
            str.set(o.getProducts());
            List<String> data = Arrays.asList(str.get().split("-"));
            for(int i = 0; i < data.size(); i++) {
                while(true) {
                    index.set(str.get().indexOf(data.get(i), index.get()));
                    if(index.get() != -1) {
                        count.incrementAndGet();
                        index.addAndGet(data.get(i).length());
                    }
                    else {
                        break;
                    }
                }
                if(data.get(i).length() > 8) {
                    k.set(Integer.parseInt(data.get(i).substring(8, data.get(i).length())));
                    found1[k.get()] += count.get();
                }

                count.set(0);
            }

         });
        for (int i = 0; i < 300; i++) {
            if(found1[i] > x) {
                result += "product "+ i + " ordered " + found1[i] +" times" + "\n";
            }
        }
        return result;
    }

    public static String findClientsMoreThanX(List<Order> orders, String nrOrder, String nrPayment) {
        String result ="";
        List<CompositeProduct> compositeProducts = new ArrayList<>();
        int x = Integer.parseInt(nrOrder);
        int y = Integer.parseInt(nrPayment);
        assert x >= 0;
        assert y >= 0;

        Integer[] clientAp = new Integer[300];
        Float[] totalPayment = new Float[300];
        for(int i = 0; i < 300; i++) {
            clientAp[i] = 0;
        }
        for(int i = 0; i < 300; i++) {
            totalPayment[i] = 0.0F;
        }
        AtomicInteger k = new AtomicInteger();
        orders.forEach((o) -> {
            //System.out.println(o.getClientID());
            clientAp[o.getClientID()] ++;
            totalPayment[o.getClientID()] += Float.parseFloat(o.getPrice());

        });
        for (int i = 0; i < 300; i++) {
            if(clientAp[i] > x && totalPayment[i] > y) {
                result += "client "+ i + " ordered " + clientAp[i] +" times and payed "+ totalPayment[i] +"\n";
            }
        }
        return result;
    }

    public static String findProducstFromDay(List<Order> orders, String day) {
        String result ="";
        List<CompositeProduct> compositeProducts = new ArrayList<>();
        int dayToSearch = Integer.parseInt(day);
        String[] productsFromDay = new String[300];
        Integer[] dayAp = new Integer[300];
        Float[] totalPayment = new Float[300];
        for(int i = 0; i < 300; i++) {
            dayAp[i] = 0;
        }
        for(int i = 0; i < 300; i++) {
            productsFromDay[i] = "";
        }
        for(int i = 0; i < 300; i++) {
            totalPayment[i] = 0.0F;
        }
        AtomicInteger k = new AtomicInteger();
        orders.forEach((o) -> {
            System.out.println(o.getDateOrder().substring(8,10));
            if(o.getDateOrder().substring(8,10).equals(day)) {
                dayAp[dayToSearch] ++;
                productsFromDay[dayToSearch] += o.getProducts()+"\n";
            }

        });
        for (int i = 0; i < 300; i++) {
            if(i == dayToSearch)
                result += "From day "+ i + " were ordered " + dayAp[i] +" times the following products: \n"+ productsFromDay[i] +"\n";
        }
        return result;
    }

    public void notifyEmployee() {
        setChanged();
        System.out.println("Employee has been notified!");
        notifyObservers();
    }

}
