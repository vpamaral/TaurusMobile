package br.com.prodap.taurusmobile.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.TB.Parto_PartoCria;
import br.com.prodap.taurusmobile.converter.Parto_PartoCriaJSON;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
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
	private Configuracoes qrcode_tb;
	private ConfiguracoesModel qrcode_model;
	private String json;
	private Auxiliar objeto;
	private Gson gson;
	PartoModel objModelParto;
	Parto_CriaModel objModelParto_Cria;

	public PostAnimaisJSON(Context ctx) {
		this.ctx = ctx;
		source();
	}

	private void source() {
		qrcode_tb 			= new Configuracoes();
		qrcode_model 		= new ConfiguracoesModel(ctx);
		objModelParto 		= new PartoModel(ctx);
		objModelParto_Cria 	= new Parto_CriaModel(ctx);
	}

	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Enviando dados para o servidor.");
	}

	@Override
	protected String doInBackground(Object... params) {
		String url = "";
		List<Configuracoes> listQRCode = qrcode_model.selectAll(ctx, "Configuracao", qrcode_tb);
		for (Configuracoes qrcode_tb : listQRCode) {
			url = qrcode_tb.getEndereco();
		}

		p_parto_cria_tb = new Parto_PartoCria();
		p_parto_cria_model = new Parto_PartoCriaModel(ctx);
		partos_parto_cria = p_parto_cria_model.selectAll(
				ctx, "Animal", p_parto_cria_tb);
		if (partos_parto_cria.size() != 0) {
			json = new Parto_PartoCriaJSON()
					.toJSON(partos_parto_cria);
			objeto = new Auxiliar(json);
			gson = new Gson();
			String retornoJSON = gson.toJson(objeto);
			new ConexaoHTTP(url + Constantes.METHODO_POST).postJson(retornoJSON);
			return retornoJSON;
		}
		return null;
	}

	@Override
	protected void onPostExecute(String json) {
		if (json != null) {
			MensagemUtil.closeProgress();
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados enviados com sucesso");
			partos_parto_cria.clear();
			objModelParto.Delete(ctx, "Parto");
			objModelParto_Cria.Delete(ctx, "Parto_Cria");

		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Nenhum dado para ser enviado.");
			MensagemUtil.closeProgress();
		}
	}
}
