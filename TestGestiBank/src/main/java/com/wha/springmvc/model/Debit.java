package com.wha.springmvc.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Retrait")
public class Debit extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Debit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Debit(Date dateEffet, double montant, String libelle, Compte compte) {
		super(dateEffet, montant, libelle, compte);
		// TODO Auto-generated constructor stub
	}
	

	

}
