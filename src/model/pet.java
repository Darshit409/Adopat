package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pet {
    public int petId;
    public String petName;
    public String species;
    public Date birthDate;
    public int adoptionPrice;
    public String Trait[];
    public int userId;
 
    public pet(int id) {
        this.petId = id;
    }
 
    public pet(String PetName, String species, Date birthDate, int AdoptionPrice, String Trait[], int userId) {
     	this.petName = PetName;
    	this.species = species;
    	this.birthDate = birthDate;
    	this.adoptionPrice = AdoptionPrice;
    	this.Trait = Trait;
    	this.userId = userId;}
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
 
    public String[] getTraits() {
        return Trait;
    }
 
    public void setTrait(String[] Trait) {
        this.Trait = Trait;
    }
    public Date getBirthDate() {
        return birthDate;
    }
 
    public void setbirthDate(String birthDate) throws ParseException {
        this.birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(birthDate);
    }
    public int getuserID() {
        return userId;
    }
 
    public void setUserID(int El) {
        this.userId = El;
    }
}