package br.com.taurusmobile.ui;

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
import android.widget.Toast;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.model.Parto_CriaModel;
import br.com.taurusmobile.util.MensagemUtil;
import br.com.taurusmobile.util.MessageDialog;

public class ListaPartosCriaActivity extends Activity {
	Parto_CriaModel p_cria_model;
	Parto_Cria p_cria_tb;
	private List<String> partos_cria;
	ListView lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_partos_cria);

		p_cria_tb = new Parto_Cria();
		p_cria_model = new Parto_CriaModel(getBaseContext());
		partos_cria = new ArrayList<String>();
		lista = (ListView) findViewById(R.id.lista_partos);
		
		this.listarPartos();
		this.consultarPorIdClick();
		
		// quando um item da lista recebe um click longo
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Toast.makeText(ListaPartosCriaActivity.this,
						"Animal selecionado " + partos_cria.get(position),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});

		
	}
	
	private void listarPartos(){
		List<Parto_Cria> lista_partos = p_cria_model.selectAll(this,
				"Parto_Cria", p_cria_tb);

		for (Parto_Cria p_cria : lista_partos) {
			partos_cria.add(p_cria.getCodigo_cria());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, partos_cria);
		
		lista.setAdapter(adapter);
	}
	
	/**
	 * M�todo executado quando algum item da lista � clicado
	 */
	private void consultarPorIdClick(){
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				p_cria_tb = p_cria_model.selectByCodigo(ListaPartosCriaActivity.this,
						(int) position + 1);

				String msg = "C�digo: " + p_cria_tb.getCodigo_cria() + "\nPeso: "
						+ p_cria_tb.getPeso_cria() + "\nSexo: "
						+ p_cria_tb.getSexo(); 

				MensagemUtil.addMsg(MessageDialog.Yes,
						ListaPartosCriaActivity.this, msg, "Parto", position);
			}
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_partos, menu);
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
