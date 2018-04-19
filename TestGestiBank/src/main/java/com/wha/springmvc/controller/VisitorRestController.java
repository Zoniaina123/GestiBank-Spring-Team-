package com.wha.springmvc.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wha.springmvc.model.Conseiller;
import com.wha.springmvc.model.Visitor;
import com.wha.springmvc.service.AdministratorService;
import com.wha.springmvc.service.ConseillerService;
import com.wha.springmvc.service.VisitorService;

@RestController
public class VisitorRestController {

	@Autowired
	VisitorService visitorService;
	
	@Autowired
	AdministratorService administratorService;
	
	 @Autowired
	 ConseillerService conseillerService;
	
	//-------------------Create a Visitor (form)--------------------------------------------------------
    
    @RequestMapping(value = "/visitor/", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Void> createVisitor(@RequestBody Visitor visitor,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating visitor " + visitor.getUsername() + visitor.getEmail()+ visitor.getAddress());
 
        if (visitorService.isVisitorExist(visitor)) {
            System.out.println("A User with name " + visitor.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
    
        visitorService.save(visitor);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/visitor/{id}").buildAndExpand(visitor.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    //-------------------Retrieve All Users: les demandes d'inscription--------------------------------------------------------
    
    @RequestMapping(value = "/visitor/", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Collection<Visitor>> listAllVisitors() {
        Collection<Visitor> visitors = visitorService.findAllVisitors();
        if(visitors.isEmpty()){
            return new ResponseEntity<Collection<Visitor>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Collection<Visitor>>(visitors, HttpStatus.OK);
    } 
    
//------------------- Update a User : affecter une demande à un conseiller :  --------------------------------------------------------
    
    @RequestMapping(value = "/visitor/{id}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Visitor> updateVisitor(@PathVariable("id") int id, @RequestBody Visitor visitor) {
        System.out.println("Updating User " + id);
         
        Visitor currentVisitor = visitorService.findByIdVisitor(id);
        
        
        
        if (currentVisitor==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Visitor>(HttpStatus.NOT_FOUND);
        }
        
        currentVisitor.setUsername(visitor.getUsername());
        currentVisitor.setAddress(visitor.getAddress());
        currentVisitor.setEmail(visitor.getEmail());
        currentVisitor.setAffectation(visitor.getAffectation());
         
        visitorService.updateVisitor(currentVisitor);
        return new ResponseEntity<Visitor>(currentVisitor, HttpStatus.OK);
    }
    
  //-------------------Retrieve All Affectation--------------------------------------------------------
    
    @RequestMapping(value = "/visitor/affectation/{num}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Collection<Visitor>> listAllAffectations(@PathVariable("num") int num) {
        Collection<Visitor> visitors = visitorService.findAllAffectedVisitor(num);
        if(visitors.isEmpty()){
            return new ResponseEntity<Collection<Visitor>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Collection<Visitor>>(visitors, HttpStatus.OK);
    }
    
    
    
    
    
    
}
