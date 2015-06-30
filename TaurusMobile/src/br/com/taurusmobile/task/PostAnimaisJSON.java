package br.com.taurusmobile.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.taurusmobile.TB.Parto_PartoCria;
import br.com.taurusmobile.converter.Parto_PartoCriaJSON;
import br.com.taurusmobile.model.Parto_PartoCriaModel;
import br.com.taurusmobile.service.PostJSON;
import br.com.taurusmobile.util.Constantes;

public class PostAnimaisJSON extends AsyncTask<Object, Object, String> {
	private Context ctx;
	private ProgressDialog progress;
	private List<Parto_PartoCria> partos_parto_cria;
	private Parto_PartoCriaModel p_parto_cria_model;
	private Parto_PartoCria p_parto_cria_tb;
	private String json;

	public PostAnimaisJSON(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(ctx, "Aguarde...",
				"Enviando dados para o servidor.");
	}

	@Override
	protected String doInBackground(Object... params) {
		PostJSON post_json = new PostJSON(Constantes.POSTJSON);
		String retornoJSON = null;
		p_parto_cria_tb = new Parto_PartoCria();
		p_parto_cria_model = new Parto_PartoCriaModel(ctx);
		partos_parto_cria = p_parto_cria_model.selectAll(ctx, "Animal",
				p_parto_cria_tb);
		json = new Parto_PartoCriaJSON().toJSON(partos_parto_cria);

		try {
			retornoJSON = post_json.postAnimais(Constantes.POSTJSON, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retornoJSON;
	}

	@Override
	protected void onPostExecute(String json) {
		progress.dismiss();
		Toast.makeText(ctx, json, Toast.LENGTH_LONG).show();
	}
}
