package net.revenda.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;
import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {
    
    @Autowired
    private ModeloRepository repositorioModelo;

    @Autowired
    private FabricanteRepository repositorioFabricante;

    //Disponibiliza a lista de fabricantes para a view
	@ModelAttribute("fabricantes")
	public List<Fabricante> gerarListaFabricantes(){
		return repositorioFabricante.findAllByOrderByDescricaoAsc();
	}
    
    @GetMapping
    public String veiculos(Model model){
        model.addAttribute("form", new BuscaVeiculoForm());
        return "veiculos";
    }

    @GetMapping("/modelos")
    @ResponseBody
    public List<Modelo> modelos(@RequestParam("idFabricante") Integer idFabricante){
        return repositorioModelo.findAllByFabricanteIdOrderByFabricanteDescricaoAsc(idFabricante); // objetos Java são serializados como JSON por meio da biblioteca Jackson
    }

    @PostMapping("/pesquisar")
    public ResponseEntity<?> pesquisar(
        @ModelAttribute @Valid BuscaVeiculoForm form, 
        BindingResult br
    ){
        System.out.println("BATEU!!!!");
        ResponseEntity<?> re;
        if(br.hasErrors()){
            re = ResponseEntity.badRequest().body("{'msgErro': 'Informe pelo menos um campo de pesquisa.'}");
        }
        else{
            // TODO : usar consulta
            List<Modelo> modelos = repositorioModelo.findAll();
            re = ResponseEntity.ok().body(modelos);
        }
        //re.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return re;
    }
}
