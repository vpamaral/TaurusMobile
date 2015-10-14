package br.com.prodap.taurusmobile.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.adapter.PartoCriaAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;

public class Parto_CriaModel extends BancoService {

	private Banco banco;
	private SQLiteDatabase db;
	private PartoCriaAdapter partoCria_adapter;
	private Parto_Cria parto_cria;

	public Parto_CriaModel(Context ctx) {
		partoCria_adapter = new PartoCriaAdapter();
	}
	
	
	@Override
	public boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE) {
		
		return false;
	}
	
	public void removerByMae(Context ctx, Long codigo){
		banco = new Banco(ctx);
		
		db = banco.getWritableDatabase();
		
		db.delete("Parto_Cria", "id_fk_animal_mae=?", new String[]{codigo.toString()});
	}
	
	public Parto_Cria selectByCodigo(Context ctx, Integer codigo) {
		banco = new Banco(ctx);

		db = banco.getReadableDatabase();
		Cursor cursor = db.query("Parto_Cria", null, "id_auto=?",
				new String[] { codigo.toString() }, null, null, "id_auto");

		parto_cria = partoCria_adapter.PartoCriaCursor(cursor);

		return parto_cria;
	}
	
	@Override
	public List<Parto_Cria> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto_Cria> listadd = new ArrayList<Parto_Cria>();
		String sql = String.format("SELECT * FROM %s WHERE sync_status = 0  ORDER BY codigo_cria ", Tabela);

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = partoCria_adapter.PartoCriaPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
