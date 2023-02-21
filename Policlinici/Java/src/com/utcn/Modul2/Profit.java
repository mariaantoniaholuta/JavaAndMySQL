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

public class Profit extends JFrame{
    private JButton backButton;
    private JButton logOutButton;
    private JScrollPane tablePanel;
    private JPanel mainPane;
    private JTextField numeMedicField;
    private JLabel numeMedicLabel;
    private JTextField prenumeMedicField;
    private JComboBox policlincaBox;
    private JComboBox specialitateBox;
    private JLabel prenumeMedicLabel;
    private JLabel policlincaLabel;
    private JLabel specialitateLabel;
    private JButton refreshButton;
    private JButton submitButton;
    private JTable table;
    private JRadioButton policlincaRadioButton;
    private JRadioButton specialitateRadioButton;
    private JComboBox mainbox;
    private DefaultTableModel dtm;

    public Profit(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Salarii");
        setSize(1000, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        tablePanel.getViewport().setBackground(new Color(36,69,74));
        setVisibleFields(false);

        tablePanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(76,80,82);
            }
        });

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        policlincaBox.setRenderer(listRenderer);
        specialitateBox.setRenderer(listRenderer);
        mainbox.setRenderer(listRenderer);

        mainbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = (String) mainbox.getSelectedItem();
                switch (item) {
                    case "medici":
                        setVisibleFieldsMedic(true);
                        break;
                    case "policlinici":
                        setVisibleFields(false);
                        break;
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Profit(connection, angajat);
            }
        });

        policlincaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(policlincaRadioButton.isSelected()) {
                    policlincaBox.removeAllItems();
                    specialitateRadioButton.setSelected(false);
                    setVisibleFieldsSpecialitate(false);
                    setVisibleFieldsPoliclinica(true);

                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT nume_policlinica " +
                                "FROM raport_final_final WHERE nume LIKE ? AND prenume LIKE ? ORDER BY nume_policlinica");
                        statement.setString(1,numeMedicField.getText() + "%");
                        statement.setString(2,prenumeMedicField.getText() + "%");
                        ResultSet resultSet = statement.executeQuery();
                        policlincaBox.addItem("");
                        if(resultSet.next()) {
                            do {
                                policlincaBox.addItem(resultSet.getString("nume_policlinica"));
                            }while (resultSet.next());
                        }
                        else {
                            JOptionPane.showMessageDialog(getParent(), "Nu exista inregistrari pentru datele introduse!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    setVisibleFieldsPoliclinica(false);
            }
        });

        specialitateRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(specialitateRadioButton.isSelected()) {
                    policlincaRadioButton.setSelected(false);
                    setVisibleFieldsPoliclinica(false);
                    setVisibleFieldsSpecialitate(true);
                    specialitateBox.removeAllItems();

                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT denumire_specialitate " +
                                "FROM medici_servicii_programari " +
                                "WHERE nume LIKE ? AND prenume LIKE ?");
                        statement.setString(1,numeMedicField.getText() + "%");
                        statement.setString(2,prenumeMedicField.getText() + "%");
                        ResultSet resultSet = statement.executeQuery();

                        specialitateBox.addItem("");
                        if(resultSet.next()) {
                            do {
                                specialitateBox.addItem(resultSet.getString("denumire_specialitate"));
                            }while (resultSet.next());
                        }
                        else {
                            JOptionPane.showMessageDialog(getParent(), "Nu exista inregistrari pentru datele introduse!");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
                else
                    setVisibleFieldsSpecialitate(false);
            }
        });

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

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainbox.getSelectedItem().equals("policlinici")) {
                    profitLantPoliclinici(connection);
                }
                else {
                    if (policlincaRadioButton.isSelected()) {/*** Afisare profit dupa policlinici**/
                        try {
                            PreparedStatement statement = connection.prepareStatement("SELECT cnp_medic, nume, prenume, luna_an, SUM(cost_fara_comision) AS cost_luna, " +
                                    "SUM(comision_medic) AS comisioane_totale, nume_policlinica " +
                                    "FROM raport_final_final GROUP BY CONCAT(luna_an, id_policlinica) " +
                                    "HAVING nume_policlinica LIKE ? AND nume LIKE ? AND prenume LIKE ?" +
                                    "ORDER BY data_consultatie");
                            statement.setString(1, (String) policlincaBox.getSelectedItem() + "%");
                            statement.setString(2, numeMedicField.getText() + "%");
                            statement.setString(3, prenumeMedicField.getText() + "%");
                            ResultSet resultSet = statement.executeQuery();

                            String[] column = {"CNP", "Nume", "Prenume", "Venit fara comisioane", "Total comisioane", "Luna/An"};
                            DefaultTableModel dtm = new DefaultTableModel();
                            for (int i = 0; i < 6; i++)
                                dtm.addColumn(column[i]);

                            Object[] rowData = new Object[6];
                            for (int i = 0; i < 6; i++)
                                rowData[i] = new Object();

                            while (resultSet.next()) {
                                rowData[0] = resultSet.getString("cnp_medic");
                                rowData[1] = resultSet.getString("nume");
                                rowData[2] = resultSet.getString("prenume");
                                rowData[3] = resultSet.getString("cost_luna") + " €";
                                rowData[4] = resultSet.getString("comisioane_totale") + " €";
                                rowData[5] = resultSet.getString("luna_an");
                                dtm.addRow(rowData);
                            }
                            setTableSettings(table, dtm);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    } else
                        if (specialitateRadioButton.isSelected()) { /*** Afisare profit dupa specialitate**/
                             specialitateProfit(connection);
                    } else
                        medicProfit(connection);
                }
            }
        });
        setVisible(true);
    }

    public void tableSettings() {
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 18));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

    }

    public void setTableSettings(JTable table, DefaultTableModel dtm) {
        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 16));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166,201,181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
    }

    public void medicProfit(Connection connection) {
        String[] column = {"CNP", "Nume", "Prenume", "Salariu","Venit fara comisioane", "Total comisioane", "Profit generat",
                "Luna/An"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 8; i++)
            dtm.addColumn(column[i]);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT sl.cnp, sl.nume, sl.prenume, sl.salariu,cl.cost_luna AS castig_fara_comision, cl.comisioane_totale, "+
                    "(cl.cost_luna - sl.salariu) AS profit, cl.luna_an " +
                    "FROM salarii_luna AS sl, (SELECT cnp_medic, luna_an, SUM(cost_fara_comision) AS cost_luna, SUM(comision_medic) AS comisioane_totale, data_consultatie " +
                    "FROM raport_medic " +
                    "GROUP BY luna_an) AS cl " +
                    "WHERE cl.cnp_medic = sl.cnp AND cl.luna_an = sl.luna_an AND sl.nume LIKE ? AND sl.prenume LIKE ? " +
                    "ORDER BY cl.data_consultatie;");
            statement.setString(1, numeMedicField.getText() + "%");
            statement.setString(2, prenumeMedicField.getText() + "%");
            ResultSet resultSet = statement.executeQuery();

            Object[] rowData = new Object[8];
            for (int i = 0; i < 8; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("cnp");
                rowData[1] = resultSet.getString("nume");
                rowData[2] = resultSet.getString("prenume");
                rowData[3] = resultSet.getString("salariu") + " €";
                rowData[4] = resultSet.getString("castig_fara_comision") +" €";
                rowData[5] = resultSet.getString("comisioane_totale") +" €";
                rowData[6] = resultSet.getString("profit") + " €";
                rowData[7] = resultSet.getString("luna_an");
                dtm.addRow(rowData);
            }
        } catch (SQLException ee) {
            System.out.println("O picat facebook ul");
        }

        setTableSettings(table, dtm);
    }

    public void specialitateProfit(Connection connection) {
        String[] column = {"CNP", "Nume", "Prenume","Venit fara comisioane", "Total comisioane",
                "Luna/An"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 6; i++)
            dtm.addColumn(column[i]);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT cnp_medic, nume, prenume, SUM(pret) - SUM(comision_medic) AS venit, SUM(comision_medic) AS comision, luna_an, denumire_specialitate " +
                    "FROM medici_servicii_programari AS m " +
                    "GROUP BY CONCAT(luna_an, denumire_specialitate) " +
                    "HAVING nume LIKE ? AND prenume LIKE ? AND denumire_specialitate LIKE ?");

            statement.setString(1, numeMedicField.getText() + "%");
            statement.setString(2,prenumeMedicField.getText() + "%");
            statement.setString(3, (String) specialitateBox.getSelectedItem() + "%");
            ResultSet resultSet = statement.executeQuery();

            Object[] rowData = new Object[6];
            for (int i = 0; i < 6; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("cnp_medic");
                rowData[1] = resultSet.getString("nume");
                rowData[2] = resultSet.getString("prenume");
                rowData[3] = resultSet.getString("venit") + " €";
                rowData[4] = resultSet.getString("comision") +" €";
                rowData[5] = resultSet.getString("luna_an");
                dtm.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTableSettings(table, dtm);
    }

    public void profitLantPoliclinici(Connection connection) {
        String[] column = {"Venit", "Cheluieli", "Salarii", "Comisioane", "Profit", "Luna/An"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (int i = 0; i < 6; i++)
            dtm.addColumn(column[i]);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT tv.venit AS venit, ts.total_salarii + tv.comisioane AS cheltuieli, " +
                            "ts.total_salarii AS salarii, tv.comisioane, (tv.venit - ts.total_salarii) AS profit, tv.luna_an " +
                    "FROM total_venituri as tv " +
                    "JOIN total_salarii_luna AS ts " +
                    "ON tv.luna_an = ts.luna_an");

            ResultSet resultSet = statement.executeQuery();
            Object[] rowData = new Object[8];
            for (int i = 0; i < 8; i++)
                rowData[i] = new Object();

            while (resultSet.next()) {
                rowData[0] = resultSet.getString("venit") + " €";;
                rowData[1] = resultSet.getString("cheltuieli") + " €";
                rowData[2] = resultSet.getString("salarii") + " €";
                rowData[3] = resultSet.getString("comisioane") + " €";
                rowData[4] = resultSet.getString("profit") +" €";
                rowData[5] = resultSet.getString("luna_an");
                dtm.addRow(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTableSettings(table, dtm);
    }

    public void setVisibleFields(boolean b) {
        numeMedicLabel.setVisible(b);
        numeMedicField.setVisible(b);
        prenumeMedicField.setVisible(b);
        prenumeMedicLabel.setVisible(b);
        policlincaLabel.setVisible(b);
        policlincaBox.setVisible(b);
        specialitateLabel.setVisible(b);
        specialitateBox.setVisible(b);
        policlincaRadioButton.setVisible(b);
        specialitateRadioButton.setVisible(b);
    }

    public void setVisibleFieldsMedic(boolean b) {
        policlincaRadioButton.setVisible(b);
        specialitateRadioButton.setVisible(b);
        numeMedicLabel.setVisible(b);
        numeMedicField.setVisible(b);
        prenumeMedicField.setVisible(b);
        prenumeMedicLabel.setVisible(b);
    }

    public void setVisibleFieldsPoliclinica(boolean b) {
        policlincaLabel.setVisible(b);
        policlincaBox.setVisible(b);
    }

    public void setVisibleFieldsSpecialitate(boolean b) {
        specialitateLabel.setVisible(b);
        specialitateBox.setVisible(b);
    }
}
