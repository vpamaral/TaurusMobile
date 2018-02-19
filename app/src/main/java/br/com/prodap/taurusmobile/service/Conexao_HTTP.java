package br.com.prodap.taurusmobile.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

import br.com.prodap.taurusmobile.util.Constantes;
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

public class Conexao_HTTP
{
	private String url;
	private Context ctx;
	public static int servResultPost;
	public static int servResultGet;
	private HttpResponse resposta;

	public Conexao_HTTP() {	}

	public Conexao_HTTP(String url, Context ctx)
	{
		this.url = url;
		this.ctx = ctx;
	}

	public String lerUrlServico(String urlServico) throws IOException, TimeoutException
	{
		String dados = "";
		InputStream objDadosInputStream 	= null;
		HttpURLConnection objUrlConnection 	= null;

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

			dados 							= sb.toString();
			//Constantes.SERVER_RESULT_GET 	= objUrlConnection.getResponseCode();
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

	private String PostData(ArrayList<String>  postDataParams) throws UnsupportedEncodingException
	{
		StringBuilder result 	= new StringBuilder();
		boolean first 			= true;

		for(String lista : postDataParams)
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(lista.toString(), "UTF-8"));
		}

		return result.toString();
	}

	public String postJson(String json)
	{
		try
		{
			URL _url = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
			connection.setRequestProperty("Content-type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			connection.setDoOutput(true);

			PrintStream output = new PrintStream(connection.getOutputStream());
			output.println(json);

			connection.connect();

			Scanner scanner = new Scanner(connection.getInputStream());
			String resposta = scanner.next();

			this.servResultPost = connection.getResponseCode();

			return resposta;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return String.valueOf(resposta);
	}
}
