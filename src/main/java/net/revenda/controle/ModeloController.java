package net.revenda.controle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;
import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;
import net.revenda.dominio.TipoVeiculo;
import net.revenda.dominio.TipoVeiculoRepository;

@Controller
@RequestMapping("/modelos")
public class ModeloController {
    
    @Autowired
    private FabricanteRepository repositorioFabricante;
    @Autowired
    private TipoVeiculoRepository repositorioTipoVeiculo;
    @Autowired
    private ModeloRepository repositorio;

    //Disponibiliza a lista de fabricantes para a view
	@ModelAttribute("fabricantes")
	public List<Fabricante> gerarListaFabricantes(){
		return repositorioFabricante.findAllByOrderByDescricaoAsc();
	}
	
	//Disponibiliza a lista de tipos de veículo para a view
	@ModelAttribute("tipos")
	public List<TipoVeiculo> gerarListaTiposVeiculo(){
		return repositorioTipoVeiculo.findAllByOrderByDescricaoAsc();
	}

    @GetMapping
    public String modelos(Model model){
        model.addAttribute("form", new BuscaModeloForm());
        return "modelos";
    }
    
    @PostMapping("/pesquisar")
    public Object pesquisar(
        @ModelAttribute("form") @Valid BuscaModeloForm form, 
        BindingResult br
    ){
        if(br.hasErrors()){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body("Informe pelo menos um campo de pesquisa.");
        }
        else{
            List<Modelo> modelos = repositorio.findModeloByFields(form.getModelo(), form.getIdFabricante(), form.getIdTipoVeiculo());
            ModelAndView mv = new ModelAndView("modelos :: linhas_tabela");
            mv.addObject("modelos", modelos);
            return mv;
        }
    }

    @GetMapping(value = {"/form", "/form/{id}"})
    public String modeloForm(
        Model model, 
        @PathVariable Optional<Integer> id
    ){
        Modelo mv = null;
        if(id.isPresent()){ //edição
            Optional<Modelo> obj = repositorio.findById(id.get());
            if(!obj.isPresent())
                throw new IllegalArgumentException("Id inválido");
            mv = obj.get();
        }
        else{ //novo modelo
            mv = new Modelo();
        }
        model.addAttribute("modelo", mv);
        return "modelo_form";
    }

    @PostMapping("/salvar")
    public String modeloSalvar(
        @ModelAttribute @Valid Modelo modelo, 
		BindingResult br, 
        final RedirectAttributes rAttrs
    ){
        if(br.hasErrors()){
            return "modelo_form";
        }
        try{
            repositorio.save(modelo);
            rAttrs.addFlashAttribute("msgSucesso", "Modelo salvo com sucesso.");
        }catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/modelos";
    }

    @GetMapping("/excluir/{id}")
    public ResponseEntity<?> modeloExcluir(@PathVariable Integer id){
        try{
            repositorio.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
        }
    }
}
