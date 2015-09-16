package br.com.prodap.taurusmobile.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.TB.Parto_PartoCria;
import br.com.prodap.taurusmobile.adapter.PartoAdapter;
import br.com.prodap.taurusmobile.adapter.PartoCriaAdapter;
import br.com.prodap.taurusmobile.adapter.Parto_PartoCriaAdapter;
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

public class PostAnimaisJSON extends AsyncTask<Object, Object, String> {
	private Context ctx;
	private Configuracoes configuracoes_tb;
	private Parto_PartoCria p_parto_cria_tb;
	private Parto_Cria p_cria_tb;
	private Parto parto_tb;
	private ConfiguracoesModel configuracoes_model;
	private Parto_PartoCriaModel p_parto_cria_model;
	private Parto_CriaModel p_cria_model;
	private PartoModel parto_model;
	private List<Configuracoes> configuracoes_list;
	private List<Parto_PartoCria> p_parto_cria_list;
	private List<Parto_Cria> parto_cria_list;
	private List<Parto> parto_list;
	private Parto_PartoCriaAdapter parto_cria_adapter;
	private PartoCriaAdapter cria_adapter;
	private PartoAdapter parto_adapter;
	private String json;
	private Auxiliar auxiliar;
	private Gson gson;

	public PostAnimaisJSON(Context ctx) {
		this.ctx = ctx;
		source();
	}

	private void source() {
		configuracoes_tb		= new Configuracoes();
		p_parto_cria_tb 		= new Parto_PartoCria();
		p_cria_tb				= new Parto_Cria();
		parto_tb				= new Parto();
		configuracoes_model		= new ConfiguracoesModel(ctx);
		p_parto_cria_model 		= new Parto_PartoCriaModel(ctx);
		p_cria_model			= new Parto_CriaModel(ctx);
		parto_model				= new PartoModel(ctx);
		parto_cria_adapter  	= new Parto_PartoCriaAdapter();
		cria_adapter			= new PartoCriaAdapter();
		parto_adapter			= new PartoAdapter();
	}

	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Enviando dados para o servidor.");
	}

	@Override
	protected String doInBackground(Object... params) {
		String url = "";
		configuracoes_list = configuracoes_model.selectAll(ctx, "Configuracao", configuracoes_tb);
		for (Configuracoes qrcode_tb : configuracoes_list) {
			url = qrcode_tb.getEndereco();
		}
		p_parto_cria_list = p_parto_cria_model.selectAll(ctx, "Animal", p_parto_cria_tb);
		if (p_parto_cria_list.size() != 0) {
			json = new Parto_PartoCriaJSON().toJSON(p_parto_cria_list);
			auxiliar = new Auxiliar(json);
			gson = new Gson();
			String retornoJSON = gson.toJson(auxiliar);
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
			clearPartoList();
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Nenhum dado para ser enviado.");
			MensagemUtil.closeProgress();
		}
	}

	private void clearPartoList() {
		for (Parto_PartoCria pp_cria_tb : p_parto_cria_list) {
			updateParto(pp_cria_tb.getId_fk_animal());
			updatePartoCria(pp_cria_tb.getId_fk_animal());
		}
		p_parto_cria_list.clear();
	}

	public void updatePartoCria(Long id_fk) {
		parto_cria_list = p_cria_model.selectAll(ctx, "Parto_Cria", p_cria_tb);
		for (Parto_Cria parto_cria : parto_cria_list) {
			if (id_fk.equals(parto_cria.getId_fk_animal_mae())) {
				parto_cria.setSync_status(1);
				p_cria_model.update(ctx, "Parto_Cria", cria_adapter.PartoCriaHelper(parto_cria));
			}
		}
	}

	public void updateParto(Long id_fk) {
		parto_list = parto_model.selectAll(ctx, "Parto", parto_tb);
		for (Parto parto : parto_list) {
			if (id_fk.equals(parto.getId_fk_animal())) {
				parto.setSync_status(1);
				parto_model.update(ctx, "Parto", parto_adapter.PartoHelper(parto));
			}
		}
	}
}