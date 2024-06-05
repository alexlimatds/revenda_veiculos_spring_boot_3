package net.revenda.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Representa um modelo de veículo como Gol, Uno, Fiesta, etc.
 */
@Entity
public class Modelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
	private String descricao;
	
    @NotNull
    @ManyToOne(optional = false)
	private Fabricante fabricante;
	
    @NotNull
    @ManyToOne(optional = false)
	private TipoVeiculo tipo;
	
	public Modelo(Integer id, String descricao, Fabricante fabricante, 
			TipoVeiculo tipo) {
		this.id = id;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.tipo = tipo;
	}
	
	public Modelo() {
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Modelo other = (Modelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (descricao != null)
            return fabricante.getDescricao() + " " + descricao + tipo.getDescricao();
        return super.toString();
    }
}
