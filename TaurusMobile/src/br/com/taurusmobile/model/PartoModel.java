package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.adapter.PartoAdapter;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class PartoModel extends BancoService {

	private Banco banco;
	private PartoAdapter parto_adapter;
	private SQLiteDatabase db;
	private Parto parto;

	public PartoModel(Context ctx) {
		parto_adapter = new PartoAdapter();
	}
	
	@Override
	public boolean validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Parto selectByCodigo(Context ctx, Integer codigo) {
		banco = new Banco(ctx);

		db = banco.getReadableDatabase();

		Cursor cursor = db.query("Parto", null, "id_auto=?",
				new String[] { codigo.toString() }, null, null, "id_auto");

		parto = parto_adapter.PartoCursor(cursor);

		return parto;
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

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
