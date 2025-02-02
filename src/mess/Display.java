package mess;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Arush\\eclipse-workspace\\diet_app\\src\\table.png")); 
            System.out.println("Display sucessfll");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}



public class Display extends JFrame {
	
	
    private JLabel bmiLabel;
    private JLabel proteinRequirementLabel;
    JRadioButton vegOption,nonVegOption;
    private JComboBox<String> dayComboBox;
    private JButton getPlanButton;
    private JPanel mealPanel,dietPanel;
    private Conn conn = new Conn(); 
    String activityLevel = ""; 
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    String username;
    String password;
    double proteinRequirement = 0.0;
    
    
    
    public Display(double bmi, double weight,String username,String password) {

    	
    	this.username = username;
    	this.password = password;
    	setTitle("Protein Requirement Display");
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new BorderLayout());
    	setUndecorated(true);
    	BackgroundPanel backgroundPanel = new BackgroundPanel(); 
        backgroundPanel.setLayout(new BorderLayout());
    	
    	try 
    	{
    		String queryString = "SELECT activity FROM info WHERE user_name = ?";
    		
    		PreparedStatement pp = conn.co.prepareStatement(queryString);
    		pp.setString(1, username);
    		ResultSet rs = pp.executeQuery();
    		if (rs.next()) {
    			activityLevel = rs.getString("activity");
    		}
    		
    	} 
    	
    	
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    	if (activityLevel.equalsIgnoreCase("GYM RAT")) {
    		proteinRequirement = weight * 1.6; // 1.6 to 2.2 grams/kg
    	} else if (activityLevel.equalsIgnoreCase("REGULAR GUY")) {
    		proteinRequirement = weight * 1.3; // Average between 1.0 and 1.5 grams/kg
    	} else if (activityLevel.equalsIgnoreCase("LAZY GUY")) {
    		proteinRequirement = weight * 0.8; // Average between 0.8 and 1.0 grams/kg
    	} else {
    		proteinRequirement = weight * 1.3; // Default to lazy if unknown
    	}
    	
    	
    	
        bmiLabel = new JLabel("Because you are a " +  activityLevel+"                                        "+"Protein Needed : "+ String.format("%.2f", proteinRequirement), JLabel.CENTER);
        bmiLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        bmiLabel.setForeground(Color.BLACK);
        bmiLabel.setOpaque(false);
        
        
        vegOption = new JRadioButton("Veg");
        nonVegOption = new JRadioButton("Non-Veg");
        vegOption.setFont(new Font("Serif", Font.PLAIN, 16));
        vegOption.setOpaque(false);
        nonVegOption.setOpaque(false);
        nonVegOption.setFont(new Font("Serif", Font.PLAIN, 16));

        
        ButtonGroup dietGroup = new ButtonGroup();
        dietGroup.add(vegOption);
        dietGroup.add(nonVegOption);
        
        dietPanel = new JPanel();
        dietPanel.add(vegOption);
        dietPanel.setOpaque(false); 

        dietPanel.add(nonVegOption);
        
        
        dayComboBox = new JComboBox<>(days);
        dayComboBox.setFont(new Font("Serif", Font.PLAIN, 16));
        
        getPlanButton = new JButton("GET PLAN");
        getPlanButton.setFont(new Font("Serif", Font.ITALIC, 16)); 

        
        
        
        
        getPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) dayComboBox.getSelectedItem();
                String veg_non ="";
                if(vegOption.isSelected()) {veg_non="veg";}
                else if (nonVegOption.isSelected()) {
					veg_non =  "non-veg";
				}
                displayMealPlan(selectedDay, proteinRequirement,veg_non);
            }
        });
        
        mealPanel = new JPanel();
        mealPanel.setLayout(new GridLayout(3, 1,10,10));
        mealPanel.setOpaque(false);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(dayComboBox);
        buttonPanel.add(getPlanButton);
        buttonPanel.setOpaque(false);
        
        
        backgroundPanel.add(bmiLabel, BorderLayout.NORTH);
        backgroundPanel.add(dietPanel,BorderLayout.EAST);
        backgroundPanel.add(mealPanel, BorderLayout.SOUTH); 
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER); 

        add(backgroundPanel);
        setVisible(true);
    }

    
    
    
    public Display(double protein,String username,String password)
    {
    	this.username = username;
    	this.password = password;
    	setExtendedState(JFrame.MAXIMIZED_BOTH); 
    	setTitle("Custom Protein Requirement Display");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new BorderLayout()); 
    	
    	BackgroundPanel backgroundPanel = new BackgroundPanel(); 
        backgroundPanel.setLayout(new BorderLayout());

        vegOption = new JRadioButton("Veg");
        nonVegOption = new JRadioButton("Non-Veg");
        vegOption.setFont(new Font("Serif", Font.PLAIN, 16));
        vegOption.setOpaque(false);
        nonVegOption.setOpaque(false);
        nonVegOption.setFont(new Font("Serif", Font.PLAIN, 16));

        ButtonGroup dietGroup = new ButtonGroup();
        dietGroup.add(vegOption);
        dietGroup.add(nonVegOption);

        dietPanel = new JPanel();
        dietPanel.add(vegOption);
        dietPanel.setOpaque(false); 

        dietPanel.add(nonVegOption);

    	dayComboBox = new JComboBox<>(days);
    	dayComboBox.setFont(new Font("Serif", Font.ITALIC, 16));
    	
    	try 
    	{
    		String queryString = "SELECT activity FROM info WHERE user_name = ?";
    		
    		PreparedStatement pp = conn.co.prepareStatement(queryString);
    		pp.setString(1, username);
    		ResultSet rs = pp.executeQuery();
    		if (rs.next()) {
    			activityLevel = rs.getString("activity");
    		}
    		
    	} 
    	
    	
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	
        bmiLabel = new JLabel("Remember You Are " +  activityLevel + "                                        "+"Protein needed : "+ protein, JLabel.CENTER);
        
        bmiLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        bmiLabel.setForeground(Color.black);

        bmiLabel.setOpaque(false);
        
    	getPlanButton = new JButton("GET PLAN");
    	getPlanButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			String selectedDay = (String) dayComboBox.getSelectedItem();
    			
    			String veg_non ="";
                if(vegOption.isSelected()) {veg_non="veg";}
                else if (nonVegOption.isSelected()) {
					veg_non =  "non-veg";
				}
    			displayMealPlan(selectedDay, protein,veg_non);
    		}
    	});
    	
    	 mealPanel = new JPanel();
         mealPanel.setLayout(new GridLayout(3, 1,10,10));
         mealPanel.setOpaque(true);
         
         JPanel buttonPanel = new JPanel();
         buttonPanel.add(dayComboBox);
         buttonPanel.add(getPlanButton);
         buttonPanel.setOpaque(false);
         
         backgroundPanel.add(bmiLabel, BorderLayout.NORTH);
         backgroundPanel.add(dietPanel,BorderLayout.EAST);
         backgroundPanel.add(mealPanel, BorderLayout.SOUTH); 
         backgroundPanel.add(buttonPanel, BorderLayout.CENTER); 

         add(backgroundPanel);
         setVisible(true);
        
    	
    }
    
    
    
    
