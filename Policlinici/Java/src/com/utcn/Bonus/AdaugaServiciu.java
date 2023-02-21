package com.utcn.Bonus;

import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class AdaugaServiciu extends JFrame{
    private JPanel mainPane;
    private JButton backButton;
    private JButton logOutButton;
    private JComboBox comboServici;
    private JTextField pretField;
    private JTextField durataField;
    private JButton submitButton;
    private JTextField cnpField;
    private JButton cautaServiciiButton;
    private JLabel serviciu;
    private JLabel pret;
    private JLabel durata;

    public AdaugaServiciu(Connection connection, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Configurare");
        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        setVisibleFields(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboServici.setRenderer(listRenderer);

        Hashtable<String, Integer> ht = new Hashtable<>();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConfigureazaServiciiMedic(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        cautaServiciiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement statement = connection.prepareStatement(" SELECT id_serviciu, denumire_serviciu FROM servicii_pentru_medic_cu_nume WHERE cnp = ?" +
                            " UNION " +
                            " SELECT id_serviciu, denumire_serviciu FROM servicii_ce_necesita_competente WHERE cnp = ? ORDER BY denumire_serviciu");
                    statement.setString(1, cnpField.getText());
                    statement.setString(2, cnpField.getText());
                    ResultSet serviciiMedic = statement.executeQuery();

                    if(serviciiMedic.next()) {
                        do {
                            comboServici.addItem(serviciiMedic.getString("denumire_serviciu"));
                            ht.put(serviciiMedic.getString("denumire_serviciu"), serviciiMedic.getInt("id_serviciu"));
                        }while (serviciiMedic.next());
                        setVisibleFields(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "Cnp invalid sau medic fara servicii");
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO servicii_custom_medic(id_serviciu, cnp_medic," +
                            "cost, durata) VALUES (?, ?, ?, ?)");
                    String item = (String) comboServici.getSelectedItem();
                    statement.setString(1, String.valueOf(ht.get(item)));
                    statement.setString(2, cnpField.getText());
                    statement.setDouble(3, Double.parseDouble(pretField.getText()));
                    statement.setInt(4, Integer.parseInt(durataField.getText()));
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(getParent(), "Date inserate cu succes!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(getParent(), "Inserarea datelor a esuat!");
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public void setVisibleFields(boolean b) {
        serviciu.setVisible(b);
        comboServici.setVisible(b);
        pretField.setVisible(b);
        pret.setVisible(b);
        durata.setVisible(b);
        durataField.setVisible(b);
        submitButton.setVisible(b);
    }
}
