package com.utcn.Modul1;

import com.utcn.models.Angajat;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Concedii extends JFrame {

    private JPanel concediiPanel;
    private JButton backBtn;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton salveazaBtn;
    private JButton updateBtn;
    private JButton addBtn;

    public Concedii (Connection connection, Angajat angajat) {
        setContentPane(concediiPanel);
        setTitle("Concedii");
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
            addBtn.setVisible(false);
        }

        String[] column = {"CNP", "Nume", "Prenume", "Data Plecare", "Data Revenire"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dtm.addColumn(column[i]);

        try {
            Statement statement = connection.createStatement();
            String querry1 = "SELECT c.cnp, c.data_plecare, c.data_revenire, a.nume, a.prenume " +
                             "FROM concediu c join angajat a on c.cnp = a.cnp";
            ResultSet resultSet = statement.executeQuery(querry1);
            Object[] rowData = new Object[5];

            for (int i = 0; i < 5; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("cnp");
                rowData[1] = resultSet.getString("nume");
                rowData[2] = resultSet.getString("prenume");
                rowData[3] = resultSet.getString("data_plecare");
                rowData[4] = resultSet.getString("data_revenire");

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

                        PreparedStatement prepare = connection.prepareStatement(querry);
                        prepare.setString(1, table.getValueAt(table.getSelectedRow(),0).toString());
                        prepare.setString(2,  table.getValueAt(table.getSelectedRow(),3).toString());

                        prepare.executeUpdate();

                        JOptionPane.showMessageDialog(getParent(), "Modificari realizata cu succes!");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }
                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(angajat.getFunctie().equals("inspector resurse")) {
                    dispose();
                    new AddConcedii(connection, angajat);
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "Nu aveti drepturile necesare");
                }
            }
        });
    }

    public String toQuerry(int line) {

        return "UPDATE Concediu SET cnp = '" + table.getModel().getValueAt(table.getSelectedRow(), 0) + "'" +
                ", data_plecare = '" + table.getModel().getValueAt(table.getSelectedRow(), 3) + "'" +
                ", data_revenire = '" + table.getModel().getValueAt(table.getSelectedRow(), 4) + "'" +
                " WHERE cnp = ? AND data_plecare = ?";
    }
}
