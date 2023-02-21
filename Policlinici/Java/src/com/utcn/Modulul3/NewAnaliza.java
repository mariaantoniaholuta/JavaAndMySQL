package com.utcn.Modulul3;

import com.utcn.models.*;
import com.utcn.CommonInterfaces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewAnaliza extends JFrame{
    private JPanel NewAnaliza;
    private JButton logOutButton;
    private JButton backButton;
    private JTextField textField1;
    private JButton analizaNouaButton;
    private JTextField textField2;

    public NewAnaliza(Connection connection, Angajat angajat) {

        setContentPane(NewAnaliza);
        setTitle("Result");
        setSize(1000, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AnalizeMedicaleM3(connection, angajat);
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(connection);
            }
        });
        analizaNouaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql="insert into analize_medicale (id_programare, analiza_efectuata, validare) values (?, ?, 0)";
                try {
                    PreparedStatement stm= connection.prepareStatement(sql);
                    stm.setInt(1, Integer.valueOf(textField2.getText()));
                    stm.setString(2, textField1.getText());
                    stm.executeUpdate();
                    JOptionPane.showMessageDialog(getParent(), "S-a facut inserarea");
                    dispose();
                    new OperationaleAsistent(connection, angajat);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(getParent(), "ERR");
                }
            }
        });
    }
}
