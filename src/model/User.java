package model;


public class User {
    public int id;
    public String UserName;
    public String Password;     
    public String FirstName;
    public String LastName;
    public String Email;
    public int petCounts; 
    public User(int id) {
        this.id = id;
    }
 
    public User(String userName2, String password2, String firstName2, String lastName2, String email2) {
     	this.UserName = userName2;
    	this.Password = password2;
    	this.FirstName = firstName2;
    	this.LastName = lastName2;
    	this.Email = email2;	}
	public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getUserName() {
        return UserName;
    }
 
    public void setUserName(String name) {
        this.UserName = name;
    }
 
    public String getPassword() {
        return Password;
    }
 
    public void setPassword(String pass) {
        this.Password = pass;
    }
 
    public String getFirstName() {
        return FirstName;
    }
 
    public void setFirstName(String firstname) {
        this.FirstName = firstname;
    }
    public String getLastName() {
        return LastName;
    }
 
    public void setLastName(String firstname) {
        this.LastName = firstname;
    }
    public String getEmail() {
        return Email;
    }
 
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setPetCounts(int petCounts) {
    	this.petCounts = petCounts;
    }
    public int getPetCounts() {
    	return petCounts;
    }
    public void incrementPetCounts(int petCounts) {
    	this.petCounts = petCounts + 1;
    }
}