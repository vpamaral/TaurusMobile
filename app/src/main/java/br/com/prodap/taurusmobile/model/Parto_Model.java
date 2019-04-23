package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.dao.Parto_Dao;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Parto_Model extends Banco_Service
{
	private Banco banco;
	private Parto_Adapter parto_adapter;
	private SQLiteDatabase db;
	private Parto p_tb;
	private Parto_Dao p_dao;
	private Date data_parto = null;

	public Parto_Model(Context ctx) {
		parto_adapter = new Parto_Adapter();
	}
	
	@Override
	public void validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) throws Validator_Exception {
		Date data = new Date();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		final Date data_atual = cal.getTime();

		p_dao = new Parto_Dao(ctx);
		p_tb = (Parto)table;

		try {
			if (p_tb.getData_parto().equals(""))
			{
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_NULO");
				Validator_Exception ve = new Validator_Exception("O campo Data do Parto não pode ser vazio!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}

			data_parto = dateFormat.parse(p_tb.getData_parto().toString());

			if (data_parto.after(data_atual)) {
				//ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_NULO");
				Validator_Exception ve = new Validator_Exception("A Data do Parto não pode ser maior que a Data de Identificação!");
				ve.setException_code(Validator_Exception.MESSAGE_TYPE_WARNING);
				ve.setException_args(new Object[] {});
				throw ve;
			}


//            if (p_dao.ifExistPastoUpdate(pasto.getPasto().toString(), pasto.getId_auto() > 0))
//            {
//                //ValidatorException ve = new ValidatorException(this.getClass().getName() + "." + "PASTO_DUPLICADO");
//                ValidatorException ve = new ValidatorException("O Nome do pasto não pode ser duplicado!");
//                ve.setException_code(ValidatorException.MESSAGE_TYPE_WARNING);
//                ve.setException_args(new Object[] {});
//                throw ve;
//            }
		} catch (Validator_Exception e) {
			throw e;
		} catch (Exception e) {
			Log.i("PASTO", e.toString());
			e.printStackTrace();
		}
	}

	public void deleteParto(Context ctx, Long codigo)
	{
		banco = new Banco(ctx);

		db = banco.getWritableDatabase();

		db.delete("Parto", "id_pk=?", new String[]{codigo.toString()});
	}

	@Override
	public List<Parto> selectAll(Context ctx, String Tabela, Object table)
	{
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto> listadd = new ArrayList<Parto>();
		String sql = "SELECT * FROM " + Tabela;

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = parto_adapter.PartoPreencheArrayCursor(c);

		banco.close();

		return listadd;
	}

	public List<Relatorio_Parto> selectRelatorioPartos(Context ctx, String Tabela, Object table)
	{
		p_dao = new Parto_Dao(ctx);

		return p_dao.selectRelatorioPartos(ctx, Tabela, table);
	}

	public void deletingLogic (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto SET sync_status ='1'");
		db.execSQL("UPDATE Parto_Cria SET sync_status ='1'");
	}

	public void recoverSentPartos (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto SET sync_status ='0'");
		db.execSQL("UPDATE Parto_Cria SET sync_status ='0'");
	}

	public void recoverDescarte (Context ctx) {
		banco = new Banco(ctx);
		db = banco.getWritableDatabase();
		db.execSQL("UPDATE Parto_Cria SET repasse = 'NAO' WHERE repasse = 'NÃO'");
	}

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String sendPartos(Context ctx, String data_atual)
	{
		p_dao = new Parto_Dao(ctx);

		return p_dao.sendPartos(ctx, data_atual);
	}
}
