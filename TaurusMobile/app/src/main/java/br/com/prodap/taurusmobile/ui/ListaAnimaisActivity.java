package br.com.prodap.taurusmobile.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class ListaAnimaisActivity extends Activity {

	AnimalModel ani_model;
	Animal ani_tb;
	private ListView lista;
	private List<Animal> listaani;
	private List<String> animais;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_animais);
		
		animais = new ArrayList<String>();
		ani_tb = new Animal();
		ani_model = new AnimalModel(getBaseContext());
		lista = (ListView) findViewById(R.id.lista_animais);
		
		this.listarAnimais();
		this.consultarPorIdClick();
		this.consultarPorIdClickLongo();
	}
	
	private void listarAnimais(){
		listaani = ani_model.selectAll(this, "Animal",
				ani_tb);
		for (Animal a : listaani) {
			animais.add(a.getCodigo());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, animais);
		
		lista.setAdapter(adapter);
	}
	
	/**
	 * Método executado quando algum item da lista é clicado
	 */
	private void consultarPorIdClick(){
				lista.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int position, long id) {
						ani_tb = ani_model.selectByCodigo(ListaAnimaisActivity.this,
								(int) position + 1);

						String msg = "Código: " + ani_tb.getCodigo() + "\nSisbov: "
								+ ani_tb.getSisbov() + "\nIdentificador: "
								+ ani_tb.getIdentificador() + "\nPeso Atual: "
								+ ani_tb.getPeso_atual();

						MensagemUtil.addMsg(MessageDialog.Yes,
								ListaAnimaisActivity.this, msg, "Animal", position);
					}
				});
	}
	
	/**
	 * Método executado quando algum item da lista tem um click longo
	 */
	private void consultarPorIdClickLongo(){
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				ani_tb = ani_model.selectByCodigo(ListaAnimaisActivity.this,
						(int) position + 1);

				String msg = "Código: " + ani_tb.getCodigo() + "\nSisbov: "
						+ ani_tb.getSisbov() + "\nIdentificador: "
						+ ani_tb.getIdentificador() + "\nPeso Atual: "
						+ ani_tb.getPeso_atual();
				MensagemUtil.addMsg(MessageDialog.Yes, ListaAnimaisActivity.this, msg, "Animal", position); 
						
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_animais, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
