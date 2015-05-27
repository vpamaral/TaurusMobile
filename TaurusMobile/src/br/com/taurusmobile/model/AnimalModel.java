package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class AnimalModel extends BancoService {
	
	private Banco banco;
	private SQLiteDatabase db;
	
	public AnimalModel(Context ctx) {
		
	}

	
	
	

	@Override
	public boolean validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE)
	{
		return true;
	}
	
	@Override
	public List<Animal> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Animal> listadd = new ArrayList<Animal>();
		String sql = "SELECT * FROM " + Tabela;
		

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		while (c.moveToNext()) {
			Animal animal = new Animal();
			animal.setCodigo(c.getString(c.getColumnIndex("codigo")));
			animal.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			animal.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			animal.setCodigo_ferro(c.getString(c.getColumnIndex("codigo_ferro")));
			listadd.add(animal);
		}

		banco.close();
		return listadd;
	}
	


	@Override
	public Animal selectID(Context ctx, String Tabela, Object table, long id) {
		Cursor cursor = null;
		Animal AnimalLinha = new Animal();
    	
    	try {				    		
    		String query = "SELECT * FROM" + Tabela + "WHERE Id =" + id + ";";
            cursor = db.rawQuery(query,null);
    		if (cursor.getCount() > 0 ) {
				while (cursor.moveToNext()){
					AnimalLinha.setCodigo(cursor.getString(cursor.getColumnIndex("codigo")));
					AnimalLinha.setSisbov(cursor.getString(cursor.getColumnIndex("sisbov")));
					AnimalLinha.setIdentificador(cursor.getString(cursor.getColumnIndex("identificador")));
					AnimalLinha.setCodigo_ferro(cursor.getString(cursor.getColumnIndex("codigo_ferro")));			
				}
			}    		
		} catch (Exception e) {
			Log.e("AnimalModel", e.toString());
		}
		finally{
			if (cursor != null) {
				if (!cursor.isClosed()) {
					cursor.close();
				}
			}
		}
    	return AnimalLinha;
	}


}
