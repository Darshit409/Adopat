package Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import model.User;
import model.pet;

public class PetService {
	private PreparedStatement preparedStatement = null;
	public boolean insert(pet Pet, Connection connect) throws SQLException {
		String petInsert = "insert into  pet(petName, species, birthDate, adoptionPrice, userId) values (?, ?, ?, ?, ?)";
		String petTraitInsert = "insert into pettraits(petId, traits) values (?,?)";
		String findPetById = "Select petID from pet where petName = ? and birthDate = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(petInsert);
		preparedStatement.setString(1,  Pet.petName);
		preparedStatement.setString(2, Pet.species);
		preparedStatement.setString(3, Pet.birthDate);
		preparedStatement.setInt(4, Pet.adoptionPrice);
		preparedStatement.setInt(5, Pet.userId);
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        
        //Getting petId
        preparedStatement = (PreparedStatement) connect.prepareStatement(findPetById);
        preparedStatement.setString(1, Pet.getpetName());
        preparedStatement.setString(2, Pet.getBirthDate());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Pet.setId(resultSet.getInt(1));
        String[] Trait = Pet.getTrait();
		for(int i = 0; i < Trait.length; i++ ) {
			preparedStatement = (PreparedStatement) connect.prepareStatement(petTraitInsert);
			preparedStatement.setInt(1,  Pet.getpetId());
			preparedStatement.setString(2, Trait[i]);
			preparedStatement.executeUpdate();
		}
		preparedStatement.close();
        return rowInserted;
        
    }
	public List<pet> printingAllpets(Connection connect) throws SQLException {
		List<pet> petList = new ArrayList<pet>();
		String printingAllPet ="Select pet.petName,pet.species, pet.adoptionPrice, user.FirstName, pet.userId, user.LastName from  pet, user where pet.userId = user.id;";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(printingAllPet);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			
			pet newPet = new pet(resultSet.getString("petName"),resultSet.getString("species"),resultSet.getInt("adoptionPrice"),resultSet.getInt("userId"), name);
			petList.add(newPet);
		}
		resultSet.close();
		return petList;
		
	}
	

	public List<pet> printingMyPets(Connection connect, User user) throws SQLException {
		List<pet> petList = new ArrayList<pet>();
		String printingMyPets = "Select pet.petName, pet.species, pet.adoptionPrice, pet.birthDate from pet where pet.userId =" + user.id;
		preparedStatement = (PreparedStatement) connect.prepareStatement(printingMyPets);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {			
			pet newPet = new pet(resultSet.getString("petName"),resultSet.getString("species"),resultSet.getString("birthDate"),resultSet.getInt("adoptionPrice"));
			petList.add(newPet);
		}
		resultSet.close();
		return petList;
	}

	public List<String> petTraitList(Connection connect) throws SQLException {
		List<String> TraitList = new ArrayList<String>();
		String petTraitList = "Select distinct Traits From pettraits;";
		preparedStatement = (PreparedStatement) connect.prepareStatement(petTraitList);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			TraitList.add(resultSet.getString("traits"));
		}
		resultSet.close();
		return TraitList;
	}
	
	public List<pet> FindByTraits(Connection connect, String trait) throws SQLException{
		List<pet> petList = new ArrayList<pet>();
		String FindByTraits = "select pet.petName,pet.species, pet.adoptionPrice, user.firstName, pet.userId, user.LastName from pet, user where pet.petId in"
				+ "(select petId from pettraits where traits = ?) and user.id = pet.userId"; 
		preparedStatement = (PreparedStatement) connect.prepareStatement(FindByTraits);
		preparedStatement.setString(1, trait);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			pet newPet = new pet(resultSet.getString("petName"),resultSet.getString("species"),resultSet.getInt("adoptionPrice"),resultSet.getInt("userId"), name);
			petList.add(newPet);
		}
		resultSet.close();
		return petList;
	}
}



