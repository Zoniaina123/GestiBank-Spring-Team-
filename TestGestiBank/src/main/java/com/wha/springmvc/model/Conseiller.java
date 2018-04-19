package com.wha.springmvc.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity(name = "Conseiller")
@DiscriminatorValue("CONSEILLER")

public class Conseiller extends User {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int matricule;

	private Date dateDebut;
	
	
	
	@OneToMany(mappedBy="conseiller",fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Collection<Client> clients;
	
	@ManyToOne(fetch = FetchType.EAGER) 
	 @JoinColumn(name="ADMIN_ID")
	@JsonIgnore
	private Administrator administrator;
	

	
	
	

	/**
	 * Default constructor
	 */
	public Conseiller() {
	}

	public Conseiller(int matricule, Date dateDebut, List<Client> clients, Administrator administrator) {
		super();
		this.matricule = matricule;
		this.dateDebut = dateDebut;
		this.clients = clients;
		this.administrator = administrator;

	}

	

	public int getMatricule() {
		return matricule;
	}

	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	
/*public List<Client> getClients() {
		return (List<Client>) clients;
	}

	public void setClients(Client clients) {
		this.clients = clients;
	}*/

	
	
	


	@Override
	public String toString() {
		return "Conseiller [matricule=" + matricule + ", dateDebut=" + dateDebut + ", admin=" + administrator.getId() + "]";
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public Collection<Client> getClients() {
		return clients;
	}

	public void setClients(Collection<Client> clients) {
		this.clients = clients;
	}


	

}