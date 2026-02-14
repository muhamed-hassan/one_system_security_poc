package app.web.validators;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import app.web.error_handler.WebValidationException;
import app.web.models.NewUserModel;

@Component
public class UserValidator {
	
	private String nameField = "name";
	private String nameFieldIsRequiredMsg = "Name is required";
	private String nameFieldValidationRegex = "[a-zA-Z\\ ]{5,100}";
	private String nameFieldIsInvalidMsg = "Name contains invalid characters and shall contain letters only with maximum of 100";

	private String passwordField = "password";
	private String passwordFieldIsRequiredMsg = "Password is required";
	private String passwordFieldValidationRegex = "[a-zA-Z0-9]{8,}";
	private String passwordFieldIsInvalidMsg = "Password should be a mixture of at least 8 characters made of lower/upper case letters and digits";
	
	private String mobileField = "mobile";
	private String mobileFieldIsRequiredMsg = "Mobile is required";
	private String mobileFieldValidationRegex = "(07)[0-9]{9}";
	private String mobileFieldIsInvalidMsg = "Mobile contains invalid characters and shall be in this form 07123456789";
	
	private String emailField = "email";
	private String emailFieldIsRequiredMsg = "Email is required";
	private String emailFieldValidationRegex = "[a-zA-Z0-9_\\.]+(\\@)[a-zA-Z0-9]+(\\.)[a-zA-Z]+";
	private String emailFieldIsInvalidMsg = "Email format is invalid";
	
	public void validateNewUserModel(NewUserModel newUserModel) { 
		
		HashMap<String, String> errorInformation = new HashMap<String, String>();
		
		String name = newUserModel.getName();
		if (name == null || name.isEmpty()) {
			
			errorInformation.put(nameField, nameFieldIsRequiredMsg);
			
		} else {
			
			name = name.trim();
			if (!name.matches(nameFieldValidationRegex)) {
				errorInformation.put(nameField, nameFieldIsInvalidMsg);
			}
			
		}
		
		String password = newUserModel.getPassword();
		if (password == null || password.isEmpty()) {
			
			errorInformation.put(passwordField, passwordFieldIsRequiredMsg);
			
		} else {
			
			password = password.trim();
			if (!password.matches(passwordFieldValidationRegex)) {
				errorInformation.put(passwordField, passwordFieldIsInvalidMsg);
			}
			
		}
		
		String mobile = newUserModel.getMobile();
		if (mobile == null || mobile.isEmpty()) {
			
			errorInformation.put(mobileField, mobileFieldIsRequiredMsg);
			
		} else {
			
			mobile = mobile.trim();
			if (!mobile.matches(mobileFieldValidationRegex)) {
				errorInformation.put(mobileField, mobileFieldIsInvalidMsg);
			}
			
		}
		
		String email = newUserModel.getEmail();
		if (email == null || email.isEmpty()) {
			
			errorInformation.put(emailField, emailFieldIsRequiredMsg);
			
		} else {
			
			email = email.trim();
			if (!email.matches(emailFieldValidationRegex)) {
				errorInformation.put(emailField, emailFieldIsInvalidMsg);
			}
			
		}
		
		if (!errorInformation.isEmpty()) {
			
			WebValidationException webValidationException = new WebValidationException();
			webValidationException.setErrorInformation(errorInformation);
			throw webValidationException;
		}
	}

}
