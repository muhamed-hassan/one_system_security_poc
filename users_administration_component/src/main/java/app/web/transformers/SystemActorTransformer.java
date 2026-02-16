package app.web.transformers;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import app.persistence.entities.SystemActor;
import app.web.models.SystemActorModel;

@Component
public class SystemActorTransformer {
	
	public HashSet<SystemActorModel> toSystemActorModels(List<SystemActor> systemActors) {
		
		HashSet<SystemActorModel> systemActorModels = new HashSet<SystemActorModel>();
		for (int cursor = 0; cursor < systemActors.size(); cursor++) {
			
			SystemActor currentElement = systemActors.get(cursor);
			
			SystemActorModel systemActorModel = new SystemActorModel();
			systemActorModel.setId(currentElement.getId());
			systemActorModel.setType(currentElement.getType());
		}
		
		return systemActorModels;
	}

}
