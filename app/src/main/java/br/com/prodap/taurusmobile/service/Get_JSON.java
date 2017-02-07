package br.com.prodap.taurusmobile.service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;

import br.com.prodap.taurusmobile.adapter.Criterio_Adapter;
import br.com.prodap.taurusmobile.adapter.Grupo_Manejo_Adapter;
import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.tb.Criterio;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Validator_Exception;

import com.google.gson.Gson;

public class Get_JSON
{
	private static String json;
	private String result;
	private String url;
	private Context ctx;

	private String animais_json;
	private String pasto_json;
	private String grupo_json;
	private String criterio_json;

	public Get_JSON() {	}

	public void Get_JSON(String json)
	{
		this.json = json;
	}

	public Get_JSON(String url, Context ctx)
	{
		this.url = url;
		this.ctx = ctx;
	}

	private void LoadJson()
	{
		this.result 				= validaJson(json, "null;", ";");
		StringTokenizer parts_json 	= new StringTokenizer(result, "|");
		this.animais_json 			= parts_json.nextToken();
		this.pasto_json 			= parts_json.nextToken();
		this.grupo_json 			= parts_json.nextToken();
		this.criterio_json			= parts_json.nextToken();
	}

	public String validaJson(String str, String charsRemove, String delimiter)
	{
		if (charsRemove != null && charsRemove.length() > 0 && str != null)
		{
			String[] remover = charsRemove.split(delimiter);

			for(int i = 0; i < remover.length; i++)
			{
				if (str.indexOf(remover[i]) != -1)
				{
					str = str.replace(remover[i], "");
				}
			}
		}
		return str;
	}

	public ArrayList<Animal> listaAnimal() throws Validator_Exception
	{
		Animal_Adapter a_helper = new Animal_Adapter();
		Animal[] objArrayAnimal;

		try
		{
			Gson gson = new Gson();
			ArrayList<Animal> animais = null;

			if (Constantes.STATUS_CONN != "conectado" )
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
				String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
				Log.i("URL", retornoDadosJSON);
				objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
			}
			else
			{
				//conexao com o bluetooth
				LoadJson();
				Log.i("JSON", this.animais_json);
				objArrayAnimal = gson.fromJson(animais_json, Animal[].class);
			}

			if (objArrayAnimal.length > 0)
			{
				animais = a_helper.arrayAnimais(objArrayAnimal);
				return animais;
			}
			else
			{
				return animais;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	public ArrayList<Pasto> listPasto() throws Validator_Exception
	{
		Pasto_Adapter pasto_helper = new Pasto_Adapter();
		Pasto[] array_pasto;

		try
		{
			Gson gson = new Gson();
			ArrayList<Pasto> pastos = null;

			if (Constantes.STATUS_CONN != "conectado" )
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
				String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
				Log.i("URL", retornoDadosJSON);
				array_pasto = gson.fromJson(retornoDadosJSON, Pasto[].class);
			}
			else
			{
				//conexao com o bluetooth
				LoadJson();
				Log.i("JSON", this.pasto_json);
				array_pasto = gson.fromJson(this.pasto_json, Pasto[].class);
			}

			if (array_pasto.length > 0)
			{
				pastos = pasto_helper.arrayPasto(array_pasto);
				return pastos;
			}
			else
			{
				return pastos;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	public ArrayList<Grupo_Manejo> listGrupo() throws Validator_Exception
	{
		Grupo_Manejo_Adapter grupo_adapter = new Grupo_Manejo_Adapter();
		Grupo_Manejo[] array_grupo;

		try
		{
			Gson gson = new Gson();
			ArrayList<Grupo_Manejo> list_grupo = null;

			if (Constantes.STATUS_CONN != "conectado" )
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
				String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
				Log.i("URL", retornoDadosJSON);
				array_grupo = gson.fromJson(retornoDadosJSON, Grupo_Manejo[].class);
			}
			else
			{
				//conexao com o bluetooth
				LoadJson();
				Log.i("JSON", this.grupo_json);
				array_grupo = gson.fromJson(this.grupo_json, Grupo_Manejo[].class);
			}

			if (array_grupo.length > 0)
			{
				list_grupo = grupo_adapter.arrayGrupo(array_grupo);
				return list_grupo;
			}
			else
			{
				return list_grupo;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	public ArrayList<Criterio> listCriterio() throws Validator_Exception
	{
		Criterio_Adapter c_adapter = new Criterio_Adapter();
		Criterio[] c_array;

		try
		{
			Gson gson = new Gson();
			ArrayList<Criterio> c_list = null;

			if (Constantes.STATUS_CONN != "conectado" )
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
				String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
				Log.i("URL", retornoDadosJSON);
				c_array = gson.fromJson(retornoDadosJSON, Criterio[].class);
			}
			else
			{
				//conexao com o bluetooth
				LoadJson();
				Log.i("JSON", this.criterio_json);
				c_array = gson.fromJson(this.criterio_json, Criterio[].class);
			}

			if (c_array.length > 0)
			{
				c_list = c_adapter.arrayCriterio(c_array);
				return c_list;
			}
			else
			{
				return c_list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}
}