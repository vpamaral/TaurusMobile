package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Lista_Animais_Activity extends Activity
{
	private Animal_Model a_model;
	private Animal ani_tb;
	private ListView animal_list;
	private ArrayAdapter<Animal> adapter;
	private List<Animal> animais;
	private long quantdAnimais;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_animais);

		ani_tb 		= new Animal();
		a_model 	= new Animal_Model(getBaseContext());
		animal_list = (ListView) findViewById(R.id.lista_animais);

		this.AnimalList();
		this.consultarPorIdClick();
		this.consultarPorIdClickLongo();

		setTitle("Quantidade de Animais: " + quantdAnimais);
	}

	private void AnimalList()
	{
		animais 		= a_model.selectAll(this, "Animal", ani_tb);
		quantdAnimais 	= animais.size();
		adapter 		= new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);
	}

	/*
	 * Método executado quando algum item da lista é clicado
	 */
	private void consultarPorIdClick()
	{
		animal_list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
			{
				ani_tb = (Animal) adapter.getItemAtPosition(position);

				String msg = "Código: " + ani_tb.getCodigo()
						+ "\nData de Nascimento: " + ani_tb.getData_nascimento()
						+ "\nCódigo Alternativo: " + ani_tb.getCodigo_ferro()
						+ "\nIdentificador: " + ani_tb.getIdentificador();

				Mensagem_Util.addMsg(Message_Dialog.Yes, Lista_Animais_Activity.this, msg, "Animal", position);
			}
		});
	}
	
	/*
	 * Método executado quando algum item da lista tem um click longo
	 */
	private void consultarPorIdClickLongo()
	{
		animal_list.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id)
			{
				ani_tb = (Animal) adapter.getItemAtPosition(position);

				String msg = "Código: " + ani_tb.getCodigo() + "\nCódigo Ferro: "
						+ ani_tb.getCodigo_ferro() + "\nIdentificador: "
						+ ani_tb.getIdentificador();

				Mensagem_Util.addMsg(Message_Dialog.Yes, Lista_Animais_Activity.this, msg, "Animal", position);

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