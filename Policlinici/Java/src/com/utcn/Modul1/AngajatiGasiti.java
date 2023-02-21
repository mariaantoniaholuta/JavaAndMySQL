package com.utcn.Modul1;

import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Login;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AngajatiGasiti extends JFrame{
    private JButton logOutButton;
    private JButton backButton;
    private JPanel resultPane;
    private JTable table;
    private JScrollPane tablePanel;
    private JButton orarSpecific;
    private JButton orarGeneric;
    private JButton modificaDate;
    private JButton salveazaModificari;

    public AngajatiGasiti(Connection connection, Angajat angajat, String querry) {
        setContentPane(resultPane);
        setTitle("Result");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        if(angajat.getFunctie().equals("expert financiar")) {
            modificaDate.setVisible(false);
            salveazaModificari.setVisible(false);
        }
        String[] column = {"CNP", "Nume", "Prenume","Telefon", "Adresa", "Email", "Numar ore", "IBAN", "Data Angajare",
                "Functie"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 10; i++)
            dtm.addColumn(column[i]);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querry);
            Object[] rowData = new Object[10];

            for (int i = 0; i < 10; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("cnp");
                rowData[1] = resultSet.getString("nume");
                rowData[2] = resultSet.getString("prenume");
                rowData[3] = resultSet.getString("telefon");
                rowData[4] = resultSet.getString("adresa");
                rowData[5] = resultSet.getString("email");
                rowData[6] = resultSet.getString("numar_ore");
                rowData[7] = resultSet.getString("iban");
                rowData[8] = resultSet.getString("data_angajare");
                rowData[9] = resultSet.getString("functie");
                dtm.addRow(rowData);
            }
        }
        catch (SQLException ee) {
            System.out.println("O picat facebook ul");
        }

        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() { /*** am schimbat culoare scroll ului pentru că da**/
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(76,80,82);
            }
        });

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

        salveazaModificari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
            }
        });

        orarGeneric.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati un angajat!");
                else {
                    String cnp = table.getValueAt(table.getSelectedRow(),0).toString();
                   // System.out.println(cnp);
                    dispose();
                    new OrarGeneric(connection, angajat, cnp, querry);
                }
            }
        });

        orarSpecific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati un angajat!");
                else {
                    String cnp = table.getValueAt(table.getSelectedRow(),0).toString();
                    dispose();
                    new OrarSpecific(connection, angajat, cnp, querry);
                }
            }
        });

        modificaDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    String querry1 = toQuerry(table.getSelectedRow());
                    try {

                        Statement statement = connection.createStatement();
                        statement.executeUpdate(querry1 + "WHERE cnp =" + "'" + table.getValueAt(table.getSelectedRow(),0) + "'");

                        JOptionPane.showMessageDialog(getParent(), "Modificari realizata cu succes!");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }
                }
            }
        });

        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        setVisible(true);
    }
    
    public String toQuerry(int line) {
        return "UPDATE Angajat SET cnp = '" + table.getModel().getValueAt(line, 0) + "'" +
                ", nume = '" + table.getModel().getValueAt(line, 1) + "'" +
                ", prenume = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", telefon = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", adresa = '" + table.getModel().getValueAt(line, 4) + "'" +
                ", email = '" + table.getModel().getValueAt(line, 5) + "'" +
                ", numar_ore = '" + table.getModel().getValueAt(line, 6) + "'" +
                ", iban = '" + table.getModel().getValueAt(line, 7) + "'" +
                ", data_angajare = '" + table.getModel().getValueAt(line, 8) + "'" +
                ", functie = '" + table.getModel().getValueAt(line, 9) + "'" ;
    }
}
