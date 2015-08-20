package br.com.prodap.taurusmobile.ui;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.AnimalAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.task.GetAnimaisJSON;
import br.com.prodap.taurusmobile.task.PostAnimaisJSON;
import br.com.prodap.taurusmobile.util.Auxiliar;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class MenuPrincipalActivity extends Activity {

	private Button btn_atualizar;
	private Button btn_animais;
	private Button btn_parto;
	private Button btn_lista_parto;
	private Button btn_enviar_dados;
	private Button btn_configurar;
	public static String idold;
	private List<Configuracoes> lista_conf;
	private ConfiguracoesModel configuracao_model;
	private Configuracoes configuracao_tb;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		idold = "";
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
		configuracao_model 	= new ConfiguracoesModel(this);
		configuracao_tb 	= new Configuracoes();
		//btn_buscar			= (Button) findViewById(R.id.btn_busca); // Bot�o para testar a funcionlidade com o bluetooth
		lista_conf = configuracao_model.selectAll(getBaseContext(), "Configuracao", configuracao_model);
		for (Configuracoes conf_tb : lista_conf) {
			url = conf_tb.getEndereco();
		}
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
				configuraQRCode();
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
			configuraQRCode();
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
		if (checksConnection()) {
			if (validateServer(url)){
				msgAtualizarDados();
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
		/**/
	}

	private void enviarDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				msgEnviarDados();
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
	}
	
	private void configuraQRCode(){
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ConfiguracoesQRCodeActivity.class);
		startActivity(intent);
	}

	private void msgAtualizarDados(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alerta").setMessage("Deseja Atualizar os dados?").setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					AnimalModel objModelAnimal = new AnimalModel(MenuPrincipalActivity.this);
					objModelAnimal.delete(MenuPrincipalActivity.this, "Animal");
					new GetAnimaisJSON(MenuPrincipalActivity.this).execute();
				}
			})
			.setNegativeButton("Não", null)
			.show();
	}

	private void msgEnviarDados(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alerta").setMessage("Deseja Enviar os dados?").setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new PostAnimaisJSON(MenuPrincipalActivity.this).execute();
					}
				})
				.setNegativeButton("Não", null)
				.show();
	}

	public boolean checksConnection() {
		boolean connected;
		ConnectivityManager conectivtyManager = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conectivtyManager.getActiveNetworkInfo() != null
				&& conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {
			connected = true;
		} else {
			connected = false;
		}
		return connected;
	}

	public boolean validateServer(String urlServer) {
		int count = 0;
		for (int i = 0; i < urlServer.length(); i++) {
			if (urlServer.charAt(i) == '/') {
				count++;
			}
		}
		if (count != 5) {
			MensagemUtil.addMsg(MessageDialog.Toast, this
					, "O URL do Servidor está inválido!" +
					"\nFavor configurar o servidor.");
			return false;
		}
		return true;
	}
	
	/*private void buscarBluetooth() {
		Intent intent = new Intent(MenuPrincipalActivity.this, 
				BuscarAnimaisBluetoothActivity.class);
		startActivity(intent);
	}*/
}
