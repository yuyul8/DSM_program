/* FILENAME: Disorder.java
 * AUTHOR: Yuyu Li
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates new disorders that keep track of the a list of criteria 
 * needed to fulfill the disorder, also including a list of symptoms. Can 
 * be added to a disorder expert so that disorder's criteria can be checked.
 * 
 * */

import java.util.*;
import java.io.*;
import javafoundations.*;


/** *****************************************************************************
  * A Disorder class creates new disorders that keep track of the a list of
  * criteria needed to fulfill the disorder, also including a list of symptoms.
  * 
  * @author Yuyu Li
  *************************************************************************** **/
public class Disorder {
  
  //instance variables
  protected String disorderName;
  protected LinkedList<String> symptomsLL;
  //linked list of critera, each housing a linked list of questions which are strings
  protected LinkedList<LinkedList<String>> criteriaLL; 
  protected int criteriaCount; //counter
  
  
  /** *****************************************************************************
    * Constructor
    * 
    * Creates a new disorder that has a name, a list of criteria, and a list of 
    * symptoms. Each criteria can have one or multiple questions. Information
    * is read in through a text file.
    * 
    * @throws FileNotFoundException
    * @param filename, the file to be read in from
    **************************************************************************** **/
  public Disorder(String filename) throws FileNotFoundException {
    symptomsLL = new LinkedList<String>();
    criteriaLL = new LinkedList<LinkedList<String>>();
    
    try {
      Scanner s = new Scanner(new File(filename));
      disorderName = s.nextLine(); //name in first line of file
      
      while (!(s.hasNext("#"))) {
        symptomsLL.add(s.nextLine()); //reads in symptoms until "#"
      }
      s.nextLine();
      
      //reads in criteria on each new line using helper method
      while (s.hasNextLine()) {
        addCriteria(s.nextLine());
      }
      s.close();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
  
  
  /** *****************************************************************************
    * Adds a criteria to the disorder, splitting the questions using ";" and adding
    * them to a linked list of questions within each criteria.
    * Checks that the criteria is not already present.
    * 
    * @param questions, the String of the entire criteria
    **************************************************************************** **/
  public void addCriteria(String questions) {
    LinkedList<String> questionsLL = new LinkedList<String>();
    String[] questionsArray = questions.split(";");
    
    //adds the questions in the criteria into the linked list 
    for (int i = 0; i < questionsArray.length; i++) {
      questionsLL.add(questionsArray[i]);
    }
    
    //checks that the criteria is not already in the linked list
    if (criteriaLL.contains(questionsLL)) {
      System.out.println("ERROR: this criteria has already been added");
    } else {
      criteriaLL.add(questionsLL); //add questions to the criteria
      criteriaCount++; //increment counter
    }
  }
  
  
  /** *****************************************************************************
    * Removes a criteria to the disorder, checking that the criteria hda already
    * been added using the counter.
    * 
    * @param criteriaToRemove, the criteria to be removed
    **************************************************************************** **/
  public void removeCriteria(char criteriaToRemove) {
    //checks that the criteria has already been added using the counter
    //int counter added/subtracted to get the char of the criteria (i.e. Criteria A)
    if ((criteriaToRemove > (char)(criteriaCount + 65)) || (criteriaToRemove < 65)) {
      System.out.println("YOU CANNOT REMOVE A NONEXISTENT CRITERA.");
    } else {
      criteriaLL.remove(criteriaToRemove-65); //remove the criteria from the list
      criteriaCount--; //decrement counter
    }
  }
  
  /** *****************************************************************************
    * Gets the name of the disorder.
    * 
    * @return String name of the disorder
    **************************************************************************** **/
  public String getName() {
    return disorderName;
  }
  
  
  /** *****************************************************************************
    * Gets the criteria for the disorder.
    * 
    * @return LinkedList<LinkedList<String>> of criteria for the disorder
    **************************************************************************** **/
  public LinkedList<LinkedList<String>> getCriteria() {
    return criteriaLL;
  }
  /** *****************************************************************************
    * Gets the symptoms for the disorder.
    * 
    * @return LinkedList<String> of symptoms for the disorder
    **************************************************************************** **/
  public LinkedList<String> getSymptoms() {
    return symptomsLL;
  }
  
  /** *****************************************************************************
    * Gets the number of criteria for the disorder.
    * 
    * @return int number of criteria for the disorder
    **************************************************************************** **/
  public int getCriteriaCount() {
    return criteriaCount;
  }
  
  /** *****************************************************************************
    * Gets the BinaryTree for the disorder from a DisorderExpert.
    * 
    * @return BinaryTree<String> binary decision tree for the disorder
    **************************************************************************** **/
  public BinaryTree<String> getTree() {
    //creates new disorder expert
    DisorderExpert de = new DisorderExpert();
    
    if(disorderName.toLowerCase().equals("schizophrenia"))
      return de.getSchiTree();
    
    if(disorderName.toLowerCase().equals("major depressive disorder"))
      return de.getMajDepTree();
    
    //returns appropriate tree based on disorder name
    else return null; // return null if no disorder with that name is found
  }
  
  /** *****************************************************************************
    * Gives a String representation of a disorder. 
    * 
    * @return String representation of the disorder
    **************************************************************************** **/
  public String toString() {
    String s = "Disorder name: " + disorderName;
    
    //add linked list of symptoms
    s += "\nSymptoms:";
    for (int i =0; i < symptomsLL.size(); i++) {
      s += "\n  " + symptomsLL.get(i);
    }
    s+= "\n";
    
    //add all criteria
    for (int j = 0; j < criteriaLL.size(); j++) {
      s += "\nCriteria " + (char)(j + 65);
      for (int k = 0; k < criteriaLL.get(j).size(); k++) {
        s += "\n  " + criteriaLL.get(j).get(k);
      }
      s += "\n";
    }
    return s;
  }

  public static void main(String[] args) {
    try {
      Disorder d = new Disorder("schizophrenia.txt");
      System.out.println(d);
      System.out.println(d.getCriteria());
      
      Disorder e = new Disorder("majorDepressiveDisorder.txt");
      System.out.println(e);
      
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}

