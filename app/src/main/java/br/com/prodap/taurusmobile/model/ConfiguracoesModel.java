package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.dao.ConfiguracoesDao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;
import br.com.prodap.taurusmobile.tb.Configuracoes;

public class ConfiguracoesModel extends BancoService {

	private Banco banco;
	private ConfiguracoesAdapter conf_adapter;
	private SQLiteDatabase db;
	private Configuracoes c_tb;
	private ConfiguracoesDao c_dao;
	
	public ConfiguracoesModel(Context ctx) {
		conf_adapter = new ConfiguracoesAdapter();
	}
	
	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

	}
	
	@Override
	public List<Configuracoes> selectAll(Context ctx, String Tabela, Object table) {
		c_dao = new ConfiguracoesDao(ctx);

		return c_dao.selectAllConfiguracoes(ctx, Tabela, table);
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
