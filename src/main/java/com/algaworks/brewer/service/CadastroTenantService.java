package com.algaworks.brewer.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Grupo;
import com.algaworks.brewer.model.Tenant;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Tenants;
import com.algaworks.brewer.service.exception.NomeTenantCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroTenantService {
	
	@Autowired
	private Tenants tenants;
	
	@Autowired
	private  PasswordEncoder passwordEnconder;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	
	
	
	@Transactional
	public Tenant salvar(Tenant tenant) {
		
		Optional<Tenant> tenantOptional = tenants.findByTenantIdIgnoreCase(tenant.getTenantId());                        
		
		if(tenantOptional.isPresent()) {
			
			throw new NomeTenantCadastradoException("Nome do tenant já cadastrado");	
			
		}
		
		if(tenant.isNovo() && StringUtils.isEmpty(tenant.getSenha())) {
			
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatoria para nova empresa");
		}
		
		
		
		
		if(tenant.isNovo()) {
			
			Usuario usuario = new Usuario();
			
			usuario.setAtivo(true);
			
			usuario.setSenha(tenant.getSenha());
			
			usuario.setTenantId(tenant.getTenantId().trim());
			
			usuario.setDataNascimento(LocalDate.now());
			
			usuario.setNome(tenant.getNomeAdmin());
			
			usuario.setEmail(tenant.getEmailEmpresa());
			
			Grupo grupo = new Grupo();
			
			grupo.setCodigo(new Long(1));
			
			grupo.setNome("Administrador");
			
			List<Grupo> grupos = new ArrayList<>();
			
			grupos.add(grupo);
			
			
			
			usuario.setGrupos(grupos);
			
			cadastroUsuarioService.salvar(usuario, tenant.getTenantId());
			
			tenant.setSenha(passwordEnconder.encode(tenant.getSenha()));
			tenant.setConfirmacaoSenha(tenant.getSenha());
			
			
		}
		
		
		tenant.setAtivo(true);
		
		
		return tenants.saveAndFlush(tenant);
	}

}
