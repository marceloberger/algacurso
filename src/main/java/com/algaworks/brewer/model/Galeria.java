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

import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="galeria")
public class Galeria implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="nome é obrigatório")
	private String nome;
	
	
	@OneToMany(mappedBy = "galeria")
	private List<Imagem> imagens;
	
	private String foto;
	
	
	@Column(name="content_type")
	private String contentType;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	
	@ManyToOne
	@JoinColumn(name = "codigo_galeria")
	private Galerias galerias;
	
	
	
	@PrePersist @PreUpdate
	private void definirTenant() {
		
		String tenantID = TenancyInterceptor.getTenantId();
		
		if(tenantID != null) {
			
			this.tenantId = tenantID;
		}
		
	}
	
	
	


	public String getTenantId() {
		return tenantId;
	}





	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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


	public List<Imagem> getImagens() {
		return imagens;
	}


	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	


	public Galerias getGalerias() {
		return galerias;
	}





	public void setGalerias(Galerias galerias) {
		this.galerias = galerias;
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
		Galeria other = (Galeria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	

}
