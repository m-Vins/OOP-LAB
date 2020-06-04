package clinic;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
	private String name;
	private String surname;
	private String ssn;
	private int badge;
	private String specialization;
	private List<Patient> Patients=new ArrayList<Patient>();
	
	public Doctor(String name,String surname,String ssn,int badge,String specialization) {
		this.setName(name);
		this.setSurname(surname);
		this.setSsn(ssn);
		this.setBadge(badge);
		this.setSpecialization(specialization);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}
	
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	
	@Override
	public String toString() {
		return this.surname+" "+this.name+" ("+
				this.ssn+") ["+this.badge+"]: "+this.specialization;
	}
	
	
	public void addPatient(Patient patient) {
		this.Patients.add(patient);
	}
	
	public List<Patient> getPatients(){
		return this.Patients;
	}
	
	public boolean isIdle() {
		return Patients.size()==0 ? true:false;
	}
	
	public boolean checkBusy(double AVG) {
		return this.Patients.size()>AVG;
	}
}
