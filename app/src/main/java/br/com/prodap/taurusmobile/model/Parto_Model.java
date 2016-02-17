package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Parto;

public class Parto_Model extends Banco_Service {

	private Banco banco;
	private Parto_Adapter parto_adapter;
	private SQLiteDatabase db;
	private Parto parto;

	public Parto_Model(Context ctx) {
		parto_adapter = new Parto_Adapter();
	}
	
	@Override
	public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) {

	}

	public void deleteParto(Context ctx, Long codigo){
		banco = new Banco(ctx);

		db = banco.getWritableDatabase();

		db.delete("Parto", "id_pk=?", new String[]{codigo.toString()});
	}

	@Override
	public List<Parto> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto> listadd = new ArrayList<Parto>();
		String sql = "SELECT * FROM " + Tabela;

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = parto_adapter.PartoPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}

	public void deletingLogic (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto SET sync_status ='1'");
		db.execSQL("UPDATE Parto_Cria SET sync_status ='1'");
	}

	public void recoverSentPartos (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto SET sync_status ='0'");
		db.execSQL("UPDATE Parto_Cria SET sync_status ='0'");
	}

	public void recoverDescarte (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto_Cria SET repasse = 'NAO' WHERE repasse = 'NÃO'");
	}

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
