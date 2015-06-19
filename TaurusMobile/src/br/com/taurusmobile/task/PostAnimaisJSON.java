package br.com.taurusmobile.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.converter.PartoConverterJSON;
import br.com.taurusmobile.converter.PartoCriaConverterJSON;
import br.com.taurusmobile.model.PartoModel;
import br.com.taurusmobile.model.Parto_CriaModel;
import br.com.taurusmobile.service.PostJSON;
import br.com.taurusmobile.util.Constantes;

public class PostAnimaisJSON extends AsyncTask<Object, Object, String> {
	// link serviço
	// "http://192.168.0.235/TaurusWebService/TaurusService.svc/insertPartosXml"
	private Context ctx;
	private ProgressDialog progress;
	private Parto parto_tb;
	private PartoModel parto_model;
	private List<Parto> partos;
	private Parto_Cria p_cria_tb;
	private Parto_CriaModel p_cria_model;
	private List<Parto_Cria> partos_cria;
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
		PostJSON post_json = new PostJSON(Constantes.POST);
		String retornoJSON = null;
		parto_tb = new Parto();
		parto_model = new PartoModel(ctx);
		partos = parto_model.selectAll(ctx, "Parto", parto_tb);
		p_cria_tb = new Parto_Cria();
		p_cria_model = new Parto_CriaModel(ctx);
		partos_cria = p_cria_model.selectAll(ctx, "Parto_Cria", p_cria_tb);

		try {
			json = new PartoConverterJSON().toJSON(partos);
			json += new PartoCriaConverterJSON().toJSON(partos_cria);
			retornoJSON = post_json.postAnimais(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retornoJSON;
		// return retornoJSON;
	}

	@Override
	protected void onPostExecute(String json) {
		progress.dismiss();
		Toast.makeText(ctx, json, Toast.LENGTH_LONG).show();
	}
}
