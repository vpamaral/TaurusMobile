package br.com.prodap.taurusmobile.ui;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.task.GetAnimaisJSON;
import br.com.prodap.taurusmobile.task.PostAnimaisJSON;

public class MenuPrincipalActivity extends Activity {

	private Button btn_atualizar;
	private Button btn_animais;
	private Button btn_parto;
	private Button btn_lista_parto;
	private Button btn_enviar_dados;
	private Button btn_configurar;
	//private Button btn_buscar; 
	protected List<Animal> objListaAnimal;
	public ProgressDialog objProgressDialog;
	public AnimalAdapter aniHelper;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		source();
		carregaListener();
	}
	
	private void source() {
		btn_atualizar 		= (Button) findViewById(R.id.btn_atualiza);
		btn_animais 		= (Button) findViewById(R.id.btn_animal);
		btn_parto 			= (Button) findViewById(R.id.btn_parto);
		btn_lista_parto 	= (Button) findViewById(R.id.btn_lista_parto);
		btn_enviar_dados 	= (Button) findViewById(R.id.btn_enviar_dados);
		btn_configurar		= (Button) findViewById(R.id.btn_configuracoes);
		//btn_buscar			= (Button) findViewById(R.id.btn_busca); // Botão para testar a funcionlidade com o bluetooth
	}
	
	private void carregaListener() {
		btn_atualizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				atualizaDados();
			}
		});

		btn_animais.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listaAnimais();
			}
		});

		btn_parto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lancaParto();
			}
		});
		
		btn_lista_parto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listaPartos();
			}
		});
		
		btn_enviar_dados.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enviarDados();
			}
		});
		
		btn_configurar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				configurarQRCode();
			}
		});
		
		/*btn_buscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buscarBluetooth();
			}
		});*/  // Botão para testar a funcionlidade com o bluetooth
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_atualiza_dados:
			atualizaDados();
			return false;
		case R.id.menu_lista_animais:
			listaAnimais();
			return false;
		case R.id.menu_novo_parto:
			lancaParto();
			return false;
		case R.id.menu_lista_partos:
			listaPartos();
			return false;
		case R.id.menu_enviar_dados:
			enviarDados();
			return false;
		case R.id.menu_QRCode:
			configurarQRCode();
			return false;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void lancaParto() {
		Intent intent = new Intent(MenuPrincipalActivity.this, PartoActivity.class);
		startActivity(intent);
	}

	private void listaAnimais() {
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ListaAnimaisActivity.class);
		startActivity(intent);
	}
	
	private void listaPartos() {
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ListaPartosCriaActivity.class);
		startActivity(intent);
	}

	private void atualizaDados() {
		AnimalModel objModelAnimal = new AnimalModel(ctx);
		objModelAnimal.Delete(this, "Animal");
		new GetAnimaisJSON(this).execute();
	}

	private void enviarDados() {
		new PostAnimaisJSON(this).execute();
		//new PostAnimaisXML(this).execute();
	}
	
	private void configurarQRCode(){
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ConfiguracoesQRCodeActivity.class);
		startActivity(intent);
	}
	
	/*private void buscarBluetooth() {
		Intent intent = new Intent(MenuPrincipalActivity.this, 
				BuscarAnimaisBluetoothActivity.class);
		startActivity(intent);
	}*/
}
