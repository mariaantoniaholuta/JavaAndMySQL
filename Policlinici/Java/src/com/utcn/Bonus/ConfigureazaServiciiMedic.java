package com.utcn.Bonus;

import com.utcn.CommonInterfaces.Login;
import com.utcn.Modul1.GestiuneResurseUmane;
import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigureazaServiciiMedic extends JFrame{
    private JPanel mainPanel;
    private JButton backButton;
    private JButton logOutButton;
    private JTextField cnpField;
    private JButton submitButton;
    private JTable table;
    private JButton salveazaModificarileButton;
    private JButton updateButton;
    private JScrollPane scrollPane;
    private JButton adaugaServiciuCustomButton;

    public ConfigureazaServiciiMedic(Connection connection, Angajat angajat) {
        setContentPane(mainPanel);
        setTitle("Repartizare");
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        scrollPane.getViewport().setBackground(new Color(36,69,74));
        setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmane(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] column = {"Id serviciu", "Denumire serviciu", "Cost", "Durata"};
                DefaultTableModel dtm = new DefaultTableModel();
                for (int i = 0; i < 4; i++)
                    dtm.addColumn(column[i]);

                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT sc.id_serviciu, sm.denumire_serviciu, sc.cost, sc.durata " +
                            "FROM servicii_custom_medic AS sc " +
                            "JOIN servicii_medicale AS sm " +
                            "ON sc.id_serviciu = sm.id_serviciu WHERE cnp_medic = ?");
                    statement.setString(1, cnpField.getText());
                    ResultSet servicii = statement.executeQuery();

                    Object[] rowData = new Object[4];
                    for (Object i: rowData)
                        i = new Object();

                    while (servicii.next()) {
                        rowData[0] = servicii.getString("id_serviciu");
                        rowData[1] = servicii.getString("denumire_serviciu");
                        rowData[2] = servicii.getString("cost");
                        rowData[3] = servicii.getString("durata");
                        dtm.addRow(rowData);
                    }
                    setTableSettings(table, dtm);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        adaugaServiciuCustomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdaugaServiciu(connection, angajat);
            }
        });

        salveazaModificarileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    try {
                        int line = table.getSelectedRow();
                        PreparedStatement statement = connection.prepareStatement("UPDATE servicii_custom_medic" +
                                " SET cost = ?, durata = ? WHERE cnp_medic = ? AND id_serviciu = ?");
                        statement.setString(1, String.valueOf(table.getModel().getValueAt(line, 2)));
                        statement.setString(2, String.valueOf(table.getModel().getValueAt(line, 3)));
                        statement.setString(3, cnpField.getText());
                        statement.setString(4, String.valueOf(table.getModel().getValueAt(line, 0)));
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(getParent(), "Date actualizate cu succes!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate!");
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void setTableSettings(JTable table, DefaultTableModel dtm) {
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 16));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
    }
}
