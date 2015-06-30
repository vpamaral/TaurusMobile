package br.com.taurusmobile.service;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.taurusmobile.util.Constantes;

public class PostJSON {
	private String url;
	
	public PostJSON(String url) {
		this.url = url;
	}
		
	public String postAnimais(String url, String json) throws Exception{
		url = Constantes.POSTJSON;
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP();
			conexaoServidor.postRequestDeprecatedJson(url, json);
			return json;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
