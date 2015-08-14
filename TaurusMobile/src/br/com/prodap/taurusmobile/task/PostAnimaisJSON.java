package br.com.prodap.taurusmobile.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import br.com.prodap.taurusmobile.TB.Parto_PartoCria;
import br.com.prodap.taurusmobile.converter.Parto_PartoCriaJSON;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.model.Parto_PartoCriaModel;
import br.com.prodap.taurusmobile.service.ConexaoHTTP;
import br.com.prodap.taurusmobile.util.Auxiliar;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

import com.google.gson.Gson;

public class PostAnimaisJSON extends AsyncTask<Object, Object, String> {
	private Context ctx;
	private Parto_PartoCria p_parto_cria_tb;
	private Parto_PartoCriaModel p_parto_cria_model;
	private List<Parto_PartoCria> partos_parto_cria;
	private String json;
	private Auxiliar objeto;
	private Gson gson;
	PartoModel objModelParto = new PartoModel(ctx);
	Parto_CriaModel objModelParto_Cria = new Parto_CriaModel(ctx);

	public PostAnimaisJSON(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Enviando dados para o servidor.");
	}

	@Override
	protected String doInBackground(Object... params) {
		p_parto_cria_tb = new Parto_PartoCria();
		p_parto_cria_model = new Parto_PartoCriaModel(ctx);
		partos_parto_cria = p_parto_cria_model.selectAll(
				ctx, "Animal", p_parto_cria_tb);
		if () {
			json = new Parto_PartoCriaJSON()
					.toJSON(partos_parto_cria);
			objeto = new Auxiliar(json);
			gson = new Gson();
			String retornoJSON = gson.toJson(objeto);
			new ConexaoHTTP(Constantes.POST).postJson(retornoJSON);
			return retornoJSON;
		} else {

		}


	}

	@Override
	protected void onPostExecute(String json) {
		MensagemUtil.closeProgress();
		MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados enviados com sucesso");
		objModelParto.Delete(ctx, "Parto");
		objModelParto_Cria.Delete(ctx, "Parto_Cria");
	}
}
