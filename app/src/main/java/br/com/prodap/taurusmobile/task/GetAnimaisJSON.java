package br.com.prodap.taurusmobile.task;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.service.GetJSON;
import br.com.prodap.taurusmobile.util.Auxiliar;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class GetAnimaisJSON extends AsyncTask<Void, Void, Void> {

	private List<Animal> objListaAnimal;
	private Context ctx;
	private AnimalAdapter aniHelper;
	private ConfiguracoesAdapter c_helper;
	private Configuracoes qrcode_tb;
	private ConfiguracoesModel qrcode_model;
	private Auxiliar auxiliar;
	private int count = 0;
	private int quantInserida = 0;
	private Constantes constantes;

	public GetAnimaisJSON(Context ctx) {
		this.ctx = ctx;
		source();	
	}
	
	private void source() {
		qrcode_tb 		= new Configuracoes();
		qrcode_model 	= new ConfiguracoesModel(ctx);
		c_helper 		= new ConfiguracoesAdapter();
		constantes		= new Constantes();
	}
	
	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Recebendo dados do servidor.");
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		constantes.TOTAL_ANIMAIS = 0;
		String url = "";
		List<Configuracoes> listQRCode = qrcode_model.selectAll(ctx, "Configuracao", qrcode_tb);
		for (Configuracoes qrcode_tb : listQRCode) {
			url = qrcode_tb.getEndereco();
		}

		AnimalModel objModelAnimal = new AnimalModel(ctx);
		GetJSON getJSON = new GetJSON(url + Constantes.METHODO_GET);
		try {

			objListaAnimal = getJSON.listaAnimal();
			aniHelper = new AnimalAdapter();
			count = objListaAnimal.size();
			Constantes.TOTAL_ANIMAIS = count;
			for (Animal animal : objListaAnimal) {
				if (objListaAnimal.size() != 0)
					objModelAnimal.insert(ctx, "Animal", aniHelper.AnimalHelper(animal));
				quantInserida++;
				if (quantInserida >= 13200) {
					int inserir = quantInserida;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		MensagemUtil.closeProgress();
		if (quantInserida <= 24924) {

		}
		if(objListaAnimal.isEmpty()){
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível atualizar os dados.");
		}
		else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados atualizados com sucesso.");
		}
	}
}
