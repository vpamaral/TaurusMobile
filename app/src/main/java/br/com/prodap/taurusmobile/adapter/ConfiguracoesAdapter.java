package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Configuracoes;

public class ConfiguracoesAdapter extends BaseAdapter {
	private List<Configuracoes> configuracoesList;
	private Activity activity;

	public ConfiguracoesAdapter() {

	}

	public ConfiguracoesAdapter(List<Configuracoes> configuracoesList, Activity activity) {
		this.configuracoesList = configuracoesList;
		this.activity = activity;
	}
	
	public Configuracoes configurarCursor(Cursor c) {
		Configuracoes config = new Configuracoes();
		
		while (c.moveToNext()) {
			config.setId_auto(c.getLong(c.getColumnIndex("id_auto")));
			config.setTipo(c.getString(c.getColumnIndex("tipo")));
			config.setEndereco(c.getString(c.getColumnIndex("endereco")));
			config.setValida_identificador(c.getString(c.getColumnIndex("valida_identificador")));
			config.setValida_manejo(c.getString(c.getColumnIndex("valida_manejo")));
			config.setValida_sisbov(c.getString(c.getColumnIndex("valida_sisbov")));
		}
		return config;
	}

	public List<Configuracoes> configurarPreencheArrayCursor(Cursor c) {
		List<Configuracoes> listaConfiguracoes = new ArrayList<Configuracoes>();
		while (c.moveToNext()) {
			Configuracoes config = new Configuracoes();
			config.setId_auto(c.getLong(c.getColumnIndex("id_auto")));
			config.setTipo(c.getString(c.getColumnIndex("tipo")));
			config.setEndereco(c.getString(c.getColumnIndex("endereco")));
			config.setValida_identificador(c.getString(c.getColumnIndex("valida_identificador")));
			config.setValida_manejo(c.getString(c.getColumnIndex("valida_manejo")));
			config.setValida_sisbov(c.getString(c.getColumnIndex("valida_sisbov")));

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
			config.setValida_identificador(configurarArray[i].getValida_identificador());
			config.setValida_manejo(configurarArray[i].getValida_manejo());
			config.setValida_sisbov(configurarArray[i].getValida_sisbov());
			
			listaConfigurar.add(config);
		}
		return listaConfigurar;
	}

	public Configuracoes configurarHelper(Configuracoes configurarTB) {
		Configuracoes config = new Configuracoes();

		config.setTipo(configurarTB.getTipo());
		config.setEndereco(configurarTB.getEndereco());
		config.setValida_identificador(configurarTB.getValida_identificador());
		config.setValida_manejo(configurarTB.getValida_manejo());
		config.setValida_sisbov(configurarTB.getValida_sisbov());
		
		return config;
	}

	@Override
	public int getCount() {
		return configuracoesList.size();
	}

	@Override
	public Object getItem(int position) {
		return configuracoesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return configuracoesList.get(position).getId_auto();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}
