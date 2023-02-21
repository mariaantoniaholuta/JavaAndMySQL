/**
 * ProductDAO is the class we need for the Data Access to MySql
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
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    static List<Product> products = new ArrayList<>();

    public static String name;
    public static String genre;
    public static String cantity;
    public static String price;

    public ProductDAO(String name, String genre, String cantity, String price) {
        this.name = name;
        this.genre = genre;
        this.cantity = cantity;
        this.price = price;
    }
    /**
     * <p>This is a simple example for the method creating a table. . .
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html">swing table</a>
     * </p>
     * @return the model for the table and makes the result set
     * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html">swing table</a>
     */
    public static DefaultTableModel makeTable() {
        String[] column = {"ID", "TITLE", "GENRE", "CNT", "PRICE"};
        DefaultTableModel dm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dm.addColumn(column[i]);

        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            String result = Querry.allProducts();
            ResultSet resultSet = statement.executeQuery(result);
            Object[] rows = new Object[5];
            for (int i = 0; i < 5; i++)
                rows[i] = new Object();

            while (resultSet.next()) {
                addRowsFromDataBase(rows, resultSet);
                dm.addRow(rows);
            }

            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);

        } catch (SQLException ee) {
            System.out.println("Connection Failed");
        }

        return dm;
    }
    /**
     * <p> This method is getting the connection and closes it, after adding a new product to the data base
     * </p>
     * @return void
     */
    public static void addProduct() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            String querry = Querry.insertProduct();
            PreparedStatement pr = connection.prepareStatement(querry);

            pr.setString(1, name);
            pr.setString(2, genre);
            pr.setString(3, cantity);
            pr.setString(4, price);
            // System.out.println(pr);

            pr.executeUpdate();

            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>This method makes any update in the data base for the product
     * </p>
     * @param table the table to be displayed in ManageProducts
     * @return void, updates the Products database and table
     */
    public static void updateProduct(JTable table, String id) {
        Connection connection = ConnectionFactory.getConnection();
        String querry = Querry.updateProduct(table, table.getSelectedRow());
        //System.out.println(querry);
        try {
            Statement statement = connection.createStatement();
            PreparedStatement pr = connection.prepareStatement(querry);

            pr.setString(1, String.valueOf(id));
            // System.out.println(pr);
            pr.executeUpdate();

            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        } catch (SQLException ex) {
            System.out.println("Can't update the product");
            ex.printStackTrace();
        }
    }
    /**
     * <p> This method is getting the connection and closes it, after deleting a product
     * </p>
     * @return void, deletes a client from the data base
     */
    public static void deleteProduct(String id) {
        Connection connection = ConnectionFactory.getConnection();
        String querry = Querry.deleteProduct();

        try {
            Statement statement = connection.createStatement();
            PreparedStatement pr = connection.prepareStatement(querry);

            pr.setString(1, id);
            pr.executeUpdate();

            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        } catch (SQLException ex) {
            System.out.println("Can't delete the product");
            ex.printStackTrace();
        }
    }
    /**
     * <p>This takes the resultSet for the table and creates the products
     * </p>
     * @param rows the rows for the table to be created
     * @return void
     */
    private static void addRowsFromDataBase(Object[] rows, ResultSet resultSet) throws SQLException {
        Product product = new Product(resultSet);
        products.add(product);
        rows[0] = String.valueOf(product.getId());
        rows[1] = product.getTitle();
        rows[2] = product.getGenre();
        rows[3] = String.valueOf(product.getCantity());
        rows[4] = String.valueOf(product.getPrice());
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void setProducts(List<Product> products) {
        ProductDAO.products = products;
    }

    public void toString(List<Client> clients) {
        for (Client c : clients) {
            System.out.println(c.getName() + "\n");
        }
    }

}
