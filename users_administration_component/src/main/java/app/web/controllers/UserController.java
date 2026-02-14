package app.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.domain.UserService;
import app.persistence.entities.User;
import app.web.models.NewUserModel;
import app.web.transformers.UserTransformer;
import app.web.validators.UserValidator;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator; 
	
	@Autowired
	private UserTransformer userTransformer;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody NewUserModel newUserModel) {
		
		userValidator.validateNewUserModel(newUserModel);
		
		User user = userTransformer.toUser(newUserModel);
		
		userService.register(newUserModel.getSystemActorId(), user);		
				
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}

}
