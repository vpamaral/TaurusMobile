package br.com.prodap.taurusmobile.service;


import android.content.Context;

public class Post_JSON {
	private String url;
	Context ctx;
	
	public Post_JSON(String url, Context ctx) {
		this.url = url;
		this.ctx = ctx;
	}
		
	public String postAnimais(String json) throws Exception{
		try {
			Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
			conexaoServidor.postJson(json);
			return json;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
