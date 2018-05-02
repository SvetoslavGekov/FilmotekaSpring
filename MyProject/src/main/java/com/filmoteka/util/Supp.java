package com.filmoteka.util;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public final class Supp {
	//Fields
	private static final int MIN_USERNAME_LENGTH = 4;
	
	//Methods
	public static final boolean isNotNullOrEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static final boolean isValidUsername(String username) {
		//Check if username is a validString
		if(!isNotNullOrEmpty(username)) {
			return false;
		}
		
		//Check for a minimum length of MIN_USERNAME_LENGTH symbols
		if(username.length() < MIN_USERNAME_LENGTH) {
			return false;
		}
		
		//Check if each symbol is a lowercase letter
		for(int i = 0; i < username.length(); i++) {
			char ch = username.charAt(i);
			if(!Character.isLetter(ch)) {
				return false;
			}
			if(Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isValidEmail(String email){
		//Check if email is a valid string
		if(!isNotNullOrEmpty(email)) {
			return false;
		}
		
		//Check if email matches the pattern
		Pattern ptr = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		return ptr.matcher(email).matches();
	}
	
	public static final boolean isValidPassword(String str){
		//Regex source - https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
		Pattern ptr = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$");
		return ptr.matcher(str).matches();
	}
	
	public static final boolean isValidPhoneNumber(String str){
		return str.length() == 10 && str.charAt(0) == '0' && str.matches("[0-9]+");
	}
	
	//Validating for the profile pictures
	public static final boolean isValidImagePath(String filePath) throws IOException {
		if(!isNotNullOrEmpty(filePath)) {
			return false;
		}
		File file = new File(filePath);
		if(file.exists() && file.isFile()) {
			//If file is an image
		    if (ImageIO.read(file) != null) {
			     return true;
			}
		}
		return false;
	}
	
	public static final void inClauseAppender(StringBuilder sql, Set<Integer> appendables) {
		//Check if there is any need to append in the first place
		if(appendables != null && !appendables.isEmpty()) {
			for (int i = 0; i < appendables.size(); i++) {
				if(i != appendables.size() - 1) {
					sql.append("?,");
				}
				else {
					//For the last one (finish the query)
					sql.append("?) ");
				}
			}
		}
	}
	
	//TODO PGRating validation
}

