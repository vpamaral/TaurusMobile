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

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import br.com.prodap.taurusmobile.util.ValidatorException;

public class ConexaoHTTP {
	private String url;
	Context ctx;

	public ConexaoHTTP(String url,  Context ctx) {
		this.url = url;
		this.ctx = ctx;
	}

	public String lerUrlServico(String urlServico) throws IOException{
		String dados = "";
		InputStream objDadosInputStream = null;
		HttpURLConnection objUrlConnection = null;
		BufferedReader br = null;

		try {
			URL url = new URL(urlServico);
			objUrlConnection = (HttpURLConnection) url.openConnection();
			objUrlConnection.connect();
			objDadosInputStream = objUrlConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(objDadosInputStream));
			StringBuffer sb = new StringBuffer();

			String linha = "";
			while ((linha = br.readLine()) != null) {
				sb.append(linha);
			}
			dados = sb.toString();
			//br.close();
		} catch (IOException e) {
			Log.i("TAG", e.toString());
			e.printStackTrace();
		} finally {
			if(br != null){
				try{
					br.close();
					throw new ValidatorException("");
				}
				catch (ValidatorException e){
					e.printStackTrace();
					MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Ocorreu um erro ao atualizar Servidor...");
				}
			}
			//objDadosInputStream.close();
			//objUrlConnection.disconnect();
		}
		return dados;
	}
	
	public String postRequest(String urlServico, ArrayList<String> postDataParams) throws Exception{
	  HttpURLConnection objUrlConnection = null;
	  String response = "";
	  BufferedReader br = null;
	  try {
		  URL url = new URL(urlServico);
		  objUrlConnection = (HttpURLConnection) url.openConnection();
		  objUrlConnection.setRequestMethod("POST");
		  objUrlConnection.setRequestProperty("Accept","application/xml");
		  objUrlConnection.setRequestProperty("Content-Type","application/json");
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
				  response+=linha;
			  }
		  }
		  else {
			  response="";
		  }
	  } 
	  catch (Exception e) {
		  Log.i("TAG", e.toString());
		  e.printStackTrace();
	  }
	   finally{
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

	public String postJson(String json) {
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(new StringEntity(json));
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");

			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse resposta = client.execute(post);

			return EntityUtils.toString(resposta.getEntity());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
