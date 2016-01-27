package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.adapter.PastoAdapter;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.model.PastoModel;
import br.com.prodap.taurusmobile.service.ConexaoHTTP;
import br.com.prodap.taurusmobile.service.GetJSON;
import br.com.prodap.taurusmobile.tb.Configuracoes;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.ui.MenuPrincipalActivity;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

/**
 * Created by Prodap on 27/01/2016.
 */
public class GetPastosJSON extends AsyncTask<Void, Integer, List<Pasto>> {

    private List<Pasto> pastoList;
    private Context ctx;
    private PastoAdapter pastoAdapter;
    private ConfiguracoesAdapter c_helper;
    private Configuracoes c_tb;
    private ConfiguracoesModel c_model;
    public  ConexaoHTTP c_http;
    private ProgressDialog mProgress;
    private int mProgressDialog=0;

    public GetPastosJSON(Context ctx, int progressDialog){
        this.ctx = ctx;
        this.mProgressDialog = progressDialog;

        source();
    }

    private void source() {
        c_tb 		    = new Configuracoes();
        c_model 	    = new ConfiguracoesModel(ctx);
        c_helper 		= new ConfiguracoesAdapter();
        c_http			= new ConexaoHTTP();
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
        List<Configuracoes> c_list = c_model.selectAll(ctx, "Configuracao", c_tb);
        for (Configuracoes qrcode_tb : c_list) {
            url = qrcode_tb.getEndereco();
        }
        try {
            PastoModel pastoModel   = new PastoModel(ctx);
            GetJSON getJSON 	    = new GetJSON(url + Constantes.METHODO_GET_PASTOS, ctx);
            pastoList               = getJSON.listPasto();
            pastoAdapter            = new PastoAdapter();
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
            MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
            mProgress.dismiss();
        } else {
            if (result != null) {
                mProgress.dismiss();
                if (pastoList.isEmpty()) {
                    MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível atualizar os dados.");
                } else {
                    MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Pastos atualizados com sucesso.");
                }
            }
        }
    }
}
