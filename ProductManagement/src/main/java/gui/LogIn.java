package gui;

import datalayer.FileWriters;
import datalayer.Serializator;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogIn extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JLabel logLbl;
    private JPanel logInPanel;
    private JButton registerButton;

    private static List<User> users = new ArrayList<>();

    public LogIn() {
        setContentPane(logInPanel);
        setTitle("LogIn");
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(400, 400);
        int x = (int)((dimensions.getWidth()- this.getWidth())/2);
        int y = (int)((dimensions.getHeight()- this.getHeight())/2);
        this.setLocation(x,y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);



        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username =  textField.getText();
                String password = String.valueOf(passwordField.getPassword());

               // List<User> users = new ArrayList<>();
                boolean check = false;

                try {
                   users = Serializator.readForUsers();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                for(User u: users) {

                    if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                        check = true;
                      //  System.out.println("cl");
                        if(u.getType().equals("administrator")) {
                            dispose();
                            new AdministratorInterface();
                        }
                        if(u.getType().equals("client")) {
                            dispose();
                            new ClientInterface();
                        }
                        if(u.getType().equals("employee")) {
                            dispose();
                            new EmployeeInterface();
                        }
                    }
                }

                if(!check) {
                    JOptionPane.showMessageDialog(getParent(), "Incorrect password or username");
                }
/*
                User user1 = new User("admin", "password", "administrator" );
                Serializator.serialize(user1);

                User savedUser = Serializator.deserialize();

                if (savedUser != null) {
                    savedUser.printInfo();
                }

 */
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User newUser = new User(textField.getText(), String.valueOf(passwordField.getPassword()), "client");
                boolean check = false;
                try {
                    users = Serializator.readForUsers();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                for(User u: users) {
                    if(u.getUsername().equals(newUser.getUsername())) {
                        JOptionPane.showMessageDialog(getParent(), "Username already exists!");
                        check = true;
                        break;
                    }
                }
                if(!check) {
                    FileWriters.writeNewUser(newUser);
                    JOptionPane.showMessageDialog(getParent(), "new user added as client!");
                }
            }
        });
    }


}
