package net.revenda.dominio;

import java.util.List;

/**
 * Interface para a definição de métodos além dos providos por JpaRepository.
 */
public interface VeiculoRepositoryCustom {
    List<Veiculo> findVeiculoByFields(String placa, Integer idFabricante, Integer idModelo);
}
