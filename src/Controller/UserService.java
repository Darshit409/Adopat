package Controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.pet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UserService {
	private PreparedStatement preparedStatement = null;
	 private PreparedStatement statement = null;
	private static String Search = "Select * from user where UserName=? and Password=?";
	private static String CheckPetFav = "Select * from favroitepets where petId = ? and userId = ?";
	private static String CheckuserFav = "Select * from favroiteuser where userId = ? and FavUserId = ?";

	
	public boolean insert(User people, Connection connect) throws SQLException {
		String sql = "insert into  user(UserName, Password, FirstName, LastName, Email, petCounts) values (?, ?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1,  people.UserName);
		preparedStatement.setString(2, people.Password);
		preparedStatement.setString(3, people.FirstName);
		preparedStatement.setString(4, people.LastName);
		preparedStatement.setString(5, people.Email);
		preparedStatement.setInt(6, 0);
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowInserted;
    }
	
	public User Login(String UserName, String Password, Connection connect) throws SQLException {
	//	String Sear = "Select ? from user where UserName=? and Password=?";
		preparedStatement = connect.prepareStatement(Search);
		preparedStatement.setString(1, UserName);
		preparedStatement.setString(2, Password);
        if(preparedStatement.execute()) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
	            String Username = resultSet.getString("UserName");
	            String password = resultSet.getString("Password");
	            String FirstName = resultSet.getString("FirstName");
	            String LastName = resultSet.getString("LastName");
	            String Email = resultSet.getString("Email");
	            User people = new User(Username, password, FirstName, LastName, Email);
	            people.setId(resultSet.getInt("id"));
	            return people;
			}
        }
        preparedStatement.close();
		return null;
	}
	public User FindByUserName(String UserName, Connection connect) throws SQLException {
		String findByUserName = "Select * from user where UserName = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(findByUserName);
		preparedStatement.setString(1, UserName);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
            String Username = resultSet.getString("UserName");
            String password = resultSet.getString("Password");
            String FirstName = resultSet.getString("FirstName");
            String LastName = resultSet.getString("LastName");
            String Email = resultSet.getString("Email");
            User people = new User(Username, password, FirstName, LastName, Email);
            people.setId(resultSet.getInt("id"));
            people.setPetCounts(resultSet.getInt("petCounts"));
            return people;
		}
		return null;

	}

	public void IncrementPetCounts(User user, Connection connect) throws SQLException {
		String IncrementPetCounts = "UPDATE user SET petCounts = ? WHERE user.id = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(IncrementPetCounts);
		 preparedStatement.setInt(1, user.getPetCounts());
		 preparedStatement.setInt(2, user.getId());
		 preparedStatement.executeUpdate();
	}

	public boolean InsertPetFav(Connection connect, int petId, int id) throws SQLException {
		String InsertPetFav = "INSERT into favroitepets(petId, userId) Values (?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(CheckPetFav);
		preparedStatement.setInt(1, petId);
		preparedStatement.setInt(2, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			return false;
		}
		else 
		{
			preparedStatement = (PreparedStatement) connect.prepareStatement(InsertPetFav);
			preparedStatement.setInt(1, petId);
			preparedStatement.setInt(2, id);
			System.out.println(petId + id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
	}
	
	public boolean InsertUserFav(Connection connect, int userId, int id) throws SQLException {
		String InsertuserFav = "INSERT into favroiteuser(userId, FavUserId) Values (?,?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(CheckuserFav);
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next())  {
			return false;
		}
		else
		{
			preparedStatement = (PreparedStatement) connect.prepareStatement(InsertuserFav);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		}
		
	}
	
	
	public User findById(Connection connect, int userId) throws SQLException {
		String findById = "Select * from user where id = ?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(findById);
		preparedStatement.setInt(1, userId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			User people = new User();
			people.setId(Integer.parseInt(resultSet.getString("id")));
			people.setUserName(resultSet.getString("UserName"));
			people.setFirstName(resultSet.getString("FirstName"));
			people.setLastName(resultSet.getString("LastName"));
			people.setEmail(resultSet.getString("Email"));
			people.setPetCounts(Integer.parseInt(resultSet.getString("petCounts")));
			return people;
		}
		return null;
	}

	public List <User> FavUserList(Connection connect, int id) throws SQLException {
		List <User> userList = new ArrayList<User>();
		String FavUserList = "select * from user where user.id "
				+ "in (select favroiteuser.FavUserId from favroiteuser where favroiteuser.userId = ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(FavUserList);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
            String Username = resultSet.getString("UserName");
            String password = resultSet.getString("Password");
            String FirstName = resultSet.getString("FirstName");
            String LastName = resultSet.getString("LastName");
            String Email = resultSet.getString("Email");
            User people = new User(Username, password, FirstName, LastName, Email);
            people.setId(resultSet.getInt("id"));
            people.setPetCounts(resultSet.getInt("petCounts"));
            userList.add(people);
		}
		resultSet.close();
		return userList;
	}
}
