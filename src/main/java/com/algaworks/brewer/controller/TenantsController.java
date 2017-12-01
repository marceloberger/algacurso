package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Tenant;
import com.algaworks.brewer.service.CadastroTenantService;
import com.algaworks.brewer.service.exception.NomeTenantCadastradoException;

@Controller
@RequestMapping("/tenants")
public class TenantsController {
	
	@Autowired
	private CadastroTenantService cadastroTenantService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Tenant tenant) {
		
		ModelAndView mv = new ModelAndView("tenant/CadastroTenant");
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tenant tenant, BindingResult result, Model model, RedirectAttributes attibutes) {
		
		if(result.hasErrors()) {
			
			
			return novo(tenant);
		}
		
		
		try {
			cadastroTenantService.salvar(tenant);
		} catch (NomeTenantCadastradoException e) {
			result.rejectValue("tenantId", e.getMessage(), e.getMessage());
			
			return novo(tenant);
			
		}
		attibutes.addFlashAttribute("mensagem", "tenant salvo com sucesso");
		
		
		return new ModelAndView("redirect:/tenants/novo");
		
	}
	
	
	

}
