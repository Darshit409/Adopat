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
	
	public boolean insert(User people, Connection connect) throws SQLException {
		String sql = "insert into  user(UserName, Password, FirstName, LastName, Email) values (?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1,  people.UserName);
		preparedStatement.setString(2, people.Password);
		preparedStatement.setString(3, people.FirstName);
		preparedStatement.setString(4, people.LastName);
		preparedStatement.setString(5, people.Email);
		
		if(checkUserName(people, connect)) {
			return false;
		}
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
//        disconnect();
        return rowInserted;
    }
	
	private boolean checkUserName(User person, Connection connect) throws SQLException {
		String sql = "SELECT EXISTS(SELECT * FROM user WHERE UserName = (?))";
		preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
		preparedStatement.setString(1, person.UserName);
		System.out.println("Hello");
		int exists = preparedStatement.executeUpdate();
		System.out.println(exists);
		preparedStatement.close();
		return exists > 0;
	}

}
