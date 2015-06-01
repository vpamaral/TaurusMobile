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
				+ "'id_fk_cria'	INTEGER,"
				+ "'codigo'	varchar(45),"
				+ "'sisbov'	varchar(45),"
				+ "'identificador'	varchar(45),"
				+ "'codigo_ferro'	varchar(45),"
				+ "'data_nascimento'	varchar(45),"
				+ "'categoria'	varchar(45),"
				+ "'raca'	varchar(45),"
				+ "'peso_atual'	double(45),"
				+ "'grau_sangue'	varchar(45)"
				+ " );";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}