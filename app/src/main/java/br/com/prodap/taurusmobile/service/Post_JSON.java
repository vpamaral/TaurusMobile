package br.com.prodap.taurusmobile.service;

import com.google.gson.Gson;

import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto_Parto_Cria;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.view.Menu_Principal_Activity;

public class Post_JSON
{
	private Menu_Principal_Activity mpa = new Menu_Principal_Activity();
	private String return_json;

	public String postDadosSend(List<Parto_Parto_Cria> p_p_cria_list) throws Exception
	{
		Gson gson 	= new Gson();
		return_json = gson.toJson(p_p_cria_list);

		if (Constantes.CREATE_ARQUIVO == false)
			mpa.sendMessage(return_json);

		return return_json;
	}

	private void loadFile(String json)
	{
		return_json += json + "|";
	}	
}
