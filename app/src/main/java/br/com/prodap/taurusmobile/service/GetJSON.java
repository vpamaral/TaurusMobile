package br.com.prodap.taurusmobile.service;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.util.ValidatorException;

import com.google.gson.Gson;

public class GetJSON {
	private String url;
	Context ctx;

	public GetJSON(String url, Context ctx) {
		this.url = url;
		this.ctx = ctx;
	}

	public ArrayList<Animal> listaAnimal() throws ValidatorException{
		AnimalAdapter a_helper = new AnimalAdapter();
		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url, ctx);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Animal> animais = null;
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
			if (objArrayAnimal.length > 0) {
			 	animais = a_helper.arrayAnimais(objArrayAnimal);
				return animais;
			} else {
				return animais;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw  new ValidatorException("Impossível estabelecer conexão com o Banco Dados do Servidor.");
			//MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Ocorreu um erro ao atualizar Servidor...");
			//throw e;
		}
		//return null;
	}
}