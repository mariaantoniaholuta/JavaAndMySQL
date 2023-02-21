package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrarPoliclinici extends JFrame{
    private JPanel orarPoliPane;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton backBtn;
    private JButton salveazaBtn;
    private JButton updateBtn;

    public OrarPoliclinici (Connection connection, Angajat angajat, String querry) {
        setContentPane(orarPoliPane);
        setTitle("Welcome");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        if(angajat.getFunctie().equals("expert financiar")) {
            salveazaBtn.setVisible(false);
            updateBtn.setVisible(false);
        }

        String[] column = {"ID Policlinica", "ID Orar", "Zi", "Ora Inceput", "Ora Sfarsit"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dtm.addColumn(column[i]);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querry);
            Object[] rowData = new Object[5];

            for (int i = 0; i < 5; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("id_policlinica");
                rowData[1] = resultSet.getString("id_orar");
                rowData[2] = resultSet.getString("zi");
                rowData[3] = resultSet.getString("ora_inceput");
                rowData[4] = resultSet.getString("ora_sfarsit");

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

        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.trackColor= new Color(76,80,82);
                this.thumbColor = new Color(124,128,130);

            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmane(connection, angajat);
            }
        });

        salveazaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    String querry = toQuerry(table.getSelectedRow());
                    try {

                        Statement statement = connection.createStatement();
                        statement.executeUpdate(querry);

                        JOptionPane.showMessageDialog(getParent(), "Modificari realizata cu succes!");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public String toQuerry(int line) {
        return "UPDATE orar_policlinica SET id_policlinica = '" + table.getModel().getValueAt(line, 0) + "'" +
                ", id_orar = '" + table.getModel().getValueAt(line, 1) + "'" +
                ", zi = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", ora_inceput = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", ora_sfarsit = '" + table.getModel().getValueAt(line, 4) + "'" +
                "WHERE id_policlinica = '" + table.getValueAt(table.getSelectedRow(),0) + "'" +
                " AND id_orar = '" + table.getValueAt(table.getSelectedRow(),1) + "'";
    }
}