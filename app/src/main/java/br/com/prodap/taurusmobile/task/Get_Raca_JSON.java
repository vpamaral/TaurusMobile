package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.adapter.Raca_Adapter;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Raca_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;
import br.com.prodap.taurusmobile.tb.Raca;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

/**
 * Created by Prodap on 02/02/2016.
 */
public class Get_Raca_JSON extends AsyncTask<Void, Integer, List<Raca>>
{
    private List<Raca> raca_list;
    private Context ctx;
    private Raca_Adapter raca_adapter;
    private Configuracao_Adapter c_helper;
    private Configuracao c_tb;
    private Configuracao_Model c_model;
    public Conexao_HTTP c_http;
    private ProgressDialog mProgress;
    private int mProgressDialog = 0;

    private String msg;

    public Get_Raca_JSON(Context ctx, int progressDialog)
    {
        this.ctx                = ctx;
        this.mProgressDialog    = progressDialog;

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

        if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
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
        if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
        {
            mProgress.setProgress(values[0]);
        }
    }

    @Override
    protected List<Raca> doInBackground(Void... params)
    {
        String url                  = "";
        List<Configuracao> c_list   = c_model.selectAll(ctx, "Configuracao", c_tb);
        Get_JSON getJSON            = null;

        for (Configuracao qrcode_tb : c_list)
        {
            url = qrcode_tb.getEndereco();
        }

        try
        {
            Raca_Model raca_model = new Raca_Model(ctx);

            if (Constantes.TIPO_ENVIO == "web")
                getJSON = new Get_JSON(url + Constantes.METHOD_GET_GRUPOS, ctx);

            if (Constantes.TIPO_ENVIO == "arquivo" || Constantes.TIPO_ENVIO == "bluetooth")
                getJSON = new Get_JSON();

            raca_list              = getJSON.listRaca();
            raca_adapter           = new Raca_Adapter();
            int i                   = 0;

            if (raca_list != null)
            {
                mProgress.setMax(raca_list.size());

                for (Raca raca_tb : raca_list) {
                    if (raca_list.size() > 0) {
                        raca_model.insert(ctx, "Raca", raca_adapter.getDadosRaca(raca_tb));
                        publishProgress(i * 1);
                    }
                    i++;
                }
            }
            else
            {
                mProgress.dismiss();
                msg = ("Não existe Raça cadastrado no Servidor.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return raca_list;
    }

    @Override
    protected void onPostExecute(List<Raca> result)
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
                    mProgress.dismiss();

                    if (raca_list.isEmpty())
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                    else
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Raças atualizadas com sucesso.");
                }
                else
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
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
                    mProgress.dismiss();

                    if (raca_list.isEmpty())
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                    else
                        Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Raças atualizadas com sucesso.");
                }
                else
                {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
                }
            }
        }

        if (Constantes.TIPO_ENVIO == "arquivo")
        {
            if (result != null)
            {
                mProgress.dismiss();

                if (raca_list.isEmpty())
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não contem dados no arquivo selecionado.");
                else
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Raças atualizada com sucesso.");
            }
            else
            {
                Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
            }
        }
    }
}
