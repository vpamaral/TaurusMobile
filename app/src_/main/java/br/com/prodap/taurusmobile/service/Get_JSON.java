package br.com.prodap.taurusmobile.service;

import java.util.ArrayList;

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
import br.com.prodap.taurusmobile.util.Validator_Exception;

import com.google.gson.Gson;

public class Get_JSON
{
	private String url;
	Context ctx;

	public Get_JSON(String url, Context ctx)
	{
		this.url = url;
		this.ctx = ctx;
	}

	public ArrayList<Animal> listaAnimal() throws Validator_Exception
	{
		Animal_Adapter a_helper = new Animal_Adapter();

		try
		{
			Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Animal> animais = null;
			Animal[] objArrayAnimal = gson.fromJson(retornoDadosJSON, Animal[].class);

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

		try
		{
			Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Pasto> pastos = null;
			Pasto[] array_pasto = gson.fromJson(retornoDadosJSON, Pasto[].class);

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

		try
		{
			Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Grupo_Manejo> list_grupo = null;
			Grupo_Manejo[] array_grupo = gson.fromJson(retornoDadosJSON, Grupo_Manejo[].class);

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

		try
		{
			Conexao_HTTP conexaoServidor = new Conexao_HTTP(url, ctx);
			String retornoDadosJSON = conexaoServidor.lerUrlServico(url);
			Log.i("URL", retornoDadosJSON);
			Gson gson = new Gson();
			ArrayList<Criterio> c_list = null;
			Criterio[] c_array = gson.fromJson(retornoDadosJSON, Criterio[].class);

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