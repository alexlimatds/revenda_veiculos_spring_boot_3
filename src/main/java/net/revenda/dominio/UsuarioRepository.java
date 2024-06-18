
package net.revenda.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRepositoryCustom{

    public Usuario findByLogin(String login);

    public List<Usuario> findByNomeIgnoreCaseContaining(String nome);
}
