package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.taurusmobile.TB.Configuracoes;
import br.com.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class ConfiguracoesModel extends BancoService {

	private Banco banco;
	private ConfiguracoesAdapter conf_adapter;
	private SQLiteDatabase db;
	private Configuracoes configurar;
	
	public ConfiguracoesModel(Context ctx) {
		conf_adapter = new ConfiguracoesAdapter();
	}
	
	@Override
	public boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<Configuracoes> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Configuracoes> listadd = new ArrayList<Configuracoes>();
		String sql = "SELECT * FROM " + Tabela;

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = conf_adapter.configurarPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
