package br.com.taurusmobile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class ConexaoHTTP {
	private String url;

	public ConexaoHTTP(String url) {
		this.url = url;
	}

	public String lerUrlServico(String urlServico) throws IOException {
		String dados = "";
		InputStream objDadosInputStream = null;
		HttpURLConnection objUrlConnection = null;

		try {
			URL url = new URL(urlServico);
			objUrlConnection = (HttpURLConnection) url.openConnection();
			objUrlConnection.connect();
			objDadosInputStream = objUrlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					objDadosInputStream));
			StringBuffer sb = new StringBuffer();
			String linha = "";
			while ((linha = br.readLine()) != null) {
				sb.append(linha);
			}
			dados = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.i("TAG", e.toString());
		} finally {
			objDadosInputStream.close();
			objUrlConnection.disconnect();
		}
		return dados;
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
