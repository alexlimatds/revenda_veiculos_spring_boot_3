package net.revenda.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Representa um usuário do sistema.
 */
@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
	private String nome;

    @Embedded
	private CPF cpf;
	
    private String telefone;
	
    @NotBlank
    @Column(unique = true)
	private String login;
	
    @NotBlank
	private String senha;
	
    @NotNull
	private Boolean ativo;
	
    @NotNull
	private Boolean gerente;
	
	public Usuario(){
	}

    public Usuario(Integer id, String nome, String numeroCpf, String telefone,
            String login, String senha, Boolean ativo, Boolean gerente) {
        this.id = id;
        this.nome = nome;
        this.cpf = new CPF(numeroCpf);
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
        this.gerente = gerente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean isGerente() {
		return gerente;
	}

	public void setGerente(Boolean gerente) {
		this.gerente = gerente;
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
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return id + " " + nome;
    }
}
