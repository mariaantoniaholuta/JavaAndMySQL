package com.utcn.Modulul3;

import com.utcn.models.*;
import com.utcn.CommonInterfaces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class OperationaleAsistent extends JFrame {
    private JPanel activitatiOperationaleMedic;
    private JButton logOutButton;
    private JButton backButton;
    private JButton toateAnalizeleButton;
    private JButton analizaNouaButton;

    public OperationaleAsistent (Connection connection, Angajat angajat) {
        setContentPane(activitatiOperationaleMedic);
        setTitle("Welcome");
        setSize(850, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                 new Homepage(connection, angajat);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });
        toateAnalizeleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AnalizeMedicaleM3(connection, angajat);
            }
        });
        analizaNouaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new NewAnaliza(connection, angajat);
            }
        });
    }
}
