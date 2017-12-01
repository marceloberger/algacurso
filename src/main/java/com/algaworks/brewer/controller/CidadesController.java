package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.repository.filter.CidadesFilter;
import com.algaworks.brewer.service.CadastroCidadeService;
import com.algaworks.brewer.service.exception.NomeCidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private Estados estados;

	@RequestMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		
		mv.addObject("todosEstados", estados.findAll());
		
		
		return mv;
	}
	
	@RequestMapping(value = "/nova", method = RequestMethod.POST)
	//@CacheEvict(value="cidades", allEntries= true)
	@CacheEvict(value="cidades", key="#cidade.estado.codigo", condition="#cidade.temEstado()")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attibutes) {
		
		if(result.hasErrors()) {
			
			
			return nova(cidade);
		}
		
		try {
		cadastroCidadeService.salvar(cidade);
		
		} catch (NomeCidadeJaCadastradaException e){
			
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			
			return nova(cidade);
			
			
			
			
		}
		attibutes.addFlashAttribute("mensagem", "cidade salva com sucesso");
		
		
		return new ModelAndView("redirect:/cidades/nova");
		
	}
	
	
	
	@Cacheable(value="cidades", key="#codigoEstado")
	// /brewer/cidades?estado=2
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(@RequestParam(name="estado", defaultValue = "-1") Long codigoEstado) {
		
		List<Cidade> todasCidades =  cidades.findByEstadoCodigo(codigoEstado);
		
		return todasCidades;
		
		
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadesFilter cidadesFilter, BindingResult result, @PageableDefault(size = 2 ) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");
		mv.addObject("todosEstados", estados.findAll());
		
		
		PageWrapper<Cidade> paginaWraper = new PageWrapper<> (cidades.filtrar(cidadesFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWraper );
		
		return mv;
	}
	
}