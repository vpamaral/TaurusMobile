package br.com.prodap.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.prodap.taurusmobile.TB.Configuracoes;

public class ConfiguracoesAdapter {
	
	public ConfiguracoesAdapter(){
		
	}
	
	public Configuracoes configurarCursor(Cursor c) {
		Configuracoes config = new Configuracoes();
		
		while (c.moveToNext()) {
			//config.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			config.setTipo(c.getString(c.getColumnIndex("tipo")));
			config.setEndereco(c.getString(c.getColumnIndex("endereco")));
			//config.setAutorizacao(c.getString(c.getColumnIndex("autorizacao")));
		}
		return config;
	}

	public List<Configuracoes> configurarPreencheArrayCursor(Cursor c) {
		List<Configuracoes> listaConfiguracoes = new ArrayList<Configuracoes>();
		while (c.moveToNext()) {

			Configuracoes config = new Configuracoes();
			//config.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			config.setTipo(c.getString(c.getColumnIndex("tipo")));
			config.setEndereco(c.getString(c.getColumnIndex("endereco")));
			//config.setAutorizacao(c.getString(c.getColumnIndex("autorizacao")));

			listaConfiguracoes.add(config);
		}

		return listaConfiguracoes;
	}

	public ArrayList<Configuracoes> configurarPreencheArrayHelper(Configuracoes[] configurarArray) {
		ArrayList<Configuracoes> listaConfigurar = new ArrayList<Configuracoes>();
		for (int i = 0; i < configurarArray.length; i++) {

			Configuracoes config = new Configuracoes();
			//config.setId_pk(configurarArray[i].getId_pk());
			config.setTipo(configurarArray[i].getTipo());
			config.setEndereco(configurarArray[i].getEndereco());
			//config.setAutorizacao(configurarArray[i].getAutorizacao());
			
			listaConfigurar.add(config);
		}
		return listaConfigurar;
	}

	public Configuracoes configurarHelper(Configuracoes configurarTB) {
		Configuracoes config = new Configuracoes();
		//config.setId_pk(configurarTB.getId_pk());
		config.setTipo(configurarTB.getTipo());
		config.setEndereco(configurarTB.getEndereco());
		//config.setAutorizacao(configurarTB.getAutorizacao());
		
		return config;
	}

}
