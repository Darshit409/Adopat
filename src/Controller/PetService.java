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
	private UserService userService = null;
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
		String printingAllPet ="Select pet.petId, pet.petName,pet.species, pet.adoptionPrice, user.FirstName, pet.userId, user.LastName from  pet, user where pet.userId = user.id;";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(printingAllPet);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			
			pet newPet = new pet(resultSet.getInt("petId"),resultSet.getString("petName"),resultSet.getString("species"),resultSet.getInt("adoptionPrice"),resultSet.getInt("userId"), name);
			petList.add(newPet);
		}
		resultSet.close();
		return petList;
		
	}
	public List<pet> printingMyPets(Connection connect, User user) throws SQLException {
		List<pet> petList = new ArrayList<pet>();
		String printingMyPets = "Select pet.petId, pet.petName, pet.species, pet.adoptionPrice, pet.birthDate from pet where pet.userId =" + user.id;
		preparedStatement = (PreparedStatement) connect.prepareStatement(printingMyPets);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {			
			pet newPet = new pet(resultSet.getInt("petId"),resultSet.getString("petName"),resultSet.getString("species"),resultSet.getString("birthDate"),resultSet.getInt("adoptionPrice"));
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
		String FindByTraits = "select pet.petId, pet.petName,pet.species, pet.adoptionPrice, user.firstName, pet.userId, user.LastName from pet, user where pet.petId in"
				+ "(select petId from pettraits where traits = ?) and user.id = pet.userId order by pet.adoptionPrice desc"; 
		preparedStatement = (PreparedStatement) connect.prepareStatement(FindByTraits);
		preparedStatement.setString(1, trait);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			pet newPet = new pet(resultSet.getInt("petId"),resultSet.getString("petName"),resultSet.getString("species"),resultSet.getInt("adoptionPrice"),resultSet.getInt("userId"), name);
			petList.add(newPet);
		}
		resultSet.close();
		return petList;
	}
	public boolean insertReview(Connection connect, int petId, String reviewCategory, String comment, User user) throws SQLException {
		String insertReview = "INSERT INTO reviews(userId, petId, ReviewCategory, Comment) Values (?,?,?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(insertReview);
		preparedStatement.setInt(1, user.getId());
		preparedStatement.setInt(2, petId);
		preparedStatement.setString(3, reviewCategory);
		preparedStatement.setString(4, comment);
		 boolean rowInserted = preparedStatement.executeUpdate() > 0;
		return rowInserted;
		
	}
	public pet FindByPetId(Connection connect, int petId) throws SQLException {
		String findBypetId = "Select * from pet where petId = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(findBypetId);
		preparedStatement.setInt(1, petId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
            int petID = resultSet.getInt("petId");
            String PetName = resultSet.getString("petName");
            String species = resultSet.getString("species");
            String birthDate = resultSet.getString("birthDate");
            int adoptionPrice = resultSet.getInt("AdoptionPrice");
            pet pet = new pet(petID, PetName, species, birthDate, adoptionPrice);
            return pet;
		}
		return null;

	}
	public List<pet> findPetReviewBypetID(Connection connect, int petId) throws SQLException {
		List<pet> petList = new ArrayList<pet>();
		String findPetReviewBypetID = "SELECT reviews.userId, reviews.Comment, reviews.ReviewCategory, user.FirstName"
				+ ", user.Lastname from reviews, user where reviews.petId = ? and user.id = reviews.userId";
		preparedStatement = (PreparedStatement) connect.prepareStatement(findPetReviewBypetID);
		preparedStatement.setInt(1, petId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			pet newPet = new pet();
			newPet.setuserName(name);
			newPet.setUserId(resultSet.getInt("userId"));
			newPet.setReviewCategory(resultSet.getString("ReviewCategory"));
			newPet.setComment(resultSet.getString("Comment"));
			petList.add(newPet);
		 }
		resultSet.close();
		return petList;
	}
	public List<pet> FavPetList(Connection connect, int id) throws SQLException {
		List<pet> petList = new ArrayList<pet>();
		String FavPetList = "SELECT pet.*, user.FirstName, user.LastName FROM pet"
				+ ", user where petId in (select favroitepets.petId from favroitepets where"
				+ " favroitepets.userId = ?) and user.id = pet.userId;";
		preparedStatement = (PreparedStatement) connect.prepareStatement(FavPetList);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			String name = resultSet.getString("FirstName") +"  " + resultSet.getString("LastName");
			pet newPet = new pet(resultSet.getInt("petId"),resultSet.getString("petName"),resultSet.getString("species"),resultSet.getInt("adoptionPrice"),resultSet.getInt("userId"), name);
			petList.add(newPet);
		}
		
		resultSet.close();
		return petList;
		}
	public boolean DeletePetFav(Connection connect, int petId, int id) throws SQLException {
		String DeletePetFav = "Delete from favroitepets where petId = ? and userId = ?";
		String CheckPetFav = "Select * from favroitepets where petId = ? and userId = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(CheckPetFav);
		preparedStatement.setInt(1, petId);
		preparedStatement.setInt(2, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()){
				preparedStatement = (PreparedStatement) connect.prepareStatement(DeletePetFav);
				preparedStatement.setInt(1, petId);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				return true;
		}
		else
		{
				return false;
		}
	}
	public boolean DeleteUserFav(Connection connect, int userId, int id) throws SQLException {
		String DeleteuserFav = "Delete from favroiteuser where userId = ? and FavUserId = ?";
		String CheckuserFav = "Select * from favroiteuser where userId = ? and FavUserId = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(CheckuserFav);
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
				preparedStatement = (PreparedStatement) connect.prepareStatement(DeleteuserFav);
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				return true;
			}
		else
		{
				return false;
		}
	}
}



