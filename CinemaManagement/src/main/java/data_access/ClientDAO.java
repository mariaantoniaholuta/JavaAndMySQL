/**
 * ClientDAO is the class we need for the Data Access to MySql
 * We use the Connector class to open and close the connection
 *
 * @author Holuta Maria
 *
 */
package data_access;

import bussinesslogic.Querry;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    static List<Client> clients = new ArrayList<>();

    public static String name;
    public static String email;
    public static String address;
    public static int age;

    public ClientDAO(String name, String email, String address, int age) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
    }
    /**
     * <p>This is a simple example for the method creating a table. . .
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html">swing table</a>
     * </p>
     * @return the model for the table and makes the result set
     * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html">swing table</a>
     */
    public static DefaultTableModel makeTable() {
        String[] column = {"ID", "NAME", "EMAIL", "ADDRESS", "AGE"};
        DefaultTableModel dm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dm.addColumn(column[i]);

        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            String result = Querry.allClients();
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
     * <p> This method is getting the connection and closes it, after adding a new client
     * </p>
     * @return void, adds a new client for the data base
     */
    public static void addClient() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            String querry = Querry.insertClient();
            PreparedStatement pr = connection.prepareStatement(querry);

            pr.setString(1, name);
            pr.setString(2, email);
            pr.setString(3, address);
            pr.setInt(4, age);
           // System.out.println(pr);

            pr.executeUpdate();

            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>This method makes any update in the data base for the client
     * </p>
     * @param table the table to be displayed in ManageClients
     * @return void, updates the Clients database and table
     */
    public static void updateClient(JTable table, String id) {
        Connection connection = ConnectionFactory.getConnection();
        String querry = Querry.updateClient(table, table.getSelectedRow());
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
            System.out.println("Can't update the client");
            ex.printStackTrace();
        }
    }
    /**
     * <p> This method is getting the connection and closes it, after deleting client
     * </p>
     * @return void, deletes a client from the data base
     */
    public static void deleteClient(String id) {
        Connection connection = ConnectionFactory.getConnection();
        String querry = Querry.deleteClient();

        try {
            Statement statement = connection.createStatement();
            PreparedStatement pr = connection.prepareStatement(querry);

            pr.setString(1, id);
            pr.executeUpdate();

            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);

        } catch (SQLException ex) {
            System.out.println("Can't delete the client");
            ex.printStackTrace();
        }
    }
    /**
     * <p>This takes the resultSet for the table and creates the clients
     * </p>
     * @param rows the rows for the table to be created
     * @return void, takes the resultSet for the table and creates the clients
     */
    private static void addRowsFromDataBase(Object[] rows, ResultSet resultSet) throws SQLException {
        Client client = new Client(resultSet);
        clients.add(client);

        rows[0] = String.valueOf(client.getId());
        rows[1] = client.getName();
        rows[2] = client.getEmail();
        rows[3] = client.getAddress();
        rows[4] = String.valueOf(client.getAge());
    }

    public static List<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> clients) {
        ClientDAO.clients = clients;
    }

    public void toString(List<Client> clients) {
        for (Client c : clients) {
            System.out.println(c.getName() + "\n");
        }
    }

}
