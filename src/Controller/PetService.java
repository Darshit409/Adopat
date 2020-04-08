package Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}


