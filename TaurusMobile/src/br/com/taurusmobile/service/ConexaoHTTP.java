package br.com.taurusmobile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class ConexaoHTTP {
	
	public String lerUrlServico(String urlServico) throws IOException{
		String dados = "";
		InputStream objDadosInputStream = null;
		HttpURLConnection objUrlConnection = null;
		
		try{
			URL url = new URL(urlServico);
			objUrlConnection = (HttpURLConnection) url.openConnection();
			objUrlConnection.connect();
			objDadosInputStream = objUrlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(objDadosInputStream));
			StringBuffer sb = new StringBuffer();
			String linha = "";
			while((linha = br.readLine()) != null){
				sb.append(linha);			
			}
			dados = sb.toString();
			br.close();
		}
		catch(Exception e)
		{
			Log.i("TAG", e.toString());
		}
		finally{
			objDadosInputStream.close();
			objUrlConnection.disconnect();
		}
		return dados;
	}
}
