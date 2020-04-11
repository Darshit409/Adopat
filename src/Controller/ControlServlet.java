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
import javax.servlet.http.HttpSession;

import model.User;
import model.pet;

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
	private pet Pet;
	private UserService userService = new UserService();
	private PetService petService = new PetService();
	private HttpSession session = null;
	public Connection setUpConnect() throws SQLException {
		if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/Adopet?"
  			          + "user=root&password=23paddock");
           return connect;
		}
		return connect;
        
	}
 
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
            	LoginUser(request, response);
            case "/petInsert":
            	LoginPet(request,response);
            case "/Logout":
            	response.sendRedirect("Login.jsp");
            case "/Explore":
            	listAllPets(request,response);
            case "/MyAdoptions":
            	listMyPets(request,response);
            case "/TraitList":
            	PetListbyTrait(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }




	private void initialize(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    		
    		connect = setUpConnect();	//Setting the connnection with database
    		String sql1 = "DROP DATABASE Adopet";
            statement = connect.createStatement();
            statement.executeUpdate(sql1);
            String sql = "CREATE DATABASE Adopet";
            statement.executeUpdate(sql);
            String User ="CREATE TABLE User " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " UserName VARCHAR(255), " + 
                    " Password VARCHAR(255), " + 
                    " FirstName VARCHAR(255),"+
                    "LastName VARCHAR(255)," +
                    "Email VARCHAR(255),"+
                    "petCounts INTEGER not NULL," + 
                    " PRIMARY KEY ( id ))";
            String pet = "CREATE TABLE pet"+ 
                    "(petId INTEGER not NULL AUTO_INCREMENT,"+
            		"petName VARCHAR(255)," +
            		"species VARCHAR(255)," +
            		"birthDate VARCHAR(255)," +
            		"adoptionPrice INTEGER," +
            		"userId INTEGER not NULL,"+
            		" PRIMARY KEY ( petId ), "
            		+ "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";
            String petTraits = "CREATE TABLE petTraits"
            		+ "(id INTEGER not null AUTO_INCREMENT,"
            		+ "petId INTEGER,"
            		+ "traits VARCHAR(255),"
            		+ "PRIMARY KEY( id),"
            		+ "FOREIGN KEY (petId) REFERENCES pet(petId) ON DELETE CASCADE)";
            String Reviews = "CREATE TABLE reviews"
            		+ "(id INTEGER not null AUTO_INCREMENT,"
            		+ "userId INTEGER,"
            		+ "petId INTEGER,"
            		+ "ReviewCategory VARCHAR(255),"
            		+ "Comment VARCHAR(255),"
            		+ "PRIMARY KEY (id), "
            		+ "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,"
            		+ "FOREIGN KEY (petId) REFERENCES pet(petId) ON DELETE CASCADE)";
            statement = connect.createStatement();
            statement.executeUpdate("use Adopet;");
            statement.executeUpdate(User);
            statement.execute(pet);
            statement.execute(petTraits);
            statement.execute(Reviews);
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
        user = new User(UserName, password, firstName, LastName, Email);
        if(userService.insert(user, connect)) {
        	 RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        	 session.setAttribute("UserName", user.UserName);
        	 session.setAttribute("FirstName", user.FirstName);
        	 session.setAttribute("LastName", user.LastName);
        	 request.setAttribute("user", user);
             dispatcher.forward(request, response);
        }
		return user;
    }
    private void LoginUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
    	String UserName = request.getParameter("UserName");
    	String Password = request.getParameter("Password");
    	connect = setUpConnect();
    	if( connect != null)
    	user = userService.Login(UserName, Password, connect);
    	if (user == null)
    	{
    	//When there is invalid Password or username
    		String Message = "Invalid UserName or Password";
    		request.setAttribute("Message",Message);
    		request.getRequestDispatcher("Login.jsp").forward(request, response);
             
    	}
    	else {
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
		 session = request.getSession();
    	 session.setAttribute("UserName", user.UserName);
    	 session.setAttribute("FirstName", user.FirstName);
    	 session.setAttribute("LastName", user.LastName);
		request.setAttribute("user",user);
        dispatcher.forward(request, response);
    }}
    // when pet is registered in an user's account
    private void LoginPet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	connect = setUpConnect();
		String petName = request.getParameter("petName");
		String species = request.getParameter("species");
		String birthDate = request.getParameter("birthDate");
		int adoptionPrice =Integer.parseInt(request.getParameter("adoptionPrice"));
		String traits = request.getParameter("traits");
		System.out.print("retrieving data works   ");
		String UserName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(UserName, connect);
		if(user.getPetCounts()< 5) {
			user.incrementPetCounts(user.getPetCounts());
			userService.IncrementPetCounts(user, connect);
			Pet = new pet(petName, species, birthDate, adoptionPrice, traits, user.id);
			if(petService.insert(Pet, connect))
			{
				request.setAttribute("success", "The Pet is successfull Registered!");
				request.getRequestDispatcher("PlacePetForAdoption.jsp").forward(request, response);
			}
			
		}
		else
		{
			request.setAttribute("success", "You can only put opto 5 Animals!");
			request.getRequestDispatcher("PlacePetForAdoption.jsp").forward(request, response);
		}
		} 
    
	private void listAllPets(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();    
		List<pet> listPeople = petService.printingAllpets(connect);
		List<String> Traits = petService.petTraitList(connect);
		session.setAttribute("Traits", Traits);
		session.setAttribute("AlllistPet", listPeople);
		request.getRequestDispatcher("Explore.jsp").forward(request, response);
		
	}

	private void listMyPets(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		user = userService.FindByUserName((String) session.getAttribute("UserName"), connect);
		List<pet> listPeople = petService.printingMyPets(connect, user);
		session.setAttribute("MylistPet", listPeople);
		request.getRequestDispatcher("MyAdoptions.jsp").forward(request, response);
	}


	private void PetListbyTrait(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String trait = request.getParameter("Traits");
		System.out.print(trait);
		List<pet> listPeople = petService.FindByTraits(connect, trait);
		request.setAttribute("trait", trait);
		session.setAttribute("AlllistPet", listPeople);
		request.getRequestDispatcher("Explore.jsp").forward(request, response);
	}
    }
	