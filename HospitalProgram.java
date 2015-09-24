/* FILENAME: HospitalProgram.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Hospital Program is the GUI of our project for Psychiatric Hospital Records.
 * It allows for the creation of patients, lets the user go through the diagnostic criteria
 * with a checklist for Schizophrenia and Major Depressive Disorder, displays all patients in
 * a scroll view and allows searches to be conducted for analysis.
 * 
 * */

import javax.swing.*;

/** *****************************************************************************
  *  HospitalProgram is the file that executes the GUI frame
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class HospitalProgram {
  
  /** *****************************************************************************
    * Sets the frame containing the demographics panel, symptom panel and  record panels
    * @params args
    **************************************************************************** **/
  public static void main (String[] args) {
    
    JFrame frame = new JFrame ("Psychiatric Hospital Record");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    HospitalRecords Records = new HospitalRecords("Boston Medical Center");
    
    JTabbedPane tp = new JTabbedPane();
    tp.addTab ("Patient Files", new DemographicsPanel(Records));
    tp.addTab ("Analysis", new AnalysisPanel(Records));
    
    frame.getContentPane().add(tp);
    frame.pack();
    frame.setVisible(true);
    
  }
  
}

