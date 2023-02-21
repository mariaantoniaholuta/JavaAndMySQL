package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddConcedii extends JFrame {
    private JPanel addConcediiPane;
    private JButton backBtn;
    private JTextField cnpField;
    private JTextField dataPlecareField;
    private JButton addConcediiBtn;
    private JLabel cnpLbl;
    private JLabel dataPlecareLbl;
    private JLabel dataRevenireLbl;
    private JTextField dataRevenireField;
    private JTextField numeField;
    private JTextField prenumeField;

    public AddConcedii (Connection connection, Angajat angajat) {
        setContentPane(addConcediiPane);
        setTitle("Welcome");
        setSize(650, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Concedii(connection, angajat );
            }
        });

        addConcediiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cnp = "'" + cnpField.getText() + "'";
                String data_plecare = "'" + dataPlecareField.getText() + "'";
                String data_revenire = "'" + dataRevenireField.getText() + "'";

                try {
                    Statement statement = connection.createStatement();
                    String querry = "INSERT INTO concediu (cnp, data_plecare, data_revenire) " +
                            "VALUES (" + cnp + "," + data_plecare + ","  + data_revenire  + ")";
                    System.out.println(querry);

                    statement.executeUpdate(querry);
                    JOptionPane.showMessageDialog(getParent(), "Concediu Adaugat!");
                }
                catch (SQLException ee) {
                    System.out.println("O picat facebook ul");
                }
            }

        });
    }
}
