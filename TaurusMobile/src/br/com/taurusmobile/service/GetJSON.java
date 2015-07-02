package br.com.taurusmobile.service;

import java.util.ArrayList;

import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.util.Constantes;

import com.google.gson.Gson;

public class GetJSON {
	private String url;

	public GetJSON(String url) {
		this.url = url;
	}

	public ArrayList<Animal> listaAnimal() throws Exception {
		AnimalAdapter ani_helper = new AnimalAdapter();
		
		try {			
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Gson gson = new Gson();
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON,
					Animal[].class);

			return ani_helper.AnimalPreencheArrayHelper(objArrayAnimal);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}