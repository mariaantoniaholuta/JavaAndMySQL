package com.utcn.Modulul3;
import com.utcn.models.*;
import com.utcn.CommonInterfaces.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AnalizeMedicaleM3 extends JFrame{
    private JPanel analizePanel;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton backButton;
    private JButton logOutButton;
    private JButton salveazaModificari;
    private JButton modificaDate;
    private JButton validareButton;
    private String oldValidate;

    public AnalizeMedicaleM3(Connection connection, Angajat angajat) {
        setContentPane(analizePanel);
        setTitle("Result");
        setSize(1000, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        String[] column = {"ID Programare", "Detalii Analiza", "Validare","Analiza Efectuata"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 4; i++)
            dtm.addColumn(column[i]);
        String querry = "SELECT * from analize_medicale";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(querry);
            Object[] rowData = new Object[10];
            for (int i = 0; i < 4; i++)
                rowData[i] = new Object();
            while (resultSet.next()) {
                rowData[0] = resultSet.getString("id_programare");
                rowData[1] = resultSet.getString("detalii_analize");
                rowData[2] = resultSet.getString("validare");
                rowData[3] = resultSet.getString("analiza_efectuata");
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
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OperationaleAsistent(connection, angajat);
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
                oldValidate = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
                System.out.println(oldValidate);
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
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
                        if(oldValidate.equals("0")) {
                            statement.executeUpdate(querry1);
                            JOptionPane.showMessageDialog(getParent(), "Modificari realizate cu succes!");
                        }
                        else {
                            JOptionPane.showMessageDialog(getParent(), "Analiza a fost deja validată!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }
                }
            }
        });

        validareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    String querry2 = "UPDATE analize_medicale SET  validare = '1'" +
                                     "WHERE id_programare =" + "'" + table.getValueAt(table.getSelectedRow(),0) + "'";
                    try {
                        Statement statement = connection.createStatement();
                        System.out.println(table.getModel().getValueAt(table.getSelectedRow(), 2));

                        if(oldValidate.equals("0")) {
                            statement.executeUpdate(querry2);
                            JOptionPane.showMessageDialog(getParent(), "Modificari realizate cu succes!");
                        }
                        else {
                            JOptionPane.showMessageDialog(getParent(), "Analiza a fost deja validată!");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public String toQuerry(int line) {
        return "UPDATE analize_medicale SET detalii_analize = '" + table.getModel().getValueAt(line, 1) + "'" +
                ", analiza_efectuata = '" + table.getModel().getValueAt(line, 3) +  "'" +
                "WHERE id_programare =" + "'" + table.getValueAt(table.getSelectedRow(),0) + "'";
    }
}