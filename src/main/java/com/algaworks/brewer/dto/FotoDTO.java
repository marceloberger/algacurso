package com.algaworks.brewer.dto;

public class FotoDTO {
	
	private String nome;
	
	private String contentType;
	
	private String tenantId;
	
	
	

	public FotoDTO(String nome, String contentType, String tenantId) {
		super();
		this.nome = nome;
		this.contentType = contentType;
		this.tenantId = tenantId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
	
	
	

}
