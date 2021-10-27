package com.laerson.treino.algaworks.springmvc.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laerson.treino.algaworks.springmvc.model.StatusOrdem;
import com.laerson.treino.algaworks.springmvc.model.Ordem;
import com.laerson.treino.algaworks.springmvc.repository.OrdemRepository;
import com.laerson.treino.algaworks.springmvc.repository.filter.OrdemFilter;
import com.laerson.treino.algaworks.springmvc.service.CadastroOrdemService;

@Controller
@RequestMapping("/ordens")
public class OrdemController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private OrdemRepository ordemRepository;
	
	@Autowired
	private CadastroOrdemService cadastroOrdemService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Ordem());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Ordem titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
		cadastroOrdemService.salvar(titulo);
		attributes.addFlashAttribute("mensagem", "Ordem de serviço salva com sucesso!");
		return "redirect:/ordens/novo";
		} catch(IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}

//	@RequestMapping(method = RequestMethod.POST)
//	public ModelAndView salvar(@Validated Titulo titulo, Errors errors) {
//		ModelAndView mv = new ModelAndView("CadastroTitulo");
//		if (errors.hasErrors()) {
//			return mv;
//		}
//		
//		tituloRepository.save(titulo);
//		mv.addObject("mensagem", "Título salvo com sucesso!");
////	Opção para limpar os dados quando o título é salvo com sucesso -->	mv.addObject(new Titulo());
//		return mv;
//	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") OrdemFilter filtro) {
		List<Ordem> todosOrdens = cadastroOrdemService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("ordens", todosOrdens);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Long codigoTitulo) {
		@SuppressWarnings("deprecation")
		Ordem titulo = ordemRepository.getOne(codigoTitulo);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroOrdemService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/ordens";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroOrdemService.receber(codigo);
		 
	}

	@ModelAttribute("todosStatusOrdem")
	public List<StatusOrdem> listaDosStatus() {
		return Arrays.asList(StatusOrdem.values());
	}

}
