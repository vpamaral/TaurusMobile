package br.com.prodap.taurusmobile.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.ui.MenuPrincipalActivity;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class GetAnimaisARQUIVO extends AsyncTask<Void, Void, Void> {

	private List<Animal> objListaAnimal;
	private Context ctx;
	private AnimalAdapter aniHelper;
	private ConfiguracoesAdapter c_helper;
	private Configuracoes qrcode_tb;
	private ConfiguracoesModel qrcode_model;

	public GetAnimaisARQUIVO(Context ctx) {
		this.ctx = ctx;
		source();	
	}
	
	private void source() {
		qrcode_tb 		= new Configuracoes();
		qrcode_model 	= new ConfiguracoesModel(ctx);
		c_helper 		= new ConfiguracoesAdapter();
	}
	
	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Recebendo dados do arquivo.");
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String url = "";
		AnimalModel objModelAnimal = new AnimalModel(ctx);
		try {
			Gson gson = new Gson();
			aniHelper = new AnimalAdapter();
			Animal[] objArrayAnimal = gson.fromJson(MenuPrincipalActivity.JSONANIMAIS, Animal[].class);
			objListaAnimal = aniHelper.AnimalPreencheArrayHelper(objArrayAnimal);

			for (Animal animal : objListaAnimal) {
				if (objListaAnimal.size() != 0)
					objModelAnimal.insert(ctx, "Animal", aniHelper.AnimalHelper(animal));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		MensagemUtil.closeProgress();
		if(objListaAnimal.isEmpty()){
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível atualizar os dados.");
		}
		else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados atualizados com sucesso.");
		}
	}
}
