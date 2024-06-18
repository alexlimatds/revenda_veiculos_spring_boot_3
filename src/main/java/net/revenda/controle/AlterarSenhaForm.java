package net.revenda.controle;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.revenda.dominio.Usuario;

public class AlterarSenhaForm {

    @NotNull
    private Integer idUsuario;

    @NotBlank
	private String senha1;

    @NotBlank
	private String senha2;

    private String nomeUsuario;

    private String login;

    public AlterarSenhaForm(){}

    public AlterarSenhaForm(Usuario usuario){
        this.idUsuario = usuario.getId();
        this.nomeUsuario = usuario.getNome();
        this.login = usuario.getLogin();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSenha1() {
        return senha1;
    }
    public void setSenha1(String senha1) {
        this.senha1 = senha1;
    }

    public String getSenha2() {
        return senha2;
    }
    public void setSenha2(String senha2) {
        this.senha2 = senha2;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @AssertTrue(message="As senhas não conferem")
    public boolean isSenhasValid(){
        return senha1.equals(senha2);
    }
}
