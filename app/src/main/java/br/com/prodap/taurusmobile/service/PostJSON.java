package br.com.prodap.taurusmobile.service;


public class PostJSON {
	private String url;
	
	public PostJSON(String url) {
		this.url = url;
	}
		
	public String postAnimais(String json) throws Exception{
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url);
			conexaoServidor.postJson(json);
			return json;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
