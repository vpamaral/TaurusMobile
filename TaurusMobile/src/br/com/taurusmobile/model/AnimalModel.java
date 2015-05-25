package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class AnimalModel extends BancoService {

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
		// TODO Auto-generated method stub
		return null;
	}

}
