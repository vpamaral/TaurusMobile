package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Suporte on 07/10/2015.
 */
public class Pasto implements Serializable {

    //private long id_auto;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
