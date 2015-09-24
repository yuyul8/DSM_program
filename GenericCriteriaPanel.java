/* FILENAME: GenericCriteriaPanel.java
 * AUTHOR: Yuyu Li
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates generic panels that can be reused for multiple
 * criteria panels independent of disorder if the criteria only
 * consists of a true/false question.
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
  * A GenericCriteriaPanel class displays a generic panel consisting of a title,
  * a message containing a criteria, true/false buttons that indicated answers
  * to the criteria, and buttons to go to the next criteria or reset.
  * 
  * @author Yuyu Li
  *************************************************************************** **/
public class GenericCriteriaPanel extends JPanel {
  
  //instance variables
  
  //variable to hold the criteria for a disorder
  private LinkedList<LinkedList<String>> critCopy;
  
  //labels for the title and the criteria message
  private JLabel titleText, critText;
  
  //protected access so actions and events can be added to these from other files
  //buttons to go to the next criteria or reset
  protected JButton btnNext, btnReset;
  //radio buttons to select an answer to the criteria question
  protected JRadioButton rbtnYes, rbtnNo;
  //button group to ensure that only one of the radio buttons is selected
  protected ButtonGroup bgroup;
  
  //booleans to store the state of the radio buttons
  private boolean rbtnYesState, rbtnNoState;
  
  private Color lighterBlue, darkerBlue, darkestBlue;
  private Dimension defaultDim;
  
   /** *****************************************************************************
    * Constructor
    * 
    * Makes a new generic criteria panel that can be reused for many criteria
    * independent of disorders as long as they share the generic format.
    *
    * @param critLL, list of criteria
    * @param letter, letter of the criteria
    **************************************************************************** **/
  public GenericCriteriaPanel(LinkedList<LinkedList<String>> critLL, String letter) {
    
    //initializing instance variables, setting labels, setting size
    critCopy = critLL;
    titleText = new JLabel("Criteria " + letter, SwingConstants.CENTER);
    titleText.setForeground(darkerBlue);
    critText = new JLabel("<html>" + critCopy.remove().toString() + "<html>");
    critText.setPreferredSize(new Dimension(300, 300));
   
    //buttons for forward with the criteria checker or resetting
    btnNext = new JButton("Next");
    btnReset = new JButton("Reset");
    
    //radio buttons for true/false answer to criteria
    rbtnYes = new JRadioButton("True", false);
    rbtnNo = new JRadioButton("False", false);
    bgroup = new ButtonGroup();
    bgroup.add(rbtnYes);
    bgroup.add(rbtnNo);
    
    //colors, formatting, layout
    Color lighterBlue = new Color(225, 240, 250);
    Color darkerBlue = new Color(86, 126, 186);
    Color darkestBlue = new Color(11, 82, 158);
    defaultDim = new Dimension(300, 300);
    
    setLayout(new BorderLayout());
    setBackground(lighterBlue);
    setPreferredSize(new Dimension(defaultDim));
    
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBackground(Color.white);
    centerPanel.add(critText);
    centerPanel.add(rbtnYes);
    centerPanel.add(rbtnNo);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(lighterBlue);
    buttonPanel.add(btnNext);
    buttonPanel.add(btnReset);
    
    
    //adds title, criteria message, true/false buttons, and next/reset buttons
    add(titleText, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
    
    //adds listeners to buttons
    ButtonListener stateListener = new ButtonListener();
    rbtnYes.addActionListener(stateListener);
    rbtnNo.addActionListener(stateListener);
    
  }
  
  /** *****************************************************************************
    * A ButtonListener class detects if either radio button was clicked. If they 
    * were, the boolean that represents its current state is altered.
    * 
    * @author Yuyu Li
    *************************************************************************** **/
  private class ButtonListener implements ActionListener {
    
    /** *****************************************************************************
      * Determines the source of an action performed by a button and alters its state
      * so that it can be used in other panels to see if the criteria has been fulfilled 
      * and the criteria checker can flip to the next criteria panel.
      **************************************************************************** **/
    public void actionPerformed (ActionEvent e) {
      if (e.getSource() == rbtnYes) rbtnYesState = true;
      if (e.getSource() == rbtnNo) rbtnNoState = true;
    }
  } 
  
  /** *****************************************************************************
    * Gets the button state of the 'true' radio button (whether it was clicked).
    * 
    * @returns boolean for whether the button was clicked
    *************************************************************************** **/
  public boolean getRbtnYesState() {
    return rbtnYesState;
  }
  
   /** *****************************************************************************
    * Gets the button state of the 'false' radio button (whether it was clicked).
    * 
    * @returns boolean for whether the button was clicked
    *************************************************************************** **/
  public boolean getRbtnNoState() {
    return rbtnNoState;
  }
  
}