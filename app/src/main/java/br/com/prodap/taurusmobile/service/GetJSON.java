package br.com.prodap.taurusmobile.service;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.ValidatorException;

import com.google.gson.Gson;

public class GetJSON {
	private String url;

	public GetJSON(String url) {
		this.url = url;
	}

	public ArrayList<Animal> listaAnimal() throws ValidatorException {
		AnimalAdapter ani_helper = new AnimalAdapter();
		
		try {			
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Animal> animais = null;
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
			if (objArrayAnimal.length > 0) {
				 animais = ani_helper.AnimalPreencheArrayHelper(objArrayAnimal);
				return animais;
			} else {
				return animais;
			}

		} catch (ValidatorException e) {
			Log.i("ERRO:", e.toString());
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			//throw e;
		}
		return null;
	}
}