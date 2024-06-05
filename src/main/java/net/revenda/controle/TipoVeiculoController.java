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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.TipoVeiculo;
import net.revenda.dominio.TipoVeiculoRepository;

@Controller
@RequestMapping("/tipos_veiculo")
public class TipoVeiculoController {
    
    @Autowired
    private TipoVeiculoRepository repositorio;

    @GetMapping
    public String tiposDeVeiculo(Model model){
        List<TipoVeiculo> tipos = repositorio.findAllByOrderByDescricaoAsc();
        model.addAttribute("tipos", tipos);
        return "tipos_veiculo";
    }

    @GetMapping(value = {"/form", "/form/{id}"})
    public String tipoVeiculoForm(
        Model model, 
        @PathVariable Optional<Integer> id
    ){
        TipoVeiculo t = null;
        if(id.isPresent()){ //edição
            Optional<TipoVeiculo> r = repositorio.findById(id.get());
            if(!r.isPresent())
                throw new IllegalArgumentException("Id inválido");
            t = r.get();
        }
        else{ //novo tipo de veículo
            t = new TipoVeiculo();
        }
        model.addAttribute("tipoVeiculo", t);
        return "tipo_veiculo_form";
    }

    @PostMapping("/salvar")
    public String tipoVeiculoSalvar(
        @ModelAttribute @Valid TipoVeiculo tipoVeiculo, 
		BindingResult br, 
        final RedirectAttributes rAttrs
    ){
        if(br.hasErrors()){
            return "tipo_veiculo_form";
        }
        try{
            repositorio.save(tipoVeiculo);
            rAttrs.addFlashAttribute("msgSucesso", "Tipo de veículo salvo com sucesso.");
        }catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/tipos_veiculo";
    }

    @GetMapping("/excluir/{id}")
    public String tipoVeiculoExcluir(
        @PathVariable Integer id, 
        final RedirectAttributes rAttrs
    ){
        try{
            repositorio.deleteById(id);
            rAttrs.addFlashAttribute("msgSucesso", "Tipo de veículo excluído com sucesso.");
        }
        catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/tipos_veiculo";
    }
}
