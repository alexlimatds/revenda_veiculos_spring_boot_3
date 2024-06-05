package net.revenda.controle;

import jakarta.validation.constraints.AssertTrue;

public class BuscaVeiculoForm {
    
    private String placa;
    private Integer idFabricante;
    private Integer idModelo;

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getIdFabricante() {
        return idFabricante;
    }
    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public Integer getIdModelo() {
        return idModelo;
    }
    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    @AssertTrue(message="Informe pelo menos um campo")
    public boolean isValid(){
        return (placa != null && !placa.isBlank()) || idFabricante != null || idModelo != null;
    }    
}
