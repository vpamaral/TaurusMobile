package br.com.taurusmobile.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.TB.Parto_PartoCria;
import br.com.taurusmobile.model.Parto_CriaModel;
import br.com.taurusmobile.util.MensagemUtil;
import br.com.taurusmobile.util.MessageDialog;

public class ListaPartosCriaActivity extends Activity {
	Parto_CriaModel p_cria_model;
	Parto_Cria p_cria_tb;
	private List<String> partos_cria;
	ListView lista;
	List<Parto_Cria> listaPartoCria;
	//private long posicao;
	
	
	ArrayAdapter<String> adapter;
	List<Parto_Cria> listaPartos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_partos_cria);

		p_cria_tb = new Parto_Cria();
		p_cria_model = new Parto_CriaModel(getBaseContext());
		partos_cria = new ArrayList<String>();
		lista = (ListView) findViewById(R.id.lista_partos);
		listaPartoCria = new ArrayList<Parto_Cria>();
		listaPartoCria.add(p_cria_tb);
		listaPartos = new ArrayList<Parto_Cria>();
		p_cria_model = new Parto_CriaModel(this);
		
		this.listarPartos();
		this.consultarPorIdClick();
		//this.ExcluirClickLongo();
	}
	
	private void listarPartos() {
		List<Parto_Cria> lista_partos = p_cria_model.selectAll(this,
				"Parto_Cria", p_cria_tb);

		for (Parto_Cria p_cria : lista_partos) {
			partos_cria.add(p_cria.getCodigo_cria());
		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, partos_cria);

		lista.setAdapter(adapter);
	}

	/**
	 * Método executado quando algum item da lista é clicado
	 */
	private void consultarPorIdClick() {
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				p_cria_tb = p_cria_model.selectByCodigo(
						ListaPartosCriaActivity.this, position + 1);

				String msg = "Código: " + p_cria_tb.getCodigo_cria()
						+ "\nPeso: " + p_cria_tb.getPeso_cria() + "\nSexo: "
						+ p_cria_tb.getSexo();

				MensagemUtil.addMsg(MessageDialog.Yes,
						ListaPartosCriaActivity.this, msg, "Parto", position);
			}
		});
	}

	/*private void ExcluirClickLongo() {
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int id, long position) {
				setPosicao(position);
				if(p_cria_tb.getCodigo_cria() == null){
					p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, p_cria_tb.getId_fk_animal_mae());
				}
				else{
					p_cria_tb.getId_fk_animal_mae();
					p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, p_cria_tb.getId_fk_animal_mae());
				}
				Intent i = new Intent(ListaPartosCriaActivity.this, ListaPartosCriaActivity.class);
				startActivity(i);
				finish();
				//p_cria_tb = listaPartos.get((int) posicao);
				//p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, p_cria_tb.getId_fk_animal_mae());
				//p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, id + 1);
				//Parto_PartoCria parto_tb = 
				
				//MensagemUtil.addMsg(MessageDialog.Toast, ListaPartosCriaActivity.this, "Parto Excluído com sucesso!!");
				//Intent i = new Intent(ListaPartosCriaActivity.this, ListaPartosCriaActivity.class);
				//startActivity(i);
				//finish();
				/*Intent i = new Intent(ListaPartosCriaActivity.this, ListaPartosCriaActivity.class);
				startActivity(i);
				finish();*/
				//p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, posicao);
				//MensagemUtil.addMsg(MessageDialog.Toast, ListaPartosCriaActivity.this, "Parto Excluído com sucesso!!");
				
				//setPosicao(id);
				  //adapter.getItemAtPosition(id);
				  //lista.removeAllViews();
				 
				//return false;
			//}
		//});
	//}
		
		/*lista.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.add(0, 1, 1, "Deletar");
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case 1:
			//p_cria_model.removerByCodigo(ListaPartosCriaActivity.this, posicao);
			//MensagemUtil.addMsg(MessageDialog.Toast, ListaPartosCriaActivity.this, "Parto Excluído com sucesso!!");
			//lista.removeViewAt(posicao);
			Intent i = new Intent(ListaPartosCriaActivity.this, ListaPartosCriaActivity.class);
			startActivity(i);
			finish();
			break; 
		case 2:
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}*/
	
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
	
	/*public long getPosicao() {
		return posicao;
	}

	public void setPosicao(long posicao) {
		this.posicao = posicao;
	}*/
	
}
