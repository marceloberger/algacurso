package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="contato")
public class Contato implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "O cabeçalho é obrigatório")
	@Size(max = 50, message = "O tamanho do cabeçalho deve estar entre 1 e 50")
	private String cabecalho1;
	
	@NotBlank(message = "O cabeçalho é obrigatório")
	@Size(max = 50, message = "O tamanho do cabeçalho deve estar entre 1 e 50")
	private String cabecalho2;
	
	private String site;
	
	private String telefone;

	@Email(message = "E-mail inválido")
	private String email;
	
	private String endereco;
	
	private String latitude;
	
	private String longitude;
	
	private String imagem1;
	
	
	@Column(name="content_type1")
	private String contentType1;
	
	private String imagem2;
	
	
	@Column(name="content_type2")
	private String contentType2;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	
	@ManyToOne
	@JoinColumn(name = "codigo_contato")
	private Contato contato;
	
	
	@OneToMany(mappedBy = "contato")
	private List<HorarioAbertura> horarios;
	
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

	public String getCabecalho1() {
		return cabecalho1;
	}

	public void setCabecalho1(String cabecalho1) {
		this.cabecalho1 = cabecalho1;
	}

	public String getCabecalho2() {
		return cabecalho2;
	}

	public void setCabecalho2(String cabecalho2) {
		this.cabecalho2 = cabecalho2;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
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

	public String getImagem1() {
		return imagem1;
	}

	public void setImagem1(String imagem1) {
		this.imagem1 = imagem1;
	}

	public String getContentType1() {
		return contentType1;
	}

	public void setContentType1(String contentType1) {
		this.contentType1 = contentType1;
	}

	public String getImagem2() {
		return imagem2;
	}

	public void setImagem2(String imagem2) {
		this.imagem2 = imagem2;
	}

	public String getContentType2() {
		return contentType2;
	}

	public void setContentType2(String contentType2) {
		this.contentType2 = contentType2;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public List<HorarioAbertura> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioAbertura> horarios) {
		this.horarios = horarios;
	}
	
	

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
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
		Contato other = (Contato) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
