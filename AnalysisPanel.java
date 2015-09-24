/* FILENAME: AnalysisPanel.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a panel with grid layout that contains a drop down menu and a result display
 * section. It allows the filtering of the patient files with regards to a given characteristic.
 * 
 * */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

/** *****************************************************************************
  * A AnalysisPanel class displays a drop down menu and different panels in accordance 
  * to what option has been selected from the drop down menu. Used to conduct searches and
  * filter patients.
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class AnalysisPanel extends JPanel {
  
  private HospitalRecords HR; 
  private JComboBox dropDown;
  private JLabel resultLabel, errorLabel;
  private JPanel resultPanel;
  private JTextField ageL, ageH, disorderName;
  private Color lighterBlue, darkerBlue, darkestBlue;
  
  
  /** *****************************************************************************
    * Constructor
    * 
    * Takes in a hospital as a parameter and creates a drop down menu and displays the
    * results of searches conducted in a scroll panel
    * 
    * @params hospital
    **************************************************************************** **/
  public AnalysisPanel (HospitalRecords hospital) { 
    
    HR = hospital;
    
    //sets colors to be used in the frame
    lighterBlue = new Color(225, 240, 250);
    darkerBlue = new Color(86, 126, 186);
    darkestBlue = new Color(11, 82, 158);
    
    this.setBackground(lighterBlue);
    
    setLayout (new GridLayout (2,1)); 
    
    //sets up the labels to be used in the panel and changes color
    JLabel l1 = new JLabel ("You can use this section to analyze information on the patients and conduct searches.");
    JLabel l2 = new JLabel ("Please select one of the options from the drop down menu.");
    l1.setForeground(darkestBlue);
    l2.setForeground(darkestBlue);
    
    JPanel explanationPanel = new JPanel();
    explanationPanel.setBackground(lighterBlue);
    
    //adds the labels to the panel
    explanationPanel.add(Box.createRigidArea (new Dimension (0, 50)));
    explanationPanel.setLayout(new BoxLayout (explanationPanel, BoxLayout.Y_AXIS)); 
    explanationPanel.add(l1);
    explanationPanel.add(Box.createRigidArea (new Dimension (0, 10)));
    explanationPanel.add(l2);     
    add(explanationPanel);
    
    //contains the menu options to be shown to the user
    String[] menuOptions= {"Please select form of analysis", "Patients with disorder X", "Patients of age (..,..)", "Female patients", "Male patients"};
    
    //initializes the menu, adds to panel and adds action listener
    dropDown = new JComboBox (menuOptions);
    explanationPanel.add(dropDown);
    dropDown.addActionListener(new AnalysisListener());
    
    //creates and adds to frame the panel that will display results
    resultPanel = new JPanel();
    resultPanel.setBackground(lighterBlue);
    add(resultPanel);
    
  }
  
   /** *****************************************************************************
    * A AnalysisListener class detects which menu option was selected, and shows the
    * appropriate panel depending on the selection.
    * 
    * @author Meltem Ozcan
    *************************************************************************** **/
  private class AnalysisListener implements ActionListener {
    
    /** *****************************************************************************
      * When an option is selected from the drop down menu determines what was selected
      * and shows the appropriate panel.
      * 
      * @params event
      **************************************************************************** **/
    public void actionPerformed (ActionEvent event) {
      
      //clears the result panel from any previous results
      resultPanel.removeAll();
      resultPanel.revalidate(); 
      resultPanel.repaint(); 
      
      int selected = dropDown.getSelectedIndex();
      
      if (HR.getNumPatients() > 0) {//only perform analysis if there is at least one patient in the hospital
        
        if (selected == 0) {
          System.out.println("Please make a selection");
          
        } else if (selected == 1) {
          System.out.println("Search for patients with given disorder");
          JLabel label  = new JLabel ("Enter a disorder you would like the patient list on");
          label.setForeground(darkerBlue);
          disorderName = new JTextField(4);
          JButton search = new JButton("Search");
          
          search.addActionListener(new DisorderListener());
          
          //add the label, text field and button into the panel
          resultPanel.add(label);
          resultPanel.add(disorderName);
          resultPanel.add(search);
          
        } else if (selected == 2) {
          System.out.println("Search for patients of age (..,..)");
          
          JPanel agePanel = new JPanel();
          agePanel.setBackground(lighterBlue);
          agePanel.setLayout(new BoxLayout (agePanel,BoxLayout.Y_AXIS));
          
          JLabel label  = new JLabel ("Enter the age boundaries you would like to search for.");
          label.setForeground(darkestBlue);
          errorLabel  = new JLabel ("              ");
          JLabel lower  = new JLabel ("Lower bound:");
          lower.setForeground(darkerBlue);
          JLabel higher  = new JLabel ("Higher bound:");
          higher.setForeground(darkerBlue);
          JLabel spacing  = new JLabel ("            ");
          
          ageL = new JTextField(4);
          ageH = new JTextField(4);
          
          JButton search = new JButton("Search");
          
          search.addActionListener(new AgeListener());
          
          //add the labels and text fields into the panel
          agePanel.add(label);
          agePanel.add(spacing);
          agePanel.add(errorLabel);
          agePanel.add(spacing);
          agePanel.add(lower);
          agePanel.add(ageL);
          agePanel.add(higher);
          agePanel.add(ageH);
          agePanel.add(search);
          
          resultPanel.add(agePanel);
          
        } else if (selected == 3) {
          System.out.println("Search for female patients");
          
          resultPanel.add(new BasicScrollPanel(HR,HR.getPatientsOfGender("female")));
          
        } else if (selected == 4) {
          System.out.println("Search for male patients");
          
          resultPanel.add(new BasicScrollPanel(HR,HR.getPatientsOfGender("male")));
        }
        
      } else {
        JLabel err = new JLabel("There needs to be patients in the hospital to conduct searches.");
        err.setForeground(darkerBlue);
        resultPanel.add(err);
        
      }
    }
    
   /** *****************************************************************************
    * A AgeListener gets the information for age and ensures it is a valid integer
    * @author Meltem Ozcan
    *************************************************************************** **/
    private class AgeListener implements ActionListener {
       /** *****************************************************************************
      * Gets the age information 
      * @params event
      **************************************************************************** **/
      public void actionPerformed (ActionEvent event){
        
        try {
          
          int age1 =  ageL.getText().isEmpty()?0:Integer.parseInt(ageL.getText());//avoids parseInt error
          int age2 =  ageH.getText().isEmpty()?0:Integer.parseInt(ageH.getText());//avoids parseInt error
          
          //clears the previous labels and text fields 
          resultPanel.removeAll();
          resultPanel.revalidate(); 
          resultPanel.repaint(); 
          
          resultPanel.add(new BasicScrollPanel(HR,HR.getPatientsOfAge(age1, age2)));
          
        } catch (NumberFormatException e){
          
          errorLabel.setText("Please enter integers to the age fields.");
          errorLabel.setForeground(darkerBlue);
          System.out.println("Please enter a valid number");
          
          resultPanel.revalidate(); 
        }
      } 
    }
    
    /** *****************************************************************************
    * A DisorderListener clears the panel and updates the result panel with the list of
    * patient with the given disorder
    * 
    * @author Meltem Ozcan
    *************************************************************************** **/
    private class DisorderListener implements ActionListener {
       /** *****************************************************************************
      * Updates the scroll panel with the list of patients
      * @params event
      **************************************************************************** **/
      public void actionPerformed (ActionEvent event) {
        
        //clears the previous labels and text fields 
        resultPanel.removeAll();
        resultPanel.revalidate(); 
        resultPanel.repaint(); 
        
        resultPanel.add(new BasicScrollPanel(HR,HR.getPatientsWithDisorder(disorderName.getText())));
        
      }
    }
  }
}

