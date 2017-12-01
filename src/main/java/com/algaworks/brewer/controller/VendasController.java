package com.algaworks.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.session.TabelasItensSession;
import com.algaworks.brewer.tenant.TenancyInterceptor;

@Controller
public class VendasController {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelasItensSession tabelaItens;
	
	@GetMapping("/{tenantid}/vendas/nova")
	public ModelAndView nova(@AuthenticationPrincipal UsuarioSistema usuario, String uuid) {
		
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		
		String tenantId = TenancyInterceptor.getTenantId();
		
		String tenantUsuario = usuario.getUsuario().getTenantId();
		
		if(!tenantId.toLowerCase().equals(tenantUsuario.toLowerCase())) {
			
			return new ModelAndView("404");
			
			
		}
		
		mv.addObject("tenant", tenantId);
		mv.addObject("uuid", UUID.randomUUID().toString());
		
		
		return mv;
	}
	
	@PostMapping("/{tenantid}/vendas/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		
		
		ModelAndView mv = mvTabelaItensVenda(uuid);
		
		return mv;
		
		
	}
	
	@PutMapping("/{tenantid}/vendas/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade, String uuid) {
		
		tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		
		ModelAndView mv = mvTabelaItensVenda(uuid);
		
		
		return mv;
	}
	
	@DeleteMapping("/{tenantid}/vendas/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid) {
		
		tabelaItens.excluirItem(uuid, cerveja);
		
		ModelAndView mv = mvTabelaItensVenda(uuid);
		
		
		return mv;
		
		
	}

	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		String tenantId = TenancyInterceptor.getTenantId();
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("tenant", tenantId);
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
	
	
	
	
	
	
	

}