package net.revenda;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.format.Formatter;

import net.revenda.dominio.CPF;

public class CpfFormatter implements Formatter<CPF>{

    private Pattern pattern = Pattern.compile("(\\d|-|\\.|_|\s)+"); //dígitos e os caracteres de traço, ponto e espaço

    @Override
    public String print(CPF object, Locale locale) {
        String num = object.getNumero();
        return num.substring(0, 3) + "." + num.substring(3, 6) + "." + num.substring(6, 9) + "-" + num.substring(9, 11);
    }

    /**
     * Aceita números de CPF contendo pontos e traços em qualquer posição e quantidade. 
     * Os pontos e traços são ignorados.
     */
    @Override
    public CPF parse(String text, Locale locale) throws ParseException {
        Matcher m = pattern.matcher(text);
        if(!m.matches())
            throw new ParseException("CPF inválido: " + text, 0);
        String str = text.replaceAll("[^0-9]", "");
        if(str.length() != 11)
        throw new ParseException("CPF inválido: " + text, 0);

        return new CPF(str);
    }

}
