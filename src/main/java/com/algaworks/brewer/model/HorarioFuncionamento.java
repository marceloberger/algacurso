package com.algaworks.brewer.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.tenant.TenancyInterceptor;

	@Entity
	@Table(name="horario_funcionamento")
	public class HorarioFuncionamento implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="dia é obrigatório")
	private String dia;
	
	@NotBlank(message="Hora de abertura é obrigatória")
	private LocalDateTime horaInicial;
	
	@NotBlank(message="Hora de fechamento é obrigatória")
	private LocalDateTime horaFinal;
	
	private Boolean aberto;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@ManyToOne
	@JoinColumn(name = "codigo_pedido_online")
	private PedidoOnline pedidoOnline;
	
	
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


	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}
	
	


	


	public Boolean getAberto() {
		return aberto;
	}


	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}


	public LocalDateTime getHoraInicial() {
		return horaInicial;
	}


	public void setHoraInicial(LocalDateTime horaInicial) {
		this.horaInicial = horaInicial;
	}


	public LocalDateTime getHoraFinal() {
		return horaFinal;
	}


	public void setHoraFinal(LocalDateTime horaFinal) {
		this.horaFinal = horaFinal;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public PedidoOnline getPedidoOnline() {
		return pedidoOnline;
	}


	public void setPedidoOnline(PedidoOnline pedidoOnline) {
		this.pedidoOnline = pedidoOnline;
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
		HorarioFuncionamento other = (HorarioFuncionamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
	
	
	

}
