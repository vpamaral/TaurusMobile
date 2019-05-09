package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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
	private Button btn_select;
	private int sexo_selected;
	private Spinner spinSexo;

	private static final String[] SEXO = new String[]{"TODOS", "FÊMEA", "MACHO"};

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

		spinSexo = (Spinner) findViewById(R.id.spnSexoFiltro);
		ArrayAdapter<String> adpSexo = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, SEXO);
		adpSexo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinSexo.setAdapter(adpSexo);

	}

	private void AnimalList()
	{
		animais 		= a_model.selectAll(this, "Animal", ani_tb);
		quantdAnimais 	= animais.size();
		adapter         = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);
		setTitle("Quantidade de Animais: " + quantdAnimais);
		sexo_selected = -1;
	}

	/*public void btn_macho_Click (View view)
	{
	    btn_select = (Button) findViewById( R.id.btn_femea);
		btn_select.setTextColor(Color.WHITE);
		//btn_select.setBackgroundColor(Color.parseColor("#f0ad4e"));
		btn_select.setBackgroundColor(Color.GRAY);

		if(sexo_selected == 1) {
			((Button)view).setTextColor(Color.WHITE);
			((Button)view).setBackgroundColor(Color.parseColor("#5cb85c"));
			btn_select.setBackgroundColor(Color.parseColor("#f0ad4e"));
			AnimalList();
			return;
		}
		else {
			//((Button) view).setTextColor(Color.BLACK);
			((Button)view).setBackgroundColor(Color.parseColor("#5cb85c"));
		}

		animais 		= a_model.selectAllbySexo(this, "Animal", ani_tb, "MA");
		quantdAnimais 	= animais.size();
		adapter         = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);

        setTitle("Quantidade de Machos: " + quantdAnimais);
		sexo_selected = 1;
	}

	public void btn_femea_Click (View view)
	{
		btn_select = (Button) findViewById( R.id.btn_macho);
		btn_select.setTextColor(Color.WHITE);
		//btn_select.setBackgroundColor(Color.parseColor("#5cb85c"));
		btn_select.setBackgroundColor(Color.GRAY);

		if(sexo_selected == 0) {
			((Button)view).setTextColor(Color.WHITE);
			((Button)view).setBackgroundColor(Color.parseColor("#f0ad4e"));
			btn_select.setBackgroundColor(Color.parseColor("#5cb85c"));
			AnimalList();
			return;
		}
		else {
			//((Button) view).setTextColor(Color.BLACK);
			((Button)view).setBackgroundColor(Color.parseColor("#f0ad4e"));
		}

		animais 		= a_model.selectAllbySexo(this, "Animal", ani_tb, "FE");
		quantdAnimais 	= animais.size();
		adapter         = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);

        setTitle("Quantidade de Fêmeas: " + quantdAnimais);
		sexo_selected = 0;
	}*/

	/*
	 * Método executado quando algum item da lista é clicado
	 */

	public void btn_filtro_Click (View view)
	{
		Spinner spnSexo = (Spinner) findViewById(R.id.spnSexoFiltro);
		String sexo_selecionado = spnSexo.getSelectedItem().toString();


		if(sexo_selecionado == "TODOS") {
			AnimalList();
			return;
		}
		else
			animais 		= a_model.selectAllbySexo(this, "Animal", ani_tb, sexo_selecionado == "MACHO" ? "MA" : "FE");

		quantdAnimais 	= animais.size();
		adapter         = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);

		setTitle("Quantidade de " + (sexo_selecionado == "MACHO" ? "Machos: " : "Fêmeas: ") + quantdAnimais);
	}

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
						+ "\nIdentificador: " + ani_tb.getIdentificador()
						+ "\nSexo: " + ani_tb.getSexo();

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

				String msg = "Código: " + ani_tb.getCodigo() + "\nCódigo Alternativo: "
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
