/* FILENAME: MasterCriteriaPanel.java
 * AUTHOR: Yuyu Li
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a panel that uses a card layout to determine 
 * which disorder to check based on user input.
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
  * A MasterCriteriaPanel class displays one introductory one for user input and
  * depending on the disorder selected, displays the corresponding checker. 
  * 
  * @author Yuyu Li
  *************************************************************************** **/
public class MasterCriteriaPanel extends JPanel {
  
  //instance variables
  private LinkedList<String> masterDisorderList;
  
  //panels for introductory panel and checkers for each disorder, as well as 
  //a container panel
  private JPanel cardPanel, introPanel;
  private SchiPanel schizophreniaPanel;
  private MajDepPanel majdepressivePanel;
  
  //labels for titles and messages, combo box for a drop down menu of disorders,
  //and a button to start the checker
  private JLabel jlTitle, jlMessage;
  private JComboBox dropDown;
  private JButton startButton; 
  
  //card layout used to flip through criteria, colors and default dimensions
  private CardLayout cardLayout;
  private Color lighterBlue, darkerBlue, darkestBlue;
  private Dimension defaultDim;
  
  /** *****************************************************************************
    * Constructor
    * 
    * Makes a new master criteria panel with a card layout that takes user input
    * and checks through criteria for a disorder depending on input.
    * 
    * @throws FileNotFoundException
    **************************************************************************** **/
  public MasterCriteriaPanel() throws FileNotFoundException {
    try {
      //makes a new disorder expert and intializes variables for it
      DisorderExpert de = new DisorderExpert();
      masterDisorderList = new LinkedList<String>();
      
      //converting from LinkedList<Disorder> to LinkedList<String>
      for (int i = 0; i < de.getDisorderList().size(); i++) {
        masterDisorderList.add(de.getDisorderList().get(i).getName());
      }
      
      //converts from LinkedList<String> to an array for use in combo box
      String[] a = new String[de.getDisorderCount()];
      dropDown = new JComboBox(masterDisorderList.toArray(a));
      
      //layout, colors, formatting
      cardLayout = new CardLayout();
      Color lighterBlue = new Color(225, 240, 250);
      Color darkerBlue = new Color(86, 126, 186);
      Color darkestBlue = new Color(11, 82, 158);
      defaultDim = new Dimension(300, 400);
      setBackground(lighterBlue);
      setPreferredSize(defaultDim);
      
      //creating title and message for introductory panel
      jlTitle = new JLabel("Criteria Checker", SwingConstants.CENTER);
      jlMessage = new JLabel("<html>Please select a disorder from the dropdown "
                               + "to check through its criteria.<html>", SwingConstants.CENTER);
      jlMessage.setPreferredSize(defaultDim);
      startButton = new JButton("Start"); //creates start button to begin criteria checker
      
      //makes new schizophrenia and major depressive disorder panels
      schizophreniaPanel = new SchiPanel();
      majdepressivePanel = new MajDepPanel();
      
      //creating cardPanel that uses a cardLayout to flip through panels
      cardPanel = new JPanel();
      cardPanel.setLayout(cardLayout);
      cardPanel.setPreferredSize(defaultDim);
      
      //creating introductory panel displaying the title, a message for the user,
      //a drop down menu of disorders, and a start button
      introPanel = new JPanel();
      
      //layout, colors, formatting
      introPanel.setLayout(new BorderLayout());
      introPanel.setBackground(lighterBlue);
      introPanel.setPreferredSize(defaultDim);
      
      introPanel.add(jlTitle, BorderLayout.NORTH);
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
      centerPanel.setBackground(Color.white); 
      centerPanel.add(jlMessage);
      centerPanel.add(dropDown);
      introPanel.add(centerPanel, BorderLayout.CENTER);
      introPanel.add(startButton, BorderLayout.SOUTH);
      
      //adding panels for disorders to main cardPanel
      cardPanel.add(introPanel, "intro");
      cardPanel.add(schizophreniaPanel, "schi");
      cardPanel.add(majdepressivePanel, "dep");
      
      add(cardPanel);
      
      //creating new button listener for all buttons in the panels
      ButtonListener l = new ButtonListener();
      startButton.addActionListener(l);
      schizophreniaPanel.bExitYes.addActionListener(l);
      schizophreniaPanel.bExitNo.addActionListener(l);
      majdepressivePanel.bExitYes.addActionListener(l);
      majdepressivePanel.bExitNo.addActionListener(l);
      
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  
  /** *****************************************************************************
    * A ButtonListener class detects which buttons were clicked. 
    * If the start button was clicked, the listener will detect which disorder the 
    * user selected and proceed with checking criteria for that disorder.
    * If the exit button was clicked, the introductory panel will be displayed.
    * 
    * @author Yuyu Li
    *************************************************************************** **/
  private class ButtonListener implements ActionListener {
    
    /** *****************************************************************************
      * Determines the source of an action performed by a button and starts the
      * checker or exits it accordingly.
      **************************************************************************** **/
    public void actionPerformed (ActionEvent e) {
      
      int selected = dropDown.getSelectedIndex();
      if (e.getSource() == startButton) {
        if (selected == 0) cardLayout.show(cardPanel, "schi");
        if (selected == 1) cardLayout.show(cardPanel, "dep");
      }
      else if ((e.getSource() == schizophreniaPanel.bExitYes) ||
               (e.getSource() == schizophreniaPanel.bExitNo) ||
               (e.getSource() == majdepressivePanel.bExitYes) ||
               (e.getSource() == majdepressivePanel.bExitNo)) {
        cardLayout.first(cardPanel);
      }
    }
  }
  
}