package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Suporte on 07/10/2015.
 */
public class Relatorio_Parto implements Serializable {

    //private long id_auto;
    private String data_parto;
    private String sexo_parto;
    private int qtd_partos;

    public String getDataParto() {
        return data_parto;
    }
    public void setDataParto(String data_parto) {
        this.data_parto = data_parto;
    }

    public String getSexoParto() {
        return sexo_parto;
    }
    public void setSexoParto(String sexo_parto) {
        this.sexo_parto = sexo_parto;
    }

    public int getQtdPartos() {
        return qtd_partos;
    }
    public void setQtdPartos(int qtd_partos) {
        this.qtd_partos = qtd_partos;
    }
}
