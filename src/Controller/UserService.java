package Controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UserService {
	private PreparedStatement preparedStatement = null;
	private static String Search = "Select * from user where UserName=? and Password=?";
	
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
}
