package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="ponto_interesse")
public class PontoInteresse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 500, message = "O tamanho da descrição deve estar entre 1 e 500")
	private String descricao;
	
	private String telefone;

	@Email(message = "E-mail inválido")
	private String email;
	
	private String site;
	
	private String endereco;
	
	private String latitude;
	
	private String longitude;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name = "codigo_nossaVolta")
	private NossaVolta  nossaVolta;
	
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	

	public NossaVolta getNossaVolta() {
		return nossaVolta;
	}

	public void setNossaVolta(NossaVolta nossaVolta) {
		this.nossaVolta = nossaVolta;
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
		PontoInteresse other = (PontoInteresse) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	

}
