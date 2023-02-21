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

public class OrarGeneric extends JFrame{
    private JPanel orarGenericPanel;
    private JScrollPane tablePanel;
    private JTable table;
    private JButton backBtn;
    private JButton salveazaBtn;
    private JButton updateOreBtn;
    private JButton updateAdresaBtn;
    private JButton addOrar;

    public OrarGeneric (Connection connection, Angajat angajat, String cnp, String querry) {
        setContentPane(orarGenericPanel);
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

        String[] column = { "Ora Inceput", "Ora Sfarsit", "Zi", "Locatie", "Nume Policlinica"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 5; i++)
            dtm.addColumn(column[i]);

        try {
            Statement statement = connection.createStatement();
            String querry1 = "SELECT o.ora_inceput, o.ora_sfarsit, o.zi, o.id_policlinica, o.cnp, p.adresa, p.nume " +
                            "from orar_generic o join policlinica p on o.id_policlinica = p.id_policlinica WHERE o.cnp = '" + cnp + "'";
            ResultSet resultSet = statement.executeQuery(querry1);
            Object[] rowData = new Object[5];

            for (int i = 0; i < 5; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("ora_inceput");
                rowData[1] = resultSet.getString("ora_sfarsit");
                rowData[2] = resultSet.getString("zi");
                rowData[3] = resultSet.getString("adresa");
                rowData[4] = resultSet.getString("nume");

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
                JOptionPane.showMessageDialog(getParent(), "Nu aveti orar generic. Consultati orarul specific!");
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
                    try {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(querry2);

                        JOptionPane.showMessageDialog(getParent(), "Modificari realizata cu succes!");

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
             new AddOrarGeneric(connection, angajat, cnp, querry);
            }
        });

    }

    public String toQuerry(int line) {
         return "UPDATE orar_generic o join policlinica p on o.id_policlinica = p.id_policlinica set o.ora_inceput = '" +
                table.getModel().getValueAt(line, 0) + "'" +
                ", o.ora_sfarsit = '" + table.getModel().getValueAt(line, 1) + "'" +
                ", o.zi = '" + table.getModel().getValueAt(line, 2) + "'" +
                ", p.adresa = '" + table.getModel().getValueAt(line, 3) + "'" +
                ", p.nume = '" + table.getModel().getValueAt(line, 4) + "'" +
                "WHERE zi = '" + table.getModel().getValueAt(line, 2) +
                "' AND p.nume= '" + table.getModel().getValueAt(line, 4) + "'";
    }

    public String toQuerryAdresa(int line) {
        return "UPDATE policlinica SET adresa = '" + table.getModel().getValueAt(line, 3) + "'" +
                "WHERE nume ='" + table.getModel().getValueAt(line, 4) + "'";
    }

}
