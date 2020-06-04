package clinic;

public class Patient {
		private String name;
		private String surname;
		private String ssn;
		private Doctor Doctor;
		
		public Patient(String name,String surname,String ssn) {
			this.setName(name);
			this.setSurname(surname);
			this.setSsn(ssn);
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
		
		@Override
		public String toString() {
			return this.surname+" "+this.name+" ("+this.ssn+")";
		}

		public Doctor getDoctor() throws NoSuchDoctor {
			if(this.Doctor==null)
				throw new NoSuchDoctor();
			return Doctor;
		}

		public void setDoctor(Doctor doctor) {
			Doctor = doctor;
		}
}
