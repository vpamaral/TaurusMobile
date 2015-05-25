package br.com.taurusmobile.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.model.AnimalModel;

public class ListaAnimaisActivity extends Activity {

	AnimalModel ani_model;
	Animal ani_tb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_animais);

		ani_tb = new Animal();
		ani_model = new AnimalModel();
		List<String> animais = new ArrayList<String>();
		ListView lista = (ListView) findViewById(R.id.lista_animais);
		

		List<Animal> listaani = ani_model.selectAll(this, "Animal", ani_tb);

		for(Animal a: listaani)
		{
			animais.add(a.getCodigo());
		}

		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animais);
		lista.setAdapter(adapter);
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
