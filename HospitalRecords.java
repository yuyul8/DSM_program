/* FILENAME: HospitalRecords.java
 * AUTHOR: Meltem Ozcan
 * LAST MODIFIED: 12/18/2014
 * 
 * PURPOSE: Creates a hospital patient database and holds patient objects. 
 * 
 * */

import java.util.*;

/** *****************************************************************************
  * A HospitalRecords class holds a collection of Patient objects and allows functionality
  * regarding these objects
  * 
  * @author Meltem Ozcan
  *************************************************************************** **/
public class HospitalRecords { 
  
  //instance variables
  private String hospitalName;
  protected LinkedList<Patient> hospitalRecord = new LinkedList<Patient>();
  
  /** *****************************************************************************
    * Constructor
    * 
    * Takes in a name to create a hospital database that will later be filled with 
    * patient objects
    * 
    * @params name
    **************************************************************************** **/
  public HospitalRecords (String name) {
    hospitalName = name;
  }
  
  
    /** *****************************************************************************
    * Adds patient to the database
    * @params patientToAdd
    **************************************************************************** **/
  public void addPatient(Patient patientToAdd) {
    
    //if the patient had not previously been added, enters the patient to the hospital records.
    if (!isPatient(patientToAdd.getName())) {
      hospitalRecord.add(patientToAdd);
    }
    
  }

    
    /** *****************************************************************************
    * Removes patient from the database. We are assuming that no two people with
   * the exact same name will become patients at this hospital at the same time.
    * @params patientToRemove
    **************************************************************************** **/
  public void removePatient(String patientToRemove) {
    
    for(int i = 0; i < hospitalRecord.size(); i++) {
      //checks for the patient with a matching name
      if (hospitalRecord.get(i).getName().equals(patientToRemove)){
        // System.out.println("removing " + hospitalRecord.get(i));
        hospitalRecord.remove(hospitalRecord.get(i));
      }
    }
  }
 
      /** *****************************************************************************
    *Returns the patient with the given name, returns null if no such patient exists
    * @params patientToGet
    **************************************************************************** **/
  public Patient getPatient(String patientToGet) {
    
    for(int i = 0; i < hospitalRecord.size(); i++) {
      if (hospitalRecord.get(i).getName().equals(patientToGet)) {
        return hospitalRecord.get(i);
      }
    }
    return null;
  }
  
   /** *****************************************************************************
    *Returns the number of patients
    **************************************************************************** **/
  public int getNumPatients() {
    return hospitalRecord.size();
  }
  

   /** *****************************************************************************
    *Returns  linked list of patients with a given disorder. Currently assumes each patient
  *can only be diagnosed with a single disorder
    * @params disorderName
    **************************************************************************** **/
  public LinkedList<Patient> getPatientsWithDisorder(String disorderName) {
    LinkedList<Patient> patientsWithDisorder = new LinkedList<Patient>();
    for(int i = 0; i < hospitalRecord.size(); i++) {
      if (hospitalRecord.get(i).getDiagnosis().equals(disorderName)) {
        patientsWithDisorder.add(hospitalRecord.get(i));
      }
    }
    return patientsWithDisorder;
  }
  
  /** *****************************************************************************
    *Returns  linked list of patients in the given age range
    * @params lowerBound
    * @params upperBound
    **************************************************************************** **/
  public LinkedList<Patient> getPatientsOfAge(int lowerBound, int upperBound) {
    LinkedList<Patient> patientsOfGivenAge = new LinkedList<Patient>();
    for(int i = 0; i < hospitalRecord.size(); i++) {
      int age = hospitalRecord.get(i).getAge();
      if ((age >= lowerBound) && (age <= upperBound)) {
        patientsOfGivenAge.add(hospitalRecord.get(i));
      }
    }
    return patientsOfGivenAge;
  }
  
   /** *****************************************************************************
    *Returns  linked list of patients in the given gender
    * @params gender
    **************************************************************************** **/
  public LinkedList<Patient> getPatientsOfGender(String gender) {
    LinkedList<Patient> patientsOfGender = new LinkedList<Patient>();
    for(int i = 0; i < hospitalRecord.size(); i++) {
      
      if (hospitalRecord.get(i).getGender().equals(gender)) {
        patientsOfGender.add(hospitalRecord.get(i));
      }
    }
    return patientsOfGender;
  }
  
  /** *****************************************************************************
    * Provides a string representation of the hospital
    **************************************************************************** **/
  public String toString () {
    String s = "\n" + hospitalName.toUpperCase() + " RECORDS" + "\n\nTotal number of patients: " + getNumPatients() + /*and here can go other 
     * statistics like # of females vs # of male etc. */"\n\n";
    for (int i = 0; i < getNumPatients(); i++) 
      s += hospitalRecord.get(i)+ "\n\n";
    
    return s;
  }
  
