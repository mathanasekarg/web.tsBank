package model;

public class UserModel {
	private String username,dob,address,email,password,rePassword,mobile,postalcode;
	private static String permissions;
	public UserModel(long userId,String password, String rePassword) {
		super();
		UserModel.userId = userId;
		this.password = password;
		this.rePassword = rePassword;
	}
	private int country,state,profession,category,gender;
	private boolean marriedStatus;
	static long userId;
	long bankId;
	String country_value,state_value,preofession_value,category_value,gender_value,created_at,updated_at;
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getCountry_value() {
		return country_value;
	}
	public void setCountry_value(String country_value) {
		this.country_value = country_value;
	}
	public String getState_value() {
		return state_value;
	}
	public void setState_value(String state_value) {
		this.state_value = state_value;
	}
	public String getPreofession_value() {
		return preofession_value;
	}
	public void setPreofession_value(String preofession_value) {
		this.preofession_value = preofession_value;
	}
	public String getCategory_value() {
		return category_value;
	}
	public void setCategory_value(String category_value) {
		this.category_value = category_value;
	}
	public String getGender_value() {
		return gender_value;
	}
	public void setGender_value(String gender_value) {
		this.gender_value = gender_value;
	}
	public long getUserId() {
		return userId;
	}
	public static void setUserId(long userId) {
		UserModel.userId = userId;
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public UserModel(String username, String dob, String address, int gender, String mobile, String email, boolean marriedStatus , int country, int state, String postalcode, int profession,
			int category, String permissions, String password,
			String rePassword) {
		super();
		this.username = username;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.rePassword = rePassword;
		this.country = country;
		this.state = state;
		this.mobile = mobile;
		this.postalcode = postalcode;
		this.profession = profession;
		this.marriedStatus = marriedStatus;
		UserModel.permissions = permissions;
		this.category = category;
	}
	public UserModel(long userId, String username, String dob, String address, int gender, String mobile, String email, boolean marriedStatus , int country, int state, String postalcode, int profession,
			int category, String permissions, String password,
			String rePassword) {
		super();
		UserModel.userId = userId;
		this.username = username;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.rePassword = rePassword;
		this.country = country;
		this.state = state;
		this.mobile = mobile;
		this.postalcode = postalcode;
		this.profession = profession;
		this.marriedStatus = marriedStatus;
		UserModel.permissions = permissions;
		this.category = category;
	}
	public UserModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public UserModel() {
		// TODO Auto-generated constructor stub
	}
	public UserModel(long userId, String username, String dob, String address, int gender, String mobile, String email, boolean marriedStatus , int country, int state, String postalcode, int profession,
			int category, String permissions) {
		super();
		UserModel.userId = userId;
		this.username = username;
		this.dob = dob;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.country = country;
		this.state = state;
		this.mobile = mobile;
		this.postalcode = postalcode;
		this.profession = profession;
		this.marriedStatus = marriedStatus;
		UserModel.permissions = permissions;
		this.category = category;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
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
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public int getProfession() {
		return profession;
	}
	public void setProfession(int profession) {
		this.profession = profession;
	}
	public boolean getMarriedStatus() {
		return marriedStatus;
	}
	public void setMarriedStatus(boolean marriedStatus) {
		this.marriedStatus = marriedStatus;
	}
	public String getPermissions() {
		return permissions;
	}
	public static void setPermissions(String permissions) {
		UserModel.permissions = permissions;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
}
