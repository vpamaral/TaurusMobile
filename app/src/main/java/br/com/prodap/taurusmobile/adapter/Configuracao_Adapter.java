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

import br.com.prodap.taurusmobile.tb.Configuracao;

public class Configuracao_Adapter extends BaseAdapter {
	private List<Configuracao> configuracaoList;
	private Activity activity;

	public Configuracao_Adapter() {

	}

	public Configuracao_Adapter(List<Configuracao> configuracaoList, Activity activity) {
		this.configuracaoList = configuracaoList;
		this.activity = activity;
	}
	
	public Configuracao configurarCursor(Cursor c) {
		Configuracao config = new Configuracao();
		
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
	public ContentValues getDadosConfig(Configuracao c_tb) {
		ContentValues c_dados = new ContentValues();
		c_dados.put("tipo", c_tb.getTipo());
		c_dados.put("endereco", c_tb.getEndereco());
		c_dados.put("valida_sisbov", c_tb.getValida_sisbov());
		c_dados.put("valida_identificador", c_tb.getValida_identificador());
		c_dados.put("valida_manejo", c_tb.getValida_manejo());

		return c_dados;
	}

	public List<Configuracao> arrayConfiguracoes(Cursor c) {
		List<Configuracao> c_list = new ArrayList<Configuracao>();
		while (c.moveToNext()) {
			Configuracao c_tb = new Configuracao();
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

	public ArrayList<Configuracao> configurarPreencheArrayHelper(Configuracao[] configurarArray) {
		ArrayList<Configuracao> listaConfigurar = new ArrayList<Configuracao>();
		for (int i = 0; i < configurarArray.length; i++) {

			Configuracao config = new Configuracao();
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

	public Configuracao configurarHelper(Configuracao configurarTB) {
		Configuracao config = new Configuracao();

		config.setId_auto(configurarTB.getId_auto());
		config.setTipo(configurarTB.getTipo());
		config.setEndereco(configurarTB.getEndereco());
		config.setValida_identificador(configurarTB.getValida_identificador());
		config.setValida_manejo(configurarTB.getValida_manejo());
		config.setValida_sisbov(configurarTB.getValida_sisbov());
		
		return config;
	}

	@Override
	public int getCount() {
		return configuracaoList.size();
	}

	@Override
	public Object getItem(int position) {
		return configuracaoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return configuracaoList.get(position).getId_auto();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}
