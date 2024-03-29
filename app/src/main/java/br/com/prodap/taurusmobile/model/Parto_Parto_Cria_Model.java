package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Parto_Parto_Cria;

public class Parto_Parto_Cria_Model extends Banco_Service {

	private Banco banco;
	private SQLiteDatabase db;
	private Parto_Parto_Cria_Adapter partoCria_adapter;

	public Parto_Parto_Cria_Model(Context ctx) {
		partoCria_adapter = new Parto_Parto_Cria_Adapter();
	}

	@Override
	public void validate(Context ctx, String Tabela, Object table, int VALIDATION_TYPE) {

	}

	public void deleteByCria(Context ctx, String codigo_cria){
		banco = new Banco(ctx);

		db = banco.getWritableDatabase();

		db.delete("Parto_Cria", "codigo_cria=?", new String[]{codigo_cria});
	}

	@Override
	public List<Parto_Parto_Cria> selectAll(Context ctx, String Tabela, Object table)
	{
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto_Parto_Cria> listadd = new ArrayList<Parto_Parto_Cria>();
		String sql = String.format(
										"SELECT p.*, pc.* "
										+"FROM %s pc "
										+"INNER JOIN Parto p ON p.id_pk = pc.id_fk_parto "
										+"WHERE "
										+"    p.sync_status   = 0 "
										+"AND pc.sync_status  = 0 "
										, Tabela
		                          );

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = partoCria_adapter.P_PartoCriaPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}

	public void delete(Parto_Parto_Cria parto_partoCria) {
		String[] args = {Long.valueOf(parto_partoCria.getCodigo_cria()).toString()};
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		return null;
	}
}
