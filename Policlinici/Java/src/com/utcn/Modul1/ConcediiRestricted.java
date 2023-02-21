package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConcediiRestricted extends JFrame {

    private JPanel concediiRestrictedPane;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton backBtn;

    public ConcediiRestricted(Connection connection, Angajat angajat) {
        setContentPane(concediiRestrictedPane);
        setTitle("Welcome");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        String[] column = {"CNP", "Data Plecare", "Data Revenire"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 3; i++)
            dtm.addColumn(column[i]);

        try {
            Statement statement = connection.createStatement();
            String querry = "SELECT * FROM concediu WHERE cnp ='" + angajat.getCnp() + "'";

            ResultSet resultSet = statement.executeQuery(querry);
            Object[] rowData = new Object[3];

            for (int i = 0; i < 3; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("cnp");
                rowData[1] = resultSet.getString("data_plecare");
                rowData[2] = resultSet.getString("data_revenire");

                dtm.addRow(rowData);
            }
        }
        catch (SQLException ee) {
            System.out.println("O picat facebook ul");
        }

        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 22));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        setVisible(true);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmaneRestricted(connection, angajat);
            }
        });
    }
}
