package br.com.prodap.taurusmobile.tb;

import java.io.Serializable;

/**
 * Created by Suporte on 07/10/2015.
 */
public class Vacas_Gestantes implements Serializable {

    //private long id_auto;
    private String codigo;
    private String data_ultimo_dg;
    private String data_parto_provavel;

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getData_ultimo_dg() {
        return data_ultimo_dg;
    }
    public void setData_ultimo_dg(String data_ultimo_dg) {
        this.data_ultimo_dg = data_ultimo_dg;
    }

    public String getData_parto_provavel() {
        return data_parto_provavel;
    }
    public void setData_parto_provavel(String data_parto_provavel) {
        this.data_parto_provavel = data_parto_provavel;
    }
}
