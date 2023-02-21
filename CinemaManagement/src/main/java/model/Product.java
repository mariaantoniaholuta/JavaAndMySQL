package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This is the class representing our products
 *
 * @author Holuta Maria
 *
 */
public class Product {

    private int id;
    private String title;
    private String genre;
    private int cantity;
    private int price;

    public Product(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.title = resultSet.getString("title");
        this.genre = resultSet.getString("genre");
        this.cantity= resultSet.getInt("cantity");
        this.price = resultSet.getInt("price");
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getCantity() {
        return cantity;
    }

    public void setCantity(int cantity) {
        this.cantity = cantity;
    }
    public int getPrice() {
        return price;
    }


}
