package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.ClientesFilter;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroClienteService;
import com.algaworks.brewer.service.exception.CpfClienteJaCadastradoException;
import com.algaworks.brewer.tenant.TenancyInterceptor;

@Controller
public class ClientesController {
	
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;

	@RequestMapping("/{tenantid}/clientes/novo")
	public ModelAndView novo(Cliente cliente, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		ModelAndView  mv = new ModelAndView("cliente/CadastroCliente");
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		
		mv.addObject("tenant", tenantId);
		return mv;
	}
	
	
	@RequestMapping(value = "/{tenantid}/clientes/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cliente cliente, BindingResult result, Model model, 
			RedirectAttributes attibutes, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		if(result.hasErrors()) {
			
			
			return novo(cliente, usuario);
		}
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		try {
			
		cadastroClienteService.salvar(cliente);
		
		} catch (CpfClienteJaCadastradoException e) {
			
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			
			return novo(cliente, usuario);
			
		}
		
		
		attibutes.addFlashAttribute("mensagem", "cliente salvo com sucesso");
		
		
		return new ModelAndView("redirect:/{tenantid}/clientes/novo");
		
	}
	
	@GetMapping("/{tenantid}/clientes")
	public ModelAndView pesquisar(ClientesFilter clientesFilter, BindingResult result, 
			@PageableDefault(size = 2 ) Pageable pageable, HttpServletRequest httpServletRequest, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		mv.addObject("tenant", tenantId);
		
		
		PageWrapper<Cliente> paginaWraper = new PageWrapper<> (clientes.filtrar(clientesFilter, pageable, tenantId), httpServletRequest);
		
		mv.addObject("pagina", paginaWraper );
		
		return mv;
	}
	
	
	@RequestMapping(value="/{tenantid}/clientes", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		validarTamanhoNome(nome);
		return clientes.findByNomeStartingWithIgnoreCaseAndTenantIdIgnoreCase(nome, tenantId);
		
		
	}


	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
	
	
}