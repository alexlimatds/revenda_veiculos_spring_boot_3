package net.revenda.controle;

import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;
import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;
import net.revenda.dominio.Veiculo;
import net.revenda.dominio.VeiculoRepository;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {
    
    @Autowired
    private ModeloRepository repositorioModelo;

    @Autowired
    private FabricanteRepository repositorioFabricante;

    @Autowired
    private VeiculoRepository repositorioVeiculo;

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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> re;
        if(br.hasErrors()){
            var body = new HashMap<String, String>();
            body.put("msgErro", "Informe pelo menos um campo de pesquisa.");
            re = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(body);
        }
        else{
            // TODO : usar consulta
            List<Veiculo> modelos = repositorioVeiculo.findAll();
            re = ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(modelos);
        }
        return re;
    }

    @GetMapping("/excluir/{id}")
    public ResponseEntity<?> veiculoExcluir(@PathVariable Integer id){
        try{
            repositorioVeiculo.deleteById(id);
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
