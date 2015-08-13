package br.com.prodap.taurusmobile.ui;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.adapter.PartoCriaAdapter;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class ListaPartosCriaActivity extends Activity {
	private PartoModel parto_model;
	private Parto parto_tb;
	private Parto_CriaModel p_cria_model;
	private Parto_Cria p_cria_tb;
	private ListView list;
	private PartoCriaAdapter p_cria_adapter;
	private List<Parto_Cria> p_cria_list;
	private List<Parto> parto_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_partos_cria);
		loadList();
		source();
	}

	private void source() {
		parto_tb		= new Parto();
		p_cria_tb 		= new Parto_Cria();
		p_cria_model	= new Parto_CriaModel(getBaseContext());
		parto_model 	= new PartoModel(getBaseContext());
		this.partoCriaList();
		this.partoCriaDetails();
		this.partoCriaDelete();
	}

	private void loadList() {
		list = (ListView) findViewById(R.id.lista_partos);
		registerForContextMenu(list);
	}
	
	private void partoCriaList() {
		parto_model.selectAll(getBaseContext(), "Parto", parto_tb);
		p_cria_list = p_cria_model.selectAll(getBaseContext(),
				"Parto_Cria", p_cria_tb);

		p_cria_adapter = new PartoCriaAdapter(p_cria_list, this);

		list.setAdapter(p_cria_adapter);
	}

	/*
	 * Método executado quando algum item da lista é clicado
	 */
	private void partoCriaDetails() {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				p_cria_tb = (Parto_Cria) p_cria_adapter.getItem(position);

				String msg = "Código: " + p_cria_tb.getCodigo_cria()
							+ "\nIdentificador: " + p_cria_tb.getIdentificador()
							+ "\nSisbov: " +p_cria_tb.getSisbov()
							+ "\nPeso: " + p_cria_tb.getPeso_cria()
							+ "\nSexo: " +p_cria_tb.getSexo();

				MensagemUtil.addMsg(MessageDialog.Yes,
						ListaPartosCriaActivity.this, msg, "Parto", position);
			}
		});
	}

	private void partoCriaDelete() {
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				p_cria_tb = (Parto_Cria) p_cria_adapter.getItem(position);
				return false;
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuItem miDelete = menu.add("Excluir");
		miDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
		{

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//parto_model.deleteById(getBaseContext(), "Parto", parto_tb.getId_fk_animal());
				//p_cria_model.deleteById(getBaseContext(), "Parto_Cria", p_cria_tb.getId_fk_animal_mae());
				partoCriaList();

				return false;
			}
		});

	}
}
