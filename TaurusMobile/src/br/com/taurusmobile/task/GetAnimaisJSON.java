package br.com.taurusmobile.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.model.AnimalModel;
import br.com.taurusmobile.service.GetJSON;
import br.com.taurusmobile.util.Constantes;
import br.com.taurusmobile.util.MensagemUtil;
import br.com.taurusmobile.util.MessageDialog;

public class GetAnimaisJSON extends AsyncTask<Void, Void, Void> {

	List<Animal> objListaAnimal;
	private Context ctx;
	private AnimalAdapter aniHelper;

	public GetAnimaisJSON(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Recebendo dados do servidor.");
	}

	@Override
	protected Void doInBackground(Void... params) {
		AnimalModel objModelAnimal = new AnimalModel(ctx);
		GetJSON getJSON = new GetJSON(Constantes.GET);
		try {
			objModelAnimal.Delete(ctx, "Animal");
			objListaAnimal = getJSON.listaAnimal();
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
		MensagemUtil.closeProgress();
		MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados atualizados com sucesso");
	}
}
