package net.revenda.dominio;

import java.util.List;

/**
 * Interface para a definição de métodos além dos providos por JpaRepository.
 */
public interface ModeloRepositoryCustom {
    List<Modelo> findModeloByFields(String descricao, Integer idFabricante, Integer idTipoVeiculo);
}
