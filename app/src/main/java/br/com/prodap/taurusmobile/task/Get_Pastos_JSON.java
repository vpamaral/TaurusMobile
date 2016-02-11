package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

/**
 * Created by Prodap on 27/01/2016.
 */
public class Get_Pastos_JSON extends AsyncTask<Void, Integer, List<Pasto>> {

    private List<Pasto> pastoList;
    private Context ctx;
    private Pasto_Adapter pastoAdapter;
    private Configuracao_Adapter c_helper;
    private Configuracao c_tb;
    private Configuracao_Model c_model;
    public Conexao_HTTP c_http;
    private ProgressDialog mProgress;
    private int mProgressDialog=0;

    public Get_Pastos_JSON(Context ctx, int progressDialog){
        this.ctx = ctx;
        this.mProgressDialog = progressDialog;

        source();
    }

    private void source() {
        c_tb 		    = new Configuracao();
        c_model 	    = new Configuracao_Model(ctx);
        c_helper 		= new Configuracao_Adapter();
        c_http			= new Conexao_HTTP();
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(ctx);
        mProgress.setTitle("Aguarde ...");
        mProgress.setMessage("Recebendo dados do servidor ...");
        if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){

            mProgress.setIndeterminate(false);
            mProgress.setMax(100);
            mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgress.setCancelable(true);
        }
        mProgress.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){
            mProgress.setProgress(values[0]);
        }
    }

    @Override
    protected List<Pasto> doInBackground(Void... params) {
        String url = "";
        List<Configuracao> c_list = c_model.selectAll(ctx, "Configuracao", c_tb);
        for (Configuracao qrcode_tb : c_list) {
            url = qrcode_tb.getEndereco();
        }
        try {
            Pasto_Model pastoModel   = new Pasto_Model(ctx);
            Get_JSON getJSON 	    = new Get_JSON(url + Constantes.METHODO_GET_PASTOS, ctx);
            pastoList               = getJSON.listPasto();
            pastoAdapter            = new Pasto_Adapter();
            int i = 0;
            mProgress.setMax(pastoList.size());
            for (Pasto pasto_tb : pastoList) {
                if (pastoList.size() != 0) {
                    pastoModel.insert(ctx, "Pasto", pasto_tb);
                    publishProgress(i * 1);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pastoList;
    }

    @Override
    protected void onPostExecute(List<Pasto> result) {
        if (c_http.servResultGet != 200) {
            Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
            mProgress.dismiss();
        } else {
            if (result != null) {
                mProgress.dismiss();
                if (pastoList.isEmpty()) {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
                } else {
                    Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Pastos atualizados com sucesso.");
                }
            }
        }
    }
}
