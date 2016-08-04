package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Criterio implements Serializable {

    private String criterio;

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
}
