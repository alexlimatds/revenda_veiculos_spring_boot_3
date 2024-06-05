
package net.revenda.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FabricanteRepository extends JpaRepository<Fabricante, Integer>{
    
    public List<Fabricante> findAllByOrderByDescricaoAsc();
}