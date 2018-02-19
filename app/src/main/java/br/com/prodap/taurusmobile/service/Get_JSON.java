package br.com.prodap.taurusmobile.service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;

import br.com.prodap.taurusmobile.adapter.Criterio_Adapter;
import br.com.prodap.taurusmobile.adapter.Grupo_Manejo_Adapter;
import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.helper.Animal_Helper;
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

	//gera a lista via bluetooth ou via arquivo
	public ArrayList<Animal> GetAnimais() throws Validator_Exception
	{
		Animal_Helper a_helper = new Animal_Helper();
		LoadJson();

		try
		{
			Log.i("JSON", this.animais_json);

			Gson gson 					= new Gson();
			ArrayList<Animal> a_list 	= null;
			Animal[] a_array 			= gson.fromJson(this.animais_json, Animal[].class);

			if (a_array.length > 0)
			{
				a_list = a_helper.arrayAnimais(a_array);
				return a_list;
			}
			else
			{
				return a_list;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	public ArrayList<Animal> listaAnimal() throws Validator_Exception
	{
		Animal_Adapter a_helper = new Animal_Adapter();
		Animal[] objArrayAnimal;

		try
		{
			Gson gson = new Gson();
			ArrayList<Animal> animais = null;

			if (Constantes.TIPO_ENVIO == "web")
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
				String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
				Log.i("URL", retornoDadosJSON);
				objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);
				animais = a_helper.arrayAnimais(objArrayAnimal);
				Constantes.SERVER_RESULT_GET = 200;
			}

			if (Constantes.TIPO_ENVIO == "bluetooth" || Constantes.TIPO_ENVIO == "arquivo")
			{
				//via bluetooth ou via arquivo 
				animais = GetAnimais();
			}

			return animais;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	//gera a lista via bluetooth ou via arquivo
	public ArrayList<Pasto> GetPastos() throws Validator_Exception
	{
		Pasto_Adapter p_helper = new Pasto_Adapter();
		LoadJson();

		try
		{
			Log.i("JSON", this.pasto_json);

			Gson gson 						= new Gson();
			ArrayList<Pasto> p_list = null;
			Pasto[] p_array 				= gson.fromJson(this.pasto_json, Pasto[].class);

			if (p_array.length > 0)
			{
				p_list = p_helper.arrayPasto(p_array);
				return p_list;
			}
			else
			{
				return p_list;
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
			Gson gson 				= new Gson();
			ArrayList<Pasto> pastos = null;

			if (Constantes.TIPO_ENVIO == "web")
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor 	= new Conexao_HTTP(url, ctx);
				String retornoDadosJSON 		= conexaoServidor.lerUrlServico(url);
				array_pasto 					= gson.fromJson(retornoDadosJSON, Pasto[].class);
				pastos 							= pasto_helper.arrayPasto(array_pasto);
			}

			if (Constantes.TIPO_ENVIO == "bluetooth" || Constantes.TIPO_ENVIO == "arquivo")
			{
				//via bluetooth ou via arquivo 
				pastos = GetPastos();
			}

			return pastos;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	//gera a lista via bluetooth ou via arquivo
	public ArrayList<Grupo_Manejo> GetGrupos() throws Validator_Exception
	{
		Grupo_Manejo_Adapter gm_helper = new Grupo_Manejo_Adapter();
		LoadJson();

		try
		{
			Log.i("JSON", this.grupo_json);

			Gson gson 						= new Gson();
			ArrayList<Grupo_Manejo> gm_list = null;
			Grupo_Manejo[] gm_array 		= gson.fromJson(this.grupo_json, Grupo_Manejo[].class);

			if (gm_array.length > 0)
			{
				gm_list = gm_helper.arrayGrupo(gm_array);
				return gm_list;
			}
			else
			{
				return gm_list;
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

			if (Constantes.TIPO_ENVIO == "web")
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor 	= new Conexao_HTTP(url, ctx);
				String retornoDadosJSON 		= conexaoServidor.lerUrlServico(url);
				array_grupo 					= gson.fromJson(retornoDadosJSON, Grupo_Manejo[].class);
				list_grupo 						= grupo_adapter.arrayGrupo(array_grupo);
			}
			
			if (Constantes.TIPO_ENVIO == "bluetooth" || Constantes.TIPO_ENVIO == "arquivo")
			{
				//via bluetooth ou via arquivo 
				list_grupo = GetGrupos();
			}

			return list_grupo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}

	//gera a lista via bluetooth ou via arquivo
	public ArrayList<Criterio> GetCriterios() throws Validator_Exception
	{
		Criterio_Adapter c_helper = new Criterio_Adapter();
		LoadJson();

		try
		{
			Log.i("JSON", this.criterio_json);

			Gson gson 					= new Gson();
			ArrayList<Criterio> c_list 	= null;
			Criterio[] c_array 			= gson.fromJson(this.criterio_json, Criterio[].class);

			if (c_array.length > 0)
			{
				c_list = c_helper.arrayCriterio(c_array);
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

	public ArrayList<Criterio> listCriterio() throws Validator_Exception
	{
		Criterio_Adapter c_adapter = new Criterio_Adapter();
		Criterio[] c_array;

		try
		{
			Gson gson = new Gson();
			ArrayList<Criterio> c_list = null;

			if (Constantes.TIPO_ENVIO == "web")
			{
				//conexao com a web
				Conexao_HTTP conexaoServidor 	= new Conexao_HTTP(url, ctx);
				String retornoDadosJSON 		= conexaoServidor.lerUrlServico(url);
				c_array 						= gson.fromJson(retornoDadosJSON, Criterio[].class);
				c_list 							= c_adapter.arrayCriterio(c_array);
			}
			
			if (Constantes.TIPO_ENVIO == "bluetooth" || Constantes.TIPO_ENVIO == "arquivo")
			{
				//via bluetooth ou via arquivo 
				c_list = GetCriterios();
			}

			return c_list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw  new Validator_Exception("Impossível estabelecer conexão com o Banco Dados do Servidor.");
		}
	}
}