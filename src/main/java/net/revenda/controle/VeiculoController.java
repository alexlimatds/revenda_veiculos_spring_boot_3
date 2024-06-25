package net.revenda.controle;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;
import net.revenda.dominio.Foto;
import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;
import net.revenda.dominio.TipoVeiculo;
import net.revenda.dominio.TipoVeiculoRepository;
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

    @Autowired
    private TipoVeiculoRepository repositorioTipoVeiculo;

    //Disponibiliza a lista de fabricantes para a view
	@ModelAttribute("fabricantes")
	public List<Fabricante> gerarListaFabricantes(){
		return repositorioFabricante.findAllByOrderByDescricaoAsc();
	}

    //Disponibiliza a lista de tipos de veículo para a view
	@ModelAttribute("tipos")
	public List<TipoVeiculo> gerarListaTiposDeVeiculo(){
		return repositorioTipoVeiculo.findAllByOrderByDescricaoAsc();
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
            List<Veiculo> veiculos = repositorioVeiculo.findVeiculoByFields(form.getPlaca(), form.getIdFabricante(), form.getIdModelo());
            for(Veiculo v : veiculos) // carrega foto
                v.getFoto();

            re = ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(veiculos);
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

    @GetMapping(value = {"/form", "/form/{id}"})
    public String veiculoForm(
        Model model, 
        @PathVariable Optional<Integer> id
    ){
        Veiculo v = null;
        if(id.isPresent()){ //edição
            Optional<Veiculo> r = repositorioVeiculo.findById(id.get());
            if(!r.isPresent())
                throw new IllegalArgumentException("Id inválido");
            v = r.get();
        }
        else{ //novo veículo
            v = new Veiculo();
        }
        model.addAttribute("veiculo", v);
        model.addAttribute("modelos", repositorioModelo.findAll());
        model.addAttribute("novoModelo", new Modelo()); //usado no cadastro de modelo
        return "veiculo_form";
    }

    @PostMapping("/salvar")
    public String veiculosSalvar(
        @ModelAttribute @Valid Veiculo veiculo, 
		BindingResult br, 
        @RequestParam("arquivoFoto") MultipartFile arquivoFoto, 
        final RedirectAttributes rAttrs
    ){
        if(br.hasErrors()){
            return "veiculo_form";
        }
        try{
            if(!arquivoFoto.isEmpty()){
                byte[] bytes = arquivoFoto.getBytes();
                String mimeType = arquivoFoto.getContentType();
                veiculo.setFoto(new Foto(bytes, mimeType));
            }
            else if(veiculo.getId() != null){
                veiculo.setFoto(
                    repositorioVeiculo.findById(veiculo.getId()).get().getFoto()
                );
            }
            repositorioVeiculo.save(veiculo);
            rAttrs.addFlashAttribute("msgSucesso", "Veículo de placa " + veiculo.getPlaca() + " salvo com sucesso.");
        }catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/veiculos";
    }

    @GetMapping("/{idVeiculo}/foto")
	public ResponseEntity<byte[]> foto(@PathVariable Integer idVeiculo){
        Foto foto = repositorioVeiculo.findById(idVeiculo).get().getFoto();
		HttpHeaders headers = new HttpHeaders();
		String[] tokens = foto.getMimeType().split("/");
		MediaType mimeType = new MediaType(tokens[0], tokens[1]);
		headers.setContentType(mimeType);
		return new ResponseEntity<>(foto.getBytes(), headers, HttpStatus.OK);
	}

    @GetMapping("/cad_modelo")
    public String formCadastroModelo(
        Model model, 
        @RequestParam(required = false) Integer idVeiculo)
    {
        model.addAttribute("modelo", new Modelo());
        if(idVeiculo != null)
            model.addAttribute("idVeiculo", idVeiculo);
        return "veiculos_cad_modelo";
    }

    @PostMapping("/salvar_modelo")
    public String modeloSalvar(
        @ModelAttribute @Valid Modelo modelo, 
		BindingResult br, 
        final RedirectAttributes rAttrs, 
        Model model, 
        @RequestParam(required = false) Integer idVeiculo
    ){
        if(br.hasErrors()){
            return "veiculos_cad_modelo";
        }
        try{
            repositorioModelo.save(modelo);
            rAttrs.addFlashAttribute("msgSucesso", "Modelo salvo com sucesso.");
        }catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        String url_destino = "redirect:/veiculos/form";
        if(idVeiculo != null)
            url_destino += "/" + idVeiculo;
        //TODO setar modelo recem cadastrado no campo de veículo
        return url_destino;
    }
}
