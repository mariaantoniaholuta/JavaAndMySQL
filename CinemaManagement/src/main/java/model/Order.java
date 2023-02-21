package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This is the class representing our orders
 *
 * @author Holuta Maria
 *
 */
public class Order {

    private int clientID;
    private int productID;
    private int orderNumber;

    public Order(ResultSet resultSet) throws SQLException {
        this.clientID = resultSet.getInt("clientID");
        this.productID = resultSet.getInt("productID");
        this.orderNumber = resultSet.getInt("orderNumber");
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
