package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.dao.Parto_Cria_Dao;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.view.Parto_Activity;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Parto_Cria_Model extends Banco_Service {

	private Banco banco;
	private SQLiteDatabase db;
	private Parto_Cria_Adapter pc_adapter;
	private Parto_Cria pc_tb;
	private Parto_Cria_Dao pc_dao;

	public Parto_Cria_Model(Context ctx) {
		pc_adapter = new Parto_Cria_Adapter();
	}
	
	
	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) throws Validator_Exception {
		pc_dao = new Parto_Cria_Dao(ctx);
		pc_tb = (Parto_Cria)table;

		try {
			if (pc_tb.getIdentificador().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Identificador não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (pc_tb.getSisbov() == null)
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Sisbov não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (pc_tb.getCodigo_cria().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Código da Cria não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (pc_tb.getGrupo_manejo().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Grupo de Manejo não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (pc_tb.getPasto().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Pasto não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (pc_tb.getPeso_cria().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
				Validator_Exception ve = new Validator_Exception("O campo Peso da Cria não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			if (Parto_Activity.FLAG_CODIGO_MATRIZ_DUPLICADO != true) {
				if (pc_dao.ifExistCodMatriz(pc_tb.getCod_matriz_invalido().toString())) {
					//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
					Validator_Exception ve = new Validator_Exception("Matriz com cria já cadastrada deseja cadastrar "
							+ "outra cria para essa Matriz?");
					ve.setException_code(Validator_Exception.MESSAGE_TYPE_QUESTION);
					ve.setException_args(new Object[]{"FLAG_CODIGO_MATRIZ_DUPLICADO"});
					throw ve;
				}
			}
		} catch (Validator_Exception e) {
			throw e;
		} catch (Exception e) {
			Log.i("PARTOCRIA", e.toString());
			e.printStackTrace();
		}
	}
	
	public void deletePartoCria(Context ctx, Long codigo){
		banco = new Banco(ctx);
		
		db = banco.getWritableDatabase();
		
		db.delete("Parto_Cria", "id_fk_parto=?", new String[]{codigo.toString()});
	}
	
	public Parto_Cria selectByCodigo(Context ctx, Integer codigo) {
		banco = new Banco(ctx);

		db = banco.getReadableDatabase();
		Cursor cursor = db.query("Parto_Cria", null, "id_auto=?",
				new String[] { codigo.toString() }, null, null, "id_auto");

		pc_tb = pc_adapter.PartoCriaCursor(cursor);

		return pc_tb;
	}
	
	@Override
	public List<Parto_Cria> selectAll(Context ctx, String Tabela, Object table) {
		pc_dao = new Parto_Cria_Dao(ctx);

		return pc_dao.selectAllPartosCria(ctx, Tabela, table);
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
