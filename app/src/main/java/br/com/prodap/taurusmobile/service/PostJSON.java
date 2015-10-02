package br.com.prodap.taurusmobile.service;


import android.content.Context;

public class PostJSON {
	private String url;
	Context ctx;
	
	public PostJSON(String url, Context ctx) {
		this.url = url;
		this.ctx = ctx;
	}
		
	public String postAnimais(String json) throws Exception{
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url, ctx);
			conexaoServidor.postJson(json);
			return json;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
