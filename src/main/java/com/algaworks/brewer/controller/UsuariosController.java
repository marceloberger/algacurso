package com.algaworks.brewer.controller;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;

import com.algaworks.brewer.model.Tenant;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Grupos;
import com.algaworks.brewer.repository.Tenants;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroUsuarioService;
import com.algaworks.brewer.service.StatusUsuario;
import com.algaworks.brewer.service.exception.NomeUsuarioJaCadastradaException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import com.algaworks.brewer.tenant.TenancyInterceptor;

@Controller
public class UsuariosController {
	
	@Autowired
	private Tenants tenants;
	
	@Autowired
	private  Grupos grupos;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Usuarios usuarios;

	@RequestMapping("/{tenantid}/usuarios/novo")
	public ModelAndView novo(Usuario usuario, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		Optional<Tenant>  tenantEncontado = tenants.findByTenantIdIgnoreCase(tenantId);
		
		String tenantUsuario = usuarioSistema.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		
		
		if(tenantEncontado.isPresent()) {
			
			mv.addObject("tenant", tenantId);
			mv.addObject("grupos", grupos.findAll());
			
			return mv;
		} else {
			
			return new ModelAndView("404");
		}
		
		
		
		
	}
	
	@RequestMapping(value = "/{tenantid}/usuarios/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attibutes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		if(result.hasErrors()) {
			
			
			return novo(usuario, usuarioSistema);
		}
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuarioSistema.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		
		
		
		
		try {
			
		cadastroUsuarioService.salvar(usuario, tenantId);
		
		} catch (NomeUsuarioJaCadastradaException e) {
			
			result.rejectValue("email", e.getMessage(), e.getMessage());
			
			return novo(usuario, usuarioSistema);
		} catch (SenhaObrigatoriaUsuarioException e) {
			
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			
			return novo(usuario, usuarioSistema);
		}
		
		attibutes.addFlashAttribute("mensagem", "usuario salvo com sucesso");
		
		
		return new ModelAndView("redirect:/{tenantid}/usuarios/novo");
		
	}
	
	@GetMapping("/{tenantid}/usuarios")
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, 
			@PageableDefault(size = 3 ) Pageable pageable, HttpServletRequest httpServletRequest, 
			@AuthenticationPrincipal UsuarioSistema usuario) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
		
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		mv.addObject("tenant", tenantId);
		mv.addObject("grupos", grupos.findAll());
		
		
		
		
		PageWrapper<Usuario> paginaWraper = new PageWrapper<> (usuarios.filtrar(usuarioFilter, pageable, tenantId), httpServletRequest);
		
		mv.addObject("pagina", paginaWraper );
		
		
		return mv;
	}
	
	
	@PutMapping("/{tenantid}/usuarios/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, 
			@AuthenticationPrincipal UsuarioSistema usuario, @RequestParam("status") StatusUsuario statusUsuario) {
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			cadastroUsuarioService.alterarStatus(codigos, statusUsuario, tenantUsuario);
			
			
			
		}
		
		
		
		
		
		
	}
}