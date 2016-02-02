package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.dao.AnimalDao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;
import br.com.prodap.taurusmobile.tb.Animal;

public class AnimalModel extends BancoService {

	private Banco banco;
	private AnimalAdapter ani_adapter;
	private SQLiteDatabase db;
	private Animal animal;
	private AnimalDao a_dao;

	public AnimalModel(Context ctx) {
		ani_adapter = new AnimalAdapter();
	}

	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

	}

	@Override
	public List<Animal> selectAll(Context ctx, String Tabela, Object table) {
		a_dao = new AnimalDao(ctx);

		return a_dao.selectAllAnimais(ctx, Tabela, table);
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
			String query = String.format("SELECT * FROM Animal WHERE codigo ='%s';", codigo);

			List<Animal> listadd = new ArrayList<Animal>();

			cursor = banco.getWritableDatabase().rawQuery(query, null);

			listadd = ani_adapter.arrayAnimais(cursor);

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
