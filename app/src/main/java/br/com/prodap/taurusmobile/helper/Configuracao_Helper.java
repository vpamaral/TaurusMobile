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
            loadConfig(c_activity, config_tb);
        }

        if (leitor != null)
        {
            edtEndereco.setText(leitor.getScanResult());
            tipo = leitor.getTipo();

            c_tb = getConfig();
        }
        //metodo para não deixar abrir o teclado do aparelho.
        c_activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public Configuracao getConfig()
    {
        c_tb.setEndereco(edtEndereco.getText().toString());
        c_tb.setTipo(tipo);
        c_tb.setValida_identificador(cbIdentificador.isChecked() ? "S" : "N");
        c_tb.setValida_manejo(cbManejo.isChecked() ? "S" : "N");
        c_tb.setValida_sisbov(cbSisbov.isChecked() ? "S" : "N");
        c_tb.setValida_cod_alternativo(cbCodAlternativo.isChecked() ? "S" : "N");

        return c_tb;
    }

    public void loadConfig(Configuracao_Activity c_activity, Configuracao config_tb)
    {
        config_tb = c_model.selectID(c_activity, "Configuracao", c_tb, c_tb.getId_auto());

        edtEndereco.setText(config_tb.getEndereco().toString());
        tipo = config_tb.getTipo().toString();
        if (config_tb.getValida_identificador().equals("S")) cbIdentificador.setChecked(true);
        if (config_tb.getValida_sisbov().equals("S")) cbSisbov.setChecked(true);
        if (config_tb.getValida_manejo().equals("S")) cbManejo.setChecked(true);
        if (config_tb.getValida_cod_alternativo().equals("S")) cbCodAlternativo.setChecked(true);
    }

    /*//metodo para preencher o formulario
    public void FillForm(Configuracao_Activity c_activity, Configuracao c_tb)
    {
        c_tb	= c_model.selectPK(pasto_activity, "Configuracao", p_tb, pasto_tb.getId_pk());

        edt_data.setText(p_tb.getData_cadastro());
        edt_cod_pasto.setText(p_tb.getCodigo());
        edt_nome.setText(p_tb.getNome());
        edt_retiro.setText(r_tb.getNome());
        edt_pot_prod.setText(Double.valueOf(p_tb.getPotencial_producao()).toString());
        edt_area_aberta.setText(Double.valueOf(p_tb.getArea_aberta()).toString());
        edt_aee.setText(Double.valueOf(p_tb.getAee()).toString());
        edt_densidade.setText(Double.valueOf(p_tb.getDensidade()).toString());
        edt_area_cocho_disp.setText(Double.valueOf(p_tb.getArea_cocho_disponivel()).toString());
        edt_area_min_cab.setText(Double.valueOf(p_tb.getArea_minima_cab()).toString());

        cb_forrageira.setSelection((int)f_tb.getId_auto() - 1);
    }

    public Pasto_TB getPasto(Pasto_Activity pasto_activity)
    {
        f_tb = f_model.selectByNome(pasto_activity, "Forrageira", p_tb, cb_forrageira.getSelectedItem().toString());

        p_tb.setId_pk(p_tb.getId_pk() != 0 ? p_tb.getId_pk() : 0);

        p_tb.setId_user(-1);

        p_tb.setPotencial_producao(Double.valueOf(edt_pot_prod.getText().toString()));
        p_tb.setId_fk_forrageira(f_tb.getId_pk());
        p_tb.setArea_aberta(Double.valueOf(edt_area_aberta.getText().toString()));
        p_tb.setAee(Double.valueOf(edt_aee.getText().toString()));
        p_tb.setDensidade(Double.valueOf(edt_densidade.getText().toString()));
        p_tb.setArea_cocho_disponivel(Double.valueOf(edt_area_cocho_disp.getText().toString()));
        p_tb.setArea_minima_cab(Double.valueOf(edt_area_min_cab.getText().toString()));

        return p_tb;
    }*/

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

            c_list.add(config);
        }
        return c_list;
    }

    public Configuracao configurarHelper(Configuracao configurarTB)
    {
        Configuracao config = new Configuracao();

        config.setId_auto(configurarTB.getId_auto());
        config.setTipo(configurarTB.getTipo());
        config.setEndereco(configurarTB.getEndereco());
        config.setValida_identificador(configurarTB.getValida_identificador());
        config.setValida_manejo(configurarTB.getValida_manejo());
        config.setValida_sisbov(configurarTB.getValida_sisbov());

        return config;
    }
}
