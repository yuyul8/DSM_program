/* FILENAME: DisorderExpert.java
 * AUTHOR: Yuyu Li
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Create a disorder expert that will house the different decision trees
 * for each disorder. The disorder expert will check if the diagnosis is correct
 * using the tree.
 * 
 * */

import java.util.*;
import java.io.*;
import javafoundations.*;

/** *****************************************************************************
  * A DisorderExpert class creates a disorder expert that will house the
  * different decision trees for each disorder. The disorder expert will check 
  * if the diagnosis is correct using the tree.
  * 
  * @author Yuyu Li
  *************************************************************************** **/
public class DisorderExpert {
  
  //instance variables
  private LinkedList<Disorder> disorderList; //list of all disorders 
  //trees for disorders
  private LinkedBinaryTree<String> schizophreniaChecker;
  private LinkedBinaryTree<String> majDepressiveChecker;
  //strings to be used for end nodes (leaves)
  String yes = "The patient fits the criteria for this disorder.";
  String no = "The patient DOES NOT fit the criteria for this disorder."; 
  
  /** *****************************************************************************
    * Constructor
    *
    * Creates a disorder expert to check if a diagnosis is correct using criteria
    * as nodes and checking if they're fulfilled.
    **************************************************************************** **/
  public DisorderExpert() {
    try {
      disorderList = new LinkedList<Disorder>();
      schizophreniaChecker = makeSchiTree(); //helper method
      majDepressiveChecker = makeMajDepTree(); //helper method
      
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  
  /** *****************************************************************************
    * Makes a tree based on the criteria of schizophrenia.
    *
    * @throws FileNotFoundException
    * @return LinkedBinaryTree<String> schizophrenia tree
    **************************************************************************** **/
  public LinkedBinaryTree<String> makeSchiTree() throws FileNotFoundException {
    try{
      //makes a new disorder schizophrenia and add it to list of disorders
      Disorder schizophrenia = new Disorder("schizophrenia.txt"); 
      addToList(schizophrenia);
      //copy of criteria
      LinkedList<LinkedList<String>> schiCopy = schizophrenia.getCriteria();
      
      LinkedBinaryTree<String> critA, critB, critC, critD, critE, critF;
      critF = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), new LinkedBinaryTree(yes));
      critE = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), critF);
      critD = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), critE);
      critC = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), critD);
      critB = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), critC);
      critA = new LinkedBinaryTree<String>(schiCopy.removeLast().toString(), new LinkedBinaryTree(no), critB);
      
      return critA; //returns tree
      
    } catch (Exception e) {
      System.out.println(e);
    }
    return null; //returns null if tree can't be made
  }
  
  /** *****************************************************************************
    * Makes a tree based on the criteria of major depressive disorder.
    *
    * @throws FileNotFoundException
    * @return LinkedBinaryTree<String> major depressive disorder tree
    **************************************************************************** **/
  public LinkedBinaryTree<String> makeMajDepTree() throws FileNotFoundException {
    try{
      //makes a new disorder schizophrenia and add it to list of disorders
      Disorder majDepressive = new Disorder("majorDepressiveDisorder.txt");
      addToList(majDepressive);
      //copy of criteria
      LinkedList<LinkedList<String>> majDepCopy = majDepressive.getCriteria();
      
      LinkedBinaryTree<String> critA, critB, critC, critD, critE;
      critE = new LinkedBinaryTree<String>(majDepCopy.removeLast().toString(), new LinkedBinaryTree(no), new LinkedBinaryTree(yes));
      critD = new LinkedBinaryTree<String>(majDepCopy.removeLast().toString(), new LinkedBinaryTree(no), critE);
      critC = new LinkedBinaryTree<String>(majDepCopy.removeLast().toString(), new LinkedBinaryTree(no), critD);
      critB = new LinkedBinaryTree<String>(majDepCopy.removeLast().toString(), new LinkedBinaryTree(no), critC);
      critA = new LinkedBinaryTree<String>(majDepCopy.removeLast().toString(), new LinkedBinaryTree(no), critB);
      
      return critA; //returns tree
      
    } catch (Exception e) {
      System.out.println(e);
    }
    return null; //returns null if tree can't be made
  }
  
  /** *****************************************************************************
    * Returns the schizophrenia tree.
    * 
    * @return BinaryTree<String> schizophrenia tree
    **************************************************************************** **/
  public BinaryTree<String> getSchiTree() {
    return schizophreniaChecker;
  }
  
  /** *****************************************************************************
    * Returns the major depressive disorder tree.
    * 
    * @return BinaryTree<String> major depressive disorder tree
    **************************************************************************** **/
  public BinaryTree<String> getMajDepTree() {
    return majDepressiveChecker;
  }
  
  /** *****************************************************************************
    * Returns the list of disorders that have been added to the expert.
    * 
    * @returns LinkedList<Disorder> list of disorders added
    **************************************************************************** **/
  public LinkedList<Disorder> getDisorderList() {
    return disorderList;
  }
  
  /** *****************************************************************************
    * Returns the number of disorders that have been added to the expert.
    * 
    * @returns int counter for number of disorders added
    **************************************************************************** **/
  public int getDisorderCount() {
    return disorderList.size();
  }
  
  /** *****************************************************************************
    * Adds the given disorder to the list of disorders for the expert.
    * 
    * @params d, the Disorder to be added
    **************************************************************************** **/
  public void addToList (Disorder d) {
    if (disorderList.contains(d)) 
      System.out.println("The master list already contains this disorder.");
    else 
      disorderList.add(d);
  }
  
  /** *****************************************************************************
    * Traverses a binary tree and uses its recursive properties to check whether 
    * the diagnosis for a certain disorder is fulfilled or not based on the 
    * disorder's criteria.
    * 
    *@params d, Disorder to check
    **************************************************************************** **/
  public void checkDiagnosis(Disorder d) {
    Scanner scan = new Scanner(System.in);
    LinkedBinaryTree current = schizophreniaChecker;
    System.out.println("I will check if the diagnosis is correct.");
    
    //while the tree is not at a leaf
    while(current.size() > 1) {
      System.out.println(current.getRootElement());
      if(scan.nextLine().equalsIgnoreCase("N"))
        current = current.getLeft(); //if N for no, go to left child of current node
      else
        current = current.getRight(); //otherwise, go to right child of current node
    }
    System.out.println(current.getRootElement());
  }
  
  /** *****************************************************************************
    * Returns a String representation of trees the disorder expert holds.
    * 
    * @returns a String representation of disorder expert's trees
    **************************************************************************** **/
  public String toString() {
    return schizophreniaChecker.toString() + "\n" + majDepressiveChecker.toString();
  }
  
  public static void main (String[] args) {
    try {
      DisorderExpert de = new DisorderExpert();
      Disorder schizophrenia = new Disorder("schizophrenia.txt");
      System.out.println(de);
      de.checkDiagnosis(schizophrenia);

    } catch(Exception e) {
      System.out.println(e);
    }
  }
}