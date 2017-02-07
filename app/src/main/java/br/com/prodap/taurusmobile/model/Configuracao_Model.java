package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.dao.Configuracao_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Configuracao_Model extends Banco_Service {

	private Banco banco;
	private Configuracao_Adapter conf_adapter;
	private SQLiteDatabase db;
	private Configuracao c_tb;
	private Configuracao_Dao c_dao;
	
	public Configuracao_Model(Context ctx) {
		conf_adapter = new Configuracao_Adapter();
	}
	
	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE)  throws Validator_Exception
	{
		c_dao = new Configuracao_Dao(ctx);
		c_tb  = (Configuracao) table;

		try
		{
			if (c_tb.getEndereco().toString().equals(""))
			{
				Validator_Exception ve = new Validator_Exception("O campo Endereço não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[]{});
				throw ve;
			}
		}
		catch (Validator_Exception e)
		{
			throw e;
		}
		catch (Exception e)
		{
			Log.i("CONFIGURACAO", e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Configuracao> selectAll(Context ctx, String Tabela, Object table)
	{
		c_dao = new Configuracao_Dao(ctx);

		return c_dao.selectAllConfiguracoes(ctx, Tabela, table);
	}
	
	@Override
	public Configuracao selectID(Context ctx, String Tabela, Object table, long id)
	{
		c_dao = new Configuracao_Dao(ctx);

		return c_dao.selectPK(ctx, Tabela, table, id);
	}
}
