package net.revenda.dominio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <S extends Usuario> S save(S usuario) {
        if(usuario.getId() != null){
            Usuario usuarioEmBanco = entityManager.find(Usuario.class, usuario.getId());
            usuario.setLogin(usuarioEmBanco.getLogin());
            usuario.setSenha(usuarioEmBanco.getSenha());
        }
        else{
            // valida login
            long count = (long)entityManager.createQuery("SELECT COUNT(u.id) from Usuario u WHERE u.login = :param")
                .setParameter("param", usuario.getLogin())
                .getSingleResult();
            if(count != 0)
                throw new LoginIndisponivelException("Login não disponível");
        }
        entityManager.merge(usuario);
        return usuario;
    }
}
