package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="informacao")
public class Informacao implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String fotoMobile;
	
	
	@Column(name="content_mobile")
	private String contentMobile;
	
	private String fotoTablet;
	
	
	@Column(name="content_mobile")
	private String contentTablet;
	
	@NotBlank(message="nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 500, message = "O tamanho da descrição deve estar entre 1 e 500")
	private String descricao;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_categoria")
	private CategoriaInformacao categoria;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	
	
	@PrePersist @PreUpdate
	private void definirTenant() {
		
		String tenantID = TenancyInterceptor.getTenantId();
		
		if(tenantID != null) {
			
			this.tenantId = tenantID;
		}
		
	}



	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}



	public String getFotoMobile() {
		return fotoMobile;
	}



	public void setFotoMobile(String fotoMobile) {
		this.fotoMobile = fotoMobile;
	}



	public String getContentMobile() {
		return contentMobile;
	}



	public void setContentMobile(String contentMobile) {
		this.contentMobile = contentMobile;
	}



	public String getFotoTablet() {
		return fotoTablet;
	}



	public void setFotoTablet(String fotoTablet) {
		this.fotoTablet = fotoTablet;
	}



	public String getContentTablet() {
		return contentTablet;
	}



	public void setContentTablet(String contentTablet) {
		this.contentTablet = contentTablet;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public CategoriaInformacao getCategoria() {
		return categoria;
	}



	public void setCategoria(CategoriaInformacao categoria) {
		this.categoria = categoria;
	}



	public String getTenantId() {
		return tenantId;
	}



	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informacao other = (Informacao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	
	

}
