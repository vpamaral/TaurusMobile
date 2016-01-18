package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Configuracoes;
import br.com.prodap.taurusmobile.tb.Parto_PartoCria;
import br.com.prodap.taurusmobile.converter.Parto_PartoCriaJSON;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_PartoCriaModel;
import br.com.prodap.taurusmobile.service.ConexaoHTTP;
import br.com.prodap.taurusmobile.util.Auxiliar;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class PostAnimaisJSON extends AsyncTask<Object, Integer, String> {
	private Context ctx;
	private Configuracoes configuracoes_tb;
	private Parto_PartoCria p_parto_cria_tb;
	private ConfiguracoesModel configuracoes_model;
	private Parto_PartoCriaModel p_parto_cria_model;
	private PartoModel parto_model;
	private List<Configuracoes> configuracoes_list;
	private List<Parto_PartoCria> p_parto_cria_list;
	private String json;
	private Auxiliar auxiliar;
	private Gson gson;
	private String retornoJSON;
	public  ConexaoHTTP c_http;
	public ProgressDialog mProgress;
	private int mProgressDialog=0;

	public PostAnimaisJSON(Context ctx, int progressDialog) {
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
		source();
	}

	private void source() {
		configuracoes_tb		= new Configuracoes();
		p_parto_cria_tb 		= new Parto_PartoCria();
		configuracoes_model		= new ConfiguracoesModel(ctx);
		p_parto_cria_model 		= new Parto_PartoCriaModel(ctx);
		parto_model				= new PartoModel(ctx);
		c_http					= new ConexaoHTTP();
	}

	@Override
	protected void onPreExecute() {
		mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Enviando dados para o servidor ...");
		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){
			mProgress.setIndeterminate(false);
			mProgress.setMax(0);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setCancelable(false);
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
	protected String doInBackground(Object... params) {
		String url = "";
		configuracoes_list = configuracoes_model.selectAll(ctx, "Configuracao", configuracoes_tb);
		for (Configuracoes qrcode_tb : configuracoes_list) {
			url = qrcode_tb.getEndereco();
		}

		try {
			p_parto_cria_list = p_parto_cria_model.selectAll(ctx, "Animal", p_parto_cria_tb);
			if (p_parto_cria_list.size() != 0) {
				mProgress.setMax(p_parto_cria_list.size());
				for(int i = 0; p_parto_cria_list.size() < i; i++){
					publishProgress(i * 1);
				}
				json = new Parto_PartoCriaJSON().toJSON(p_parto_cria_list);
				auxiliar = new Auxiliar(json);
				gson = new Gson();
				retornoJSON = gson.toJson(auxiliar);
				new ConexaoHTTP(url + Constantes.METHODO_POST, ctx).postJson(retornoJSON);
				return retornoJSON;
			}
		}catch (Exception e) {
			Log.i("POSTJSON", e.toString());
			e.printStackTrace();
		}
		return retornoJSON;
	}

	@Override
	protected void onPostExecute(String json) {
		if (json != null) {
			if (c_http.servResultPost != 200) {
				MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
				mProgress.dismiss();
			} else {
				if (retornoJSON.isEmpty()) {
					mProgress.dismiss();
					MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Nenhum dado para ser enviado.");
				} else {
					writeInFileSendPartos(json);
					parto_model.deletingLogic(ctx);
					MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados enviados com sucesso.");
					mProgress.dismiss();
				}
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não existem dados para serem enviados.");
			mProgress.dismiss();
		}
	}

	private boolean writeInFileSendPartos(String text)
	{
		BufferedReader input = null;
		File file = null;

		try{
			file = new File(Environment.getExternalStorageDirectory()+"/Prodap","partos_enviados.txt");
			FileOutputStream in = new FileOutputStream(file, true);
			in.write(text.getBytes());
			in.write("\n".getBytes());
			in.flush();
			in.close();

			return true;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}