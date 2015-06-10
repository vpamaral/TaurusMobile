package br.com.taurusmobile.model;

import java.util.List;

import android.content.Context;
import br.com.taurusmobile.adapter.*;
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
	public <T> List<T> selectAll(Context ctx, String Tabela, Object table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T selectID(Context ctx, String Tabela, Object table, long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
