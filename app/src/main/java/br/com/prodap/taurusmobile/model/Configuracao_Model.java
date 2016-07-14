package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.dao.Configuracao_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Configuracao;

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
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

	}
	
	@Override
	public List<Configuracao> selectAll(Context ctx, String Tabela, Object table) {
		c_dao = new Configuracao_Dao(ctx);

		return c_dao.selectAllConfiguracoes(ctx, Tabela, table);
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
