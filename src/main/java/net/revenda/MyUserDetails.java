package net.revenda;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import net.revenda.dominio.Usuario;

public class MyUserDetails extends org.springframework.security.core.userdetails.User{

    private Usuario usuario;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Usuario usuario) {
        super(username, password, authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}