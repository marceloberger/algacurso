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

import com.algaworks.brewer.tenant.TenancyInterceptor;

@Entity
@Table(name="musica")
public class Musica implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_artista")
	private Artista artista;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_album")
	private Album album;
	
	private String musica;
	
	
	@Column(name="content_type_musica")
	private String contentTypeMusica;
	
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

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String musica) {
		this.musica = musica;
	}

	public String getContentTypeMusica() {
		return contentTypeMusica;
	}

	public void setContentTypeMusica(String contentTypeMusica) {
		this.contentTypeMusica = contentTypeMusica;
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
		Musica other = (Musica) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	

}
