package br.com.taurusmobile.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import br.com.taurusmobile.Annotation.AColumn;

public abstract class BancoService {

	public static void insert(String Tabela, Object table) {
		// criar conexao aqui

		Class s = table.getClass();
		int column = 0;

		String sql = "INSERT INTO" + Tabela + "(";

		for (Field f : s.getDeclaredFields()) {
			sql += f.getName() + ",";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql += ") VALUES (";

		for (Field f : s.getDeclaredFields()) {
			sql += "'" + getValueAt(table, column).toString() + "'" + ",";
			column++;
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";

		// conn.executaQuery();
	}

	private static Object getValueAt(Object table, int column) {

		try {
			Object obj = table;
			Class<?> classe = obj.getClass();

			for (Method method : classe.getDeclaredMethods()) {
				if (method.isAnnotationPresent(AColumn.class)) {
					AColumn annotation = method.getAnnotation(AColumn.class);
					if (annotation.position() == column) {
						return method.invoke(obj);
					}
				}
			}
			return "";
		} catch (Exception e) {
			return "Erro";
		}
	}

}
