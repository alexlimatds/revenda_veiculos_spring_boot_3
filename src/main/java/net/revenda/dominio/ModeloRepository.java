
package net.revenda.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Integer>, ModeloRepositoryCustom{
    
    public List<Modelo> findAllByOrderByDescricaoAsc();

    public List<Modelo> findAllByFabricanteIdOrderByFabricanteDescricaoAsc(Integer idFabricante);
}
