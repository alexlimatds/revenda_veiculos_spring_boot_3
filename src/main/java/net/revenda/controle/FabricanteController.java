package net.revenda.controle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;

@Controller
public class FabricanteController {
    
    @Autowired
    private FabricanteRepository repositorio;

    @GetMapping("/fabricantes")
    public String fabricantes(Model model){
        List<Fabricante> fabricantes = repositorio.findAllByOrderByDescricaoAsc();
        model.addAttribute("fabricantes", fabricantes);
        return "fabricantes";
    }

    @GetMapping(value = {"/fabricantes/form", "/fabricantes/form/{id}"})
    public String fabricanteForm(
        Model model, 
        @PathVariable Optional<Integer> id
    ){
        Fabricante f = null;
        if(id.isPresent()){ //edição
            Optional<Fabricante> r = repositorio.findById(id.get());
            if(!r.isPresent())
                throw new IllegalArgumentException("Id inválido");
            f = r.get();
        }
        else{ //novo fabricante
            f = new Fabricante();
        }
        model.addAttribute("fabricante", f);
        return "fabricante_form";
    }

    @PostMapping("/fabricantes/salvar")
    public String fabricantesSalvar(
        @ModelAttribute @Valid Fabricante fabricante, 
		BindingResult br, 
        final RedirectAttributes rAttrs
    ){
        if(br.hasErrors()){
            return "fabricante_form";
        }
        try{
            repositorio.save(fabricante);
            rAttrs.addFlashAttribute("msgSucesso", "Fabricante salvo com sucesso.");
        }catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/fabricantes";
    }

    @GetMapping("/fabricantes/excluir/{id}")
    public String fabricanteExcluir(
        @PathVariable Integer id, 
        final RedirectAttributes rAttrs
    ){
        try{
            repositorio.deleteById(id);
            rAttrs.addFlashAttribute("msgSucesso", "Fabricante excluído com sucesso.");
        }
        catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/fabricantes";
    }
}
