package br.com.taurusmobile.service;

import java.util.List;

import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.model.AnimalModel;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class GetAnimaisTask extends AsyncTask<Void, Void, Void> {

	List<Animal> objListaAnimal;
	private Context ctx;
	private ProgressDialog progress;
	private ServicoRecebido objServicoRecebido;
	private AnimalAdapter aniHelper;

	public GetAnimaisTask(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(ctx, "Aguarde...",
				"Recebendo dados do servidor.");
	}

	@Override
	protected Void doInBackground(Void... params) {
		AnimalModel objModelAnimal = new AnimalModel(ctx);
		objServicoRecebido = new ServicoRecebido();
		try {
			objListaAnimal = objServicoRecebido.listaAnimal();
			aniHelper = new AnimalAdapter();
			for (Animal animal : objListaAnimal) {
				if (animal != null)
					objModelAnimal.insert(ctx, "Animal",
							aniHelper.AnimalHelper(animal));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		progress.dismiss();
		Toast.makeText(ctx, "Dados atualizado com sucesso!", Toast.LENGTH_SHORT)
				.show();
	}
}
