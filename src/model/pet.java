package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pet {
    public int petId;
    public String petName;
    public String species;
    public String birthDate;
    public int adoptionPrice;
    public String Traits;
    public String[] Trait;
    public int userId;
    public String userName;
    public pet(int id) {
        this.petId = id;
    }
    
    public pet(String petName, String species, int AdoptionPrice,int id, String user) {
    	this.petName = petName;
    	this.species = species;
    	this.adoptionPrice = AdoptionPrice;
    	this.userName = user;
    	this.userId = id;
    }
 
    public pet(String PetName, String species, String birthDate, int AdoptionPrice, String Traits, int userId){
     	this.petName = PetName;
    	this.species = species;
    	this.birthDate = birthDate;
    	this.adoptionPrice = AdoptionPrice;
    	this.Traits = Traits;
    	setTrait(Traits);
    	this.userId = userId;
    	}
	public pet(String PetName, String species, String birthDate, int AdoptionPrice) {
		this.petName = PetName;
    	this.species = species;
    	this.birthDate = birthDate;
    	this.adoptionPrice = AdoptionPrice;
	}

	public int getpetId() {
        return petId;
    }
 
    public void setId(int id) {
        this.petId = id;
    }
 
    public String getpetName() {
        return petName;
    }
 
    public void setUserName(String name) {
        this.petName = name;
    }
 
    public String getSpecies() {
        return species;
    }
 
    public void setSpecies(String pass) {
        this.species = pass;
    }
 
    public String[] getTrait() {
        return Trait;
    }
 
    public void setTrait(String Traits) {
       this.Trait = Traits.split("\\W+");
    }
    public String getBirthDate() {
        return birthDate;
    }
 
    public void setbirthDate(String birthDate)  {
        this.birthDate = birthDate;
    }
    public int getuserId() {
        return userId;
    }
 
    public void setUserId(int El) {
        this.userId = El;
    }
    public String getuserName()
    {
    	return userName;
    }
    public int getAdoptionPrice() {
    	return adoptionPrice;
    }
    public void setAdoptionPrice(String price) {
    	this.adoptionPrice = Integer.parseInt(price);
    	}
}