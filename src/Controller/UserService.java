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
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class UserService extends HttpServlet {
	private PreparedStatement preparedStatement = null;
	private static String Search = "Select UserName, Password, FirstName, LastName, Email from user where UserName=? and Password=?";
	
	public boolean insert(User people, Connection connect) throws SQLException {
		String sql = "insert into  user(UserName, Password, FirstName, LastName, Email) values (?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1,  people.UserName);
		preparedStatement.setString(2, people.Password);
		preparedStatement.setString(3, people.FirstName);
		preparedStatement.setString(4, people.LastName);
		preparedStatement.setString(5, people.Email);
		
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowInserted;
    }
	
	public User Login(String UserName, String Password, Connection connect) throws SQLException {
		System.out.print(UserName + Password + connect);
	//	String Sear = "Select ? from user where UserName=? and Password=?";
		preparedStatement = connect.prepareStatement(Search);
		preparedStatement.setString(1, UserName);
		preparedStatement.setString(2, Password);
        if(preparedStatement.execute()) {
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.print(resultSet);
			while(resultSet.next()) {
	            String Username = resultSet.getString("UserName");
	            String password = resultSet.getString("Password");
	            String FirstName = resultSet.getString("FirstName");
	            String LastName = resultSet.getString("LastName");
	            String Email = resultSet.getString("Email");
	            System.out.print(Username + "1");
	            User people = new User(Username, password, FirstName, LastName, Email);
	            return people;
			}
        }
		return null;
	}

}
