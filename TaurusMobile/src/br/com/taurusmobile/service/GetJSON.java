package br.com.taurusmobile.service;

import java.util.ArrayList;

import android.util.Log;
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
		url = Constantes.GET;
		AnimalAdapter ani_helper = new AnimalAdapter();
		try {

			ConexaoHTTP conexaoServidor = new ConexaoHTTP();
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);

			Log.i("Retorno RESTful JSON", retornoDadosJSON);

			Gson gson = new Gson();

			Log.i("Exibir:", "criou Gson");
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON,
					Animal[].class);

			return ani_helper.AnimalPreencheArrayHelper(objArrayAnimal);

		} catch (Exception e) {
			Log.i("Erro GSON:", e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}