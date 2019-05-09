package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Raca implements Serializable{

    private String codigo;
    private String nome;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
