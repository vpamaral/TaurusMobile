package br.com.prodap.taurusmobile.helper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Leitor;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.view.Configuracao_Activity;
import br.com.prodap.taurusmobile.view.Leitor_Activity;

/**
 * Created by Prodap on 02/02/2017.
 */

public class Configuracao_Helper
{
    /*################################### INICIO VIEW ############################################*/
    private CheckBox cbIdentificador;
    private CheckBox cbManejo;
    private CheckBox cbSisbov;
    private CheckBox cbCodAlternativo;
    private EditText edtEndereco;

    private Configuracao_Helper c_helper;
    private Configuracao c_tb;
    private Configuracao_Model c_model;
    public static List<Configuracao> c_list;
    private Leitor leitor;
    private String tipo;

    public Configuracao_Helper(){}

    public Configuracao_Helper(Configuracao_Activity c_activity)
    {
        c_tb	 		 	= new Configuracao();
        c_model 	 		= new Configuracao_Model(c_activity);
        c_helper 		 	= new Configuracao_Helper();

        cbIdentificador 	= (CheckBox) c_activity.findViewById (R.id.cbIdentificador);
        cbManejo 			= (CheckBox) c_activity.findViewById(R.id.cbManejo);
        cbSisbov        	= (CheckBox) c_activity.findViewById(R.id.cbSisbov);
        cbCodAlternativo	= (CheckBox) c_activity.findViewById(R.id.cbCodALternativo);
        edtEndereco 		= (EditText) c_activity.findViewById(R.id.edtEndereco);

        Intent intent  		=  c_activity.getIntent();
        leitor 				= (Leitor) intent.getSerializableExtra("leitor");
        c_list 				= c_model.selectAll(c_activity.getBaseContext(), "Configuracao", c_tb);

        for (Configuracao config_tb : c_list)
        {
            FillForm(c_activity, config_tb);
        }

        if (leitor != null)
        {
            edtEndereco.setText(leitor.getScanResult());
            tipo = leitor.getTipo();

            c_tb = getConfig(c_activity);
        }

        //metodo para não deixar abrir o teclado do aparelho.
        c_activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public Configuracao getConfig(Configuracao_Activity c_activity)
    {
        c_tb.setEndereco(edtEndereco.getText().toString());
        c_tb.setTipo(tipo);
        c_tb.setValida_identificador(cbIdentificador.isChecked() ? "S" : "N");
        c_tb.setValida_manejo(cbManejo.isChecked() ? "S" : "N");
        c_tb.setValida_sisbov(cbSisbov.isChecked() ? "S" : "N");
        c_tb.setValida_cod_alternativo(cbCodAlternativo.isChecked() ? "S" : "N");
        if(c_list.size() > 0){

            c_tb.setUltimaAtualizacao(c_list.get(0).getUltimaAtualizacao());
        }

        return c_tb;
    }

    //metodo para preencher o formulario
    public void FillForm(Configuracao_Activity c_activity, Configuracao c_tb)
    {
        c_tb = c_model.selectID(c_activity, "Configuracao", c_tb, c_tb.getId_auto());

        edtEndereco.setText(c_tb.getEndereco().toString());
        tipo = c_tb.getTipo() != null ? c_tb.getTipo().toString() : "QRCODE";

        if (c_tb.getValida_identificador().equals("S")) cbIdentificador.setChecked(true);
        if (c_tb.getValida_sisbov().equals("S")) cbSisbov.setChecked(true);
        if (c_tb.getValida_manejo().equals("S")) cbManejo.setChecked(true);
        if (c_tb.getValida_cod_alternativo().equals("S")) cbCodAlternativo.setChecked(true);
    }

    /*#################################### FIM VIEW #############################################*/

    public Configuracao cursorConfiguracao(Cursor c)
    {
        Configuracao c_tb = new Configuracao();

        while (c.moveToNext())
        {
            c_tb = getConfiguracao(c);
        }
        return c_tb;
    }

    public List<Configuracao> arrayConfiguracao(Cursor c)
    {
        List<Configuracao> c_list = new ArrayList<Configuracao>();

        while (c.moveToNext())
        {
            c_list.add(getConfiguracao(c));
        }
        return c_list;
    }

    @NonNull
    public ContentValues getDadosConfiguracao(Configuracao c_tb)
    {
        ContentValues c_dados = new ContentValues();

        c_dados.put("tipo", c_tb.getTipo());
        c_dados.put("endereco", c_tb.getEndereco());
        c_dados.put("valida_sisbov", c_tb.getValida_sisbov());
        c_dados.put("valida_identificador", c_tb.getValida_identificador());
        c_dados.put("valida_manejo", c_tb.getValida_manejo());
        c_dados.put("valida_cod_alternativo", c_tb.getValida_cod_alternativo());
        c_dados.put("ultima_atualizacao", c_tb.getUltimaAtualizacao());

        return c_dados;
    }

    @NonNull
    private Configuracao getConfiguracao(Cursor c)
    {
        Configuracao c_tb = new Configuracao();

        c_tb.setId_auto(c.getLong(c.getColumnIndex("id_auto")));
        c_tb.setTipo(c.getString(c.getColumnIndex("tipo")));
        c_tb.setEndereco(c.getString(c.getColumnIndex("endereco")));
        c_tb.setValida_identificador(c.getString(c.getColumnIndex("valida_identificador")));
        c_tb.setValida_manejo(c.getString(c.getColumnIndex("valida_manejo")));
        c_tb.setValida_sisbov(c.getString(c.getColumnIndex("valida_sisbov")));
        c_tb.setValida_cod_alternativo(c.getString(c.getColumnIndex("valida_cod_alternativo")));
        c_tb.setUltimaAtualizacao(c.getString(c.getColumnIndex("ultima_atualizacao")));

        return c_tb;
    }

    public ArrayList<Configuracao> arrayConfiguracao(Configuracao[] ConfiguracaoArray)
    {
        ArrayList<Configuracao> c_list = new ArrayList<Configuracao>();

        for (int i = 0; i < ConfiguracaoArray.length; i++)
        {
            Configuracao config = new Configuracao();
            //config.setId_pk(configurarArray[i].getId_pk());
            config.setTipo(ConfiguracaoArray[i].getTipo());
            config.setEndereco(ConfiguracaoArray[i].getEndereco());
            config.setValida_identificador(ConfiguracaoArray[i].getValida_identificador());
            config.setValida_manejo(ConfiguracaoArray[i].getValida_manejo());
            config.setValida_sisbov(ConfiguracaoArray[i].getValida_sisbov());
            config.setUltimaAtualizacao(ConfiguracaoArray[i].getUltimaAtualizacao());

            c_list.add(config);
        }
        return c_list;
    }

    public Configuracao configuracaoHelper(Configuracao c_tb)
    {
        Configuracao config = new Configuracao();

        config.setId_auto(c_tb.getId_auto());
        config.setTipo(c_tb.getTipo());
        config.setEndereco(c_tb.getEndereco());
        config.setValida_identificador(c_tb.getValida_identificador());
        config.setValida_manejo(c_tb.getValida_manejo());
        config.setValida_sisbov(c_tb.getValida_sisbov());
        config.setUltimaAtualizacao(c_tb.getUltimaAtualizacao());

        return config;
    }
}
