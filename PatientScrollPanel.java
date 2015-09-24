
/* FILENAME: PatientScrollPanel.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: This file is not used in the program, however holds duplicate code used in demographics panel in
 * order to create the scrolling mechanism to show the patients and testing code.
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.Color;


/** *****************************************************************************
  *  PatientScrollPanel allows a set of data to be viewed using a scroll bar by creating
  *  a JList and a scroll bar
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class PatientScrollPanel extends JPanel{
  
  //instance variables
  private HospitalRecords HR; 
  private String[] patients = {};//will be used to create a JList
  private JList patientList;
  private Color lighterBlue, darkerBlue;
  
    /** *****************************************************************************
    * Constructor
    * 
    * Takes in a hospital and displays the patients in a scroll bar 
    * 
    * @params hospital
    **************************************************************************** **/
  public PatientScrollPanel(HospitalRecords hospital) {
    
    HR = hospital; 
    
    //calls on toStringArrayWithHtml() to achieve a wrapping format
    patients = HR.toStringArrayWithHtml();
    
    //sets colors to be used in the frame
    Color lighterBlue = new Color(225, 240, 250);
    Color darkerBlue = new Color(86, 126, 186);
    
    //creates jpanel with borderlayout
    JPanel panelForScroll = new JPanel(new BorderLayout());
    
    //this will contain only the title for the patient record
    JPanel titlePanel = new JPanel();
    
    //Creates and adds the title to the top
    JLabel label = new JLabel("Patients");
    label.setForeground(darkerBlue);//sets font color
    titlePanel.add(label);//adds title to the panel
    titlePanel.setBackground(lighterBlue);
    //adds titlepanel to the frame
    panelForScroll.add(titlePanel, BorderLayout.NORTH);
    
    patientList = new JList(patients);//makes a JList using the information in the string array
    
    //sets the height and width for each patient's information
    patientList.setFixedCellHeight(170);
    patientList.setFixedCellWidth(400);
    
    //the number of patients visible without scrolling is set to 2s
    patientList.setVisibleRowCount(2);
    
    //creates scroller and puts it to the center of the borderlayout
    JScrollPane scroller = new JScrollPane(patientList);
    panelForScroll.add(scroller,BorderLayout.CENTER);
    
    //adds the panelForScroll to the frame after all modifications
    this.add(panelForScroll);
  }
  
  /** *****************************************************************************
    * this method resets the values in the list by calling on to setListData() in JList
    * 
    * @params arrayOfPatients
    *************************************************************************** **/
  public void resetScrollList (String[] arrayOfPatients) {
    patientList.setListData(arrayOfPatients);
  }
  


  /** *****************************************************************************
    * Provides testing code
    * 
    * @params args
    *************************************************************************** **/
  public static void main (String[] args) {
    
    JFrame frame = new JFrame ("Hospital Record");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    HospitalRecords BMC = new HospitalRecords("Boston Medical Center");
    BMC.addPatient(new Patient("John Smith", 36, "male", "major depressive disorder", "diminished interest,significant weight loss,sense of hopelessness,fatigue or loss of energy"));
    BMC.addPatient(new Patient("Carry Roy", 21, "female", "bipolar disorder", "manic episode,overtalkativeness,reduced need for sleep"));
    BMC.addPatient(new Patient("Barbara White", 27, "female", "bipolar disorder", "elevated expansive mood,inflated self-esteem or grandiosity,decreased need for sleep,increase in goal-directed activity"));
    BMC.addPatient(new Patient("Maria Kim", 22, "female", "major depressive disorder", "insomnia,depressed mood during day,psychomotor agitation,significant weight loss, recurrent thoughts of death"));
    BMC.addPatient(new Patient("Joseph Lee", 36, "male", "major depressive disorder", "diminished interest,diminished ability to think or concentrate,feelings of" 
                                 + "worthlessness,depressed mood during day,significant weight loss"));
    BMC.addPatient(new Patient("Mark Jones", 23, "male", "schizophrenia", "delusions,hallucinations,disorganized speech,negative symptoms"));
    
    
    //adds the scroll to the frame
    frame.getContentPane().add(new PatientScrollPanel(BMC));
    frame.pack();
    frame.setVisible(true);
    
  }
}