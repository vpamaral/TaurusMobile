package br.com.prodap.taurusmobile.service;

import java.util.ArrayList;

import android.util.Log;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.util.Constantes;

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
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
			
			return ani_helper.AnimalPreencheArrayHelper(objArrayAnimal);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}