package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.service.ConexaoHTTP;
import br.com.prodap.taurusmobile.service.GetJSON;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import br.com.prodap.taurusmobile.util.ValidatorException;

public class GetAnimaisJSON extends AsyncTask<Void, Integer, List<Animal>> {

	private List<Animal> objListaAnimal;
	private Context ctx;
	private AnimalAdapter aniHelper;
	private ConfiguracoesAdapter c_helper;
	private Configuracoes qrcode_tb;
	private ConfiguracoesModel qrcode_model;
	private ConexaoHTTP c_http;
	private ProgressDialog mProgress;
	private int mProgressDialog=0;

	public GetAnimaisJSON(Context ctx, int progressDialog) {
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
		source();	
	}
	
	private void source() {
		qrcode_tb 		= new Configuracoes();
		qrcode_model 	= new ConfiguracoesModel(ctx);
		c_helper 		= new ConfiguracoesAdapter();
		c_http			= new ConexaoHTTP();
	}
	
	@Override
	protected void onPreExecute() {
		mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Recebendo dados do servidor.");
		mProgress.setIndeterminate(false);
		mProgress.setMax(0);
		mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgress.setCancelable(true);
		mProgress.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){
			mProgress.setProgress(values[0]);
		}
	}
	
	@Override
	protected List<Animal> doInBackground(Void... params) {
		String url = "";
		List<Configuracoes> listQRCode = qrcode_model.selectAll(ctx, "Configuracao", qrcode_tb);
		for (Configuracoes qrcode_tb : listQRCode) {
			url = qrcode_tb.getEndereco();
		}
		try {
			AnimalModel objModelAnimal 	= new AnimalModel(ctx);
			GetJSON getJSON 			= new GetJSON(url + Constantes.METHODO_GET, ctx);
			objListaAnimal 				= getJSON.listaAnimal();
			aniHelper 					= new AnimalAdapter();
			int i = 0;
			mProgress.setMax(objListaAnimal.size());
			for (Animal animal : objListaAnimal) {
				if (objListaAnimal.size() != 0) {
					objModelAnimal.insert(ctx, "Animal", aniHelper.animalHelper(animal));
					publishProgress(i);
				}
				i++;
			}

		} catch (ValidatorException e) {
			Log.i("TAG", e.toString());
			e.printStackTrace();
		}
		return objListaAnimal;
	}

	@Override
	protected void onPostExecute(List<Animal> result) {
		if (c_http.servResultGet != 200) {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
			mProgress.dismiss();
		} else {
			if (result != null) {
				mProgress.dismiss();
				if (objListaAnimal.isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível atualizar os dados.");
				} else {
					MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados atualizados com sucesso.");
				}
			}
		}
	}
}
