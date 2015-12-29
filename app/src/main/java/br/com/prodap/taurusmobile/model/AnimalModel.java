package br.com.prodap.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;

public class AnimalModel extends BancoService {

	private Banco banco;
	private AnimalAdapter ani_adapter;
	private SQLiteDatabase db;
	private Animal animal;

	public AnimalModel(Context ctx) {
		ani_adapter = new AnimalAdapter();
	}

	@Override
	public boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE) {
		return true;
	}

	@Override
	public List<Animal> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Animal> listadd = new ArrayList<Animal>();
		String sql = "SELECT * FROM " + Tabela + " ORDER BY codigo ";

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = ani_adapter.arrayCursorAnimais(c);

		banco.close();
		return listadd;
	}

	public Animal selectByCodigo(Context ctx, Integer codigo) {
		banco = new Banco(ctx);

		db = banco.getReadableDatabase();

		Cursor cursor = db.query("Animal", null, "id_auto=?",
				new String[] { codigo.toString() }, null, null, "id_auto");

		animal = ani_adapter.cursorAnimais(cursor);

		return animal;

	}

	public Animal selectByCodigo(Context ctx, String codigo) {
		Cursor cursor = null;
		Animal AnimalLinha = new Animal();
		Banco banco = new Banco(ctx);

		try {
			String query = "SELECT * FROM Animal WHERE codigo ='" + codigo
					+ "';";

			List<Animal> listadd = new ArrayList<Animal>();

			cursor = banco.getWritableDatabase().rawQuery(query, null);

			listadd = ani_adapter.arrayCursorAnimais(cursor);

			AnimalLinha = listadd.get(0);

		} catch (Exception e) {
			Log.e("AnimalModel", e.toString());
		} finally {
			if (cursor != null) {
				if (!cursor.isClosed()) {
					cursor.close();
				}
			}
		}
		return AnimalLinha;
	}

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
