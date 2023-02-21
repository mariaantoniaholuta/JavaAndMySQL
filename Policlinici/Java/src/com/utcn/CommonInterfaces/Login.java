package com.utcn.CommonInterfaces;

import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField tfUserName;
    private JTextField tfPassword;
    private JButton btnClear;
    private JButton btnOK;
    private JPanel loginPanel;
    private JLabel lbWelcome;

    public Login(Connection connection) {
        setContentPane(loginPanel);
        setTitle("LogIn");
        setSize(450,300);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username =  "'" + tfUserName.getText() + "'";
                String password = "'" + tfPassword.getText() + "'";
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM angajat WHERE parola = ? AND " +
                            "utilizator = ?");
                    statement.setString(1, tfPassword.getText());
                    statement.setString(2, tfUserName.getText());
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next()) {
                        Angajat angajat = new Angajat(resultSet);
                        dispose();
                        new Homepage(connection, angajat);
                    }
                    else {
                        lbWelcome.setText("Date incorecte");
                    }
                }
                catch (SQLException ee) {
                    System.out.println("O picat facebook ul");
                }
            }
        });

        tfPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username =  tfUserName.getText() ;
                String password =  tfPassword.getText() ;

                try {
                    Statement statement = connection.createStatement();
                    String querry = "SELECT * FROM Angajat where parola = ?"
                                    + " and utilizator = ?";
                    PreparedStatement pr = connection.prepareStatement(querry);
                    pr.setString(1, password);
                    pr.setString(2, username);
                    ResultSet resultSet = pr.executeQuery();

                    if(resultSet.next()) {
                        Angajat angajat = new Angajat(resultSet);
                        dispose();
                        Homepage homepage = new Homepage(connection, angajat);
                    }
                    else {
                        lbWelcome.setText("Date incorecte");
                    }
                }
                catch (SQLException ee) {
                    System.out.println("O picat facebook ul");
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfPassword.setText("");
                tfUserName.setText("");
                lbWelcome.setText("");
            }
        });

    }
}
