/* FILENAME: MajDepPanel.java
 * AUTHOR: Yuyu Li
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a panel that uses a card layout to check through
 * criteria for major depressive disorder. Determines whether a 
 * patient fits the criteria by using a tree.
 * 
 * */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import javafoundations.*;

/** *****************************************************************************
  * A MajDepPanel class displays a series of panels in a card layout, flipping 
  * through each criteria to check if a patient fits the diagnosis for 
  * major depressive disorder.
  * 
  * @author Yuyu Li
  *************************************************************************** **/
public class MajDepPanel extends JPanel {
  
  //instance variables
  
  //variables for disorder characteristics
  private Disorder dis;
  private BinaryTree<String> tree;
  private LinkedList<LinkedList<String>> disCriteriaLL;
  private LinkedList<String> disSymptomsLL;
  
  //variables for messages to appear as label
  private String yes, no;
  
  //linked list of generic panels, can reuse generic panels for several criterion
  private LinkedList<GenericCriteriaPanel> criteriaPanelsLL;
  private GenericCriteriaPanel cB, cC, cD, cE;
  
  //panels for first criteria (different from generic since it includes symptoms,
  //and for panels that display end message, as well as container panel
  private JPanel cardPanel, cA, yesEndPanel, noEndPanel;
  
  //labels for messages and panels, checkboxes for symptoms, and buttons to reset and exit
  private JLabel jlCritA, jlTitleA, jlYesTitle, jlNoTitle, jlYesMessage, jlNoMessage;
  private JCheckBox jcbA1, jcbA2, jcbA3, jcbA4, jcbA5, jcbA6, jcbA7, jcbA8, jcbA9;
  private JButton bNextA, bResetA;
  protected JButton bExitYes, bExitNo;
  
  //card layout used to flip through criteria, colors and default dimensions
  private CardLayout cardLayout;
  private Color lighterBlue, darkerBlue, darkestBlue;
  private Dimension defaultDim;
  
