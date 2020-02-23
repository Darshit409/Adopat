package Controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
	private Statement statement = null;
	private User user;
	private UserService userService = new UserService();
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/initialize":
         initialize(request, response);
                break;
            case "/insert":
            	user = insertPeople(request, response);
            case "/Home":
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void initialize(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/Adopet?"
  			          + "user=john&password=Pass1234");
            System.out.println(connect);
        }
    		String sql1 = "DROP DATABASE Adopet";
            statement = connect.createStatement();
            statement.executeUpdate(sql1);
            System.out.print("Database Deleted");
            String sql = "CREATE DATABASE Adopet";
            statement.executeUpdate(sql);
            System.out.print("Database created");
            String User ="CREATE TABLE User " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " UserName VARCHAR(255), " + 
                    " Password VARCHAR(255), " + 
                    " FirstName VARCHAR(255),"+
                    "LastName VARCHAR(255)," +
                    "Email VARCHAR(255),"+
                    " PRIMARY KEY ( id ))";   
            statement = connect.createStatement();
            statement.executeUpdate("use Adopet;");
            statement.executeUpdate(User);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            User newPeople = new User("Admin", "pass1234", "Root","User", "Root@wayne.edu");
            userService.insert(newPeople, connect);
		}
    private User insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String UserName = request.getParameter("UserName");
        String password = request.getParameter("Password");
        String firstName = request.getParameter("FirstName");
        String LastName = request.getParameter("LastName");
        String Email = request.getParameter("Email");
        User newPeople = new User(UserName, password, firstName, LastName, Email);
        if(userService.insert(newPeople, connect)) {
        	 RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
             dispatcher.forward(request, response);
        }
		return newPeople;
    }
    }
		
