package net.revenda.dominio;

public interface UsuarioRepositoryCustom {
    
    /**
     * Insere ou atualiza um usuário na base de dados.
     * @param <S>
     * @param usuario
     * @return
     */
    public <S extends Usuario> S save(S usuario);
    
    /**
     * Altera a senha de um usuário.
     * @param idUsuario
     * @param novaSenha Não deve estar codificada. A codificação será realizada por esta operação.
     */
    public void alterarSenha(Integer idUsuario, String novaSenha);
}
