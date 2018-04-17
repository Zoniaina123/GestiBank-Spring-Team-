package com.wha.springmvc.controller;

import java.util.Collection;

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

import com.wha.springmvc.model.Administrator;
import com.wha.springmvc.model.Conseiller;
import com.wha.springmvc.service.AdministratorService;
import com.wha.springmvc.service.ConseillerService;
import com.wha.springmvc.service.UserService;

@RestController
public class AdministratorRestController {

	@Autowired
	AdministratorService administratorService; // Service which will do all data retrieval/manipulation work

	@Autowired
	ConseillerService conseillerService;
	
	@Autowired
	UserService userService;

	// -------------------Retrieve All
	// Admins--------------------------------------------------------

	@RequestMapping(value = "/administrator/", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Collection<Administrator>> listAllAdmins() {

		Collection<Administrator> admins = administratorService.findAllAdministrators();

		if (admins.isEmpty()) {
			return new ResponseEntity<Collection<Administrator>>(HttpStatus.NO_CONTENT);// You many decide to return
																						// HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Collection<Administrator>>(admins, HttpStatus.OK);
	}
	
	 
    @RequestMapping(value = "/administrator/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Administrator> getAdmin(@PathVariable("id") int id) {
        System.out.println("Fetching Admin with id " + id);
        Administrator admin = administratorService.findById(id);
        if (admin == null) {
            System.out.println("Conseiller with id " + id + " not found");
            return new ResponseEntity<Administrator>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Administrator>(admin, HttpStatus.OK);
    }

	// -------------------Create an
	// Administrator--------------------------------------------------------

	@RequestMapping(value = "/administrator/", method = RequestMethod.POST)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Void> createAdministrator(@RequestBody Administrator admin, UriComponentsBuilder ucBuilder) {

		if (administratorService.isUsExist(admin)) {
			System.out.println("A Administrator with name " + admin.getUsername() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		administratorService.saveAdministrator(admin);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/administrator/{id}/").buildAndExpand(admin.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Delete a admin
	// --------------------------------------------------------

	@RequestMapping(value = "/administrator/{id}", method = RequestMethod.DELETE)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Administrator> deleteAdministrator(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting Admin with id " + id);

		Administrator admin = administratorService.findById(id);
		if (admin == null) {
			System.out.println("Unable to delete. admin with id " + id + " not found");
			return new ResponseEntity<Administrator>(HttpStatus.NOT_FOUND);
		}

		administratorService.deleteAdministratorById(id);
		return new ResponseEntity<Administrator>(HttpStatus.NO_CONTENT);
	}
	
	
	 
   /* //-------------------Retrieve All Users: les demandes d'inscription--------------------------------------------------------
     
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> listAllUsers() {
        Collection<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<Collection<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }
    
	*/
	
	/* //------------------- Update a User : affecter une demande à un conseiller :  --------------------------------------------------------
    
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        System.out.println("Updating User " + id);
         
        User currentUser = userService.findById(id);
         
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        currentUser.setUsername(user.getUsername());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
       // currentUser.setAffectation(user.getAffectation());
         
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }*/
 

}
