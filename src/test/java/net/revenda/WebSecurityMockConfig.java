package net.revenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import net.revenda.dominio.Usuario;

@TestConfiguration
public class WebSecurityMockConfig {
    
    @Bean
    @Primary
    public UserDetailsService mockUserDetailsService(){
        Map<String, UserDetails> usuarios = new HashMap<>();
        usuarios.put(
            "ana", 
            new MyUserDetails(
                new ArrayList<SimpleGrantedAuthority>(), 
                new Usuario(1, "Ana Silva", "11122233300", null, "ana", "senha", true, false)
            )
        );
        List<SimpleGrantedAuthority> permissoes = new ArrayList<>();
        permissoes.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
        usuarios.put(
            "valdir", 
            new MyUserDetails(
                permissoes, 
                new Usuario(1, "Valdir Silva", "44455566600", null, "valdir", "senha", true, true)
            )
        );

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserDetails ud = usuarios.get(username);
                if(ud == null)
                    throw new UsernameNotFoundException(username);
                return ud;
            }
            
        };
        
    }
}
