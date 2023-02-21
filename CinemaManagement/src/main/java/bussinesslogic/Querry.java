/**
 * Querry is the class we need to make the querries for the statement and resultSet
 *
 * @author Holuta Maria
 *
 */
package bussinesslogic;

import javax.swing.*;

public class Querry {

    public static String allClients() {
        String querry = "SELECT * FROM Clients";

        return querry;
    }

    public static String insertClient() {
        String querry = "INSERT INTO Clients (name, email, address, age) VALUES (?,?,?,?)";

        return querry;
    }

    public static String updateClient(JTable table, int line) {
        String querry = "UPDATE Clients set name = '" +
                table.getModel().getValueAt(line, 1) + "'" +
                ", email = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", address = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", age = '" + table.getModel().getValueAt(line, 4) + "'" +
                " WHERE id = ?";

        return querry;
    }

    public static String deleteClient() {
        String querry = "DELETE FROM Clients WHERE id = ?";

        return querry;
    }

    public static String allProducts() {
        String querry = "SELECT * FROM Products";

        return querry;
    }

    public static String insertProduct() {
        String querry = "INSERT INTO Products (title, genre, cantity, price) VALUES (?,?,?,?)";

        return querry;
    }

    public static String updateProduct(JTable table, int line) {
        String querry = "UPDATE Products set title = '" +
                table.getModel().getValueAt(line, 1) + "'" +
                ", genre = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", cantity = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", price = '" + table.getModel().getValueAt(line, 4) + "'" +
                " WHERE id = ?";

        return querry;
    }

    public static String deleteProduct() {
        String querry = "DELETE FROM Products WHERE id = ?";

        return querry;
    }

    public static String createOrder() {
        String querry = "INSERT INTO Orders (clientID, productID) VALUES (?,?)";

        return querry;
    }

    public static String updateCantity() {
        String querry = "UPDATE Products set cantity = ? WHERE id = ?";

        return querry;
    }
}
