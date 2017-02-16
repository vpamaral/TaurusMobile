package br.com.prodap.taurusmobile.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import br.com.prodap.taurusmobile.service.Post_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Parto_Parto_Cria;
import br.com.prodap.taurusmobile.converter.Parto_Parto_Cria_JSON;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.model.Parto_Parto_Cria_Model;
import br.com.prodap.taurusmobile.service.Conexao_HTTP;
import br.com.prodap.taurusmobile.util.Auxiliar;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Post_Animais_JSON extends AsyncTask<Object, Integer, String>
{
	private Context ctx;
	private Configuracao configuracao_tb;
	private Parto_Parto_Cria p_parto_cria_tb;
	private Configuracao_Model configuracoes_model;
	private Parto_Parto_Cria_Model parto_parto_cria_model;
	private Parto_Model parto_model;
	private List<Configuracao> configuracao_list;
	private List<Parto_Parto_Cria> p_parto_cria_list;
	private String json;
	private Auxiliar auxiliar;
	private Gson gson;
	private String retornoJSON;
	public Conexao_HTTP c_http;
	public ProgressDialog mProgress;
	private int mProgressDialog = 0;
	private Post_JSON post_json;

	private SimpleDateFormat dateFormat;
	private Date data;
	private Date data_atual;
	private Calendar calendar;
	private String data_dd_mm_yyyy;

	private String filename;

	public Post_Animais_JSON(Context ctx, int progressDialog)
	{
		this.ctx = ctx;
		this.mProgressDialog = progressDialog;
		source();
	}

	private void source()
	{
		configuracao_tb 		= new Configuracao();
		p_parto_cria_tb 		= new Parto_Parto_Cria();
		configuracoes_model		= new Configuracao_Model(ctx);
		parto_parto_cria_model  = new Parto_Parto_Cria_Model(ctx);
		parto_model				= new Parto_Model(ctx);
		c_http					= new Conexao_HTTP();
		post_json 				= new Post_JSON();
		dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
		data 					= new Date();
		calendar 				= Calendar.getInstance();

		calendar.setTime(data);

		data_atual 				= calendar.getTime();
		data_dd_mm_yyyy 		= dateFormat.format(data_atual);

	}

	@Override
	protected void onPreExecute()
	{
		mProgress = new ProgressDialog(ctx);
		mProgress.setTitle("Aguarde ...");
		mProgress.setMessage("Enviando dados para o servidor ...");

		if (mProgressDialog == ProgressDialog.STYLE_HORIZONTAL)
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
	protected String doInBackground(Object... params)
	{
		String url 			= "";
		configuracao_list 	= configuracoes_model.selectAll(ctx, "Configuracao", configuracao_tb);

		for (Configuracao qrcode_tb : configuracao_list)
		{
			url = qrcode_tb.getEndereco();
		}

		try
		{
			p_parto_cria_list = parto_parto_cria_model.selectAll(ctx, "Parto_Cria", p_parto_cria_tb);

			if (p_parto_cria_list.size() != 0)
			{
				mProgress.setMax(p_parto_cria_list.size());

				for(int i = 0; p_parto_cria_list.size() < i; i++)
				{
					publishProgress(i * 1);
				}

				retornoJSON = post_json.postDadosSend(p_parto_cria_list);

				if (Constantes.TIPO_ENVIO == "web")
					new Conexao_HTTP(url + Constantes.METHOD_POST, ctx).postJson(retornoJSON);
			}
		}
		catch (Exception e)
		{
			Log.i("POSTJSON", e.toString());
			e.printStackTrace();
		}
		return retornoJSON;
	}

	@Override
	protected void onPostExecute(String json)
	{
		if (json != null)
		{
			if (Constantes.TIPO_ENVIO == "web")
			{
				if (c_http.servResultPost != HttpsURLConnection.HTTP_OK)
				{
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Impossível estabelecer conexão com o Banco Dados do Servidor.");
					mProgress.dismiss();
				}
				else
				{
					try
					{
						createFilePartoSend();
						writeInFileSendPartos(json);
					}
					catch (IOException e)
					{
						Log.i("PARTOS_WEB", e.toString());
						e.printStackTrace();
					}

					parto_model.deletingLogic(ctx);
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados enviados com sucesso.");
					mProgress.dismiss();
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
					try
					{
						createFilePartoSend();
						writeInFileSendPartos(json);
					}
					catch (IOException e)
					{
						Log.i("PARTOS_BLUETOOTH", e.toString());
						e.printStackTrace();
					}

					parto_model.deletingLogic(ctx);
					Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Dados enviados com sucesso.");
					mProgress.dismiss();
				}
			}

			if (Constantes.TIPO_ENVIO == "arquivo")
			{
				try
				{
					createFilePartoSend();
					writeInFileSendPartos(json);
				}
				catch (IOException e)
				{
					Log.i("ARQUIVO_PARTOS", e.toString());
					e.printStackTrace();
				}

				parto_model.deletingLogic(ctx);
				Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Arquivo gerado com sucesso.");
				mProgress.dismiss();
			}
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, ctx, "Não existem dados para serem enviados.");
			mProgress.dismiss();
		}
	}

	private boolean writeInFileSendPartos(String text)
	{
		BufferedReader input = null;
		File file = null;

		try
		{
			file 				= new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);
			FileOutputStream in = new FileOutputStream(file, true);

			in.write(text.getBytes());
			in.write("\n".getBytes());
			in.flush();
			in.close();

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	private void createFilePartoSend() throws IOException
	{
		Date data 			= new Date();
		final Calendar cal 	= Calendar.getInstance();

		cal.setTime(data);

		if (Constantes.CREATE_ARQUIVO == true)
			filename 		= "Partos_Arquivo"+ data_dd_mm_yyyy +".txt";
		else
			filename 		= "Partos_Enviados_Web_Bluetooth"+ data_dd_mm_yyyy +".txt";

		String conteudo = "";
		File diretorio 	= new File(obterDiretorio(), "Prodap");

		if(!diretorio.exists())
			diretorio.mkdir();

		File arquivo = new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);

		FileOutputStream outputStream = null;

		try
		{
			if(!arquivo.exists())
			{
				outputStream = new FileOutputStream(arquivo);
				outputStream.write(conteudo.getBytes());
				outputStream.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String obterDiretorio()
	{
		File root = android.os.Environment.getExternalStorageDirectory();

		return root.toString();
	}
}