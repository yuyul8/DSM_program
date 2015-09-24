/* FILENAME: Patient.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a patient with information such as symptoms and demographic 
 * information.
 * 
 * */

import java.util.*;

/** *****************************************************************************
  * A Patient class is used to create Patient objects that will hold information on
  * the patients at the hospital
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class Patient {
  
  //instance variables 
  private String name;
  private int patientNumber;
  private int age;
  private String gender;
  private String diagnosis;
  private LinkedList<String> symptomsLL = new LinkedList<String>();
  
  final int MIN = 1000; //minimum value for patient number (arbitrary)
  final int MAX = 3000; //maximum value for patient number (arbitrary)
  
  /** *****************************************************************************
    * Constructor
    * 
    * Takes in a name, age, gender, diagnosis and symptoms and makes a new patient with 
    * the provided information.
    * 
    * @params namePatient
    * @params agePatient
    * @params genderPatient
    * @params diagnosisPatient
    * @params symptomsPatient
    **************************************************************************** **/
  public Patient (String namePatient, int agePatient, String genderPatient, String diagnosisPatient, String symptomsPatient) {
    name = namePatient;
    
    //Generates a random number that will be assigned to the patient as their unique patient number to 
    //achieve a more realistic representation than using the patient counter. The possibility of the 
    //same number being assigned exists, but is very low.
    Random generator = new Random();
    patientNumber = generator.nextInt(MAX-MIN)+MIN; 
    
    age = agePatient;
    gender = genderPatient;
    diagnosis = diagnosisPatient;
    
    String[] symptomsArray = symptomsPatient.split(",");
    
    
    //adds the symptoms inputted into the linked list
    for (int i = 0; i < symptomsArray.length; i++) 
      symptomsLL.add(symptomsArray[i]);
    
  }
  
  /*SETTERS*/
  
  /** *****************************************************************************
    * sets the name of the patient 
    * @params newName
    **************************************************************************** **/
  public void setName(String newName) {
    name = newName;
  }
  
  /** *****************************************************************************
    * sets the age of the patient 
    * @params newAge
    **************************************************************************** **/
  public void setAge(int newAge) {
    age = newAge;
  }
  
  /** *****************************************************************************
    * sets the gender of the patient 
    * @params newGender
    **************************************************************************** **/
  public void setGender(String newGender) {
    gender = newGender;
  }
  
  /** *****************************************************************************
    * sets the symptoms presented by the patient 
    * @params newSymptoms
    **************************************************************************** **/
  public void setSymptoms(String newSymptoms) {
    LinkedList<String> tempSymptomsLL = new LinkedList<String>();
    String[] symptomsArray = newSymptoms.split(",");
    for (int i = 0; i < symptomsArray.length; i++) {
      tempSymptomsLL.add(symptomsArray[i]);
    }
    symptomsLL = tempSymptomsLL;
  }
  
  /** *****************************************************************************
    * sets the diagnosis of the patient 
    * @params newDiagnosis
    **************************************************************************** **/
  public void setDiagnosis(String newDiagnosis) {
    diagnosis = newDiagnosis;
  }
  
  
  /*GETTERS*/
  
  /** *****************************************************************************
    * Returns the name of the patient 
    **************************************************************************** **/
  public String getName() {
    return name;
  }
  
  /** *****************************************************************************
    * Returns the number of the patient (no setter method for patient number is required)
    **************************************************************************** **/
  public int getPatientNumber() {
    return patientNumber;
  }
  
  /** *****************************************************************************
    * Returns the age of the patient 
    **************************************************************************** **/
  public int getAge() {
    return age;
  }
  
  /** *****************************************************************************
    * Returns the gender of the patient 
    **************************************************************************** **/
  public String getGender() {
    return gender;
  }
  
  /** *****************************************************************************
    * Returns the diagnosis of the patient 
    **************************************************************************** **/
  public String getDiagnosis() {
    return diagnosis;
  }
  
  /** *****************************************************************************
    * Returns the symptoms presented by the patient 
    **************************************************************************** **/
  public LinkedList getSymptoms() {
    return symptomsLL;
  }
  
  //other methods
  
  /** *****************************************************************************
    * Method to add syptoms to a patient.
    * @params symptomsToAdd
    **************************************************************************** **/
  public void addSymptoms(String symptomsToAdd) {
    
    //splits the string of symptoms by commas
    String[] symptomsAddArray = symptomsToAdd.split(",");
    
    for (int i = 0; i < symptomsAddArray.length; i++) {
      if(!(symptomsLL.contains(symptomsAddArray[i])))
        symptomsLL.add(symptomsAddArray[i]);
    }
  }
  /** *****************************************************************************
    * Method to remove syptoms from a patient's file.
    * @params symptomsToRemove
    **************************************************************************** **/
  public void removeSymptoms(String symptomsToRemove) {
    
    String[] symptomsRemoveArray = symptomsToRemove.split(",");
    
    for (int i = 0; i < symptomsRemoveArray.length; i++) {
      if((symptomsLL.contains(symptomsRemoveArray[i])))
        symptomsLL.remove(symptomsRemoveArray[i]);
    }
  }
  
  /** *****************************************************************************
    * Method to add additional diagnosis
    * @params diagnosisToAdd
    **************************************************************************** **/
  public void addDiagnosis(String diagnosisToAdd) {
    
    //assuming clinician is not adding the same diagnosis
    diagnosis += ", " + diagnosisToAdd;
    
  }
  
  /** *****************************************************************************
    * Returns a string representation of the patient's information
    * @params symptomsToAdd
    **************************************************************************** **/
  public String toString() {
    String s = "\nName: " + name + "\nPatient number: " + patientNumber + "\nAge: " + age + 
      "\nGender: " + gender +"\nDiagnosis: " + diagnosis +"\nSymptoms: " + symptomsLL;
    return s;
  }
  
  /** *****************************************************************************
    * Returns a string representation of the patient's information with HTML tags to be used in the GUI
    * @params symptomsToAdd
    **************************************************************************** **/
  public String toStringWithHtml() {
    String s = "<html><p>\nName: " + name + "</p><p>\nPatient number: " + patientNumber + "</p><p>\nAge: " + age + 
      "</p><p>\nGender: " + gender +"</p><p>\nDiagnosis: " + diagnosis +"</p><p>\nSymptoms: " + symptomsLL+ "</p></html>";
    return s;
  }
  
  /** *****************************************************************************
    * Provides testing code
    * @params args
    **************************************************************************** **/
  public static void main (String[] args) {
    
    System.out.println("\nTESTING patient.java\n");
    
    Patient p1 = new Patient ("John Smith", 36, "male", "major depressive disorder", "diminished interest,weight loss,sense of hopelessness,early wakening");
    
    System.out.println("Josh Smith's initial file: \n" + p1 + "\n");
    
    System.out.println("Testing getters and setters\n");
    
    System.out.println("getName(): "+ p1.getName());
    System.out.println("getPatientNumber(): "+ p1.getPatientNumber());
    System.out.println("getAge(): "+ p1.getAge());
    System.out.println("getGender(): "+ p1.getGender());
    System.out.println("getDiagnosis(): "+ p1.getDiagnosis());
    System.out.println("getSymptoms(): "+ p1.getSymptoms());
    p1.setName("Joshua Smith");
    System.out.println("setName() to Joshua Smith: ");
    System.out.println("getName(): " +  p1.getName());
    p1.setAge(37);
    System.out.println("setAge() to 37 \ngetName(): " +  p1.getAge());
    p1.setDiagnosis("bipolar disorder");
    System.out.println("setDiagnosis() to bipolar disorder \ngetDisorder(): " +  p1.getDiagnosis()+ "\n");
    
    System.out.println("Josh Smith's updated file: \n" + p1 + "\n");
    
    System.out.println("Testing other methods\n");
    
    System.out.println("Add diagnosis of major depressive disorder.");
    p1.addDiagnosis("major depressive disorder");
    System.out.println("getDiagnosis(): " + p1.getDiagnosis());
    System.out.println("Remove symptom of 'sense of hopelessness', 'weight loss'.");
    p1.removeSymptoms("sense of hopelessness,weight loss");
    System.out.println(p1.getSymptoms());
    System.out.println("Add symptom 'sense of guilt'");
    p1.addSymptoms("sense of guilt");
    System.out.println(p1.getSymptoms());
    System.out.println("Remove symptom 'stomach ache' not displayed by the patient (no change expected)");
    p1.removeSymptoms("stomach ache");
    System.out.println(p1.getSymptoms());
    System.out.println("Add symptom 'early wakening' already presented by the patient (no change expected)");
    p1.addSymptoms("early wakening");
    
    System.out.println(p1.getSymptoms());
    
  }
}
