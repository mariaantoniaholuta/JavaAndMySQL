package com.utcn.Modul2;


import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Login;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Salarii extends JFrame{
    private JButton backButton;
    private JButton logOutButton;
    private JTextField numeField;
    private JTextField prenumeField;
    private JButton searchButton;
    private JTable table;
    private JPanel mainPane;
    private JScrollPane tablePane;

    public Salarii(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Salarii");
        setSize(1000, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        tablePane.getViewport().setBackground(new Color(36,69,74));
        String[] column = {"CNP", "Nume", "Prenume", "Salariu", "Data incasare"};

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OperatiiFinanciarContabile(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });


        tablePane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(76,80,82);
        }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel dtm = new DefaultTableModel();
                for (int i = 0; i < 5; i++)
                    dtm.addColumn(column[i]);
                try {
                    PreparedStatement statement = connection.prepareStatement("select s.cnp, a.nume, a.prenume, s.data_incasare, s.salariu from salariu as s " +
                            " join angajat as a on s.cnp = a.cnp where a.nume LIKE ? AND a.prenume LIKE ? order by s.cnp");
                    statement.setString(1,numeField.getText() + "%");
                    statement.setString(2,prenumeField.getText() + "%");
                    ResultSet resultSet = statement.executeQuery();

                    Object[] rowData = new Object[5];
                    for (int i = 0; i < 5; i++)
                        rowData[i] = new Object();

                    while (resultSet.next()) {
                        rowData[0] = resultSet.getString("cnp");
                        rowData[1] = resultSet.getString("nume");
                        rowData[2] = resultSet.getString("prenume");
                        rowData[3] = resultSet.getString("salariu") + " €";
                        rowData[4] = resultSet.getString("data_incasare");
                        dtm.addRow(rowData);
                    }

                    table.setEnabled(false);
                    table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
                    table.getTableHeader().setOpaque(false);
                    table.getTableHeader().setBackground(new Color(166,201,181));
                    table.setModel(dtm);
                    table.setRowHeight(30);
                    table.setFillsViewportHeight(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setVisible(true);
    }
}