//    private double calculateProteinRequirement(double bmi, double weight) {
//        double proteinPerKg;
//        if (bmi < 18.5) {
//            proteinPerKg = 1.2; 
//        } else if (bmi < 24.9) {
//            proteinPerKg = 1.0; 
//        } else if (bmi < 29.9) {
//            proteinPerKg = 0.8; 
//        } else {
//            proteinPerKg = 0.7; 
//        }
//        return proteinPerKg * weight; 
//    }
//
//

    
    private StyledDocument getMealPlan(String day, String mealType, double proteinRequirement,String veg_non) {
        DefaultStyledDocument mealPlanDoc = new DefaultStyledDocument();
        double totalProtein = 0;
        String[] names = new String[10]; 
        double[] protein = new double[10];
        int[] servings = new int[10]; 
        int count = 0;
        int excessServingIndex = -1;
        double totalExcess = 0.0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Conn conn = new Conn();
            if(veg_non.equals("veg")) {
            stmt = conn.co.prepareStatement("SELECT food_name, protein FROM meals WHERE day = ? and meal_type = ? and veg_non=? ");
            stmt.setString(1, day);
            stmt.setString(2, mealType);
            stmt.setString(3, veg_non);
            }
            
            else if (veg_non.equals("non-veg")){
				
            	stmt = conn.co.prepareStatement("SELECT food_name, protein FROM meals WHERE day = ? and meal_type = ? ");
            	stmt.setString(1, day);
            	stmt.setString(2, mealType);
			}
            
            else {
            	JOptionPane.showMessageDialog(this, "Are you veg/non-veg");
            }
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (totalProtein >= proteinRequirement) {
                    break; 
                }

                String name = rs.getString("food_name");
                double proteinValue = rs.getDouble("protein");
                names[count] = name;
                protein[count] = proteinValue;
                servings[count] = 1; 
                totalProtein += proteinValue; 
                count++;
            }

            
            if (totalProtein > proteinRequirement) {
            	excessServingIndex = count - 1; 
            	totalExcess = totalProtein - proteinRequirement;
            }

            while(totalProtein < proteinRequirement && count > 0) {
                for (int i = 0; i < count; i++) {
                    totalProtein += protein[i]; 
                    servings[i]++; 
                    if (totalProtein >= proteinRequirement) {
                        excessServingIndex = i;
                        totalExcess = totalProtein - proteinRequirement;
                        break; 
                    }
                }
            }


            for (int i = 0; i < count; i++) {
                StringBuilder mealEntry = new StringBuilder();
                mealEntry.append(names[i]).append(": ").append(servings[i]).append(" serving(s)");

                SimpleAttributeSet mealAttrs = new SimpleAttributeSet();
                StyleConstants.setBold(mealAttrs, true); 
                StyleConstants.setFontFamily(mealAttrs, "SansSerif"); 
                
                
                mealPlanDoc.insertString(mealPlanDoc.getLength(), mealEntry.toString()+"\n", mealAttrs);
                
                

                if (totalExcess != 0 && i == excessServingIndex) {
                    SimpleAttributeSet attrs = new SimpleAttributeSet();
                    StyleConstants.setForeground(attrs, Color.RED);
                    StyleConstants.setItalic(attrs, true);
                    StyleConstants.setFontFamily(attrs, "SansSerif"); 
                    mealPlanDoc.insertString(mealPlanDoc.getLength(), " (Excess: " + (int) totalExcess + "g)", attrs); 
                }
                
                mealPlanDoc.insertString(mealPlanDoc.getLength(), "\n", null);
            }

            mealPlanDoc.insertString(mealPlanDoc.getLength(), "Total Protein: " + totalProtein + " grams", null);

        } catch (SQLException | BadLocationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.co.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mealPlanDoc;
    }

    
    
    
    
    private void displayMealPlan(String day, double proteinRequirement,String dharam) {
        mealPanel.removeAll();

        double breakfastProteinRequirement = proteinRequirement * 0.3; // 30% for breakfast
        double lunchProteinRequirement = proteinRequirement * 0.4; // 40% for lunch
        double dinnerProteinRequirement = proteinRequirement * 0.3; // 30% for dinner

        JPanel breakfastPanel = new JPanel(new BorderLayout());
        JTextPane breakfastTextPane = new JTextPane(); 
        breakfastTextPane.setStyledDocument(getMealPlan(day, "Breakfast", breakfastProteinRequirement, dharam));
        breakfastTextPane.setEditable(false);
        breakfastTextPane.setOpaque(true); 
        breakfastTextPane.setBackground(new Color(239, 207, 69, 0));
        breakfastPanel.setBackground(Color.decode("#EFCF45"));
        JScrollPane breakfastScrollPane = new JScrollPane(breakfastTextPane);
        breakfastScrollPane.getViewport().setOpaque(false); 
        breakfastPanel.add(new JLabel("Breakfast Plan:"), BorderLayout.NORTH);
        breakfastPanel.add(breakfastScrollPane, BorderLayout.CENTER);
        breakfastPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        breakfastPanel.setOpaque(true); 
        
        JPanel lunchPanel = new JPanel(new BorderLayout());
        JTextPane lunchJTextPane = new JTextPane(); 
        lunchJTextPane.setStyledDocument(getMealPlan(day, "Lunch", lunchProteinRequirement, dharam));
        lunchJTextPane.setEditable(false);
        lunchJTextPane.setOpaque(false); 

        lunchPanel.setBackground(Color.decode("#33CBB9"));

        JScrollPane lunchJScrollPane = new JScrollPane(lunchJTextPane);
        lunchJScrollPane .getViewport().setOpaque(false); 

        lunchPanel.add(new JLabel("Lunch Plan:"), BorderLayout.NORTH);
        lunchPanel.add(lunchJScrollPane, BorderLayout.CENTER);
        lunchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lunchPanel.setOpaque(true); 

        JPanel dinnerPanel = new JPanel(new BorderLayout());
        JTextPane dinnerTextPane = new JTextPane(); 
        dinnerTextPane.setStyledDocument(getMealPlan(day, "Dinner", dinnerProteinRequirement, dharam));
        dinnerTextPane.setEditable(false);
        dinnerTextPane.setOpaque(false); //

        dinnerPanel.setBackground(Color.decode("#7967E2"));
        
        JScrollPane dinnerScrollPane = new JScrollPane(dinnerTextPane);
        dinnerScrollPane.getViewport().setOpaque(false); 

        dinnerPanel.add(new JLabel("Dinner Plan:"), BorderLayout.NORTH);
        dinnerPanel.add(dinnerScrollPane, BorderLayout.CENTER);
        dinnerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dinnerPanel.setOpaque(true); 
        
        mealPanel.add(breakfastPanel);
        mealPanel.add(lunchPanel);
        mealPanel.add(dinnerPanel);

        mealPanel.revalidate();
        mealPanel.repaint();
    }
    


    
    
}
