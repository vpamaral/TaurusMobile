package br.com.prodap.taurusmobile.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

	static final String DATABASE = "DBTaurusMobile";
	static final int VERSION = 1;

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
				+ "'data_nascimento'	varchar(45),"
				+ "'sexo'				varchar(10)"
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
				+ "'codigo_ferro_cria'		varchar(45),"
				+ "'data_identificacao'     varchar(45),"
				+ "'repasse'     			varchar(10),"
				+ "'sisbov'     	 		varchar(45),"
				+ "'raca_cria'     	 		varchar(45),"
				+ "'identificador'     	 	varchar(45),"
				+ "'grupo_manejo'     	 	varchar(45),"
				+ "'criterio'     	 		varchar(45),"
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
				+ "'valida_manejo'   		varchar(5),"
				+ "'valida_cod_alternativo'	varchar(5),"
				+ "'ultima_atualizacao'		varchar(15)"
				+");";

		String sql_pasto = "CREATE TABLE 'Pasto' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'nome'        			varchar(100)"
				+");";

		String sql_grupo_manejo = "CREATE TABLE 'Grupo_Manejo' ("
				+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'codigo'        			varchar(100)"
				+");";

		String sql_criterio = "CREATE TABLE 'Criterio' ("
				+ "'id_auto'	    	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "'criterio'      		varchar(100),"
				+ "'sexo'      			varchar(10)"
				+");";

		db.execSQL(sql_animal);
		db.execSQL(sql_parto);
		db.execSQL(sql_parto_cria);
		db.execSQL(sql_configuracao);
		db.execSQL(sql_pasto);
		db.execSQL(sql_grupo_manejo);
		db.execSQL(sql_criterio);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		/*String sql = "";
		switch (oldVersion) {
			case 1:
				sql  = " ALTER TABLE Parto_Cria ADD COLUMN codigo_ferro_cria VARCHAR(45) ";

				sql += " ALTER TABLE Configuracao ADD COLUMN valida_cod_alternativo VARCHAR(5) ";

				sql += " ALTER TABLE Animal ADD COLUMN sexo VARCHAR(10) ";

				sql += " CREATE TABLE 'Criterio' ("
						+ "'id_auto'	    		INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
						+ "'criterio'        			varchar(100)"
						+"); ";
				db.execSQL(sql); // indo para versao 2
		}*/
	}
}