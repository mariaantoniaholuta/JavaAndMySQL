package model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This is the class representing our clients
 *
 * @author Holuta Maria
 *
 */
public class Client {

    private int id;
    private String name;
    private String email;
    private String address;
    private int age;

    public Client(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.email = resultSet.getString("email");
        this.address = resultSet.getString("address");
        this.age = resultSet.getInt("age");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

}
