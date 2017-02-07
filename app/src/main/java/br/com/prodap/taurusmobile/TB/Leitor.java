package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by João on 1/6/2016.
 */
public class Leitor implements Serializable
{
    private String tipo;
    private String scanResult;

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getScanResult()
    {
        return scanResult;
    }

    public void setScanResult(String scanResult)
    {
        this.scanResult = scanResult;
    }
}
