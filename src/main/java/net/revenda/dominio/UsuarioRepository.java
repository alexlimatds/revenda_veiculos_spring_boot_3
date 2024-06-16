
package net.revenda.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

    public Usuario findByLogin(String login);
}
