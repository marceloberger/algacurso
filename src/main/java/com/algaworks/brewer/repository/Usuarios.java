package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.helper.usuario.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {
	
	public Optional<Usuario> findByEmailAndTenantId(String email, String tenantId);
	
	public Optional<Usuario> findByEmailIgnoreCase(String email);
	
	public List<Usuario> findByTenantIdAndCodigoIn(String tenantId, Long[] codigos);

}
