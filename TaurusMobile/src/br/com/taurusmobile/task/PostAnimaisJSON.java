package br.com.taurusmobile.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.taurusmobile.TB.Parto_PartoCria;
import br.com.taurusmobile.converter.Parto_PartoCriaJSON;
import br.com.taurusmobile.model.Parto_PartoCriaModel;
import br.com.taurusmobile.service.ConexaoHTTP;
import br.com.taurusmobile.util.Auxiliar;
import br.com.taurusmobile.util.Constantes;

import com.google.gson.Gson;

public class PostAnimaisJSON extends AsyncTask<Object, Object, String> {
	private Context ctx;
	private ProgressDialog progress;
	private Parto_PartoCria p_parto_cria_tb;
	private Parto_PartoCriaModel p_parto_cria_model;
	private List<Parto_PartoCria> partos_parto_cria;
	private String json;
	private Auxiliar objeto;
	private Gson gson;

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
		p_parto_cria_tb = new Parto_PartoCria();
		p_parto_cria_model = new Parto_PartoCriaModel(ctx);
		partos_parto_cria = p_parto_cria_model.selectAll(
				ctx, "Animal", p_parto_cria_tb);
		json = new Parto_PartoCriaJSON()
				.toJSON(partos_parto_cria);
		objeto = new Auxiliar(json);
		gson = new Gson();
		String retornoJSON = gson.toJson(objeto );
		new ConexaoHTTP(Constantes.POST).postJson(retornoJSON);
		return retornoJSON;
	}

	@Override
	protected void onPostExecute(String json) {
		progress.dismiss();
		Toast.makeText(ctx, "Dados enviados com sucesso.", Toast.LENGTH_SHORT).show();
	}
}
