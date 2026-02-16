package app.web.controllers;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.domain.SystemActorService;
import app.persistence.entities.SystemActor;
import app.web.models.SystemActorModel;
import app.web.transformers.SystemActorTransformer;

@RestController
@RequestMapping("system-actor")
public class SystemActorController {
	
	@Autowired
	private SystemActorService systemActorService;
	
	@Autowired
	private SystemActorTransformer systemActorTransformer;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<HashSet<SystemActorModel>> getSystemActorModels() {
		
		List<SystemActor> systemActors = systemActorService.getAll();
		
		HashSet<SystemActorModel> systemActorModels = systemActorTransformer.toSystemActorModels(systemActors);		
		
		return new ResponseEntity<HashSet<SystemActorModel>>(systemActorModels, HttpStatus.OK);
	}

}
