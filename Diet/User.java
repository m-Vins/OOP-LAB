package diet;


/**
 * Represent a take-away system user
 *  
 */
public class User {
	private String FirstName;
	private String LastName;
	private String Email;
	private String Phone;
	
	public User(String firstname,String lastname, String email, String phone) {
		this.FirstName=firstname;
		this.LastName=lastname;
		this.Email=email;
		this.Phone=phone;
	}
	/**
	 * get user's last name
	 * @return last name
	 */
	public String getLastName() {
		return this.LastName;
	}
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return this.FirstName;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return this.Email;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return this.Phone;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		this.Email=email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		this.Phone=phone;
	}
	
	@Override
	public String toString() {
		return this.getFirstName()+" "+this.getLastName();
	}
}
