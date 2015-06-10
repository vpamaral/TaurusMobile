package br.com.taurusmobile.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.adapter.PartoCriaAdapter;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class Parto_CriaModel extends BancoService {

	private Banco banco;
	PartoCriaAdapter partoCria_adapter;

	public Parto_CriaModel(Context ctx) {
		partoCria_adapter = new PartoCriaAdapter();
	}
	
	
	@Override
	public boolean validate(Context ctx, String Tabela, Object table,
			int VALIDATION_TYPE) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Parto_Cria> selectAll(Context ctx, String Tabela, Object table) {
		Banco banco = new Banco(ctx);

		Class classe = table.getClass();
		List<Parto_Cria> listadd = new ArrayList<Parto_Cria>();
		String sql = "SELECT * FROM " + Tabela;

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
