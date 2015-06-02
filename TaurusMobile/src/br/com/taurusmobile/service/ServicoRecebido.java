package br.com.taurusmobile.service;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.util.Log;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;

public class ServicoRecebido {

	String URLIPCaminhoServico = "http://192.168.0.235/TaurusWebService/TaurusService.svc/listaAnimaisJson";
	
	public ArrayList<Animal> listaAnimal() throws Exception{
			
			AnimalAdapter ani_helper = new AnimalAdapter();
			try {
				
				ConexaoHTTP conexaoServidor = new ConexaoHTTP();
				String retornoDadosJSON = conexaoServidor.lerUrlServico(URLIPCaminhoServico);
	
				Log.i("Retorno RESTful JSON", retornoDadosJSON);
				
				Gson gson = new Gson();
				
				Log.i("Exibir:", "criou Gson");
				Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
				
				return ani_helper.AnimalPreencheArrayHelper(objArrayAnimal);
				
			} catch (Exception e) {
				Log.i("Erro GSON:", e.getMessage());
				e.printStackTrace();
				throw e;
			}
		}
	}
