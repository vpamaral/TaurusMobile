package br.com.taurusmobile.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

public abstract class BancoService {

	public abstract boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE);

	public abstract <T> List<T> selectAll(Context ctx, String Tabela,
			Object table);

	public abstract <T> T selectID(Context ctx, String Tabela, Object table,
			long id);

	public void insert(Context ctx, String Tabela, Object table) {

		try {
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
		}
	}

	public void Update(Context ctx, String Tabela, Object table) {

	}

	public void Delete(Context ctx, String Tablela) {
		try {
			Banco banco = new Banco(ctx);
			
			banco.getWritableDatabase().delete(Tablela, null, null);

			/*
			 * Toast toast = Toast.makeText(ctx, Tabela +
			 * " cadastrado com sucesso!!", 5); toast.show();
			 */
			banco.close();

		} catch (Exception e) {
			/*
			 * Toast toast = Toast.makeText(ctx,
			 * "Erro ao salvar informações no banco!", 5); toast.show();
			 */
		}
	}

	private static Object getValueAt(Object table, String column) {

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
			return "Erro";
		}
	}
}
