package net.revenda.dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF {

    private String numero;

    public CPF(){}

    public CPF(String numero){
        validarNumero(numero);
        this.numero = numero;
    }

    /*
     * Apenas garante que o número contém 11 dígitos. Um aplicação real 
     * faria a validação do número em relação aos dígitos verificadores.
     */
    private void validarNumero(String numero){
        Pattern p = Pattern.compile("\\d{11}");
        Matcher m = p.matcher(numero);
        if(!m.matches())
            throw new IllegalArgumentException("CPF inválido: " + numero);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        validarNumero(numero);
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
        CPF other = (CPF) obj;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        return true;
    }
}
