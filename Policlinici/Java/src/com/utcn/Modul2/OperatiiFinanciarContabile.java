package com.utcn.Modul2;

import com.utcn.models.Angajat;
import com.utcn.CommonInterfaces.Homepage;
import com.utcn.CommonInterfaces.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class OperatiiFinanciarContabile extends JFrame {
    private JPanel operatiiFinanciarePanel;
    private JLabel selecteazaOperatia;
    private JButton logOutBtn;
    private JButton backBtn;
    private JButton salariileMeleBtn;
    private JPanel operatiiFinanciare;
    private JButton emiteSalariuButton;
    private JButton actualizare;
    private JButton profit;
    private JButton salariiButton;


    public OperatiiFinanciarContabile(Connection connection, Angajat angajat) {
            setContentPane(operatiiFinanciarePanel);
            setTitle("Welcome");
            setSize(850, 500);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);

            ImageIcon image = new ImageIcon("logo1.png");
            setIconImage(image.getImage());

            if(!angajat.functie.equals("expert financiar")) {
                emiteSalariuButton.setVisible(false);
                actualizare.setVisible(false);
                salariiButton.setVisible(false);
                profit.setVisible(false);
            }

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
                    new Homepage(connection, angajat);
                }
            });

            salariileMeleBtn.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                       dispose();
                       new SalariileMele(connection, angajat);
                  }
            });

            emiteSalariuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new EmitereSalariu(connection, angajat);
                }
            });

            actualizare.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new SalariuActualizare(connection, angajat);
                }
            });

            salariiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new Salarii(connection, angajat);
                }
            });

            profit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new Profit(connection, angajat);
                }
            });
        }
}
