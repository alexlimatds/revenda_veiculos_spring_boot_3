package net.revenda.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional
    public void alterarSenha(Integer idUsuario, String novaSenha) {
        Usuario usuario = entityManager.find(Usuario.class, idUsuario);
        if(usuario == null)
            throw new IllegalArgumentException("Usuário não encontrado");
        usuario.setSenha(
            passwordEncoder.encode(novaSenha)
        );
        entityManager.merge(usuario);
    }
}
