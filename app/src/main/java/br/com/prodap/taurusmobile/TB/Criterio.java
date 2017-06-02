package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Criterio implements Serializable
{
    private String criterio;
    private String sexo;

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
