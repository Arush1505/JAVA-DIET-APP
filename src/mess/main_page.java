package mess; 

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_page extends JFrame implements ActionListener {
    private JTextField heightField;
    private JTextField weightField;
    private JButton calculateButton;
    private JButton customButton, submit;
    private JTextField customField; 
    private JLabel customLabel;
    private JRadioButton veg_non;
    String username;
    String password;
    
    public main_page(String username,String password) {
    	this.username = username;
    	this.password = password;
        
        setTitle("Your Info");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout(null); 


        
        JLabel yourInfoLabel = new JLabel("HI  "+username+ "     Enter your Info");
        yourInfoLabel.setBounds(350, 50, 600, 50);
        yourInfoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        yourInfoLabel.setForeground(Color.decode("#3498db"));
        add(yourInfoLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBounds(350,150,480,600);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#3498db"), 2));
        infoPanel.setBackground(Color.decode("#9BD3B4"));
        
        
        JLabel heightLabel = new JLabel("Height (m):");
        heightLabel.setBounds(50, 50, 200, 30);
        heightLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(heightLabel);
        
        
        
        

        heightField = new JTextField();
        heightField.setBounds(50, 90, 300, 30);
        heightField.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(heightField);

        JLabel weightLabel = new JLabel("Weight (kg):");
        weightLabel.setBounds(50, 140, 200, 30);
        weightLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(weightLabel);

        weightField = new JTextField();
        weightField.setBounds(50, 180, 300, 30);
        weightField.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(weightField);

        calculateButton = new JButton("GET PLAN");
        calculateButton.setBounds(100, 230, 200, 30);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 18));
        calculateButton.setBackground(Color.decode("#3498db"));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(this);
        infoPanel.add(calculateButton);

        
        customButton = new JButton("Custom");
        customButton.setBounds(100, 280, 200, 30);
        customButton.setFont(new Font("Arial", Font.BOLD, 18));
        customButton.setBackground(Color.decode("#3498db"));
        customButton.setForeground(Color.WHITE);
        customButton.addActionListener(this);
        infoPanel.add(customButton);
        
        customLabel = new JLabel("Enter Custom Protein Requirement:");
        customLabel.setBounds(50, 330, 300, 30);
        customLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        customLabel.setVisible(false);
        infoPanel.add(customLabel);

        customField = new JTextField();
        customField.setBounds(50, 370, 300, 30);
        customField.setFont(new Font("Arial", Font.PLAIN, 18));
        customField.setVisible(false); 
        infoPanel.add(customField);
        
        submit  = new JButton("GET PLAN");
        submit.setBounds(100,420,200,30);
        submit.setFont(new Font("Arial", Font.BOLD, 20));
        submit.setBackground(Color.decode("#3498db"));
        submit.setForeground(Color.WHITE);
        submit.setVisible(false);
        submit.addActionListener(this);
        infoPanel.add(submit);
        add(infoPanel);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == calculateButton) {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            double bmi = weight / (height * height);

            new Display(bmi,weight,username,password);
        }        
        
        else if (e.getSource() == customButton) {        	
        	customLabel.setVisible(true);
            customField.setVisible(true);
            submit.setVisible(true);
            
            
        }
        
        
        else if(e.getSource() == submit)
        {
            if (!customField.getText().isEmpty()) {
                try {
                    double protein = Double.parseDouble(customField.getText());
                    new Display(protein,username,password);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for the protein value.");
                }
        }
            
            else {
				JOptionPane.showMessageDialog(this, "Please enter some value");
			}
       
        }
        
    }
    
    public static void main(String[] args) {
	}
    
}