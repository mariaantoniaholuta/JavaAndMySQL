package com.utcn.Modul1;

import com.utcn.CommonInterfaces.Homepage;
import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class GestiuneResurseUmaneRestricted extends JFrame {

    private JPanel mainPane;
    private JPanel gestiunePanel;
    private JLabel selecteazaOperatia;
    private JButton concediiBtn;
    private JButton logOutBtn;
    private JButton backBtn;
    private JButton orarulMeuGenericBtn;
    private JButton orarulMeuSpecificBtn;

    public GestiuneResurseUmaneRestricted(Connection connection, Angajat angajat) {
        setContentPane(gestiunePanel);
        setTitle("Welcome");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Homepage homepage = new Homepage(connection, angajat);
            }
        });

        concediiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ConcediiRestricted(connection, angajat);
            }
        });

        orarulMeuGenericBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String querry = "SELECT * FROM Angajat where nume = '" + angajat.getNume() + " and prenume = '" +
                                 angajat.getPrenume() + " and functie = '" + angajat.getFunctie();
                new OrarGeneric(connection, angajat, angajat.getCnp(), querry);
            }
        });

        orarulMeuSpecificBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String querry = "SELECT * FROM Angajat where nume = '" + angajat.getNume() + " and prenume = '" +
                        angajat.getPrenume() + " and functie = '" + angajat.getFunctie();
                new OrarSpecific(connection, angajat, angajat.getCnp(), querry);
            }
        });

    }
}
