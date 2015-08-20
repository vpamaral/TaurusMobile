package br.com.prodap.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Parto;
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
	
	/*public void removerByCodigo(Context ctx, Parto_Cria p_cria_tb){
		banco = new Banco(ctx);
		
		db = banco.getWritableDatabase();
		
		db.delete("Parto_Cria", "id_fk_animal_mae=?", new String[]{p_cria_tb.toString()});
	}*/
	
	public Parto_Cria selectByCodigo(Context ctx, Integer codigo) {
		banco = new Banco(ctx);

		db = banco.getReadableDatabase();
		Cursor cursor = db.query("Parto_Cria", null, "id_auto=?",
				new String[] { codigo.toString() }, null, null, "id_auto");

		parto_cria = partoCria_adapter.PartoCriaCursor(cursor);

		return parto_cria;
	}
	
	/*public void removerForCodigo(Context ctx, Integer codigo){
		banco = new Banco(ctx);
		
		db = banco.getWritableDatabase();
		
		try{
			String query = "SELECT * FROM Parto_Cria WHERE codigo_cria ='" + codigo
					+ "';";
			db.execSQL(query);
			
		}catch(Exception ex){
			Log.e("AnimalModel", ex.toString());
		}
	}*/
	
	/*public Animal selectByCodigo(Context ctx, String codigo) {
		Cursor cursor = null;
		Animal AnimalLinha = new Animal();
		Banco banco = new Banco(ctx);

		try {
			String query = "SELECT * FROM Animal WHERE codigo ='" + codigo
					+ "';";

			List<Animal> listadd = new ArrayList<Animal>();

			cursor = banco.getWritableDatabase().rawQuery(query, null);

			listadd = ani_adapter.AnimalPreencheArrayCursor(cursor);

			AnimalLinha = listadd.get(0);

		} catch (Exception e) {
			Log.e("AnimalModel", e.toString());
		} finally {
			if (cursor != null) {
				if (!cursor.isClosed()) {
					cursor.close();
				}
			}
		}
		return AnimalLinha;
	}*/
	
	@Override
	public List<Parto_Cria> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto_Cria> listadd = new ArrayList<Parto_Cria>();
		String sql = String.format("SELECT * FROM %s ", Tabela);

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
