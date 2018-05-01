package com.filmoteka.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.filmoteka.exceptions.InvalidFormDataException;
import com.filmoteka.exceptions.InvalidGenreDataException;
import com.filmoteka.exceptions.InvalidOrderDataException;
import com.filmoteka.exceptions.InvalidProductCategoryDataException;
import com.filmoteka.exceptions.InvalidProductDataException;
import com.filmoteka.exceptions.InvalidUserDataException;
import com.filmoteka.manager.UserManager;
import com.filmoteka.model.User;

import com.filmoteka.model.dao.UserDao;
import com.filmoteka.util.BCrypt;


@Controller
public class UserController {
	private static final String dbError = "An error occured while accessing the database. Please try again later!";

	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public String redirectToHome(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return "unauthorized";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHomePage() {
		return "index";
	}
	
	@RequestMapping(value = "auth/account", method = RequestMethod.GET)
	public String myAccount(HttpSession session){
		
		if((User) session.getAttribute("USER") == null) {
			return "index";
		}
		
		return "account";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(Model model, @RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request) throws SQLException {

		// Check if the credentials are valid
		User user;
		try {
			user = UserManager.getInstance().logIn(username, password);
		}
		catch (InvalidProductDataException | SQLException | InvalidUserDataException | InvalidOrderDataException
				| InvalidGenreDataException | InvalidProductCategoryDataException e) {
			//If an error occurs while loading the user --> throw a DB exception
			throw new SQLException(dbError, e);
		}

		if (user != null) {
			// Get a new session
			HttpSession session = request.getSession();

			// Set the user in the session
			session.setAttribute("USER", user);

			// Set the IP of the request which called the server
			session.setAttribute("ip", request.getRemoteAddr());
			// Redirect to the main page service
			return "redirect:/main";
		}
		else {
			//Show an error for invalid username or password
			String message = "Invalid Username or Password";
			
			model.addAttribute("loginError",message);
			return "index";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String userLogout(HttpSession session){
		//Invalidate the session
		session.invalidate();
		
		//Return to the index page
		return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) throws Exception {
		try {
			//Check if the given user parameters are valid
			if(!UserManager.getInstance().isValidUserRegistrationData(username, password, email)) {
				throw new InvalidFormDataException("You've entered incorrect registration data in your form. Please follow the input hints.");
			}
			
			//Check if there are no users with the same username or email
			if(UserManager.getInstance().hasUserWithSameCredentials(username, email)) {
				String message= "The selected username or email is already taken by another user.";
				throw new Exception(message);
			}
			//Register user
			UserManager.getInstance().register(firstName, lastName, username, password, email);
			//Redirect to the login form again
			return "index";
		}
		catch (SQLException e) {
			throw new Exception(dbError);
		}
	}
	
	@RequestMapping(value = "auth/updateProfile", method = RequestMethod.POST)
	public String updateProfile(HttpSession session, 
								@RequestParam("firstname") String firstName,
								@RequestParam("lastname") String lastName,
								@RequestParam("email") String email,
								@RequestParam("phone") String phone,
								HttpServletRequest request) throws Exception{
		
		User user = (User) session.getAttribute("USER");
		
		String currentPass = request.getParameter("currentPass");
		String newPass1 = request.getParameter("newPass1");
		String newPass2 = request.getParameter("newPass2");
		
		//If all three password fields are empty set all of them as the users's password
		if(currentPass.isEmpty() && newPass1.isEmpty() && newPass2.isEmpty()){
			currentPass = user.getPassword();
			newPass1 = currentPass;
			newPass2 = currentPass;
		}
		//Otherwise check if current coincides with users's
		else if(BCrypt.checkpw(currentPass, user.getPassword())){
			//Check if newPass1 isn't equals to newPass2
			if(!newPass1.equals(newPass2)){
				throw new InvalidUserDataException("New passwords didn't match!");
			}
			
			//If everything has passed successfully, hash the new password to be set to the user
			newPass1 = BCrypt.hashpw(newPass1, BCrypt.gensalt());
		}
		else{
			throw new InvalidUserDataException("This is not your current password!");
		}
		
		//Test if can create user with these data (Will throw an exception if cannot)
		User test = new User(false,firstName, lastName, user.getUsername(), newPass1 , email);
		if(phone.isEmpty()) phone = null;
		test.setPhone(phone);
		
		//Update current user
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(newPass1);
		UserDao.getInstance().updateUser(user);
	
		return "account";
	}
}
