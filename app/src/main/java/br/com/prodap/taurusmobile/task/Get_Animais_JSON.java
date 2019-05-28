package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;
import java.util.HashMap;
import android.database.DatabaseUtils.InsertHelper;

import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.helper.Configuracao_Helper;
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
import br.com.prodap.taurusmobile.view.Configuracao_Activity;
import br.com.prodap.taurusmobile.view.Menu_Principal_Activity;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.R;
import android.widget.TextView;
import java.util.Map.Entry;
import java.util.Iterator;
import android.database.DatabaseUtils;


public class Get_Animais_JSON extends AsyncTask<Void, Integer, List<Animal>>
{
	private List<Animal> objListaAnimal;
	private Context ctx;
	private Animal_Adapter aniHelper;
	private Configuracao_Helper c_helper;
	private Configuracao c_tb;
	private Configuracao_Model c_model;
	public Conexao_HTTP c_http;
	public ProgressDialog mProgress;
	private int mProgressDialog=0;
	private Menu_Principal_Activity menu_principal_activity;

	private String msg;

	public Get_Animais_JSON(Context ctx, int progressDialog, Menu_Principal_Activity activity, ProgressDialog _mProgress)
	{
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
		source();
		this.menu_principal_activity = activity;
        this.mProgress          = _mProgress;
	}
	
	private void source()
	{
		c_tb 		= new Configuracao();
		c_model 	= new Configuracao_Model(ctx);
		c_helper 	= new Configuracao_Helper();
		c_http		= new Conexao_HTTP();
	}
	
	@Override
	protected void onPreExecute()
	{
		//mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Recebendo animais do servidor ...");

		if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
		{
			mProgress.setIndeterminate(false);
			mProgress.setMax(100);
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

				HashMap<Long, Animal> animalMap = new HashMap<Long, Animal>();
				for (Animal animal_tb : objListaAnimal) {
					animalMap.put(animal_tb.getId_pk(), animal_tb);
				}

				fillDatabase(ctx, animalMap, "Animal");

				/*for (Animal animal : objListaAnimal)
				{
					if (objListaAnimal.size() > 0)
					{
						objModelAnimal.insert(ctx, "Animal", aniHelper.getDadosAnimal(animal));
						publishProgress(i * 1);
					}
					i++;
				}*/

				try {
					String data_atualizacao = getJSON.data_arquivo.toString();
					if (c_list.size() > 0) {
						data_atualizacao = data_atualizacao.replace("[{\"DataArquivo\":\"", "");
						data_atualizacao = data_atualizacao.replace("\"}]", "");

						c_tb = (Configuracao) c_list.get(0);
						c_tb.setUltimaAtualizacao(data_atualizacao);

						c_model.validate(ctx, "Configuracao", c_tb, Constantes.VALIDATION_TYPE_UPDATE);
						c_model.update(ctx, "Configuracao", c_helper.getDadosConfiguracao(c_tb));

					}
				}
				catch (Exception e) {
					Log.i("TAG", e.toString());
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
			msg = ("Erro ao atualizar os animais.");
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
					mProgress.dismiss();
					return;
				}
			}
		}

		if (Constantes.TIPO_ENVIO == "bluetooth")
		{
			boolean teste = Constantes.btSocket.isConnected();
			if (Constantes.STATUS_CONN == "desconectado" ||  Constantes.btSocket == null || Constantes.btSocket.isConnected() == false)
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
						Constantes.CONNECT.cancel();
					}
				}
				else
				{
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
					mProgress.dismiss();
					return;
				}
			}
		}

		if (Constantes.TIPO_ENVIO == "arquivo")
		{
			if (result != null)
			{
				mProgress.dismiss();

				if (objListaAnimal.isEmpty())
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não contém dados no arquivo selecionado.");
				else
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados atualizados com sucesso.");
			}
			else
			{
				Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, msg);
				mProgress.dismiss();
				return;
			}

		}

		this.menu_principal_activity.buscaDataAtualizacao();
	}

	public void fillDatabase(Context ctx, HashMap<Long, Animal> localData, String Table){

		Banco banco = new Banco(ctx);

		//The InsertHelper needs to have the db instance + the name of the table where you want to add the data
		InsertHelper ih = new InsertHelper(banco.getReadableDatabase(), Table);
		Iterator<Entry<Long, Animal>> it = localData.entrySet().iterator();
		final int id_pk = ih.getColumnIndex("id_pk");
		final int codigo = ih.getColumnIndex("codigo");
		final int identificador = ih.getColumnIndex("identificador");
		final int codigo_ferro = ih.getColumnIndex("codigo_ferro");
		final int data_nascimento = ih.getColumnIndex("data_nascimento");
		final int sexo = ih.getColumnIndex("sexo");
		final int situacao_reprodutiva = ih.getColumnIndex("situacao_reprodutiva");
		final int data_ultimo_dg = ih.getColumnIndex("data_ultimo_dg");
		final int data_parto_provavel = ih.getColumnIndex("data_parto_provavel");
		try{
			int i = 0;
			banco.getReadableDatabase().setLockingEnabled(false);
			while(it.hasNext()){
				Entry<Long, Animal> entry = it.next();

				ih.prepareForInsert();
				ih.bind(id_pk, entry.getKey());
				ih.bind(codigo, entry.getValue().getCodigo());
				ih.bind(identificador, entry.getValue().getIdentificador());
				ih.bind(codigo_ferro, entry.getValue().getCodigo_ferro());
				ih.bind(data_nascimento, entry.getValue().getData_nascimento());
				ih.bind(sexo, entry.getValue().getSexo());
				ih.bind(situacao_reprodutiva, entry.getValue().getSituacao_reprodutiva());
				ih.bind(data_ultimo_dg, entry.getValue().getData_ultimo_dg());
				ih.bind(data_parto_provavel, entry.getValue().getData_parto_provavel());
				ih.execute();

				publishProgress(i * 1);
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			if(ih!=null)
				ih.close();
			banco.getReadableDatabase().setLockingEnabled(true);
		}
	}



}
