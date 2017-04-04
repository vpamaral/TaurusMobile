package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Get_Animais_JSON extends AsyncTask<Void, Integer, List<Animal>>
{
	private List<Animal> objListaAnimal;
	private Context ctx;
	private Animal_Adapter aniHelper;
	private Configuracao_Adapter c_helper;
	private Configuracao c_tb;
	private Configuracao_Model c_model;
	public Conexao_HTTP c_http;
	public ProgressDialog mProgress;
	private int mProgressDialog=0;

	private String msg;

	public Get_Animais_JSON(Context ctx, int progressDialog)
	{
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
		source();	
	}
	
	private void source()
	{
		c_tb 		= new Configuracao();
		c_model 	= new Configuracao_Model(ctx);
		c_helper 		= new Configuracao_Adapter();
		c_http			= new Conexao_HTTP();
	}
	
	@Override
	protected void onPreExecute()
	{
		mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Recebendo dados do servidor ...");

		if (mProgressDialog==ProgressDialog.STYLE_HORIZONTAL)
		{
			mProgress.setIndeterminate(false);
			mProgress.setMax(0);
			mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgress.setCancelable(false);
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
	protected List<Animal> doInBackground(Void... params)
	{
		String url = "";
		List<Configuracao> c_list = c_model.selectAll(ctx, "Configuracao", c_tb);
		Get_JSON getJSON = null;

		for (Configuracao qrcode_tb : c_list)
		{
			url = qrcode_tb.getEndereco();
		}

		try
		{
			Animal_Model objModelAnimal = new Animal_Model(ctx);

			if (Constantes.TIPO_ENVIO == "web")
				getJSON = new Get_JSON(url + Constantes.METHOD_GET_ANIMAIS, ctx);

			if (Constantes.TIPO_ENVIO == "arquivo" || Constantes.TIPO_ENVIO == "bluetooth")
				getJSON = new Get_JSON();

			objListaAnimal 				= getJSON.listaAnimal();
			aniHelper 					= new Animal_Adapter();
			int i 						= 0;

			if (objListaAnimal != null)
			{
				mProgress.setMax(objListaAnimal.size());

				for (Animal animal : objListaAnimal)
				{
					if (objListaAnimal.size() > 0)
					{
						objModelAnimal.insert(ctx, "Animal", aniHelper.getDadosAnimal(animal));
						publishProgress(i * 1);
					}
					i++;
				}
			}
			else
			{
				mProgress.dismiss();
				msg = ("Não existe Animal cadastrado no Servidor.");
			}
		}
		catch (Validator_Exception e)
		{
			Log.i("TAG", e.toString());
			e.printStackTrace();
		}
		return objListaAnimal;
	}

	@Override
	protected void onPostExecute(List<Animal> result)
	{
		if (Constantes.TIPO_ENVIO == "web")
		{
			if (c_http.servResultGet != 200)
			{
				Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
				mProgress.dismiss();
			}
			else
			{
				if (result != null)
				{
					mProgress.dismiss();

					if (objListaAnimal.isEmpty())
						Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
					else
						Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados atualizados com sucesso.");
				}
				else
				{
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
				}
			}
		}

		if (Constantes.TIPO_ENVIO == "bluetooth")
		{
			if (Constantes.STATUS_CONN == "desconectado")
			{
				Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "O dipositivo não esta conectado ao Servidor.");
				mProgress.dismiss();
			}
			else
			{
				if (result != null)
				{
					mProgress.dismiss();

					if (objListaAnimal.isEmpty())
						Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não foi possível atualizar os dados.");
					else
					{
						Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados atualizados com sucesso.");
						Constantes.STATUS_CONN = ("desconectado");
                        Constantes.LBL_STATUS.setText("Desconectado");
					}
				}
				else
				{
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
				}
			}
		}

		if (Constantes.TIPO_ENVIO == "arquivo")
		{
			if (result != null)
			{
				mProgress.dismiss();

				if (objListaAnimal.isEmpty())
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não contem dados no arquivo selecionado.");
				else
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados atualizados com sucesso.");
			}
			else
			{
				Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
			}
		}
	}
}
