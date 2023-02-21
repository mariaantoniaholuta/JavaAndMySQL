package com.utcn.Modul1;

import com.utcn.Bonus.ConfigureazaServiciiMedic;
import com.utcn.CommonInterfaces.Homepage;
import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class GestiuneResurseUmane extends JFrame {


    private JPanel resurseUmanePanel;
    private JLabel selecteazaOperatia;
    private JButton cautaAngajatBtn;
    private JButton orarPolicliniciBtn;
    private JButton concediiBtn;
    private JTextField numeTextField;
    private JTextField prenumeTextField;
    private JTextField functieTextField;
    private JButton logOutBtn;
    private JButton backBtn;
    private JButton angajeazaBtn;
    private JButton configureazaGradeButton;
    private JButton configureazaServiciiButton;

    public GestiuneResurseUmane(Connection connection, Angajat angajat) {
        setContentPane(resurseUmanePanel);
        setTitle("Welcome");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        if(!angajat.getFunctie().equals("inspector resurse")) {
            angajeazaBtn.setVisible(false);
        }

        if(!angajat.getFunctie().equals("inspector resurse") && !angajat.getFunctie().equals("expert financiar")) {
            functieTextField.setVisible(false);
            prenumeTextField.setVisible(false);
            numeTextField.setVisible(false);
        }

        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login(connection);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Homepage homepage = new Homepage(connection, angajat);
            }
        });

        cautaAngajatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AngajatiGasiti(connection, angajat, toQuerry(numeTextField.getText(), prenumeTextField.getText(),
                        functieTextField.getText()));
            }
        });

        angajeazaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Angajeaza(connection, angajat);
            }
        });

        concediiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Concedii(connection, angajat);
            }
        });

        orarPolicliniciBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String querryOrar = "SELECT * FROM orar_policlinica";
                new OrarPoliclinici(connection, angajat, querryOrar);
            }
        });

        configureazaGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConfigurareGrade(connection, angajat);
            }
        });

        configureazaServiciiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConfigureazaServiciiMedic(connection, angajat);
            }
        });
    }

    public String toQuerry(String firstName, String lastName, String function) {
        String nume =  "'" + firstName + "%'";
        String prenume = "'" + lastName + "%'";
        String functie = "'" + function + "%'";
        return "SELECT * FROM Angajat where nume LIKE " + nume + " and prenume LIKE "
                + prenume + " and functie LIKE " + functie;
    }
}

