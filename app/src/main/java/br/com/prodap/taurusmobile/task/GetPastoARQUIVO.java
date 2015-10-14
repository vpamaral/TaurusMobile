package br.com.prodap.taurusmobile.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;
import br.com.prodap.taurusmobile.TB.Pasto;
import br.com.prodap.taurusmobile.adapter.PastoAdapter;
import br.com.prodap.taurusmobile.model.PastoModel;
import br.com.prodap.taurusmobile.ui.MenuPrincipalActivity;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class GetPastoARQUIVO extends AsyncTask<Void, Void, List<Pasto>> {

	private List<Pasto> pastoList;
	private Context ctx;
	private PastoAdapter pastoAdapter;

	public GetPastoARQUIVO(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void onPreExecute() {
		MensagemUtil.addMsg(ctx, "Aguarde...", "Recebendo dados do arquivo.");
	}
	
	@Override
	protected List<Pasto> doInBackground(Void... params) {
		String url = "";
		PastoModel pastoModel = new PastoModel(ctx);
		try {
			Gson gson = new Gson();
			pastoAdapter = new PastoAdapter();
			Pasto[] arrayPasto = gson.fromJson(MenuPrincipalActivity.JSONPASTO, Pasto[].class);
			pastoList = pastoAdapter.PastoPreencheArrayHelper(arrayPasto);

			for (Pasto pasto_tb : pastoList) {
				if (pastoList.size() != 0)
					pastoModel.insert(ctx, "Pasto", pastoAdapter.PastoHelper(pasto_tb));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pastoList;
	}

	@Override
	protected void onPostExecute(List<Pasto> result) {
		MensagemUtil.closeProgress();

		if(pastoList.isEmpty()){
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível atualizar os dados.");
		}
		else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados atualizados com sucesso.");
		}
	}
}
