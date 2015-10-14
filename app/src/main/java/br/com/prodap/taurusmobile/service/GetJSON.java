package br.com.prodap.taurusmobile.service;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
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
		AnimalAdapter ani_helper = new AnimalAdapter();

		try {
			ConexaoHTTP conexaoServidor = new ConexaoHTTP(url, ctx);
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
		} catch (Exception e) {
			e.printStackTrace();
			throw  new ValidatorException("Ocorreu um erro ao atualizar Servidor...");
			//MensagemUtil.addMsg(MessageDialog.Toast, ctx, "Ocorreu um erro ao atualizar Servidor...");
			//throw e;
		}
		//return null;
	}
}