package app.web.transformers;

import org.springframework.stereotype.Component;

import app.persistence.entities.User;
import app.web.models.NewUserModel;

@Component
public class UserTransformer {
	
	public User toUser(NewUserModel newUserModel) {
		
		User user = new User();		
		user.setName(newUserModel.getName());
		user.setPassword(newUserModel.getPassword());
		user.setEmail(newUserModel.getEmail());
		user.setMobile(newUserModel.getMobile());		
		
		return user;
	}

}
