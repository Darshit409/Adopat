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
            case "/AdoptPet":
            	DeletePet(request,response);
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
            case "/EnterReview":
            	writeReview(request,response);
            case "/PetReviewList":
            	petReviewList(request, response);
            case "/Userinfo":
            	UserInfo(request, response);
            case "/addpet":
            	addFavoritePet(request, response);
            case "/adduser":
            	addFavoriteUser(request, response);
            case "/FavoritePet":
            	FavoritePet(request, response);
            case "/FavoriteBreeder":
            	FavoriteUser(request, response);
            case "/deletepet":
            	deleteFavoritePet(request,response);
            case "/deleteuser":
            	deleteFavoriteUser(request, response);
            case "/AdoptionBySpecies":
            	AdoptionBySpecies(request, response);
            case "/addCart":
            	AddToCart(request,response);
            case "/CrayCrayReviewExceptions":
            	CrayCrayExceptions(request, response);
            case "/NoCrayCrayReviewExceptions":
            	NoCrayCrayExceptions(request, response);
            case "/goodUser":
            	GoodUser(request, response);
            case "/UserWithGreatPets":
            	UserWithGreatPets(request, response);
            case "/CommonFavPets":
            	commonFabPets(request, response);
            case "/GoodPets":
            	GoodPets(request, response);
            case "/TopReviewers":
            	listTopReviewers(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTopReviewers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException
	{
		connect = setUpConnect();
		List<User> listTopReviewsUser = userService.topReviewers(connect);
		request.setAttribute("listTopReviewsUser", listTopReviewsUser);       
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListUsersMostReviews.jsp");       
        dispatcher.forward(request, response);
	}

	private void GoodPets(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		List<pet> FavPet = petService.GoodPets(connect, user.id);
		request.setAttribute("GoodPets", FavPet);
		request.getRequestDispatcher("GoodPets.jsp").forward(request, response);
	}

	private void commonFabPets(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		List<pet> FavPet = petService.printingCommonFavPet(connect, user.id);
		request.setAttribute("CommonFavPet", FavPet);
		request.getRequestDispatcher("CommonFavPets.jsp").forward(request, response);

	}

	private void UserWithGreatPets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		connect = setUpConnect();
		List<User> userList = userService.UserWithGreatPets(connect);
		List<User> userList2 = userService.UserWithNoGreatPets(connect);
		request.setAttribute("GoodUserList", userList);
		request.setAttribute("GoodUserList2", userList2);
		request.getRequestDispatcher("UserWithGreatPet.jsp").forward(request, response);
	}

	private void GoodUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		connect = setUpConnect();
		List<User> userList = userService.GoodUser(connect);
		request.setAttribute("GoodUserList", userList);
		request.getRequestDispatcher("GoodUser.jsp").forward(request, response);
	}

	private void NoCrayCrayExceptions(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		List<User> userList = userService.petNoCrayCray(connect);
		request.setAttribute("NoCrayCrayList", userList);
		request.getRequestDispatcher("NoCrayCray.jsp").forward(request, response);
	}

	private void CrayCrayExceptions(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		List<User> userList = userService.CrayCrayExceptions(connect);
		request.setAttribute("CrayCrayList", userList);
		request.getRequestDispatcher("CrayCray.jsp").forward(request, response);
	}

	private void AddToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String petId = request.getParameter("petId");
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		if(petId != null) {
			userService.AddToCart(connect, petId, user.getId());
		}
		List<pet> petList = petService.printCart(connect, user.getId());
		session.setAttribute("AlllistPet", petList);
		request.getRequestDispatcher("Cart.jsp").forward(request, response);
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
            String favoritePets = "CREATE TABLE favroitePets"
            		+ "(id INTEGER not null AUTO_INCREMENT,"
            		+ " petId INTEGER,"
            		+ "userId INTEGER,"
            		+ "PRIMARY KEY (id),"
            		+ "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,"
            		+ "FOREIGN KEY (petId) REFERENCES pet(petId) ON DELETE CASCADE)";
            String favoriteUser = "CREATE TABLE favroiteUser"
            		+ "(id INTEGER not null AUTO_INCREMENT,"
            		+ " userId INTEGER,"
            		+ "FavUserId INTEGER,"
            		+ "PRIMARY KEY (id),"
            		+ "FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE,"
            		+ "FOREIGN KEY (FavUserId) REFERENCES User(id) ON DELETE CASCADE)";
            String cart = "CREATE TABLE cart"
            		+ "(id INTEGER not null auto_increment,"
            		+ " userId INTEGER,"
            		+ " petId INTEGER,"
            		+ "PRIMARY KEY (id),"
            		+ " FOREIGN KEY (petId) REFERENCES pet(petId) ON DELETE CASCADE,"
            		+ " FOREIGN KEY (userId) REFERENCES User(id) ON DELETE CASCADE)";
            statement = connect.createStatement();
            statement.executeUpdate("use Adopet;");
            statement.executeUpdate(User);
            statement.execute(pet);
            statement.execute(petTraits);
            statement.execute(Reviews);
            statement.execute(favoritePets);
            statement.execute(favoriteUser);
            statement.execute(cart);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            User newPeople = new User("Admin", "pass1234", "Root","User", "Root@wayne.edu");
            userService.insert(newPeople, connect);
		}
    private User insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	connect = setUpConnect();
        String UserName = request.getParameter("UserName");
        String password = request.getParameter("Password");
        String firstName = request.getParameter("FirstName");
        String LastName = request.getParameter("LastName");
        String Email = request.getParameter("Email");
        user = new User(UserName, password, firstName, LastName, Email);
        if(userService.insert(user, connect)) {
        	 RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        	 System.out.print(user.UserName);
        	 session = request.getSession();
        	 session.setAttribute("UserName", user.getUserName());
        	 session.setAttribute("FirstName", user.FirstName);
        	 session.setAttribute("LastName", user.LastName);
        	 request.setAttribute("user", user);
             dispatcher.forward(request, response);
        }
        else
        {
        String Message = "UserName Already Exists";
		request.setAttribute("Message",Message);
		request.getRequestDispatcher("Registration.jsp").forward(request, response);
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
    }
    	
    }
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

	private void petReviewList(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int petId = Integer.parseInt(request.getParameter("petId"));
		Pet = petService.FindByPetId(connect, petId);
		List<pet> listPeople = petService.findPetReviewBypetID(connect, petId);
		System.out.print(listPeople);
		session.setAttribute("petList", listPeople);
		session.setAttribute("pet", Pet);
		request.getRequestDispatcher("PetDetail.jsp").forward(request, response);

	}

	private void writeReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		int petId = Integer.parseInt(request.getParameter("petId"));
		String ReviewCategory = request.getParameter("ReviewCategory");
		String comment = request.getParameter("comment");
		if(petService.insertReview(connect, petId, ReviewCategory, comment, user)) {
			request.setAttribute("register", "The Review is successfull Entered!");
			request.getRequestDispatcher("Explore.jsp").forward(request, response);
		}
		
		
	}
	private void UserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		System.out.print("user Info loading");
		user = userService.findById(connect, userId);
		session.setAttribute("user", user);
		 RequestDispatcher dispatcher = request.getRequestDispatcher("UserDetail.jsp");
         dispatcher.forward(request, response);
	}
	private void FavoriteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		List<User> favList = userService.FavUserList(connect, user.getId());
		request.setAttribute("userList", favList);
		request.getRequestDispatcher("FavUserList.jsp").forward(request, response);
		}

	private void FavoritePet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		List <pet> favList = petService.FavPetList(connect, user.getId());
		request.setAttribute("petList", favList);
		request.getRequestDispatcher("FavPetList.jsp").forward(request, response);
		}
	private void addFavoritePet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int petId = Integer.parseInt(request.getParameter("petId"));
		String message;
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		if(userService.InsertPetFav(connect, petId, user.getId())) {
			message = "Pet is succesfully added to Favorites";
		}
		else {
			message = "Pet already exists in favorites";
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("PetDetail.jsp").forward(request, response);

		}
	private void addFavoriteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
	int userId = Integer.parseInt(request.getParameter("userId"));
	String message;
	String userName = (String) session.getAttribute("UserName");
	user = userService.FindByUserName(userName, connect);
	if(userService.InsertUserFav(connect, user.getId(), userId)) {
		message = "Breeder is succesfully added to Favorites";
	}
	else {
		message = "Breeder already exists in favorites";
	}
	request.setAttribute("message", message);
	request.getRequestDispatcher("UserDetail.jsp").forward(request, response);

	}

	private void deleteFavoritePet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
	int petId = Integer.parseInt(request.getParameter("petId"));
	String message;
	String userName = (String) session.getAttribute("UserName");
	user = userService.FindByUserName(userName, connect);
	if(petService.DeletePetFav(connect, petId, user.getId())) {
		message = "Pet is succesfully Deleted from Favorites";
	}
	else {
		message = "Pet does not exists in favorites";
	}
	request.setAttribute("message", message);
	request.getRequestDispatcher("FavoritePet").forward(request, response);

	}
	private void deleteFavoriteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	int userId = Integer.parseInt(request.getParameter("userId"));
	String message;
	String userName = (String) session.getAttribute("UserName");
	user = userService.FindByUserName(userName, connect);
	if(petService.DeleteUserFav(connect, user.getId(), userId)) {
		message = "Breeder is succesfully Deleted from Favorites";
	}
	else {
		message = "Breeder does not exists in favorites";
	}
	request.setAttribute("message", message);
	request.getRequestDispatcher("FavoriteBreeder").forward(request, response);

	}
	private void AdoptionBySpecies(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		List <User> userList = null;
		String species1 = (String)request.getParameter("species1");
		String species2 = request.getParameter("species2");
		if(species1!= null && species2 != null) {
			userList = userService.FindUserBy2Species(connect, species1, species2);
			System.out.print("This runs");
			session.setAttribute("userList", userList);	
		}
		else {
			session.setAttribute("userList", userList);	
		}
		List <String> speciesList = petService.SpeciesList(connect);
		request.setAttribute("speciesList", speciesList);
		request.getRequestDispatcher("AdoptionBySpecies.jsp").forward(request, response);
		
	}

	private void DeletePet(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		connect = setUpConnect();
		String userName = (String) session.getAttribute("UserName");
		user = userService.FindByUserName(userName, connect);
		List<Integer> IdList = petService.PrintpetIdInCart(connect, user.getId());
		petService.DeletePet(connect, IdList);		
		System.out.print("delete is working");
		request.getRequestDispatcher("addCart").forward(request, response);

	}






    }
	