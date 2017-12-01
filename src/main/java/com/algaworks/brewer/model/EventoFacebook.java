package com.algaworks.brewer.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="evento_facebook")
public class EventoFacebook extends Evento{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="url é obrigatório")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	
	

}
