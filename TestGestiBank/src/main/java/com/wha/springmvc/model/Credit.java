package com.wha.springmvc.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Depot")
public class Credit extends Operation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Credit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Credit(Date dateEffet, double montant, String libelle, Compte compte) {
		super(dateEffet, montant, libelle, compte);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	

}
