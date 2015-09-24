/* FILENAME: DemographicsPanel.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a panel with border layout that contains panels for information input,
 * the disorder checklist and the scroll view of the patient files
 * 
 * */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Integer;
import java.awt.Color;

/** *****************************************************************************
  *  DemographicsPanel displays a group of panels that the user will interact with 
  * in order to add a patient to the hospital data base and display patients
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class DemographicsPanel extends JPanel {
  
  private JLabel nameLabel, ageLabel, genderLabel, diagnosisLabel, symptomsLabel, patientsLabel, patientListLabel;
  private JTextField nameField, ageField, diagnosisField, symptomsField;
  private JButton addPatientButton;
  private JRadioButton maleButton, femaleButton;
  private  ButtonGroup group;
  
  private HospitalRecords HR; 
  private PatientScrollPanel ScrollPanel;
  private String gender, name, age, diagnosis,symptoms;
 private Color lighterBlue, darkerBlue, darkestBlue;
  private String[] patients = {};//will be used to create a JList
  private JList patientList;
  
  /** *****************************************************************************
    * Constructor
    * 
    * Takes in a hospital as a parameter and creates panels for user input, a checklist
    * and patent display. Creates patients with the information acquired from the 
    * input fields and adds them to the hospital.
    * 
    * @params hospital
    **************************************************************************** **/
  public DemographicsPanel(HospitalRecords hospital) {
    
    HR = hospital;
    
    setLayout (new BorderLayout ());
    
    //sets colors to be used in the frame
    lighterBlue = new Color(225, 240, 250);
    darkerBlue = new Color(86, 126, 186);
    darkestBlue = new Color(11, 82, 158);
    
    //sets up the labels
    nameLabel = new JLabel ("Name: ");
    nameLabel.setForeground(darkerBlue);
    ageLabel= new JLabel ("Age: ");
    ageLabel.setForeground(darkerBlue);
    genderLabel= new JLabel ("Gender: ");
    genderLabel.setForeground(darkerBlue);
    
    //sets the radio buttons for the gender selection
    maleButton = new JRadioButton("Male");
    maleButton.setBackground(lighterBlue);
    femaleButton = new JRadioButton("Female");
    femaleButton.setBackground(lighterBlue);
    
    //ensures the buttons are mutually exclusive
    group = new ButtonGroup();
    group.add(maleButton);
    group.add(femaleButton);
    
    //makes and adds the action listener
    GenderListener gListener = new GenderListener();
    maleButton.addActionListener(gListener);
    femaleButton.addActionListener(gListener);
    
    //creates panel to hold buttons
    JPanel genderPanel = new JPanel();
    genderPanel.setBackground(lighterBlue);
    genderPanel.add(maleButton);
    genderPanel.add(femaleButton);
    
    //sets up remaining labels
    diagnosisLabel = new JLabel ("Diagnosis: ");
    diagnosisLabel.setForeground(darkerBlue);
    symptomsLabel = new JLabel ("Symptoms: ");
    symptomsLabel.setForeground(darkerBlue);
    
    //sets up the button and adds action listener
    addPatientButton = new JButton("Add Patient");
    addPatientButton.addActionListener (new HospitalListener());
    
    //initializes the text fields
    nameField = new JTextField (10);
    ageField = new JTextField (5);
    diagnosisField = new JTextField (10);
    symptomsField = new JTextField (20);
    
    //this will contain the input fields for demographical information
    JPanel InputPanel = new JPanel();
    InputPanel.setLayout(new BoxLayout (InputPanel, BoxLayout.Y_AXIS));
    InputPanel.setBackground(lighterBlue);
    
    //add all the labels, fields and buttons related to input panel
    InputPanel.add(new JLabel("          "));
    InputPanel.add(nameLabel);
    InputPanel.add(nameField);
    InputPanel.add(ageLabel);
    InputPanel.add(ageField);
    InputPanel.add(genderLabel);
    InputPanel.add(genderPanel);
    InputPanel.add(symptomsLabel);
    InputPanel.add(symptomsField);
    
    try {
      InputPanel.add(new MasterCriteriaPanel());
    } catch (Exception e) {
      System.out.println(e);
    }
    
    InputPanel.add(diagnosisLabel);
    InputPanel.add(diagnosisField);
    InputPanel.add(addPatientButton);
    
    
    //At the top of the GUI
    JPanel FirstPanel = new JPanel();
    FirstPanel.setLayout(new BoxLayout(FirstPanel,BoxLayout.Y_AXIS));
    
    FirstPanel.setBackground(lighterBlue);
    JLabel explanation1 = new JLabel("Welcome to Boston Medical Center's Patient Records Programme. ");
    JLabel explanation2 = new JLabel("Please enter the demographic information of the patient to" +
                                     " be added below and click 'Add Patient' to update the patient list.");
    explanation1.setForeground(darkestBlue);
    explanation2.setForeground(darkestBlue);
    
    //adds labels and spacing
    FirstPanel.add(Box.createRigidArea(new Dimension(20,20)));
    FirstPanel.add(explanation1);
    FirstPanel.add(Box.createRigidArea(new Dimension(20,20)));
    FirstPanel.add(explanation2);
    FirstPanel.add(Box.createRigidArea(new Dimension(20,20)));
    
    add(FirstPanel,BorderLayout.NORTH);//add the panel to the frame
    
    //this panel will contain the input panel and the checklist
    JPanel SecondPanel = new JPanel();
    
    SecondPanel.setLayout(new BoxLayout(SecondPanel, BoxLayout.Y_AXIS));
    
    SecondPanel.add(InputPanel);//add to the top left
    
    
    //Code regarding scroll panel and patient display
    
    //I got help from http://stackoverflow.com/questions/9812980/how-to-use-the-jscrollpane-in-java to figure
    //out how to use the scroll bar.
    
    //calls on toStringArrayWithHtml() to achieve a wrapping format
    patients = HR.toStringArrayWithHtml();
    
    //creates jpanel with borderlayout
    JPanel panelForScroll = new JPanel(new BorderLayout());
    
    //Creates and adds the title to the top of the newly created title panel
    JPanel titlePanel = new JPanel();
    JLabel label = new JLabel("Patients");
    label.setForeground(darkerBlue);//sets font color
    titlePanel.add(label);//adds title to the panel
    titlePanel.setBackground(lighterBlue);
    
    //adds titlepanel to the frame
    panelForScroll.add(titlePanel, BorderLayout.NORTH);
    
    patientList = new JList(patients);//makes a JList using the information in the string array
    
    //sets the height and width for each patient's information
    patientList.setFixedCellHeight(170);
    patientList.setFixedCellWidth(300);
    
    //the number of patients visible without scrolling is set to 2s
    patientList.setVisibleRowCount(2);
    
    //creates scroller and puts it to the center of the borderlayout
    JScrollPane scroller = new JScrollPane(patientList);
    panelForScroll.add(scroller,BorderLayout.CENTER);
    panelForScroll.add(new JLabel("      "),BorderLayout.SOUTH);//puts space below the scroll section
    panelForScroll.setBackground(lighterBlue);
    
    
    //as a filler
    JPanel emptyPanel = new JPanel();
    emptyPanel.add(new JLabel("      "));
    emptyPanel.setBackground(lighterBlue);
    
    //will contain the patient list, in border layout to add additional spacing
    JPanel RightPanel = new JPanel();
    RightPanel.setLayout(new BorderLayout());
    RightPanel.setBackground(lighterBlue);
    RightPanel.add(panelForScroll,BorderLayout.CENTER);
    RightPanel.add(emptyPanel,BorderLayout.WEST);
    
    
    //adds the panels to the frame
    add(RightPanel,BorderLayout.CENTER);
    add(SecondPanel,BorderLayout.WEST);
    
    
  }
  
  /** *****************************************************************************
    * This method resets the values in the list by calling on to setListData() in JList
    * @params arrayOfPatients
    **************************************************************************** **/
  public void resetScrollList (String[] arrayOfPatients) {
    patientList.setListData(arrayOfPatients);
  }
  
  /** *****************************************************************************
    * A HospitalListener class gathers information from the input fields and button and 
    * adds a patient to the hospital
    * 
    * @author Meltem Ozcan
    *************************************************************************** **/
  private class HospitalListener implements ActionListener {
    
    /** *****************************************************************************
      * When the add patient is clicked makes a new Patient object and adds to database
      * @params event
      **************************************************************************** **/
    public void actionPerformed (ActionEvent event) {
      
      try{
        
        //gets the user input from the fields and stores them in variables
        String name = nameField.getText();
        int age =  ageField.getText().isEmpty()?0:Integer.parseInt(ageField.getText());//avoids parseInt error
        String diagnosis = diagnosisField.getText();
        String symptoms = symptomsField.getText();
        
        //only add a patient if none of the fields are empty strings
        if (!(name.equals(""))&&!(ageField.getText().equals("")) && !(gender.equals(""))&&!(diagnosis.equals(""))&&!(symptoms.equals(""))){
          
          HR.addPatient(new Patient(name, age, gender, diagnosis, symptoms));
          
          //clear the text fields for the next entry
          nameField.setText("");
          group.clearSelection();
          ageField.setText("");
          diagnosisField.setText("");
          symptomsField.setText("");
        }
        
        //updates the list of patients 
        resetScrollList(HR.toStringArrayWithHtml());
        
      } catch (NumberFormatException e){
        System.out.println("Please enter a valid number");
      }            
    }
  }
  
  
  /** *****************************************************************************
    * A GenderListener class detects which buttons were clicked, gets the gender 
    * selection from the radio buttons upon click and stores the value
    * 
    * @author Meltem Ozcan
    *************************************************************************** **/
  private class GenderListener implements ActionListener {
    /** *****************************************************************************
      * When a radio button is clicked updates the gender variable
      * @params event
      **************************************************************************** **/
    public void actionPerformed (ActionEvent event) {
      
      if (event.getSource() == maleButton)
        gender = "male";
      else 
        gender = "female";
    }
  }
  
}