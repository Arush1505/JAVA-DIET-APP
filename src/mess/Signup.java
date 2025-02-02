package mess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class Signup extends JFrame implements ActionListener {

    public JLabel nameJLabel, usernameJLabel, hostelLabel, passwordLabel, ActivityLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton submitButton;
    JComboBox<String> hostel, Activity;

    public Signup() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        nameJLabel = new JLabel("SIGN UP");
        nameJLabel.setBounds(630, 70, 200, 100);
        nameJLabel.setFont(new Font("Arial", Font.BOLD, 30));
        nameJLabel.setForeground(Color.decode("#3498db"));
        add(nameJLabel);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createLineBorder(Color.cyan, 5));
        form.setBackground(Color.LIGHT_GRAY); 
        form.setBounds(500, 250, 500, 250);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.cyan, 5),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) 
        ));

        usernameJLabel = new JLabel("Username: ");
        usernameField = new JTextField();

        passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField();

        hostelLabel = new JLabel("Select Hostel");
        String[] hostel_name = {"Paari", "Kaari", "OORI", "Adhiyaman", "Nelson", "Sannasi - A", "Sannasi - C", "Manoranjithm", "Avaiyar"};
        hostel = new JComboBox<>(hostel_name);

        ActivityLabel = new JLabel("Select Activity");
        String[] Activity_name = {"REGULAR GUY", "GYM RAT", "LAZY GUY"};
        Activity = new JComboBox<>(Activity_name);

        form.add(usernameJLabel);
        form.add(usernameField);
        form.add(passwordLabel);
        form.add(passwordField);
        form.add(hostelLabel);
        form.add(hostel);
        form.add(ActivityLabel);
        form.add(Activity);

        // Create submit button and set background color
        submitButton = new JButton("Submit");
        submitButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        submitButton.setBackground(Color.decode("#3498db")); // Set background color of the button
        submitButton.setForeground(Color.WHITE); // Set text color of the button
        submitButton.setBounds(650, 600, 100, 50);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(this);
        add(submitButton);

        add(form, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
        	
        	if(usernameField.getText().equals("") || passwordField.getText().equals(""))
        	{
        		JOptionPane.showMessageDialog(this, "PLEASE FILL ALL THE DETAILS");
        		return;
        	}
        	
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String activity = (String) Activity.getSelectedItem();
            String hostel_name = (String) hostel.getSelectedItem();
            
            try {
                Conn c = new Conn();
            
                String q= "select * from info where user_name=? and pass_word=?";
                PreparedStatement pp1 = c.co.prepareStatement(q);
                pp1.setString(1, username);
                pp1.setString(2, password);
                
                ResultSet rs= pp1.executeQuery();
                if(rs.next())
                {
                	JOptionPane.showMessageDialog(this, "Naughty :)  Same username and password exist");
                	
                }
                
                
                else {
					
                	String query = "insert into info VALUES(?,?,?,?)";
                	PreparedStatement pp = c.co.prepareStatement(query);
                	pp.setString(1, username);
                	pp.setString(2, password);
                	pp.setString(3, hostel_name);
                	pp.setString(4, activity);
                	
                	int rows = pp.executeUpdate();
                	if (rows > 0) {
                		JOptionPane.showMessageDialog(this, "Sign Up successful");
                		new main_page(username, password);
                		dispose();
                	} else {
                		JOptionPane.showMessageDialog(this, "Failed to insert data.");
                	}
                	
				}
                
            }
            
        
            
            
            
            
            catch (Exception e1) {
                JOptionPane.showMessageDialog(this, e1);
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//       new Signup()
;    }
}
