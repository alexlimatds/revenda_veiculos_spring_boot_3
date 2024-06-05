package net.revenda.controle;

import jakarta.validation.constraints.AssertTrue;

public class BuscaModeloForm {
    
    private String modelo;
    private Integer idFabricante;
    private Integer idTipoVeiculo;
    
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Integer getIdFabricante() {
        return idFabricante;
    }
    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }
    
    public Integer getIdTipoVeiculo() {
        return idTipoVeiculo;
    }
    public void setIdTipoVeiculo(Integer idTipoVeiculo) {
        this.idTipoVeiculo = idTipoVeiculo;
    }

    @AssertTrue(message="Informe pelo menos um campo")
    public boolean isValid(){
        return idFabricante != null || idTipoVeiculo != null || (modelo != null && !modelo.isBlank());
    }
}
