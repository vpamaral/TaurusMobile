package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.adapter.PastoAdapter;
import br.com.prodap.taurusmobile.model.PastoModel;
import br.com.prodap.taurusmobile.ui.MenuPrincipalActivity;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class GetPastoARQUIVO extends AsyncTask<Void, Integer, List<Pasto>> {

	private List<Pasto> pastoList;
	private Context ctx;
	private PastoAdapter pastoAdapter;
	ProgressDialog mProgress;
	private int mProgressDialog=0;

	public GetPastoARQUIVO(Context ctx, int progressDialog){
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute() {
		mProgress = new ProgressDialog(ctx);
		mProgress.setMessage("Tranferindo dados para tabela de Pasto.");
		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){

			mProgress.setIndeterminate(false);
			mProgress.setMax(100);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setCancelable(true);
		}
		mProgress.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL){
			mProgress.setProgress(values[0]);
		}
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
			int i = 0;
			mProgress.setMax(pastoList.size());
			for (Pasto pasto_tb : pastoList) {
				if (pastoList.size() != 0) {
					pastoModel.insert(ctx, "Pasto", pasto_tb);
					publishProgress(i * 1);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pastoList;
	}

	@Override
	protected void onPostExecute(List<Pasto> result) {
		mProgress.dismiss();
		if(pastoList.isEmpty()){
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Não foi possível transferir os dados.");
		}
		else {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Dados transferidos com sucesso.");
		}
	}
}
