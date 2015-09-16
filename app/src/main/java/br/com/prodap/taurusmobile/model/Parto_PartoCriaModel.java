package br.com.prodap.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.prodap.taurusmobile.TB.Parto_PartoCria;
import br.com.prodap.taurusmobile.adapter.Parto_PartoCriaAdapter;
import br.com.prodap.taurusmobile.service.Banco;
import br.com.prodap.taurusmobile.service.BancoService;

public class Parto_PartoCriaModel extends BancoService {

	private Banco banco;
	private SQLiteDatabase db;
	private Parto_PartoCriaAdapter partoCria_adapter;

	public Parto_PartoCriaModel(Context ctx) {
		partoCria_adapter = new Parto_PartoCriaAdapter();
	}

	@Override
	public boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE) {
		return false;
	}

	public void deleteByCria(Context ctx, String codigo_cria){
		banco = new Banco(ctx);

		db = banco.getWritableDatabase();

		db.delete("Parto_Cria", "codigo_cria=?", new String[]{codigo_cria});
	}

	@Override
	public List<Parto_PartoCria> selectAll(Context ctx, String Tabela,
			Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto_PartoCria> listadd = new ArrayList<Parto_PartoCria>();
		String sql = "SELECT DISTINCT p.*, c.* FROM "+Tabela+" a INNER JOIN Parto p ON a.id_pk = p.id_fk_animal INNER JOIN Parto_Cria c ON a.id_pk = c.id_fk_animal_mae GROUP BY c.codigo_cria";
		

		Cursor c = banco.getWritableDatabase().rawQuery(sql, null);

		listadd = partoCria_adapter.P_PartoCriaPreencheArrayCursor(c);

		banco.close();
		return listadd;
	}
	
	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		return null;
	}
}