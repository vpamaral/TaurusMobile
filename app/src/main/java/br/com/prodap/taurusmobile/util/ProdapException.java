package br.com.prodap.taurusmobile.util;

/**
 * Created by Prodap on 30/09/2015.
 */
public abstract class ProdapException extends Exception {

    public final String MSG_ERRO_BANCO_DADOS = "MSG_ERRO_BANCO_DADOS";

    public static final int MESSAGE_TYPE_WARNING = 0;
    public static final int MESSAGE_TYPE_ERRO = 1;
    public static final int MESSAGE_TYPE_INFO = 2;
    public static final int MESSAGE_TYPE_QUESTION = 3;

    private int exception_code;
    private Object[] exception_args;


    public int getException_code() {
        return exception_code;
    }

    public void setException_code(int exception_code) {
        this.exception_code = exception_code;
    }

    public Object[] getException_args() {
        return exception_args;
    }

    public void setException_args(Object[] exception_args) {
        this.exception_args = exception_args;
    }

    public ProdapException(String e) {
        super(e);
    }
}
