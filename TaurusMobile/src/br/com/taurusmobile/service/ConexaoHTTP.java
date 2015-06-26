package br.com.taurusmobile.service;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.taurusmobile.util.Auxiliar;
import br.com.taurusmobile.util.AuxiliarXML;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

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
	
	public String postRequest(String urlServico, ArrayList<String> postDataParams) throws Exception
	{
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
		}
		 finally{
			br.close();
 			objUrlConnection.disconnect();
 		}
		
		return response;
		
		   /*HttpPost request = new HttpPost(urlServico);
		   request.setHeader("Accept", "application/json");
		   request.setHeader("Content-type", "application/json");
		   MinhaClasse objeto = new MinhaClasse("teste");
		   Gson gson = new Gson();
		   //converte objeto para json
		   String json = gson.toJson(objeto);
		   StringEntity entity = new StringEntity(json);
		   //inclui entidade na requisicao
		   request.setEntity(entity);
		   DefaultHttpClient httpClient = new DefaultHttpClient();
		   HttpResponse response = httpClient.execute(request);
		   HttpEntity responseEntity = response.getEntity();
		   return responseToString(responseEntity);*/
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
	
	public String postRequestDeprecatedJson(String url, String param) throws Exception
	{
		   HttpPost request = new HttpPost(url);
		   request.setHeader("Accept", "application/json");
		   request.setHeader("Content-type", "application/json");
		   
		   Auxiliar objeto = new Auxiliar(param);
		   Gson gson = new Gson();
		   //converte objeto para json
		   String json = gson.toJson(objeto);
		   
		   StringEntity entity = new StringEntity(json);
		   //inclui entidade na requisicao
		   request.setEntity(entity);
		   DefaultHttpClient httpClient = new DefaultHttpClient();
		   HttpResponse response = httpClient.execute(request);
		   HttpEntity responseEntity = response.getEntity();
		   return responseToString(responseEntity);
	 }
	
	public String postRequestDeprecatedXml(String url, String param) throws Exception
	{
		   HttpPost request = new HttpPost(url);
		   request.setHeader("Accept", "application/xml");
		   request.setHeader("Content-type", "application/xml");
		   
		   AuxiliarXML objeto = new AuxiliarXML(param);
		   XStream xmlConvert = new XStream();
		   //converte objeto para xml
		   String xml = xmlConvert.toXML(objeto);
		   
		   StringEntity entity = new StringEntity(xml);
		   entity.setContentType("text/xml");
		   //inclui entidade na requisicao
		   request.setEntity(entity);
		   DefaultHttpClient httpClient = new DefaultHttpClient();
		   HttpResponse response = httpClient.execute(request);
		   HttpEntity responseEntity = response.getEntity();
		   return responseToString(responseEntity);
	 }
	
	String responseToString(HttpEntity responseEntity) throws Exception
	{
		   char[] buffer = new char[(int) responseEntity.getContentLength()];
			InputStream stream = responseEntity.getContent();
			InputStreamReader reader = new InputStreamReader(stream);
			reader.read(buffer);
			stream.close();					
			return new String(buffer);	
	}
	   
	   
}
