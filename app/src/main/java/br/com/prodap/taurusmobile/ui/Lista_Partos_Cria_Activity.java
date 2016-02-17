package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.List;

import br.com.prodap.taurusmobile.model.Parto_Cria_Model;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Lista_Partos_Cria_Activity extends Activity {
	private Parto_Model parto_model;
	private Parto parto_tb;
	private Parto_Cria_Model p_cria_model;
	private Parto_Cria p_cria_tb;
	private ListView list;
	private Parto_Cria_Adapter p_cria_adapter;
	private Parto_Adapter parto_adapter;
	private List<Parto_Cria> p_cria_list;
	private List<Parto> parto_list;
	private List<Animal> animal_list;
	private Animal_Model ani_model;
	private Animal matriz_tb;
	private int quantdPartos;
	private int quantMachos;
	private int quantFemeas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_partos_cria);
		loadList();
		source();

		setTitle("MA.: " + quantMachos + "  |  FE.: " + quantFemeas + "  |  Total: " + quantdPartos);
	}

	private void source() {
		parto_tb		= new Parto();
		p_cria_tb 		= new Parto_Cria();
		p_cria_model	= new Parto_Cria_Model(getBaseContext());
		parto_model 	= new Parto_Model(getBaseContext());
		ani_model 		= new Animal_Model(getBaseContext());
		matriz_tb 		= new Animal();
		animal_list 	= ani_model.selectAll(getBaseContext(),"Animal", matriz_tb);

		this.partoCriaList();
		this.partoCriaDetails();
		this.partoCriaDelete();
	}

	private void loadList() {
		list = (ListView) findViewById(R.id.lista_partos);
		registerForContextMenu(list);
	}
	
	private void partoCriaList() {
		quantFemeas = 0;
		quantMachos = 0;
		p_cria_list = p_cria_model.selectAll(getBaseContext(), "Parto_Cria", p_cria_tb);
		parto_list 	= parto_model.selectAll(getBaseContext(), "Parto", parto_tb);
		for (Parto_Cria parto_cria_tb : p_cria_list) {
			if (parto_cria_tb.getSexo().toString().equals("MA")) {
				quantMachos++;
			} else {
				quantFemeas++;
			}
		}

		quantdPartos = p_cria_list.size();

		p_cria_adapter 	= new Parto_Cria_Adapter(p_cria_list, this);
		parto_adapter 	= new Parto_Adapter(parto_list, this);

		list.setAdapter(p_cria_adapter);
		//list.setAdapter(parto_adapter);
	}

	/*
	 * Método executado quando algum item da lista é clicado
	 */
	private void partoCriaDetails() {
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				p_cria_tb = (Parto_Cria) p_cria_adapter.getItem(position);
				String matriz = "";
				String data_parto = "";

				for(Animal a : animal_list)
				{
					if(p_cria_tb.getId_fk_animal_mae() == a.getId_pk())
					{
						matriz = a.getCodigo();
						break;
					} else {
						matriz = p_cria_tb.getCod_matriz_invalido();
					}
				}

				for(Parto p : parto_list)
				{
					if(p_cria_tb.getId_fk_animal_mae() == p.getId_fk_animal())
					{
						data_parto = p.getData_parto();
					}
				}

				String msg = "Dados da Matriz\n"
						+ "\nCódigo da Matriz: " + matriz
						+ "\nDescarte: " + p_cria_tb.getRepasse()
						+ "\n\nDados da Cria\n"
						+ "\nCódigo da Cria: " + p_cria_tb.getCodigo_cria()
						+ "\nIdentif.: " + p_cria_tb.getIdentificador()
						+ "\nSisbov: " + p_cria_tb.getSisbov()
						+ "\nData do Parto: " + data_parto
						+ "\nData da Identif.: " + p_cria_tb.getData_identificacao()
						+ "\nTipo de Parto: " + p_cria_tb.getTipo_parto()
						+ "\nSexo: " + p_cria_tb.getSexo()
						+ "\nPeso: " + p_cria_tb.getPeso_cria()
						+ "\nGrupo de Manejo: " + p_cria_tb.getGrupo_manejo()
						+ "\nPasto: " + p_cria_tb.getPasto();

				Mensagem_Util.addMsg(Message_Dialog.Yes,
						Lista_Partos_Cria_Activity.this, msg, "Parto", position);
			}
		});
	}

	private void partoCriaDelete() {
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				p_cria_tb = (Parto_Cria) p_cria_adapter.getItem(position);
				parto_tb = (Parto) parto_adapter.getItem(position);
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuItem miDelete = menu.add("Excluir Parto");
		miDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Lista_Partos_Cria_Activity.this);
				builder.setTitle("Alerta").setMessage("Deseja Excluir o lançamento de parto?").setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Long id_fk_parto = p_cria_tb.getId_fk_parto();
								Long id_pk = parto_tb.getId_pk();

								parto_model.deleteParto(Lista_Partos_Cria_Activity.this, id_pk);
								p_cria_model.deletePartoCria(Lista_Partos_Cria_Activity.this, id_fk_parto);

								Intent i = new Intent(Lista_Partos_Cria_Activity.this, Lista_Partos_Cria_Activity.class);
								startActivity(i);
								Mensagem_Util.addMsg(Message_Dialog.Toast, Lista_Partos_Cria_Activity.this, "Parto e Cria excluído com sucesso.");
								finish();
							}
						})
						.setNegativeButton("Não", null)
						.show();

				//parto_model.deleteById(getBaseContext(), "Parto", parto_tb.getId_fk_animal());
				//p_cria_model.deleteById(getBaseContext(), "Parto_Cria", p_cria_tb.getId_fk_animal_mae());
				//partoCriaList();

				return false;
			}
		});
		//MenuItem recoverSentPartos = menu.add("Recuperar Partos enviados");
	}

	private void alertMsg(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alerta").setMessage("Deseja Atualizar os dados?").setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setNegativeButton("Não", null)
				.show();
	}
/*
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
	}*/
}
