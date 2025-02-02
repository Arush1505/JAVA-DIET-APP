package mess; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, sign_upButton;
    private BufferedImage backgroundImage;

    public login() {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Arush\\eclipse-workspace\\diet_app\\src\\img2.jpg")); 
            System.out.println("Image loaded successfully!");
        } catch (Exception e) {
            System.out.println("Image not found!");
            e.printStackTrace();
        }

        setTitle("Diet Day");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout(new BorderLayout());
//        setResizable(false);
        setUndecorated(true);
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null); 
        add(backgroundPanel, BorderLayout.CENTER); 

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(575, 340, 450, 300);
        loginPanel.setBackground(new Color(211, 211, 211, 150)); 
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#3498db"), 2));
        backgroundPanel.add(loginPanel);

        JLabel dietDayLabel = new JLabel("DIET DAY");
        dietDayLabel.setBounds(550, 80, 500, 100);
        dietDayLabel.setFont(new Font("Verdana", Font.BOLD, 80));
        dietDayLabel.setForeground(Color.decode("#3BB19A"));
        backgroundPanel.add(dietDayLabel);


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 200, 30);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 90, 300, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 140, 200, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 180, 300, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(50, 250, 100, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(Color.decode("#3498db"));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        sign_upButton = new JButton("Sign UP");
        sign_upButton.setBounds(230, 250, 150, 30);
        sign_upButton.setFont(new Font("Arial", Font.BOLD, 18));
        sign_upButton.setBackground(Color.decode("#3498db"));
        sign_upButton.setForeground(Color.WHITE);
        sign_upButton.addActionListener(this);
        loginPanel.add(sign_upButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            Conn c = new Conn();
            try {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                String query = "SELECT user_name, pass_word FROM info WHERE user_name = ? AND pass_word = ?";
                PreparedStatement pp = c.co.prepareStatement(query);
                pp.setString(1, username);
                pp.setString(2, password);

                ResultSet rs = pp.executeQuery();

                if (rs.next()) {
                    new main_page(username, password);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No user found. Please check your username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                pp.close();
                rs.close();
            } 
            catch (Exception e2) {
                JOptionPane.showMessageDialog(this, e2);
            }
        }
        
        
        else if (e.getSource() == sign_upButton) {
            new Signup();
            dispose();
        }
    }

    public static void main(String[] args) {
        new login(); 
    }
}










