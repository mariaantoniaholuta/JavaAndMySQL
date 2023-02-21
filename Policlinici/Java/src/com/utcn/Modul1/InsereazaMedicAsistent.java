package com.utcn.Modul1;

import com.utcn.CommonInterfaces.Login;
import com.utcn.models.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsereazaMedicAsistent extends JFrame{
    private JButton backButton;
    private JButton logOutButton;
    private JTextField postField;
    private JTextField parafaField;
    private JButton submitButton;
    private JLabel post;
    private JLabel parafa;
    private JLabel grad;
    private JLabel tip;
    private JComboBox comboTip;
    private JComboBox comboGrad;
    private JPanel mainPane;
    private JComboBox postCombo;
    private JComboBox titluBox;
    private JLabel titlu;

    InsereazaMedicAsistent(Connection connection, String cnp, String functie, Angajat angajat) {
        setContentPane(mainPane);
        setTitle("Medici");
        setSize(650, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setVisibleFieldsAsistent(false);
        setVisibleFieldsMedic(false);

        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        comboTip.setRenderer(listRenderer);
        comboGrad.setRenderer(listRenderer);

        if(functie.equals("medic")) {
            setVisibleFieldsMedic(true);
        }
        else
            setVisibleFieldsAsistent(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GestiuneResurseUmane(connection, angajat);
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
                if(functie.equals("medic"))
                    insertIntoMedic(connection, cnp);
                else
                    insertIntoAsistent(connection, cnp);
            }
        });
    }

    public void insertIntoMedic(Connection connection, String cnp) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Medic(cnp, post_didactic, cod_parafa, titlu_stiintific) VALUES " +
                    " (?, ?, ?, ?)");
            statement.setString(1,cnp);
            statement.setString(2, (String) postCombo.getSelectedItem());
            statement.setString(3,parafaField.getText());
            statement.setString(4, (String) titluBox.getSelectedItem());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(getParent(), "Date introduse cu succes");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(getParent(), "Date nu au putut fi introduse!");
            e.printStackTrace();
        }
    }

    public void insertIntoAsistent(Connection connection, String cnp) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO asistent_medical(cnp, grad, tip) VALUES " +
                    "(?, ?, ?)");
            statement.setString(1,cnp);
            statement.setString(2, (String) comboGrad.getSelectedItem());
            statement.setString(3, (String) comboTip.getSelectedItem());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(getParent(), "Date introduse cu succes");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(getParent(), "Date nu au putut fi introduse!");
            e.printStackTrace();
        }

    }

    public void setVisibleFieldsAsistent(boolean b) {
        grad.setVisible(b);
        tip.setVisible(b);
        comboTip.setVisible(b);
        comboGrad.setVisible(b);
    }

    public void setVisibleFieldsMedic(boolean b) {
        post.setVisible(b);
        postCombo.setVisible(b);
        parafaField.setVisible(b);
        parafa.setVisible(b);
        titlu.setVisible(b);
        titluBox.setVisible(b);
    }

}