  /** *****************************************************************************
    * Constructor
    * 
    * Makes a new major depressive disorder panel with a card layout that checks 
    * through each criteria and determines if the patient can be diagnosed.
    * 
    * @throws FileNotFoundException
    **************************************************************************** **/
  public MajDepPanel() throws FileNotFoundException {
    
    try{
      //creating new disorder for schizophrenia and setting instance variables for it
      dis = new Disorder("majorDepressiveDisorder.txt");
      tree = dis.getTree();
      disCriteriaLL = dis.getCriteria();
      disSymptomsLL = dis.getSymptoms();
      LinkedList<String> disSymptomsLLCopy = disSymptomsLL;
      criteriaPanelsLL = new LinkedList<GenericCriteriaPanel>();
      
      //messages to display once the patient is determined as fitting the criteria
      //or not
      yes = "The patient fits the criteria for this disorder.";
      no = "The patient DOES NOT fit the criteria for this disorder.";
      
      //layout, colors, formatting
      cardLayout = new CardLayout();
      Color lighterBlue = new Color(225, 240, 250);
      Color darkerBlue = new Color(86, 126, 186);
      Color darkestBlue = new Color(11, 82, 158);
      defaultDim = new Dimension(300, 400);
      setPreferredSize(defaultDim);
      
      //creating labels for titles and body messages of special panels 
      jlTitleA = new JLabel("Criterion A", SwingConstants.CENTER);
      jlCritA = new JLabel("<html>" + disCriteriaLL.poll().toString() + "<html>");
      jlYesTitle = new JLabel("Diagnosis: Positive", SwingConstants.CENTER);
      jlNoTitle = new JLabel("Diagnosis: Negative", SwingConstants.CENTER);
      jlYesMessage = new JLabel("<html>" + yes + "<html>");
      jlNoMessage = new JLabel("<html>" + no + "<html>");
      
      //creating checkboxes for symptoms
      jcbA1 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA2 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA3 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA4 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA5 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA6 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA7 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA8 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      jcbA9 = new JCheckBox(disSymptomsLLCopy.remove().toString(), false);
      
      
      //creating buttons to reset inputted information and exit the criteria checker
      bNextA = new JButton("Next");
      bResetA = new JButton("Reset");
      bExitYes = new JButton("Exit");
      bExitNo = new JButton("Exit");
      
      //creating cardPanel that uses a cardLayout to flip through panels
      cardPanel = new JPanel();
      cardPanel.setLayout(cardLayout);
      cardPanel.setPreferredSize(defaultDim);
      
      //creating panel that will be shown if the patient fits the criteria for
      //the diagnosis
      yesEndPanel = new JPanel();
      yesEndPanel.setLayout(new BorderLayout());
      yesEndPanel.setBackground(lighterBlue);
      yesEndPanel.setPreferredSize(defaultDim);
      
      yesEndPanel.add(jlYesTitle, BorderLayout.NORTH);
      yesEndPanel.add(jlYesMessage, BorderLayout.CENTER);
      yesEndPanel.add(bExitYes, BorderLayout.SOUTH);
      
      //creating panel that will be shown if the patient does not fit the 
      //criteria for the diagnosis
      noEndPanel = new JPanel();
      noEndPanel.setLayout(new BorderLayout());
      noEndPanel.setBackground(lighterBlue);
      noEndPanel.setPreferredSize(defaultDim);
      
      noEndPanel.add(jlNoTitle, BorderLayout.NORTH);
      noEndPanel.add(jlNoMessage, BorderLayout.CENTER);
      noEndPanel.add(bExitNo, BorderLayout.SOUTH);
      
      //creating panel for first criteria, which is different because
      //it displays symptoms also (unlike the other panels)
      cA = new JPanel();
      cA.setLayout(new BorderLayout());
      cA.setBackground(lighterBlue);
      cA.setPreferredSize(defaultDim);
      
      cA.add(jlTitleA, BorderLayout.NORTH);
      JPanel centerPanelA = new JPanel();
      centerPanelA.setLayout(new BoxLayout(centerPanelA, BoxLayout.Y_AXIS));
      centerPanelA.setBackground(Color.white);
      centerPanelA.add(jlCritA);
      centerPanelA.add(jcbA1);
      centerPanelA.add(jcbA2);
      centerPanelA.add(jcbA3);
      centerPanelA.add(jcbA4);
      centerPanelA.add(jcbA5);
      centerPanelA.add(jcbA6);
      centerPanelA.add(jcbA7);
      centerPanelA.add(jcbA8);
      centerPanelA.add(jcbA9);
      cA.add(centerPanelA, BorderLayout.CENTER);
      
      JPanel buttonPanelA = new JPanel();
      buttonPanelA.add(bNextA);
      buttonPanelA.add(bResetA);
      buttonPanelA.setBackground(lighterBlue);
      cA.add(buttonPanelA, BorderLayout.SOUTH);
      
      //creating generic panels for criterion B-F, since they all have the 
      //same format
      cB = new GenericCriteriaPanel(disCriteriaLL, "B");
      cC = new GenericCriteriaPanel(disCriteriaLL, "C");
      cD = new GenericCriteriaPanel(disCriteriaLL, "D");
      cE = new GenericCriteriaPanel(disCriteriaLL, "E");
      
      //adding generic panels to a linked list to be able to listen for events
      //for all of them and perform actions more efficiently
      criteriaPanelsLL.add(cB);
      criteriaPanelsLL.add(cC);
      criteriaPanelsLL.add(cD);
      criteriaPanelsLL.add(cE);
      
      //adding all criterion panels and ending diagnosis panels to main cardPanel
      cardPanel.add(cA, "A");
      cardPanel.add(cB, "B");
      cardPanel.add(cC, "C");
      cardPanel.add(cD, "D");
      cardPanel.add(cE, "E");
      cardPanel.add(yesEndPanel, "Yes Panel");
      cardPanel.add(noEndPanel, "No Panel");
      
      add(cardPanel);
      
      //creating new button listener for all buttons in the panels
      ButtonListener l = new ButtonListener();
      bNextA.addActionListener(l);
      bResetA.addActionListener(l);
      bExitYes.addActionListener(l);
      bExitNo.addActionListener(l);
      
      //loops through list of generic panels to add listeners more efficiently
      for (int i = 0; i < criteriaPanelsLL.size(); i++) {
        criteriaPanelsLL.get(i).btnNext.addActionListener(l);
        criteriaPanelsLL.get(i).btnReset.addActionListener(l);
      } 
      
    } catch (Exception e) {
    System.out.println(e);
  }
}

/** *****************************************************************************
  * A ButtonListener class detects which buttons were clicked. Depending on the 
  * button's purpose, the criteria checker will either advance to the next 
  * criteria panel or reset.
  * 
  * @author Yuyu Li
  *************************************************************************** **/
private class ButtonListener implements ActionListener {
  
  
  /** *****************************************************************************
    * Determines the source of an action performed by a button and advances or resets
    * the criteria checker accordingly. Also updates the decision tree.
    **************************************************************************** **/
  public void actionPerformed (ActionEvent e) {
    
    //if the next button in the first panel is clicked, advance
    if (e.getSource() == bNextA) {
      tree = tree.getRight();
      cardLayout.next(cardPanel);
      
    }  else {
      //loops through linked list of generic panels to check for
      //the next button being clicked
      for (int j = 0; j < criteriaPanelsLL.size(); j++) {
        if (e.getSource() == criteriaPanelsLL.get(j).btnNext) {
          
          //if button is clicked and the criteria is fulfilled, advance
          if (criteriaPanelsLL.get(j).getRbtnYesState()) {
            tree = tree.getRight();
            treeChecker();
            
            //if button is clicked and the criteria is not fulfilled,
            //do not advance; the patient does not fit the diagnosis
          } else if (criteriaPanelsLL.get(j).getRbtnNoState()) {
            tree = tree.getLeft();
            treeChecker();
          } 
        }
        
        //if the reset button in the first panel is clicked, checker resets
        //all checkboxes and radio buttons
        if ((e.getSource() == criteriaPanelsLL.get(j).btnReset) 
              || (e.getSource() == bResetA)
              || (e.getSource() == bExitYes)
              || (e.getSource() == bExitNo)) {
          jcbA1.setSelected(false);
          jcbA2.setSelected(false);
          jcbA3.setSelected(false);
          jcbA4.setSelected(false);
          jcbA5.setSelected(false);
          jcbA6.setSelected(false);
          jcbA7.setSelected(false);
          jcbA8.setSelected(false);
          jcbA9.setSelected(false);
          
          //loops through linked list of generic panels to check for the reset
          //button being clicked. if it has, checker resets all checkboxes and 
          //radio buttons and goes back to first panel
          for (int k = 0; k < criteriaPanelsLL.size(); k++) {
            criteriaPanelsLL.get(k).bgroup.clearSelection();
          }
          cardLayout.first(cardPanel);
          tree = dis.getTree(); //tree is reset as well
        }
        
      }
    }
  }
  
  /** *****************************************************************************
    * Checks the tree to see if it is at the end (a leaf). If it is, it displays the
    * appropriate panel depending on if the patient fits the criteria or not.
    **************************************************************************** **/
  public void treeChecker() {
    if (tree.getRootElement() == yes) {
      cardLayout.show(cardPanel, "Yes Panel");
    } else if (tree.getRootElement() == no) {
      cardLayout.show(cardPanel, "No Panel");
    } else {
      cardLayout.next(cardPanel);
    }
  }
  
}

}