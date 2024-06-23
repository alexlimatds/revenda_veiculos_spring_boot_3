package net.revenda.controle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.revenda.dominio.CPF;

public class UsuarioForm {
    
    private Integer id;

    @NotBlank
	private String nome;
    
    @NotNull(message = "Campo obrigatório")
	private CPF cpf;
	
    private String telefone;
	
    @NotBlank
	private String login;
	
    @NotBlank
	private String senha;
	
    @NotNull
	private Boolean ativo;
	
    @NotNull
	private Boolean gerente;
    
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

    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getGerente() {
        return gerente;
    }
    public void setGerente(Boolean gerente) {
        this.gerente = gerente;
    }
}
