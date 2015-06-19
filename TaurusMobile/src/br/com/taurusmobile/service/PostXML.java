package br.com.taurusmobile.service;

import br.com.taurusmobile.util.Constantes;

public class PostXML {
	private String url;

	public PostXML(String url) {
		this.url = url;
	}
		
	public String postAnimais(String xml) throws Exception{
		url = Constantes.POST;
		//url = Constantes.GETJSON;
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP();
			conexaoServidor.lerUrlServico(url);

			return xml;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}	
}
