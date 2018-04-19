package com.wha.springmvc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wha.springmvc.model.Client;
import com.wha.springmvc.model.Compte;
import com.wha.springmvc.model.CompteCourant;
import com.wha.springmvc.model.CompteRemunerateur;
import com.wha.springmvc.model.Credit;
import com.wha.springmvc.model.Operation;
import com.wha.springmvc.service.ClientService;
import com.wha.springmvc.service.CompteService;
import com.wha.springmvc.service.OperationService;


@RestController
public class CompteRestController {
	
	
	 @Autowired
	    CompteService compteService;  //Service which will do all data retrieval/manipulation work
	 @Autowired
	   ClientService clientService; 
	 
	 @Autowired
	   OperationService operationService;
	  
	 
	 //afficher les comptes d'un client
	 @RequestMapping(value = "/client/{id}/comptes", method = RequestMethod.GET) //ok
	 @CrossOrigin(origins = "http://localhost:4200")
		public    List<Compte>   trouverComptesClient(@PathVariable int id) {
	       List<Compte>  listComptes =compteService.findComptesClient(id);
		return listComptes;

		}
	 
	 
	 //afficher les operations d'un compte client
	 @RequestMapping(value = "client/compte/{id}", method = RequestMethod.GET) //ok
		public List<Operation> trouveOperaionsClient(@PathVariable int id) {
			
			Client client=clientService.findById(id);

			
			return operationService.findOprationsCompte(id);
		}
	 
	 //creation d'un compte
	 @RequestMapping(value = "/client/{id}/compte", method = RequestMethod.POST) //  ?? 
		public void savecompte(@PathVariable int id, @RequestBody Compte compte) {
			Client cl = clientService.findById(id);
			compte.setClient(cl);
			compte.setDateCreation(new Date());
			compteService.saveCompte(compte);
		}
	 
	 //creation d'un compte courant
	 @RequestMapping(value = "/client/{id}/compteCourant", method = RequestMethod.POST) //  ?? 
		public void savecompteCourant(@PathVariable int id, @RequestBody CompteCourant compteCou) {
			Client cl = clientService.findById(id);
			compteCou.setClient(cl);
			compteCou.setDateCreation(new Date());
			compteService.saveCompteCourant(compteCou);
		}
	 
	 //creation d'un compte rémunérateur
	 @RequestMapping(value = "/client/{id}/compteRemunerateur", method = RequestMethod.POST) //  ?? 
		public void savecompteRemunerateur(@PathVariable int id, @RequestBody CompteRemunerateur compteRem) {
			Client cl = clientService.findById(id);
			compteRem.setClient(cl);
			compteRem.setDateCreation(new Date());
			compteService.saveCompteRemunerateur(compteRem);
		}
	 
	 //faire une transaction
	 @RequestMapping(value = "/client/{id1}/compte/{id2}/operations", method = RequestMethod.POST)
		public void saveoperation(@PathVariable int id1, @PathVariable int id2, @RequestBody Operation op) {
			
			Client client = clientService.findById(id1);
	        Compte compte = compteService.findCompteById(id2, id1);
	        if (op instanceof Credit) {
	        	compte.setSolde(compte.getSolde() + op.getMontant());
	        	op.setDateEffet(new Date());
	        }else {
	        	compte.setSolde(compte.getSolde() - op.getMontant());
	        	op.setDateEffet(new Date());
	        }
			op.setCompte(compte);
			//credit.setDateEffet(new Date());
			compteService.saveCompte(compte);
			operationService.saveOperation(op);
		
		}
	 
	 
	 
	 
}
