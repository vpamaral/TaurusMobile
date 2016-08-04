package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Animal_Adapter;
import br.com.prodap.taurusmobile.dao.Animal_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Animal;

public class Animal_Model extends Banco_Service {

	private Banco banco;
	private Animal_Adapter ani_adapter;
	private SQLiteDatabase db;
	private Animal animal;
	private Animal_Dao a_dao;

	public Animal_Model(Context ctx) {
		ani_adapter = new Animal_Adapter();
	}

	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

	}

	@Override
	public List<Animal> selectAll(Context ctx, String Tabela, Object table)
	{
		a_dao = new Animal_Dao(ctx);

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

	public Animal selectByCodigo(Context ctx, String codigo)
	{
		Cursor cursor 		= null;
		Animal a_tb 		= new Animal();
		Banco banco 		= new Banco(ctx);

		try
		{
			String query = String.format("SELECT * FROM Animal WHERE codigo ='%s' OR codigo_ferro = '%s';", codigo, codigo);

			List<Animal> listadd = new ArrayList<Animal>();

			cursor = banco.getWritableDatabase().rawQuery(query, null);

			listadd = ani_adapter.arrayAnimais(cursor);

			a_tb = listadd.get(0);

		}
		catch (Exception e)
		{
			Log.e("Animal_Model", e.toString());
		}
		finally
		{
			if (cursor != null)
			{
				if (!cursor.isClosed())
				{
					cursor.close();
				}
			}
		}
		return a_tb;
	}

	public Animal selectByCodAlternativo(Context ctx, String codigo_ferro)
	{
		Cursor cursor 		= null;
		Animal a_tb 		= new Animal();
		Banco banco 		= new Banco(ctx);

		try
		{
			String query = String.format("SELECT * FROM Animal WHERE codigo_ferro ='%s';", codigo_ferro);

			List<Animal> listadd = new ArrayList<Animal>();

			cursor = banco.getWritableDatabase().rawQuery(query, null);

			listadd = ani_adapter.arrayAnimais(cursor);

			a_tb = listadd.get(0);

		}
		catch (Exception e)
		{
			Log.e("Animal_Model", e.toString());
		}
		finally
		{
			if (cursor != null)
			{
				if (!cursor.isClosed())
				{
					cursor.close();
				}
			}
		}
		return a_tb;
	}

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
