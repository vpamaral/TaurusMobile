package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.view.Menu_Principal_Activity;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Get_Via_Arquivo extends AsyncTask<Void, Integer, List<Pasto>>
{
	private List<Pasto> pastoList;
	private Context ctx;
	private Pasto_Adapter pastoAdapter;
	private ProgressDialog mProgress;
	private int mProgressDialog=0;

	public Get_Via_Arquivo(Context ctx, int progressDialog)
	{
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute()
	{
		mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Recebendo dados do servidor via arquivo ...");

		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL)
		{
			mProgress.setIndeterminate(false);
			mProgress.setMax(100);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setCancelable(true);
		}

		mProgress.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL)
			mProgress.setProgress(values[0]);
	}
	
	@Override
	protected List<Pasto> doInBackground(Void... params)
	{
		Pasto_Model pastoModel = new Pasto_Model(ctx);
		try
		{
			Gson gson 			= new Gson();
			pastoAdapter 		= new Pasto_Adapter();
			Pasto[] arrayPasto 	= gson.fromJson(Menu_Principal_Activity.JSONPASTO, Pasto[].class);
			pastoList 			= pastoAdapter.arrayPasto(arrayPasto);
			int i 				= 0;
			mProgress.setMax(pastoList.size());

			for (Pasto pasto_tb : pastoList)
			{
				if (pastoList.size() != 0)
				{
					pastoModel.insert(ctx, "Pasto", pastoAdapter.getDadosPasto(pasto_tb));
					publishProgress(i * 1);
				}
				i++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return pastoList;
	}

	@Override
	protected void onPostExecute(List<Pasto> result)
	{
		mProgress.dismiss();

		if(pastoList.isEmpty())
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível transferir os dados.");
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados transferidos com sucesso.");
		}
	}
}
