package br.com.taurusmobile.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.OpenableColumns;

public class Banco extends SQLiteOpenHelper {

	static final String DATABASE = "BDTaurus";
	static final int VERSION = 1;
	private String scriptSQLCreate;
	private String scriptSQLDelete;
	
	public Banco(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		
		String sql = "CREATE TABLE 'Animal' ("
				+ "'id_auto'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_pk'	INTEGER,"
				+ "'codigo'	INT,"
				+ "'sibov'	TEXT,"
				+ "'data_nascimento'	TEXT,"
				+ "'sexo'	TEXT,"
				+ "'id_fk_gi_raca'	INTEGER,"
				+ "'id_fk_gi_categoria'	INTEGER,"
				+ "'identificador'	TEXT,"
				+ "'peso_atual'	NUMERIC,"
				+ "'codigo_ferro'	TEXT"
				+ " );";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}