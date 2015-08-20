package br.com.prodap.taurusmobile.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

	static final String DATABASE = "BDTaurus";
	static final int VERSION = 1;

	public Banco(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql_animal = "CREATE TABLE 'Animal' ("
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
				+ "'raca_reprod'	varchar(45)"
				+ " );";
		
		String sql_parto = "CREATE TABLE 'Parto' ("
				+ "'id_auto'	       	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal'   	INTEGER,"
				+ "'fgStatus'     		TINYINT DEFAULT 0,"
				+ "'data_parto'     	varchar(45),"
				+ "'sexo_parto'     	varchar(45),"
				+ "'perda_gestacao' 	varchar(45)"
				+");";

		String sql_parto_cria = "CREATE TABLE 'Parto_Cria' ("
				+ "'id_auto'	         	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal_mae' 		INTEGER,"
				+ "'data_identificacao'     varchar(45),"
				+ "'repasse'     			varchar(10),"
				+ "'sisbov'     	 		varchar(45),"
				+ "'raca_cria'     	 		varchar(45),"
				+ "'identificador'     	 	varchar(45),"
				+ "'grupo_manejo'     	 	varchar(45),"
				+ "'fgStatus'     			TINYINT DEFAULT 0,"
				+ "'peso_cria'        		varchar(45),"
				+ "'codigo_cria'     	 	varchar(45),"
				+ "'sexo'            	 	varchar(45),"
				+ "'tipo_parto'     	 	varchar(45)"
				+");";
		
		String sql_configuracao = "CREATE TABLE 'Configuracao' ("
				+ "'id_auto'	    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				//+ "'autorizacao'  varchar(45),"
				+ "'tipo'        	varchar(45),"
				+ "'validaId'       varchar(45),"
				+ "'validaManejo'   varchar(45),"
				+ "'endereco'      	varchar(200)"
				+");";

		db.execSQL(sql_animal);
		db.execSQL(sql_parto);
		db.execSQL(sql_parto_cria);
		db.execSQL(sql_configuracao);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}