package com.utcn.CommonInterfaces;

import com.utcn.Modul2.OperatiiFinanciarContabile;
import com.utcn.Modul1.GestiuneResurseUmane;
import com.utcn.Modul1.GestiuneResurseUmaneRestricted;
import com.utcn.models.Angajat;
import com.utcn.Modulul3.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Homepage extends JFrame {
    private JPanel homepagePanel;
    private JTabbedPane mainPane;
    private JPanel home;
    private JButton gestiune_resurse_umane;
    private JButton operatii_financiar_contabile;
    private JButton gestiune_activitati_operationale;
    private JButton logOutBtn;
    private JLabel error;
    private JScrollPane tablePanel;
    private JTable table;
    private JPanel grade;
    private JTable table1;
    private JTable tableServicii;
    private JPanel serviciiMedic;


    public Homepage(Connection connection, Angajat angajat) {
        setContentPane(homepagePanel);
        setTitle("Welcome");
        setSize(700, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        grade.setVisible(false);

        if(!angajat.getFunctie().equals("medic")) {
           mainPane.remove(grade);
           mainPane.remove(serviciiMedic);
        }

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Datele Mele");

        dtm.addRow(new Object[]{"CNP: " + angajat.getCnp()});
     //   dtm.addRow(new Object[]{"Utilizator: " + angajat.getUtilizator()});
     //   dtm.addRow(new Object[]{"Parola: " + angajat.getParola()});
        dtm.addRow(new Object[]{"Adresa: " + angajat.getAdresa()});
        dtm.addRow(new Object[]{"Email: " + angajat.getEmail()});
        dtm.addRow(new Object[]{"Numar de ore: " + angajat.getNumar_ore()});
        dtm.addRow(new Object[]{"Telefon: " + angajat.getTelefon()});
        dtm.addRow(new Object[]{"IBAN: " + angajat.getIban()});
        dtm.addRow(new Object[]{"Data angajare: " + angajat.getData_angajare()});
        dtm.addRow(new Object[]{"Nume: " + angajat.getNume()});
        dtm.addRow(new Object[]{"Prenume: " + angajat.getPrenume()});
        dtm.addRow(new Object[]{"Functie: " + angajat.getFunctie()});
        dtm.addRow(new Object[]{"Salariu Negociat: " + angajat.getSalariu()});

        if(angajat.getFunctie().equals("medic")) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT cod_parafa, titlu_stiintific, post_didactic FROM " +
                        "medic WHERE cnp = ?");
                statement.setString(1, angajat.getCnp());
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                dtm.addRow(new Object[]{"Cod parafa: " + String.valueOf(resultSet.getString("cod_parafa"))});
                dtm.addRow(new Object[]{"Post didactic: " + resultSet.getString("post_didactic")});
                dtm.addRow(new Object[]{"Titlu stiintific: " + resultSet.getString("titlu_stiintific")});

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(angajat.getFunctie().equals("asistent medical")) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT grad, tip FROM " +
                        "asistent_medical WHERE cnp = ?");
                statement.setString(1, angajat.getCnp());
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                dtm.addRow(new Object[]{"Tip: " + String.valueOf(resultSet.getString("tip"))});
                dtm.addRow(new Object[]{"Grad: " + resultSet.getString("grad")});

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        table.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 22));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(166, 201, 181));
        table.setModel(dtm);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
        rendar.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(rendar);

        setVisible(true);

        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        gestiune_resurse_umane.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!angajat.getFunctie().equals("inspector resurse") && !angajat.getFunctie().equals("expert financiar")) {
                     dispose();
                     GestiuneResurseUmaneRestricted gestiuneResurseUmaneRestricted = new GestiuneResurseUmaneRestricted(connection, angajat);
                }
                else {
                    dispose();
                    new GestiuneResurseUmane(connection, angajat);
                }
            }
        });

        operatii_financiar_contabile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OperatiiFinanciarContabile(connection, angajat);
            }
        });

        gestiune_activitati_operationale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(angajat.getFunctie().equals("inspector resurse") || angajat.getFunctie().equals("expert financiar")) {
                    error.setText("    Nu aveti drepturile necesare pentru acest modul");
                }
                else {
                    dispose();
                    switch (angajat.getFunctie()){
                        case "receptioner" -> {
                            new OperationaleReceptioner(connection, angajat);
                        }
                        case "medic" ->{
                            new OperationaleMedic(connection, angajat);}
                        case "asistent medical" -> {
                            new OperationaleAsistent(connection, angajat); }
                    }
                }
            }
        });
        gestiune_activitati_operationale.requestFocus();

        if(angajat.getFunctie().equals("medic")) {
            grade.setVisible(true);
            DefaultTableModel dtm1 = new DefaultTableModel();
            dtm1.addColumn("Grad");
            dtm1.addColumn("Specialitate");
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT denumire_specialitate, grad FROM " +
                "grad WHERE cnp = ?");
                statement.setString(1, angajat.getCnp());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    dtm1.addRow(new String[]{resultSet.getString("denumire_specialitate"), resultSet.getString("grad")});
                }

                table1.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 22));
                table1.getTableHeader().setOpaque(false);
                table1.getTableHeader().setBackground(new Color(166, 201, 181));
                table1.setModel(dtm1);
                table1.setRowHeight(30);
                table1.setFillsViewportHeight(true);
                table1.setEnabled(false);
                table1.getColumnModel().getColumn(0).setCellRenderer(rendar);
                table1.getColumnModel().getColumn(1).setCellRenderer(rendar);

                String[] column = {"Id serviciu", "Denumire serviciu", "Cost", "Durata"};
                DefaultTableModel dtm2 = new DefaultTableModel();
                for (int i = 0; i < 4; i++)
                    dtm2.addColumn(column[i]);

                try {
                    PreparedStatement statement1 = connection.prepareStatement("SELECT sc.id_serviciu, sm.denumire_serviciu, sc.cost, sc.durata " +
                            "FROM servicii_custom_medic AS sc " +
                            "JOIN servicii_medicale AS sm " +
                            "ON sc.id_serviciu = sm.id_serviciu WHERE cnp_medic = ?");
                    statement1.setString(1, angajat.getCnp());
                    ResultSet servicii = statement1.executeQuery();

                    Object[] rowData = new Object[4];
                    for (Object i: rowData)
                        i = new Object();

                    while (servicii.next()) {
                        rowData[0] = servicii.getString("id_serviciu");
                        rowData[1] = servicii.getString("denumire_serviciu");
                        rowData[2] = servicii.getString("cost");
                        rowData[3] = servicii.getString("durata");
                        dtm2.addRow(rowData);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                tableServicii.getTableHeader().setFont(new Font("Rockwell", Font.BOLD, 22));
                tableServicii.getTableHeader().setOpaque(false);
                tableServicii.getTableHeader().setBackground(new Color(166, 201, 181));
                tableServicii.setModel(dtm2);
                tableServicii.setRowHeight(30);
                tableServicii.setFillsViewportHeight(true);
                tableServicii.setEnabled(false);
                tableServicii.getColumnModel().getColumn(0).setCellRenderer(rendar);
                tableServicii.getColumnModel().getColumn(1).setCellRenderer(rendar);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}