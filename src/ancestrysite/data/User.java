package ancestrysite.data;

public class User {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Long userId;
	private String birthDate;
	private String activation;
	private String gender;
	private Long fatherId;
	private Long motherId;
	private boolean ifLogIn=false;
	
	
	public boolean isIfLogIn() {
		return ifLogIn;
	}
	public void setIfLogIn(boolean ifLogIn) {
		this.ifLogIn = ifLogIn;
	}
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}
	public Long getMotherId() {
		return motherId;
	}
	public void setMotherId(Long motherId) {
		this.motherId = motherId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getActivation() {
		return activation;
	}
	public void setActivation(String activation) {
		this.activation = activation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
