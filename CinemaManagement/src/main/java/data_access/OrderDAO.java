/**
 * OrderDAO is the class we need for the Data Access to MySql
 * We use the Connector class to open and close the connection
 *
 * @author Holuta Maria
 *
 */
package data_access;

import bussinesslogic.Querry;
import model.Client;
import model.Product;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {

    public static String clientId;
    public static String productId;
    public static int cantity;

    public OrderDAO(String clientId, String productId, String cantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.cantity = Integer.parseInt(cantity);
    }
    /**
     * <p> This method is getting the connection and closes it, after creating an order if possible, and also changing the cantity for the products
     * </p>
     * @return void
     */
    public static void createOrder() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            String querry = Querry.createOrder();
            PreparedStatement pr = connection.prepareStatement(querry);
            pr.setString(1, clientId);
            pr.setString(2, productId);
            Client c = getClient();
            writeOnBill();
            for(Product p : ProductDAO.products) {
                if(p.getId() == Integer.parseInt(productId)) {
                    if(cantity > p.getCantity()) {
                       System.out.println("Not enough tickets!");
                    }
                    else {
                        p.setCantity(p.getCantity() - cantity);
                        String querryUpdate = Querry.updateCantity();
                        PreparedStatement prUpdate = connection.prepareStatement(querryUpdate);

                        prUpdate.setString(1, String.valueOf(p.getCantity()));
                        prUpdate.setString(2, productId);
                        System.out.println("      CINEMA BILL      \n" + "Client __________ " + c.getName() + "\n" + "Movie ___________ " + p.getTitle() + "\n" + "Cantity _________  " + cantity + "\n" +
                                "Price per One ____ " + p.getPrice() + "\n" + "Total Price ______ " + cantity * p.getPrice());
                        prUpdate.executeUpdate();
                        pr.executeUpdate();
                        break;
                    }
                }
            }
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p> This method is to get the client with the specified id
     * </p>
     * @return client
     */
    public static Client getClient() {
        for(Client c: ClientDAO.clients) {
            if(c.getId() == Integer.parseInt(clientId)) {
                  return c;
            }
        }
        return null;
    }
    /**
     * <p> This method creates a bill text file
     * </p>
     * @return void
     */
    public static void writeOnBill() {
        try {
            PrintStream file = new PrintStream("./bill.txt");
            System.setOut(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
