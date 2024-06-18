package net.revenda.controle;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import net.revenda.dominio.LoginIndisponivelException;
import net.revenda.dominio.Usuario;
import net.revenda.dominio.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository repositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String usuarios(Model model){
        return "usuarios";
    }
    
    @GetMapping("/pesquisar")
    public Object pesquisar(
        @RequestParam String nome
    ){
        if(nome == null || nome.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body("Informe pelo menos um campo de pesquisa.");
        }
        else{
            List<Usuario> usuarios = repositorio.findByNomeIgnoreCaseContaining(nome);
            ModelAndView mv = new ModelAndView("usuarios :: linhas_tabela");
            mv.addObject("usuarios", usuarios);
            return mv;
        }
    }

    @GetMapping(value = {"/form", "/form/{id}"})
    public String usuarioForm(
        Model model, 
        @PathVariable Optional<Integer> id
    ){
        UsuarioForm usuarioForm = new UsuarioForm();
        if(id.isPresent()){ //edição
            Optional<Usuario> obj = repositorio.findById(id.get());
            if(!obj.isPresent())
                throw new IllegalArgumentException("Id inválido");
            Usuario usuario = obj.get();
            usuarioForm.setNome(usuario.getNome());
            usuarioForm.setId(usuario.getId());
            usuarioForm.setCpf(usuario.getCpf());
            usuarioForm.setTelefone(usuario.getTelefone());
            usuarioForm.setAtivo(usuario.isAtivo());
            usuarioForm.setGerente(usuario.isGerente());
            usuarioForm.setLogin(usuario.getLogin());
        }
        model.addAttribute("usuarioForm", usuarioForm);
        return "usuario_form";
    }

    @PostMapping("/salvar")
    public String usuarioSalvar(
        @ModelAttribute @Valid UsuarioForm usuarioForm, 
		BindingResult br, 
        final RedirectAttributes rAttrs, 
        Model model
    ){
        if(br.hasErrors()){
            return "usuario_form";
        }
        try{
            Usuario usuario = new Usuario();
            usuario.setNome(usuarioForm.getNome());
            usuario.setId(usuarioForm.getId());
            usuario.setCpf(usuarioForm.getCpf());
            usuario.setTelefone(usuarioForm.getTelefone());
            usuario.setAtivo(usuarioForm.getAtivo());
            usuario.setGerente(usuarioForm.getGerente());
            usuario.setLogin(usuarioForm.getLogin());
            usuario.setSenha(
                passwordEncoder.encode(usuarioForm.getSenha())
            );
            repositorio.save(usuario);
            rAttrs.addFlashAttribute("msgSucesso", "Usuário salvo com sucesso.");
        }
        catch(LoginIndisponivelException ex){
            model.addAttribute("msgErro", "Login indisponível.");
            return "usuario_form";
        }
        catch(Exception ex){
            ex.printStackTrace();
            rAttrs.addFlashAttribute("msgErro", "Ocorreu um erro durante a operação.");
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/excluir/{id}")
    public ResponseEntity<?> usuarioExcluir(@PathVariable Integer id){
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
