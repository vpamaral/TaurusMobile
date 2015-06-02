package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class AnimalModel extends BancoService {

	private Banco banco;
	AnimalAdapter ani_adapter;

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
		String sql = "SELECT * FROM " + Tabela;

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = ani_adapter.AnimalPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}

	public Animal selectByCodigo(Context ctx, String codigo) {
		Cursor cursor = null;
		Animal AnimalLinha = new Animal();
		Banco banco = new Banco(ctx);
		
		try {
			String query = "SELECT * FROM Animal WHERE codigo ='" + codigo + "';";
			
			List<Animal> listadd = new ArrayList<Animal>();
			
			cursor = banco.getWritableDatabase().rawQuery(query, null);
			
			
			listadd = ani_adapter.AnimalPreencheArrayCursor(cursor);
			
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
