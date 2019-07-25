package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.List;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import android.widget.AdapterView.OnItemSelectedListener;

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
	private Spinner spinID;

	private static final String[] SEXO = new String[]{"TODOS", "FÊMEA", "MACHO"};
	private static final String[] IDENTIFICADORES = new String[]{"Cód. Alternativo", "Cód. de Manejo", "Identificador"};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_animais);

		ani_tb 		= new Animal();
		a_model 	= new Animal_Model(getBaseContext());
		animal_list = (ListView) findViewById(R.id.lista_animais);

		//this.AnimalList();
		this.consultarPorIdClick();
		this.consultarPorIdClickLongo();

		spinSexo = (Spinner) findViewById(R.id.spnSexoFiltro);
		ArrayAdapter<String> adpSexo = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, SEXO);
		adpSexo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinSexo.setAdapter(adpSexo);

		spinID = (Spinner) findViewById(R.id.spnIdentificador);
		ArrayAdapter<String> adpID = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, IDENTIFICADORES);
		adpID.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinID.setAdapter(adpID);
		selectedItem();

		search();

		configuraSearch(false);
	}

	private void selectedItem()
	{
		spinID.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
                AnimalList(IDENTIFICADORES[index]);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				int index =0;
			}
		});
	}

	private void AnimalList(String id_selecionado)
	{
		animais 		= a_model.selectAllAni(this, "Animal", ani_tb, id_selecionado);
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

	public void search(){

		EditText inputSearch = (EditText) findViewById(R.id.edtSearch);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				// When user changed the Text
				//MainActivity.this.adapter.getFilter().filter(cs);

				adapter.getFilter().filter(cs);

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

				Spinner spnSexo = (Spinner) findViewById(R.id.spnSexoFiltro);
				String sexo_selecionado = spnSexo.getSelectedItem().toString();

				/*if(arg0.toString().equals("")) {
					if (sexo_selecionado == "TODOS")
						setTitle("Quantidade de Animais: " + quantdAnimais);
					else
						setTitle("Quantidade de " + (sexo_selecionado == "MACHO" ? "Machos: " : "Fêmeas: ") + quantdAnimais);
				}
				else
					setTitle("Animais");*/

			}
		});

	}

	public void btn_filtro_Click (View view)
	{
		Spinner spnSexo = (Spinner) findViewById(R.id.spnSexoFiltro);
		String sexo_selecionado = spnSexo.getSelectedItem().toString();
		String id_selecionado = spinID.getSelectedItem().toString();

		EditText inputSearch = (EditText) findViewById(R.id.edtSearch);
		inputSearch.setText("");
		configuraSearch(false);
		//inputSearch.setFocusable(false);
//		((InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);

		if(sexo_selecionado == "TODOS") {
			AnimalList(id_selecionado);
			return;
		}
		else
			animais 		= a_model.selectAllbySexo(this, "Animal", ani_tb, sexo_selecionado == "MACHO" ? "MA" : "FE", id_selecionado);

		quantdAnimais 	= animais.size();
		adapter         = new ArrayAdapter<Animal>(this, android.R.layout.simple_list_item_1, animais);

		animal_list.setAdapter(adapter);

		setTitle("Quantidade de " + (sexo_selecionado == "MACHO" ? "Machos: " : "Fêmeas: ") + quantdAnimais);
	}

	public void searchClick(View b) {
		try {

			EditText edtSearch = (EditText) findViewById(R.id.edtSearch);

			int w = edtSearch.getLayoutParams().width;
			if(w == 0) {
				edtSearch.setFocusable(true);
				edtSearch.setFocusableInTouchMode(true);
				edtSearch.setEnabled(true);
				edtSearch.setClickable(true);
				changeSearch();
				configuraSearch(true);
			}
			else{
				configuraSearch(false);
				edtSearch.setText("");
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	private void configuraSearch(boolean valor){

		EditText edtSearch = (EditText) findViewById(R.id.edtSearch);

		if(valor){
			edtSearch.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			((InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE)).showSoftInput(edtSearch, InputMethodManager.SHOW_FORCED);

			edtSearch.requestFocus();
		}
		else{
			edtSearch.setLayoutParams(new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.WRAP_CONTENT));
			((InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

			spinSexo.requestFocus();
		}
	}

	private void changeSearch()
	{
		EditText edtSearch = (EditText) findViewById(R.id.edtSearch);

		edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				configuraSearch(hasFocus);

			}
		});
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
				if(ani_tb.getSexo().equals("FE"))
					msg += "\nSituação Reprodutiva: " + ani_tb.getSituacao_reprodutiva();

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
