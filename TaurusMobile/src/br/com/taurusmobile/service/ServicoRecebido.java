package br.com.taurusmobile.service;

import java.util.ArrayList;

import com.google.gson.Gson;

import android.util.Log;
import br.com.taurusmobile.TB.Animal;

public class ServicoRecebido {

	String URLIPCaminhoServico = "http://192.168.0.246/WcfServiceAnimal/ServiceAnimal.svc/listaAnimal";
	
	public ArrayList<Animal> listaAnimal(){
			
			ArrayList<Animal> listaAnimal = new ArrayList<Animal>();
			
			try {
				
				Log.i("URL", URLIPCaminhoServico);
				ConexaoHTTP conexaoServidor = new ConexaoHTTP();
				String retornoDadosJSON = conexaoServidor.lerUrlServico(URLIPCaminhoServico);
	
				Log.i("Retorno RESTful JSON", retornoDadosJSON);
				
				Gson gson = new Gson();
				
				Log.i("Exibir:", "criou Gson");
				Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
				
				for (int i=0; i < objArrayAnimal.length ; i++){
					
					Animal objAnimal = new Animal();
					
					objAnimal.setSisbov(objArrayAnimal[i].getSisbov());
					objAnimal.setCodigo(objArrayAnimal[i].getCodigo());
					objAnimal.setCodigo_ferro(objArrayAnimal[i].getCodigo_ferro());
					objAnimal.setIdentificador(objArrayAnimal[i].getIdentificador());
					
					listaAnimal.add(objAnimal);
				}
				
			} catch (Exception e) {
				Log.i("Erro GSON:", e.getMessage());
				e.printStackTrace();
			}
			return listaAnimal;
		}
	}
