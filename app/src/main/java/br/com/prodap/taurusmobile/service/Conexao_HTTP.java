package br.com.prodap.taurusmobile.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class Conexao_HTTP {
	private String url;
	private Context ctx;
	public static int servResultPost;
	public static int servResultGet;
	private HttpResponse resposta;

	public Conexao_HTTP() {
	}

	public Conexao_HTTP(String url, Context ctx) {
		this.url = url;
		this.ctx = ctx;
	}

	public String lerUrlServico(String urlServico) throws IOException, TimeoutException
	{
		String dados = "";
		InputStream objDadosInputStream = null;
		HttpURLConnection objUrlConnection = null;

		try
		{
			URL url 			= new URL(urlServico);
			objUrlConnection 	= (HttpURLConnection) url.openConnection();

			objUrlConnection.connect();

			this.servResultGet 	= objUrlConnection.getResponseCode();
			objDadosInputStream = objUrlConnection.getInputStream();
			BufferedReader br 	= new BufferedReader(new InputStreamReader(objDadosInputStream));
			StringBuffer sb 	= new StringBuffer();
			String linha 		= "";

			while ((linha = br.readLine()) != null)
			{
				sb.append(linha);
			}

			dados = sb.toString();
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.i("TAG", e.toString());
		}
		finally
		{
			objDadosInputStream.close();
			objUrlConnection.disconnect();
		}
		return dados;
	}

	public String postRequest(String urlServico, ArrayList<String> postDataParams) throws Exception
	{
	  	HttpURLConnection objUrlConnection = null;
	  	String response = "";
	  	BufferedReader br = null;

	  	try
		{
			URL url = new URL(urlServico);
			objUrlConnection = (HttpURLConnection) url.openConnection();
			objUrlConnection.setRequestMethod("POST");
			objUrlConnection.setRequestProperty("Accept", "application/xml");
			objUrlConnection.setRequestProperty("Content-Type", "application/json");
			objUrlConnection.connect();
			OutputStream objDadosOutputStream = objUrlConnection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(objDadosOutputStream, "UTF-8"));
			writer.write(PostData(postDataParams));
			writer.flush();
			writer.close();
			objDadosOutputStream.close();
			int responseCode = objUrlConnection.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String linha;
				br = new BufferedReader(new InputStreamReader(objUrlConnection.getInputStream()));
				while ((linha = br.readLine()) != null) {
					response += linha;
				}
			} else {
				response = "";
			}
		}
	 	catch (Exception e)
	 	{
			  Log.i("TAG", e.toString());
			  e.printStackTrace();
	 	}
		finally
		{
			  br.close();
			  objUrlConnection.disconnect();
	 	}
	 	return response;
	}

	private String PostData(ArrayList<String>  postDataParams) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
			boolean first = true;
			for(String lista : postDataParams){
				if (first)
					first = false;
				else
					result.append("&");

				result.append(URLEncoder.encode(lista.toString(), "UTF-8"));

			}

		return result.toString();
	}

	public String postJson(String json) throws IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(json));
		post.setHeader("Content-type", "application/json");
		post.setHeader("Accept", "application/json");

		try {
			resposta = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.i("TAG", e.toString());
		}
		this.servResultPost = resposta.getStatusLine().getStatusCode();
		return EntityUtils.toString(resposta.getEntity());
	}
}
