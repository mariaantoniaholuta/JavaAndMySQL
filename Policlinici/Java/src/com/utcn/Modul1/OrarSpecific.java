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

public class OrarSpecific extends JFrame{
    private JPanel orarSpecificPanel;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton backBtn;
    private JButton salveazaBtn;
    private JButton updateOreBtn;
    private JButton updateAdresaBtn;
    private JButton addOrar;

    public OrarSpecific (Connection connection, Angajat angajat, String cnp, String querry) {
        setContentPane(orarSpecificPanel);
        setTitle("Welcome");
        setSize(1200, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        if(angajat.getFunctie().equals("expert financiar")) {
            salveazaBtn.setVisible(false);
            updateOreBtn.setVisible(false);
        }

        if (!angajat.getFunctie().equals("inspector resurse") && !angajat.getFunctie().equals("expert financiar")) {
            salveazaBtn.setVisible(false);
            updateOreBtn.setVisible(false);
            addOrar.setVisible(false);
        }

        if(angajat.getFunctie().equals("expert financiar")) {
            addOrar.setVisible(false);
        }

        String[] column = { "Ora Inceput", "Ora Sfarsit", "Data", "Locatie", "Nume Policlinica", "ID Policlinica"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 6; i++)
            dtm.addColumn(column[i]);

        try {
            Statement statement = connection.createStatement();
            String querry1 = "SELECT o.ora_inceput, o.ora_sfarsit, o.data_inregistrare, o.id_policlinica, o.cnp, p.adresa, p.nume " +
                    "from orar_specific o join policlinica p on o.id_policlinica = p.id_policlinica WHERE o.cnp = '" + cnp + "'";
            ResultSet resultSet = statement.executeQuery(querry1);
            Object[] rowData = new Object[6];

            for (int i = 0; i < 6; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("ora_inceput");
                rowData[1] = resultSet.getString("ora_sfarsit");
                rowData[2] = resultSet.getString("data_inregistrare");
                rowData[3] = resultSet.getString("adresa");
                rowData[4] = resultSet.getString("nume");
                rowData[5]= resultSet.getString("id_policlinica");

                dtm.addRow(rowData);
            }

        }
        catch (SQLException ee) {
            ee.printStackTrace();
            System.out.println("O picat facebook ul");
        }



        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 22));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        if (!angajat.getFunctie().equals("inspector resurse") && !angajat.getFunctie().equals("expert financiar"))
            table.setEnabled(false);
        setVisible(true);

        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.trackColor= new Color(76,80,82);
                this.thumbColor = new Color(124,128,130);

            }
        });

        if (table != null && table.getModel() != null) {
            if (table.getModel().getRowCount() <= 0) {
                JOptionPane.showMessageDialog(getParent(), "Nu aveti orar specific. Consultati orarul generic!");
            }
        }

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!angajat.getFunctie().equals("inspector resurse") && !angajat.getFunctie().equals("expert financiar")) {
                    dispose();
                    new GestiuneResurseUmaneRestricted(connection, angajat);
                }
                else {
                    dispose();
                    new AngajatiGasiti(connection, angajat, querry);
                }
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

        updateOreBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() == -1)
                    JOptionPane.showMessageDialog(getParent(), "Selectati o linie!");
                else {
                    String querry2 = toQuerry(table.getSelectedRow());
                    System.out.println(querry2);
                    try {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(querry2);

                        JOptionPane.showMessageDialog(getParent(), "Modificari realizate cu succes!");

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(getParent(), "Datele nu au putut fi actualizate");
                        ex.printStackTrace();
                    }

                }
            }
        });


        addOrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddOrarSpecific(connection, angajat, cnp, querry);
            }
        });
    }

    public String toQuerry(int line) {
        return "UPDATE orar_specific o join policlinica p on o.id_policlinica = p.id_policlinica set o.ora_inceput = '" +
                table.getModel().getValueAt(line, 0) + "'" +
                ", o.ora_sfarsit = '" + table.getModel().getValueAt(line, 1) + "'" +
                ", o.data_inregistrare = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", p.adresa = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", p.nume = '" + table.getModel().getValueAt(line, 4) + "'" +
                "WHERE o.id_policlinica = '" + table.getModel().getValueAt(line, 5) +
                "' AND p.id_policlinica = '" + table.getModel().getValueAt(line, 5) + "'";
    }

    public String toQuerryAdresa(int line) {
        return "UPDATE policlinica SET adresa = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", nume = '" + table.getModel().getValueAt(line, 4) + "'" +
                "WHERE id_policlinica ='" + table.getModel().getValueAt(line, 5) + "'";
    }

}