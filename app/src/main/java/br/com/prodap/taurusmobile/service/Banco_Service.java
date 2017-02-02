package br.com.prodap.taurusmobile.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import br.com.prodap.taurusmobile.util.Validator_Exception;

public abstract class Banco_Service
{
	public abstract void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) throws Validator_Exception;

	public abstract <T> List<T> selectAll(Context ctx, String Tabela, Object table);

	public abstract <T> T selectID(Context ctx, String Tabela, Object table, long id);

	/*public void insert(Context ctx, String Tabela, Object table) {
	{
		try
		{
			Banco banco = new Banco(ctx);
			ContentValues cv = new ContentValues();
			Class<? extends Object> s = table.getClass();

			for (Field f : s.getDeclaredFields()) {
				cv.put(f.getName(), getValueAt(table, "get" + f.getName()).toString());
			}

			banco.getWritableDatabase().insert(Tabela, null, cv);
			banco.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("BancoService", e.toString());
		}
	}

	public void update(Context ctx, String Tabela, Object table) {
		try {
			Banco banco = new Banco(ctx);
			ContentValues cv = new ContentValues();
			Class<? extends Object> s = table.getClass();

			for (Field f : s.getDeclaredFields()) {
				cv.put(f.getName(), getValueAt(table, "get" + f.getName()).toString());
			}
			banco.getWritableDatabase().update(Tabela, cv, null, null);
			banco.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("BancoService", e.toString());
		}
	}*/

	public void insert(Context ctx, String Tabela, ContentValues cv)
	{
		try
		{
			Banco banco = new Banco(ctx);
			banco.getWritableDatabase().insert(Tabela, null, cv);
			banco.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.i("BancoService", e.toString());
		}
	}

	public void update(Context ctx, String Tabela, ContentValues cv) {
		try {
			Banco banco = new Banco(ctx);
			banco.getWritableDatabase().update(Tabela, cv, null, null);
			banco.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.i("BancoService", e.toString());
		}
	}

	public void delete(Context ctx, String Table) {
		try {
			Banco banco = new Banco(ctx);
			
			banco.getWritableDatabase().delete(Table, null, null);
			banco.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.i("BancoService", e.toString());
		}
	}

	/*private static Object getValueAt(Object table, String column) {
		try {
			Object obj = table;
			Class<?> classe = obj.getClass();

			for (Method method : classe.getDeclaredMethods()) {
				if (method.getName().toLowerCase().equals(column)) {
					return method.invoke(obj);
				}
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("BancoService", e.toString());
			return "Erro";
		}
	}*/
}
