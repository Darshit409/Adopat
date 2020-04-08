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
 
    public pet(int id) {
        this.petId = id;
    }
 
    public pet(String PetName, String species, String birthDate, int AdoptionPrice, String Traits, int userId){
     	this.petName = PetName;
    	this.species = species;
    	this.birthDate = birthDate;
    	this.adoptionPrice = AdoptionPrice;
    	this.Traits = Traits;
    	setTrait(Traits);
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
    public int getuserID() {
        return userId;
    }
 
    public void setUserID(int El) {
        this.userId = El;
    }
    public int getAdoptionPrice() {
    	return adoptionPrice;
    }
    public void setAdoptionPrice(String price) {
    	this.adoptionPrice = Integer.parseInt(price);
    	}
}