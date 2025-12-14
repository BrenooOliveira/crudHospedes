package com.fatec.crudHospedes.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fatec.crudHospedes.domain.exception.BusinessException;
import com.fatec.crudHospedes.domain.model.Endereco;
import com.fatec.crudHospedes.domain.model.HospedeModel;
import com.fatec.crudHospedes.domain.service.HospedeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService service;


    @GetMapping("/cadastrar")
    public String formCadastrar(Model model) {
        HospedeModel h = new HospedeModel();
        h.setEndereco(new Endereco());
        model.addAttribute("hospede", h);
        return "hospedes/form";
    }



    @PostMapping
    public String cadastrar(HospedeModel hospede, Model model) {
        try {
            service.cadastrar(hospede);
            return "redirect:/hospedes";

        } catch (BusinessException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("hospede", hospede);
            return "hospedes/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model){
        model.addAttribute("hospede", service.consultar(id));
        return "hospedes/form";
    }

    @PostMapping("/{id}")
    public String alterar(@PathVariable Long id, HospedeModel hospede, Model model) {
        try {
            service.alterar(id, hospede);
            return "redirect:/hospedes";

        } catch (BusinessException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("hospede", hospede);
            return "hospedes/form";
        }
    }


    @GetMapping("/{id}/inativar")
    public String inativar(@PathVariable Long id){
        service.inativar(id);
        return "redirect:/hospedes";
    }
    
    @GetMapping("/{id}/ativar")
    public String ativar(@PathVariable Long id) {
        service.ativar(id);
        return "redirect:/hospedes";
    }


    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) Boolean ativo,
            Model model) {

        model.addAttribute("lista",
                service.consultarComFiltros(nome, cpf, cidade, ativo));

        model.addAttribute("nome", nome);
        model.addAttribute("cpf", cpf);
        model.addAttribute("cidade", cidade);
        model.addAttribute("ativo", ativo);

        return "hospedes/listar";
    }



}
