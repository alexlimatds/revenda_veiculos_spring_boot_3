
package net.revenda.dominio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Integer>{
    
    public List<TipoVeiculo> findAllByOrderByDescricaoAsc();
}