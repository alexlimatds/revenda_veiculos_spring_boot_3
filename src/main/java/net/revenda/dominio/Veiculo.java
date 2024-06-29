package net.revenda.dominio;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Representa um veículo comercializado pela loja.
 */
@Entity
public class Veiculo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Min(1900)
	@NotNull
	private Integer anoFabricacao;

	@Pattern(regexp="[A-Za-z]{3}\\d{1}[0-9A-Za-z]\\d{2}")  // padrão Mercosul e anterior
	@NotNull
	private String placa;

    @Embedded
	private Foto foto;
	
    @NotNull
    @ManyToOne
	private Modelo modelo;
	
	public Veiculo() {
		super();
	}
	
	public Veiculo(Integer id, String placa){
		this.id = id;
		this.placa = placa;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getMimeTypeFoto() {
		if(foto != null)
			return foto.getMimeType();
		return null;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
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
        Veiculo other = (Veiculo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
	
    @Override
    public String toString() {
        return placa + " " + modelo.getDescricao() + " " + anoFabricacao;
    }
}