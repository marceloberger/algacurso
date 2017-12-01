package com.algaworks.brewer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.algaworks.brewer.security.UsuarioSistema;

@Controller
public class SegurancaController {

	@GetMapping("/login")
	public String login(@AuthenticationPrincipal UsuarioSistema usuario) {
		
		if (usuario != null) {
			
			
			
			
			return "redirect: /brewer/" + usuario.getUsuario().getTenantId() + "/cervejas";
		}
		
		return "Login";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	
	
	@GetMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}
	
	@GetMapping("/500")
	public String erroServidor() {
		return "500";
	}
	
	
	
	
}