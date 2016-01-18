package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Suporte on 07/10/2015.
 */
public class Pasto implements Serializable {

    //private long id_auto;
    private String pasto;

//    public long getId_auto() {
//        return id_auto;
//    }

//    public void setId_auto(long id_auto) {
//        this.id_auto = id_auto;
//    }

    public String getPasto() {
        return pasto;
    }

    public void setPasto(String pasto) {
        this.pasto = pasto;
    }
}
