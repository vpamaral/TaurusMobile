package br.com.prodap.taurusmobile.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

	static final String DATABASE = "BDTaurus";
	static final int VERSION = 2;

	public Banco(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql_animal = "CREATE TABLE 'Animal' ("
				+ "'id_auto'			INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_pk'				INTEGER,"
				+ "'codigo'				varchar(45),"
				+ "'codigo_ferro'		varchar(45),"
				+ "'identificador'		varchar(45),"
				+ "'data_nascimento'	varchar(45)"
				+ " );";
				/*+ "'sisbov'			varchar(45),"
				+ "'id_fk_cria'			INTEGER,"
				+ "'categoria'			varchar(45),"
				+ "'raca'				varchar(45),"
				+ "'peso_atual'			double(45),"
				+ "'raca_reprod'		varchar(45)"*/
		
		String sql_parto = "CREATE TABLE 'Parto' ("
				+ "'id_auto'	       	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal'   	INTEGER,"
				+ "'id_pk'    			INTEGER,"
				+ "'sync_status'     	TINYINT(1),"
				+ "'data_parto'     	varchar(45),"
				+ "'sexo_parto'     	varchar(45),"
				+ "'perda_gestacao' 	varchar(45)"
				+");";

		String sql_parto_cria = "CREATE TABLE 'Parto_Cria' ("
				+ "'id_auto'	         	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal_mae' 		INTEGER,"
				+ "'id_fk_parto' 			INTEGER,"
				+ "'data_identificacao'     varchar(45),"
				+ "'repasse'     			varchar(10),"
				+ "'sisbov'     	 		varchar(45),"
				+ "'raca_cria'     	 		varchar(45),"
				+ "'identificador'     	 	varchar(45),"
				+ "'grupo_manejo'     	 	varchar(45),"
				+ "'sync_status'     		TINYINT(1),"
				+ "'peso_cria'        		varchar(45),"
				+ "'codigo_cria'     	 	varchar(45),"
				+ "'sexo'            	 	varchar(45),"
				+ "'tipo_parto'     	 	varchar(45),"
				+ "'cod_matriz_invalido'	varchar(45),"
				+ "'pasto'					varchar(45)"
				+");";

		String sql_configuracao = "CREATE TABLE 'Configuracao' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'tipo'        			varchar(45),"
				+ "'endereco'      			varchar(200),"
				+ "'valida_identificador'   varchar(5),"
				+ "'valida_sisbov'   		varchar(5),"
				+ "'valida_manejo'   		varchar(5)"
				+");";

		String sql_pasto = "CREATE TABLE 'Pasto' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'nome'        			varchar(100)"
				+");";

		String sql_grupo_manejo = "CREATE TABLE 'Grupo_Manejo' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'codigo'        			varchar(100)"
				+");";

		db.execSQL(sql_animal);
		db.execSQL(sql_parto);
		db.execSQL(sql_parto_cria);
		db.execSQL(sql_configuracao);
		db.execSQL(sql_pasto);
		db.execSQL(sql_grupo_manejo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql_animal = "CREATE TABLE IF NOT EXISTS 'Animal' ("
				+ "'id_auto'			INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_pk'				INTEGER,"
				+ "'codigo'				varchar(45),"
				+ "'codigo_ferro'		varchar(45),"
				+ "'identificador'		varchar(45),"
				+ "'data_nascimento'	varchar(45)"
				+ " );";
				/*+ "'sisbov'			varchar(45),"
				+ "'id_fk_cria'			INTEGER,"
				+ "'categoria'			varchar(45),"
				+ "'raca'				varchar(45),"
				+ "'peso_atual'			double(45),"
				+ "'raca_reprod'		varchar(45)"*/

		String sql_drop_parto = "DROP TABLE Parto";

		String sql_parto = "CREATE TABLE IF NOT EXISTS 'Parto' ("
				+ "'id_auto'	       	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal'   	INTEGER,"
				+ "'id_pk'    			INTEGER,"
				+ "'sync_status'     	TINYINT(1),"
				+ "'data_parto'     	varchar(45),"
				+ "'sexo_parto'     	varchar(45),"
				+ "'perda_gestacao' 	varchar(45)"
				+");";

		String sql_drop_parto_cria = "DROP TABLE Parto_Cria";

		String sql_parto_cria = "CREATE TABLE IF NOT EXISTS 'Parto_Cria' ("
				+ "'id_auto'	         	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'id_fk_animal_mae' 		INTEGER,"
				+ "'id_fk_parto' 			INTEGER,"
				+ "'data_identificacao'     varchar(45),"
				+ "'repasse'     			varchar(10),"
				+ "'sisbov'     	 		varchar(45),"
				+ "'raca_cria'     	 		varchar(45),"
				+ "'identificador'     	 	varchar(45),"
				+ "'grupo_manejo'     	 	varchar(45),"
				+ "'sync_status'     		TINYINT(1),"
				+ "'peso_cria'        		varchar(45),"
				+ "'codigo_cria'     	 	varchar(45),"
				+ "'sexo'            	 	varchar(45),"
				+ "'tipo_parto'     	 	varchar(45),"
				+ "'cod_matriz_invalido'	varchar(45),"
				+ "'pasto'					varchar(45)"
				+");";

		String sql_configuracao = "CREATE TABLE IF NOT EXISTS 'Configuracao' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'tipo'        			varchar(45),"
				+ "'endereco'      			varchar(200),"
				+ "'valida_identificador'   varchar(5),"
				+ "'valida_sisbov'   		varchar(5),"
				+ "'valida_manejo'   		varchar(5)"
				+");";

		String sql_drop_pasto = "DROP TABLE Pasto";

		String sql_pasto = "CREATE TABLE IF NOT EXISTS 'Pasto' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'nome'        			varchar(100)"
				+");";

		String sql_grupo_manejo = "CREATE TABLE IF NOT EXISTS 'Grupo_Manejo' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'codigo'        			varchar(100)"
				+");";

		db.execSQL(sql_animal);
		db.execSQL(sql_drop_parto);
		db.execSQL(sql_parto);
		db.execSQL(sql_drop_parto_cria);
		db.execSQL(sql_parto_cria);
		db.execSQL(sql_configuracao);
		db.execSQL(sql_drop_pasto);
		db.execSQL(sql_pasto);
		db.execSQL(sql_grupo_manejo);
	}
}