package net.revenda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.revenda.dominio.Usuario;
import net.revenda.dominio.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username);
        if(usuario == null)
            throw new UsernameNotFoundException("Usuário não encontrado");
        
        List<SimpleGrantedAuthority> permissoes = new ArrayList<>();
        if(usuario.isGerente())
            permissoes.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
        return new MyUserDetails(permissoes, usuario);
    }
    
}
