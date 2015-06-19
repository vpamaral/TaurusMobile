package br.com.taurusmobile.service;

import br.com.taurusmobile.util.Constantes;

public class PostJSON {
	private String url;

	public PostJSON(String url) {
		this.url = url;
	}
		
	public String postAnimais(String json) throws Exception{
		url = Constantes.POST;
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP();
			conexaoServidor.lerUrlServico(url);

			return json;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
