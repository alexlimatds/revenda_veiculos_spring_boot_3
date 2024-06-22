package net.revenda;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import net.revenda.dominio.Usuario;

public class MyUserDetails extends org.springframework.security.core.userdetails.User{

    private Usuario usuario;

    public MyUserDetails(Collection<? extends GrantedAuthority> authorities, Usuario usuario) {
        super(usuario.getLogin(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}