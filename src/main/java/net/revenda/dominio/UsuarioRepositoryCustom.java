package net.revenda.dominio;

public interface UsuarioRepositoryCustom {
    
    /**
     * Insere ou atualiza um usuário na base de dados.
     * @param <S>
     * @param usuario No caso de um novo usário, a senha NÃO deve estar codificada. A codificação será realizada por esta operação.
     * @return
     */
    public <S extends Usuario> S save(S usuario);
    
    /**
     * Altera a senha de um usuário.
     * @param idUsuario
     * @param novaSenha NÃO deve estar codificada. A codificação será realizada por esta operação.
     */
    public void alterarSenha(Integer idUsuario, String novaSenha);
}
