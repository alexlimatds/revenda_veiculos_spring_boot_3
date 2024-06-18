package net.revenda.dominio;

public class LoginIndisponivelException extends RuntimeException{
    public LoginIndisponivelException(String msg){
        super(msg);
    }
}
