package net.revenda.dominio;

public interface UsuarioRepositoryCustom {
    public <S extends Usuario> S save(S usuario);
}
