package br.com.taurusmobile.service;

import com.github.kevinsawicki.http.HttpRequest;

import br.com.taurusmobile.util.Constantes;

public class PostXML {
	private String url;

	public PostXML(String url) {
		this.url = url;
	}
		
	public String postAnimais(String url, String xml) throws Exception{
		url = Constantes.POSTJSON;
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP();
			conexaoServidor.postRequestDeprecatedXml(url, xml);
			return xml;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
