package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
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

import java.util.List;

import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.task.GetAnimaisJSON;
import br.com.prodap.taurusmobile.task.PostAnimaisJSON;
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
		loadListener();
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
		lista_conf = configuracao_model.selectAll(getBaseContext(), "Configuracao", configuracao_model);
		for (Configuracoes conf_tb : lista_conf) {
			url = conf_tb.getEndereco();
		}
		existCelular(lista_conf, configuracao_tb);
	}
	
	private void loadListener() {
		btn_atualizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDados();
			}
		});

		btn_animais.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animaisList();
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
				partosList();
			}
		});
		
		btn_enviar_dados.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				postDados();
			}
		});
		
		btn_configurar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadConfiguracoes();
			}
		});
	}

	private void lancaParto() {
		Intent intent = new Intent(MenuPrincipalActivity.this, PartoActivity.class);
		startActivity(intent);
	}

	private void animaisList() {
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ListaAnimaisActivity.class);
		startActivity(intent);
	}
	
	private void partosList() {
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ListaPartosCriaActivity.class);
		startActivity(intent);
	}

	private void updateDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				msgUpdateDados();
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
	}

	private void postDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				msgPostDados();
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
	}
	
	private void loadConfiguracoes(){
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ConfiguracoesActivity.class);
		startActivity(intent);
	}

	private void msgUpdateDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				MensagemUtil.addMsg(MenuPrincipalActivity.this, "Aviso", "Deseja atualizar os dados?"
						, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AnimalModel objModelAnimal = new AnimalModel(MenuPrincipalActivity.this);
						objModelAnimal.delete(MenuPrincipalActivity.this, "Animal");
						new GetAnimaisJSON(MenuPrincipalActivity.this).execute();
					}
				});
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
		/**/
	}

	private void msgPostDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				MensagemUtil.addMsg(MenuPrincipalActivity.this, "Aviso", "Deseja enviar os dados?"
						, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new PostAnimaisJSON(MenuPrincipalActivity.this).execute();
					}
				});
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
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

	private void existCelular(List<Configuracoes> listQRCode, Configuracoes qrcode_tb) {
		if (listQRCode.size() != 0) {
			for (Configuracoes conf_tb : listQRCode) {
				if (conf_tb.getTipo().equals(qrcode_tb.getTipo())
						&& conf_tb.getEndereco().equals(qrcode_tb.getEndereco())) {
					break;
				}
			}
		} else {
			loadLeitor();
		}
	}

	private void loadLeitor() {
		Intent intent = new Intent(MenuPrincipalActivity.this, LeitorActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public boolean validateServer(String urlServer) {
		int count = 0;
		for (int i = 0; i < urlServer.length(); i++) {
			if (urlServer.charAt(i) == '/') {
				count++;
			}
		}
		if (count != 5) {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "O URL do Servidor está inválido!" +
					"\nFavor configurar o servidor.");
			return false;
		}
		return true;
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
				updateDados();
				return false;
			case R.id.menu_lista_animais:
				animaisList();
				return false;
			case R.id.menu_novo_parto:
				lancaParto();
				return false;
			case R.id.menu_lista_partos:
				partosList();
				return false;
			case R.id.menu_enviar_dados:
				postDados();
				return false;
			case R.id.menu_QRCode:
				loadConfiguracoes();
				return false;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}