   /** *****************************************************************************
    * This method returns a string array representation of a given List of Patients
    * @params patients
    **************************************************************************** **/
  public String[] toStringArray (LinkedList<Patient> patientList) {
    String[] arrayPatients = new String[patientList.size()];
    for (int i = 0; i < patientList.size(); i++) 
      arrayPatients[i] = patientList.get(i).toString();
    
    return arrayPatients;
  }
     /** *****************************************************************************
    * This method returns a string array representation of a given list of patients and
    * adds HTML tags which will be used to wrap the text in the GUI
    * @params patientList
    **************************************************************************** **/
  public String[] toStringArrayWithHtml (LinkedList<Patient> patientList) {
    String[] arrayPatients = new String[patientList.size()];
    for (int i = 0; i <patientList.size(); i++) 
      arrayPatients[i] = "<html><p>" + patientList.get(i).toStringWithHtml() + "</p></hmtl>";
    
    return arrayPatients;
  }
  
 
  /** *****************************************************************************
    *  This method returns a string array representation of the hospital and adds HTML 
    * tags which will be used to wrap the text in the GUI.
    **************************************************************************** **/
  public String[] toStringArrayWithHtml () {
    String[] arrayPatients = new String[getNumPatients()];
    for (int i = 0; i < getNumPatients(); i++) 
      arrayPatients[i] = "<html><p>" + hospitalRecord.get(i).toStringWithHtml() + "</p></hmtl>";
    
    return arrayPatients;
    
  }
  
  
  /** *****************************************************************************
    * returns boolean determining if the patient with the given name is already a patient
    * @ params patientName
    **************************************************************************** **/
  public Boolean isPatient(String patientName) {
    
    boolean isPatient = false;//boolean to check if patient had already been added
    
    for(int i = 0; i < getNumPatients(); i++) {
      if (!isPatient) {
        //checks for a patient with a matching name
        if (hospitalRecord.get(i).getName().equals(patientName)){
          isPatient = true;//if such patient is found, update boolean
          break;//break out of for loop
        }
      }
    }
    return isPatient;
  }
  
  
  /** *****************************************************************************
    * provides testing code for the HospitalRecords class
    * @ params args[]
    **************************************************************************** **/
  public static void main (String[] args) {
    
    HospitalRecords BMC = new HospitalRecords("Boston Medical Center");
    BMC.addPatient(new Patient("John Smith", 36, "male", "major depressive disorder", "diminished interest,weight loss,sense of hopelessness,early wakening"));
    BMC.addPatient(new Patient("Carry Roy", 21, "female", "bipolar disorder", "manic episode,overtalkativeness,reduced need for sleep"));
    System.out.println(BMC);
    System.out.println("Testing method isPatient('John Smith') (TRUE): " + BMC.isPatient("John Smith"));
    System.out.println("Testing getPatient('John Smith') \n" + BMC.getPatient("John Smith"));
    BMC.removePatient("John Smith");
    System.out.println("\nTesting removePatient('John Smith'):"+ BMC);
    BMC.removePatient("Johnny Smith");
    System.out.println("Testing removePatient('Johnny Smith'), a nonexistent patient (no change expected):\n"+ BMC);
    System.out.println("Testing method isPatient('John Smith') (FALSE): " + BMC.isPatient("John Smith"));
    
    //Adding John Smith back
    BMC.addPatient(new Patient("John Smith", 36, "male", "major depressive disorder", "diminished interest,weight loss,sense of hopelessness,early wakening"));
    
    System.out.println("Attempt to add duplicate patients - add patient Carry Roy again");
    BMC.addPatient(new Patient("Carry Roy", 21, "female", "bipolar disorder", "manic episode,overtalkativeness,reduced need for sleep"));
    System.out.println(BMC);
    
    //Adding more patients
    BMC.addPatient(new Patient("Mary Miller", 25, "female", "major depressive disorder", "diminished interest,weight gain,sense of hopelessness, suicidal thoughts"));
    BMC.addPatient(new Patient("Harry Taylor", 21, "male", "schizophrenia", "delusions, hallucinations, reduced affect"));
    System.out.println(BMC);
    
    System.out.println("Testing getPatientsWithDisorder('major depressive disorder'):\n" + BMC.getPatientsWithDisorder("major depressive disorder") + "\n");
    
    System.out.println("Testing getPatientsOfAge('21,21'):\n" + BMC.getPatientsOfAge(21,21) + "\n");
    System.out.println("Testing getPatientsOfAge('25,40'):\n" + BMC.getPatientsOfAge(25,40) + "\n");
    System.out.println("Testing getPatientsOfAge('10,18'):\n" + BMC.getPatientsOfAge(10,18) + "\n");
    
    System.out.println("Testing getPatientsOfGender('female'):\n" + BMC.getPatientsOfGender("female") + "\n");
    
    
  }
  
  
}