package com.algaworks.brewer.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="evento_unico")
public class EventoUnico extends Evento{

	
	private static final long serialVersionUID = 1L;
	
	
	private String imagemMovel;
	
	
	@Column(name="content_typemovel")
	private String contentTypeMovel;
	
	private String imagemTablet;
	
	
	@Column(name="content_typetablet")
	private String contentTypeTablet;
	
	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;
	
	@Column(name = "data_termino")
	private LocalDateTime dataTermino;
	
	@NotBlank(message="nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatória")
	@Size(max = 500, message = "O tamanho da descrição deve estar entre 1 e 500")
	private String descricao;
	
	@Column(name="endereco_secao1")
	private String enderecoSecao1;
	
	@Column(name="endereco_secao2")
	private String enderecoSecao2;
	
	private String endereco;
	
	private String latitude;
	
	private String longitude;

	public String getImagemMovel() {
		return imagemMovel;
	}

	public void setImagemMovel(String imagemMovel) {
		this.imagemMovel = imagemMovel;
	}

	public String getContentTypeMovel() {
		return contentTypeMovel;
	}

	public void setContentTypeMovel(String contentTypeMovel) {
		this.contentTypeMovel = contentTypeMovel;
	}

	public String getImagemTablet() {
		return imagemTablet;
	}

	public void setImagemTablet(String imagemTablet) {
		this.imagemTablet = imagemTablet;
	}

	public String getContentTypeTablet() {
		return contentTypeTablet;
	}

	public void setContentTypeTablet(String contentTypeTablet) {
		this.contentTypeTablet = contentTypeTablet;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDateTime dataTermino) {
		this.dataTermino = dataTermino;
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

	public String getEnderecoSecao1() {
		return enderecoSecao1;
	}

	public void setEnderecoSecao1(String enderecoSecao1) {
		this.enderecoSecao1 = enderecoSecao1;
	}

	public String getEnderecoSecao2() {
		return enderecoSecao2;
	}

	public void setEnderecoSecao2(String enderecoSecao2) {
		this.enderecoSecao2 = enderecoSecao2;
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
	
	
	
	
	
	

}
