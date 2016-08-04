package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.adapter.Criterio_Adapter;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Criterio_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Criterio;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

/**
 * Created by Prodap on 28/07/2016.
 */
public class Get_Criterios_JSON extends AsyncTask<Void, Integer, List<Criterio>>
{
    private List<Criterio> criterio_list;
    private Context ctx;
    private Criterio_Adapter c_adapter;
    private Configuracao_Adapter c_helper;
    private Configuracao c_tb;
    private Configuracao_Model c_model;
    public Conexao_HTTP c_http;
    private ProgressDialog mProgress;
    private int mProgressDialog=0;

    public Get_Criterios_JSON(Context ctx, int progressDialog)
    {
        this.ctx = ctx;
        this.mProgressDialog = progressDialog;

        source();
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
        mProgress.setMessage("Recebendo dados do servidor ...");

        if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL)
        {
            mProgress.setIndeterminate(false);
            mProgress.setMax(100);
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setCancelable(true);
        }
        mProgress.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL)
        {
            mProgress.setProgress(values[0]);
        }
    }

    @Override
    protected List<Criterio> doInBackground(Void... params)
    {
        String url = "";
        List<Configuracao> c_list = c_model.selectAll(ctx, "Configuracao", c_tb);

        for (Configuracao qrcode_tb : c_list)
        {
            url = qrcode_tb.getEndereco();
        }

        try
        {
            Criterio_Model pastoModel   = new Criterio_Model(ctx);
            Get_JSON getJSON 	        = new Get_JSON(url + Constantes.METHOD_GET_CRITERIOS, ctx);
            criterio_list               = getJSON.listCriterio();
            c_adapter                   = new Criterio_Adapter();
            int i = 0;
            mProgress.setMax(criterio_list.size());

            for (Criterio c_tb : criterio_list)
            {
                if (criterio_list.size() != 0)
                {
                    pastoModel.insert(ctx, "Pasto", c_adapter.getDadosCriterio(c_tb));
                    publishProgress(i * 1);
                }
                i++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return criterio_list;
    }

    @Override
    protected void onPostExecute(List<Criterio> result)
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
                mProgress.dismiss();

                if (criterio_list.isEmpty())
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                }
                else
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Pastos atualizados com sucesso.");
                }
            }
        }
    }
}
