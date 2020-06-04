package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	private HashMap<String,Patient> Patients=new HashMap<String,Patient>();
	private HashMap<Integer,Doctor> Doctors=new HashMap<Integer,Doctor>();
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		Patients.put(ssn, new Patient(first,last,ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(!Patients.containsKey(ssn))
			throw new NoSuchPatient();
		return Patients.get(ssn).toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctors.put(docID,new Doctor(first,last,ssn,docID,specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(!Doctors.containsKey(docID))
			throw new NoSuchDoctor();
		return Doctors.get(docID).toString();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if(!this.Patients.containsKey(ssn))
			throw new NoSuchPatient();
		if(!this.Doctors.containsKey(docID))
			throw new NoSuchDoctor();
		Doctor d=this.Doctors.get(docID);
		Patient p=this.Patients.get(ssn);
		d.addPatient(p);
		p.setDoctor(d);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(!this.Patients.containsKey(ssn))
			throw new NoSuchPatient();
		return this.Patients.get(ssn).getDoctor().getBadge();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if(!this.Doctors.containsKey(id))
			throw new NoSuchDoctor();
		return this.Doctors.get(id).getPatients().stream()
				.map(s->s.getSsn()).collect(Collectors.toList());
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		BufferedReader buffer=new BufferedReader(reader);
		String line;
		while((line=buffer.readLine())!=null) {
			StringTokenizer subString=new StringTokenizer(line,";");
			try {
				String token=subString.nextToken();
				if(token.equals("P")) {
					String name=subString.nextToken();
					String surname=subString.nextToken();
					String ssn=subString.nextToken();
					this.addPatient(name, surname, ssn);
				}else if(token.equals("M")) {
					int badge=Integer.parseInt(subString.nextToken());
					String name=subString.nextToken();
					String surname=subString.nextToken();
					String ssn=subString.nextToken();
					String specialization=subString.nextToken();
					this.addDoctor(name, surname, ssn, badge, specialization);
				}
			}catch(NoSuchElementException e) {
				System.err.println("ignoring wron line" + e);
			}catch(NumberFormatException e) {
				System.err.println("ignoring wron line" + e);
			}
		}
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		return this.Doctors.values().stream().filter(s->s.isIdle()).
					sorted((a,b)->{
							int ret;
							if((ret=a.getSurname().compareTo(b.getSurname()))==0)
								return a.getName().compareTo(b.getName());
							return ret;
					}).collect(Collectors.toList()).stream().map(s->s.getBadge()).
					collect(Collectors.toList());
	}
	
	
	private Double getAvgPatientsForDoctors() {
		return this.Doctors.values().stream().
				mapToInt(s->s.getPatients().size()).average().orElse(0);
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		Double AVG=this.getAvgPatientsForDoctors();
		return this.Doctors.values().stream().
				filter(s->s.checkBusy(AVG)).
				collect(Collectors.mapping(s->s.getBadge(), Collectors.toList()));
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		return Doctors.values().stream().
				map(s->String.format("%3d : %s %s %s", s.getPatients().size(), s.getBadge(), s.getName(), s.getSurname())).
				sorted((a,b)->{
					return b.substring(0, 2).compareTo(a.substring(0,2));
				}).collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		return this.Doctors.values().stream().collect(Collectors.
						groupingBy(s->s.getSpecialization(), Collectors.
						summingInt(s->s.getPatients().size()))).entrySet().stream().
						map(s->String.format("%3d - %s", s.getValue(), s.getKey())).
						sorted((a,b)->{
							int ret;
							if((ret=b.substring(0,2).compareTo(a.substring(0,2)))==0)
								return a.substring(6).compareTo(b.substring(6));
							return ret;
						}).collect(Collectors.toList());
	}
	
}
