package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.NomeUsuarioJaCadastradaException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private  PasswordEncoder passwordEnconder;
	
	@Transactional
	public void salvar(Usuario usuario, String tenantId) {
		
		Optional<Usuario> usuarioExistente =  usuarios.findByEmailIgnoreCase(usuario.getEmail());
		
		if(usuarioExistente.isPresent()) {
			
			throw new NomeUsuarioJaCadastradaException("Nome do usuário já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatoria para novo usuario");
		}
		
		
		if(usuario.isNovo()) {
			usuario.setSenha(passwordEnconder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
			
			
		}
		usuarios.save(usuario);
		
		
	}
	
	
     @Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario, String tenantUsuario) {
    	 
		statusUsuario.executar(codigos, usuarios, tenantUsuario);
		
		
		
	}
	
	
	

}
