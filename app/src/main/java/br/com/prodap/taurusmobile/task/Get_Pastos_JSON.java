package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.view.Menu_Principal_Activity;

/**
 * Created by Prodap on 27/01/2016.
 */
public class Get_Pastos_JSON extends AsyncTask<Void, Integer, List<Pasto>>
{
    private List<Pasto> pastoList;
    private Context ctx;
    private Pasto_Adapter pastoAdapter;
    private Configuracao_Adapter c_helper;
    private Configuracao c_tb;
    private Configuracao_Model c_model;
    public Conexao_HTTP c_http;
    private ProgressDialog mProgress;
    private int mProgressDialog = 0;
    private Menu_Principal_Activity menu_principal_activity;

    private String msg;

    public Get_Pastos_JSON(Context ctx, int progressDialog, Menu_Principal_Activity activity)
    {
        this.ctx                = ctx;
        this.mProgressDialog    = progressDialog;

        source();
        this.menu_principal_activity = activity;
    }

    private void source()
    {
        c_tb 		    = new Configuracao();
        c_model 	    = new Configuracao_Model(ctx);
        c_helper 		= new Configuracao_Adapter();
        c_http			= new Conexao_HTTP();
    }

    @Override
    protected void onPreExecute()
    {
        mProgress = new ProgressDialog(ctx);
        mProgress.setTitle("Aguarde ...");
        mProgress.setMessage("Recebendo pastos do servidor ...");

        if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
        {
            mProgress.setIndeterminate(false);
            mProgress.setMax(0);
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setCancelable(false);
        }

        mProgress.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
            mProgress.setProgress(values[0]);
    }

    @Override
    protected List<Pasto> doInBackground(Void... params)
    {
        String url = "";
        List<Configuracao> c_list = c_model.selectAll(ctx, "Configuracao", c_tb);
        Get_JSON getJSON = null;

        for (Configuracao qrcode_tb : c_list)
        {
            url = qrcode_tb.getEndereco();
        }

        try
        {
            Pasto_Model pastoModel  = new Pasto_Model(ctx);

            if (Constantes.TIPO_ENVIO == "web")
                getJSON = new Get_JSON(url + Constantes.METHOD_GET_PASTOS, ctx);

            if (Constantes.TIPO_ENVIO == "arquivo" || Constantes.TIPO_ENVIO == "bluetooth")
                getJSON = new Get_JSON();

            pastoList               = getJSON.listPasto();
            pastoAdapter            = new Pasto_Adapter();
            int i                   = 0;

            if (pastoList != null)
            {
                mProgress.setMax(pastoList.size());

                for (Pasto pasto_tb : pastoList)
                {
                    if (pastoList.size() > 0)
                    {
                        pastoModel.insert(ctx, "Pasto", pastoAdapter.getDadosPasto(pasto_tb));
                        publishProgress(i * 1);
                    }
                    i++;
                }
            }
            else
            {
                mProgress.dismiss();
                msg = ("Não existe Pasto cadastrado no Servidor.");
            }
        }
        catch (Exception e)
        {
            msg = ("Erro ao atualizar os pastos.");
            e.printStackTrace();
        }

        return pastoList;
    }

    @Override
    protected void onPostExecute(List<Pasto> result)
    {
        if (Constantes.TIPO_ENVIO == "web")
        {
            if (c_http.servResultGet != 200)
            {
                Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
                mProgress.dismiss();
            }
            else
            {
                if (result != null)
                {
                    //mProgress.dismiss();

                    if (pastoList.isEmpty())
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                    else
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Pasto atualizado com sucesso.");
                }
                else
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
                    mProgress.dismiss();
                    return;
                }
            }
        }

        if (Constantes.TIPO_ENVIO == "bluetooth")
        {
            if (Constantes.STATUS_CONN == "desconectado")
            {
                Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "O dipositivo não esta conectado ao Servidor.");
                mProgress.dismiss();
            }
            else
            {
                if (result != null)
                {
                    //mProgress.dismiss();

                    if (pastoList.isEmpty())
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                    else
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Pasto atualizado com sucesso.");
                }
                else
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
                    mProgress.dismiss();
                    return;
                }
            }
        }

        if (Constantes.TIPO_ENVIO == "arquivo")
        {
            if (result != null)
            {
                //mProgress.dismiss();

                if (pastoList.isEmpty())
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não contem dados no arquivo selecionado.");
                else
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Pasto atualizado com sucesso.");
            }
            else
            {
                Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
                mProgress.dismiss();
                return;
            }
        }

        Grupo_Manejo_Model grupo_model = new Grupo_Manejo_Model(this.ctx);
        grupo_model.delete(this.ctx, "Grupo_Manejo");
        new Get_Grupo_Manejo_JSON(this.ctx, ProgressDialog.STYLE_HORIZONTAL, this.menu_principal_activity, mProgress).execute();

    }
}
