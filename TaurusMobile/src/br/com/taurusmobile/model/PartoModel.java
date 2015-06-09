package br.com.taurusmobile.model;

import java.util.List;

import android.content.Context;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.adapter.PartoAdapter;
import br.com.taurusmobile.service.Banco;
import br.com.taurusmobile.service.BancoService;

public class PartoModel extends BancoService {

	private Banco banco;
	PartoAdapter parto_adapter;

	public PartoModel(Context ctx) {
		parto_adapter = new PartoAdapter();
	}
	
	@Override
	public boolean validate(Context ctx, String Tabela, Object table,int VALIDATION_TYPE) {
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
