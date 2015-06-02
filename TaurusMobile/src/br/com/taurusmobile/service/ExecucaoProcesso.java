package br.com.taurusmobile.service;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.model.AnimalModel;
import br.com.taurusmobile.ui.ListaAnimaisActivity;

public class ExecucaoProcesso extends AsyncTask<Void, Void, Void>{
	
	Context ctx;
	List<Animal> objListaAnimal;
	ProgressDialog objProgressDialog;
	AnimalAdapter aniHelper;
	ServicoRecebido objServicoRecebido;
	
	public ExecucaoProcesso(Context ctx)
	{
		this.ctx = ctx;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			InserirAnimaisBancoSQLite();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		objProgressDialog.dismiss();

		try {

			Intent intent = new Intent(this.ctx,
					ListaAnimaisActivity.class);
			
			ctx.startActivity(intent);

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onPostExecute(result);
	}
	
	private void InserirAnimaisBancoSQLite() throws Exception {

		AnimalModel objModelAnimal = new AnimalModel(this.ctx);
		objServicoRecebido = new ServicoRecebido();
		objListaAnimal = objServicoRecebido.listaAnimal();
		aniHelper = new AnimalAdapter();
		for (Animal animal : objListaAnimal) {
			if(animal != null)
			objModelAnimal.insert(this.ctx, "Animal", aniHelper.AnimalHelper(animal));
		}

	}
}
