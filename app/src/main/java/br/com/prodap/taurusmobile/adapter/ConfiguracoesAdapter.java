package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Configuracoes;

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

	@NonNull
	public ContentValues getDadosConfig(Configuracoes c_tb) {
		ContentValues c_dados = new ContentValues();
		c_dados.put("tipo", c_tb.getTipo());
		c_dados.put("endereco", c_tb.getEndereco());
		c_dados.put("valida_sisbov", c_tb.getValida_sisbov());
		c_dados.put("valida_identificador", c_tb.getValida_identificador());
		c_dados.put("valida_manejo", c_tb.getValida_manejo());

		return c_dados;
	}

	public List<Configuracoes> arrayConfiguracoes(Cursor c) {
		List<Configuracoes> c_list = new ArrayList<Configuracoes>();
		while (c.moveToNext()) {
			Configuracoes c_tb = new Configuracoes();
			c_tb.setId_auto(c.getLong(c.getColumnIndex("id_auto")));
			c_tb.setTipo(c.getString(c.getColumnIndex("tipo")));
			c_tb.setEndereco(c.getString(c.getColumnIndex("endereco")));
			c_tb.setValida_identificador(c.getString(c.getColumnIndex("valida_identificador")));
			c_tb.setValida_manejo(c.getString(c.getColumnIndex("valida_manejo")));
			c_tb.setValida_sisbov(c.getString(c.getColumnIndex("valida_sisbov")));

			c_list.add(c_tb);
		}
		return c_list;
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
