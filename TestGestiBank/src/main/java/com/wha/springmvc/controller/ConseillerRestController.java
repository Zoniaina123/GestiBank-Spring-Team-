package com.wha.springmvc.controller;

import java.util.Collection;
import java.util.Date;

import org.hibernate.jpa.criteria.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wha.springmvc.model.Conseiller;
import com.wha.springmvc.model.User;
import com.wha.springmvc.service.AdministratorService;
import com.wha.springmvc.service.ConseillerService;
import com.wha.springmvc.service.UserService;

@RestController
public class ConseillerRestController {
	
	
	
	@Autowired
    AdministratorService administratorService;
	 @Autowired
	 ConseillerService conseillerService;  //Service which will do all data retrieval/manipulation work
	 
		@Autowired
		UserService userService;
	 
	    //-------------------Retrieve All Users--------------------------------------------------------
     
	    @RequestMapping(value = "/administrator/conseiller/", method = RequestMethod.GET)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Collection<Conseiller>> listAllAsdmins() {
	        Collection<Conseiller> conseillers = conseillerService.findAllConseillers();
	      
	        if(conseillers.isEmpty()){
	            return new ResponseEntity<Collection<Conseiller>>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<Collection<Conseiller>>(conseillers, HttpStatus.OK);
	    }
	 
	 
	 
	    
	    //-------------------Retrieve Single Conseiller--------------------------------------------------------
	     
	    @RequestMapping(value = "/administrator/conseiller/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Conseiller> getConseiller(@PathVariable("id") int id) {
	        System.out.println("Fetching Conseiller with id " + id);
	        Conseiller Conseiller = conseillerService.findById(id);
	        if (Conseiller == null) {
	            System.out.println("Conseiller with id " + id + " not found");
	            return new ResponseEntity<Conseiller>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Conseiller>(Conseiller, HttpStatus.OK);
	    }
	 
	     
	     
	    //-------------------Create a Conseiller--------------------------------------------------------
	     
	    @RequestMapping(value = "/administrator/{id}/conseiller/", method = RequestMethod.POST)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Void> createConseiller(@PathVariable int id, @RequestBody Conseiller conseiller,    UriComponentsBuilder ucBuilder) {
	    	
	    	
	    	
	        if (conseillerService.isConseillerExist(conseiller)) {
	            System.out.println("A Conseiller with name " + conseiller.getUsername() + " already exist");
	            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	        }
	        
	        conseiller.setDateDebut(new Date());
	        conseiller.setAdministrator(administratorService.findById(id));
	        
	   
	        
	 
	        conseillerService.saveConseiller(conseiller);
	 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/conseiller/{id}").buildAndExpand(conseiller.getId()).toUri());
	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	    }
	 
	    
	     
	    //------------------- Update a Conseiller --------------------------------------------------------
	     
	    @RequestMapping(value = "/administrator/conseiller/{id}", method = RequestMethod.PUT)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Conseiller> updateConseiller(@PathVariable("id") int id, @RequestBody Conseiller conseiller) {
	        System.out.println("Updating Conseiller " + id);
	         
	        Conseiller currentuser = conseillerService.findById(id);
	         
	        if (currentuser==null) {
	            System.out.println("Conseiller with id " + id + " not found");
	            return new ResponseEntity<Conseiller>(HttpStatus.NOT_FOUND);
	        }
	 
	        currentuser.setUsername(conseiller.getUsername());
	        currentuser.setAddress(conseiller.getAddress());
	        currentuser.setEmail(conseiller.getEmail());
	         
	        conseillerService.updateConseiller(currentuser);
	        return new ResponseEntity<Conseiller>(currentuser, HttpStatus.OK);
	    }
	 
	    
	    
	    //------------------- Delete a Conseiller --------------------------------------------------------
	     
	    @RequestMapping(value = "/administrator/conseiller/{id}", method = RequestMethod.DELETE)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Conseiller> deleteConseiller(@PathVariable("id") int id) {
	        System.out.println("Fetching & Deleting Conseiller with id " + id);
	 
	        Conseiller conseiller = conseillerService.findById(id);
	        if (conseiller == null) {
	            System.out.println("Unable to delete. Conseiller with id " + id + " not found");
	            return new ResponseEntity<Conseiller>(HttpStatus.NOT_FOUND);
	        }
	        
	        if (!conseiller.getClients().isEmpty()) {
	        	System.out.println("Unable to delete. Conseiller with id" + id + " has clients");
	        	return new ResponseEntity<Conseiller>(HttpStatus.METHOD_NOT_ALLOWED);
	        }
	 
	        conseillerService.deleteConseillerById(id);
	        return new ResponseEntity<Conseiller>(HttpStatus.NO_CONTENT);
	    }
	 
	    
	     
	    
	    //------------------- Delete All Conseillers --------------------------------------------------------
	     
	    @RequestMapping(value = "/administrator/conseiller/", method = RequestMethod.DELETE)
	    @CrossOrigin(origins = "http://localhost:4200")
	    public ResponseEntity<Conseiller> deleteAllConseillers() {
	        System.out.println("Deleting All Conseillers");
	 
	        conseillerService.deleteAllConseillers();
	        return new ResponseEntity<Conseiller>(HttpStatus.NO_CONTENT);
	    }
	    
	    
	    //-------------------Retrieve All Affectation--------------------------------------------------------
	    
	    @RequestMapping(value = "/user/affectation/{num}", method = RequestMethod.GET)
	    public ResponseEntity<Collection<User>> listAllAffectations(@PathVariable("num") int num) {
	        Collection<User> users = userService.findAllAffectedUsers(num);
	        if(users.isEmpty()){
	            return new ResponseEntity<Collection<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
	    }
	   
	 
	}

