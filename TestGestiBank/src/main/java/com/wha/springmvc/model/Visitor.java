package com.wha.springmvc.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Visitor")
@DiscriminatorValue("VISITOR")
public class Visitor extends User {

	private static final long serialVersionUID = 1L;
	
	private int affectation;
	
	
//	@ManyToOne(fetch = FetchType.EAGER) 
//	 @JoinColumn(name="ADMIN_ID")
//	@JsonIgnore
//	private Administrator administrator;
	
	
	public Visitor() {
		super();
	}

	public Visitor(int affectation) {
		/*if (affectation>0) {
			this.affectation=affectation;
		}
		else {
			this.affectation=0;
		}*/
		this.affectation=affectation;
	}

	public int getAffectation() {
		return affectation;
	}

	public void setAffectation(int affectation) {
		this.affectation = affectation;
	}

	
}

