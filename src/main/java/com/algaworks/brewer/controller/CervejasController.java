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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroCervejaService;
import com.algaworks.brewer.tenant.TenancyInterceptor;

@Controller
public class CervejasController {
	
	
	
	
	@Autowired
	private Estilos estilos;
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@RequestMapping("/{tenantid}/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		
		
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		mv.addObject("tenant", tenantId);
		
		
		return mv;
		
	}
	
	@RequestMapping(value = "/{tenantid}/cervejas/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attibutes, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		if(result.hasErrors()) {
			
			
			return novo(cerveja, usuario);
		}
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		
		cadastroCervejaService.salvar(cerveja);
		attibutes.addFlashAttribute("mensagem", "cerveja salva com sucesso");
		
		
		return new ModelAndView("redirect:/{tenantid}/cervejas/novo");
		
	}
	
	@GetMapping("/{tenantid}/cervejas")
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 2 ) Pageable pageable, HttpServletRequest httpServletRequest, @AuthenticationPrincipal UsuarioSistema usuario) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisasCervejas");
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		mv.addObject("tenant", tenantId);
		
		
		
		
		
		PageWrapper<Cerveja> paginaWraper = new PageWrapper<> (cervejas.filtrar(cervejaFilter, pageable, tenantId), httpServletRequest);
		
		mv.addObject("pagina", paginaWraper );
		//mv.addObject("cervejas", cervejas.findAll(pageable));
		return mv;
	}
	
	@GetMapping("/{tenantid}/semPaginacao")
	public @ResponseBody ResponseEntity<?> filtrar(String nome, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		
		
		return ResponseEntity.ok(cervejas.findByTenantIdIgnoreCase( tenantId));
		
		
		//return ResponseEntity.ok(cervejas.findByNomeAndTenantIdIgnoreCase(nome != null ? nome : "%", tenantId));
	}
	
	@RequestMapping(value = "/cervejas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		return cervejas.porSkuOuNome(skuOuNome, tenantUsuario);
	}
	
	
	
	

}
