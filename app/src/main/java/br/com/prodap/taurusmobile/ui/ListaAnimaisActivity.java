package br.com.prodap.taurusmobile.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class ListaAnimaisActivity extends Activity {

	private AnimalModel ani_model;
	private Animal ani_tb;
	private ListView list;
	private AnimalAdapter animal_adapter;
	private List<Animal> animais_list;
	private Constantes constantes;
	private long quantdAnimais;
	private TextView txtContador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_animais);

		ani_tb = new Animal();
		ani_model = new AnimalModel(getBaseContext());
		list = (ListView) findViewById(R.id.lista_animais);
		txtContador = (TextView) findViewById(R.id.lblSisbov);

		constantes = new Constantes();
		this.AnimalList();
		this.consultarPorIdClick();
		this.consultarPorIdClickLongo();

		setTitle("Quantidade de Animais: " + quantdAnimais);
		//txtContador.setText("Quantidade de Animais: " + Constantes.TOTAL_ANIMAIS +  "\n" + "Código" " - " );
	}

	private void AnimalList(){
		animais_list = ani_model.selectAll(getBaseContext(), "Animal",
				ani_tb);

		quantdAnimais = animais_list.size();

		animal_adapter = new AnimalAdapter(animais_list, this);

		list.setAdapter(animal_adapter);
	}

	/*
	 * Método executado quando algum item da lista é clicado
	 */
	private void consultarPorIdClick(){
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				ani_tb = (Animal) animal_adapter.getItem(position);

				String msg = "Código: " + ani_tb.getCodigo() + "\nSisbov: "
						+ ani_tb.getSisbov() + "\nIdentificador: "
						+ ani_tb.getIdentificador() + "\nPeso Atual: "
						+ ani_tb.getPeso_atual();

				MensagemUtil.addMsg(MessageDialog.Yes,
						ListaAnimaisActivity.this, msg, "Animal", position);
			}
		});
	}
	
	/*
	 * Método executado quando algum item da lista tem um click longo
	 */
	private void consultarPorIdClickLongo(){
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
										   int position, long id) {
				ani_tb = (Animal) animal_adapter.getItem(position);

				String msg = "Código: " + ani_tb.getCodigo() + "\nSisbov: "
						+ ani_tb.getSisbov() + "\nIdentificador: "
						+ ani_tb.getIdentificador() + "\nPeso Atual: "
						+ ani_tb.getPeso_atual();
				MensagemUtil.addMsg(MessageDialog.Yes, ListaAnimaisActivity.this, msg, "Animal", position);

				return true;
			}
		});
	}
	/*
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
	}*/
}
