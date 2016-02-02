package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.task.Get_Animais_JSON;
import br.com.prodap.taurusmobile.task.Get_Grupo_Manejo_JSON;
import br.com.prodap.taurusmobile.task.Get_Pastos_JSON;
import br.com.prodap.taurusmobile.task.Post_Animais_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class MenuPrincipalActivity extends Activity {

	public static String JSONPASTO;
	private Button btn_atualizar;
	private Button btn_atualizar_dados;
	private Button btn_animais;
	private Button btn_parto;
	private Button btn_lista_parto;
	private Button btn_enviar_dados;
	private Button btn_configurar;
	public static String old_identificador;
	public static String old_sisbov;
	private List<Configuracao> lista_conf;
	private Configuracao_Model configuracao_model;
	private Configuracao c_tb;
	private String url;
	private Parto_Model parto_model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_principal);
		old_sisbov 			= "";
		old_identificador	= "";
		try {
			createFile();
			createFileParto();
		} catch (IOException e) {
			e.printStackTrace();
		}
		source();
		parto_model.recoverDescarte(getBaseContext());
		atualizarBotoes();
		loadListener();
	}
	
	private void source() {
		btn_atualizar 		= (Button) findViewById(R.id.btn_atualiza);
//		btn_atualizar_dados	= (Button) findViewById(R.id.btn_atualiza_dados);
		btn_animais 		= (Button) findViewById(R.id.btn_animal);
		btn_parto 			= (Button) findViewById(R.id.btn_parto);
		btn_lista_parto 	= (Button) findViewById(R.id.btn_lista_parto);
		btn_enviar_dados 	= (Button) findViewById(R.id.btn_enviar_dados);
		btn_configurar		= (Button) findViewById(R.id.btn_configuracoes);
		configuracao_model 	= new Configuracao_Model(this);
		c_tb 				= new Configuracao();
		parto_model			= new Parto_Model(this);
		lista_conf = configuracao_model.selectAll(getBaseContext(), "Configuracao", c_tb);

		for (Configuracao conf_tb : lista_conf) {
			url = conf_tb.getEndereco();
		}
		existCelular(lista_conf, c_tb);
	}

	private void atualizarBotoes()
	{
		final List<Pasto> pasto_list;
		final List<String> nome_pasto_list = new ArrayList<String>();
		final Pasto pasto_tb = new Pasto();
//		Pasto_Model pasto_model = new Pasto_Model(getBaseContext());
//		Pasto_Adapter pasto_adapter = new Pasto_Adapter();
//
//		pasto_list = pasto_model.selectAll(getBaseContext(), "Pasto", pasto_tb);
//		if(pasto_list.size() > 0) {
//			btn_atualizar_dados.setVisibility(View.INVISIBLE);
//		} else {
//			btn_atualizar_dados.setVisibility(View.VISIBLE);
//		}
	}
	
	private void loadListener() {
		btn_atualizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDados();
			}
		});

//		btn_atualizar_dados.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				try {
//					JSONPASTO = createListAnimais();
//					msgUpdatePastoViaArquivo();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});

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
				loadConfiguracao();
			}
		});
	}

	private void lancaParto() {
		Intent intent = new Intent(MenuPrincipalActivity.this, PartoActivity.class);
		startActivity(intent);
	}

	private void novoPasto() {
		Intent pasto = new Intent(MenuPrincipalActivity.this, PastoActivity.class);
		startActivity(pasto);
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
				msgUpdateAnimais();
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
	
	private void loadConfiguracao(){
		Intent intent = new Intent(MenuPrincipalActivity.this,
				ConfiguracaoActivity.class);
		startActivity(intent);
	}

	private void msgUpdateAnimais() {
		if (checksConnection()) {
			if (validateServer(url)){
				MensagemUtil.addMsg(MenuPrincipalActivity.this, "Aviso", "Deseja atualizar os dados?"
						, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						updatePastos();
						updateGrupoManejo();
						Animal_Model objModelAnimal = new Animal_Model(MenuPrincipalActivity.this);
						objModelAnimal.delete(MenuPrincipalActivity.this, "Animal");
						new Get_Animais_JSON(MenuPrincipalActivity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
					}
				});
			}
		} else {
			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
	}

	private void updatePastos() {
		Pasto_Model pasto_model = new Pasto_Model(MenuPrincipalActivity.this);
		pasto_model.delete(MenuPrincipalActivity.this, "Pasto");
		new Get_Pastos_JSON(MenuPrincipalActivity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
	}

	private void updateGrupoManejo() {
		Grupo_Manejo_Model grupo_model = new Grupo_Manejo_Model(MenuPrincipalActivity.this);
		grupo_model.delete(MenuPrincipalActivity.this, "Grupo_Manejo");
		new Get_Grupo_Manejo_JSON(MenuPrincipalActivity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
	}

//	private void msgUpdatePastoViaArquivo() {
//		if (checksConnection()) {
//			if (validateServer(url)){
//				MensagemUtil.addMsg(MenuPrincipalActivity.this, "Aviso", "Criar tabela de Pasto?"
//						, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Pasto_Model pasto_model = new Pasto_Model(getBaseContext());
//						pasto_model.delete(MenuPrincipalActivity.this, "Pasto");
//
//						new Get_ARQUIVO(MenuPrincipalActivity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
//						btn_atualizar_dados.setVisibility(View.INVISIBLE);
//					}
//				});
//			}
//		} else {
//			MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao atulaizar via arquivo!");
//			return;
//		}
//	}

	private void msgPostDados() {
		if (checksConnection()) {
			if (validateServer(url)){
				MensagemUtil.addMsg(MenuPrincipalActivity.this, "Aviso", "Deseja enviar os dados?"
						, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Post_Animais_JSON(MenuPrincipalActivity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
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

	private void existCelular(List<Configuracao> listQRCode, Configuracao qrcode_tb) {
		if (listQRCode.size() != 0) {
			for (Configuracao conf_tb : listQRCode) {
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
			case R.id.menu_novo_pasto:
				novoPasto();
				return false;
			case R.id.menu_lista_partos:
				partosList();
				return false;
			case R.id.menu_enviar_dados:
				postDados();
				return false;
			case R.id.menu_QRCode:
				loadConfiguracao();
				return false;
			/*case R.id.menu_recover_sent_partos:
				parto_model.recoverSentPartos(getBaseContext());
				return false;*/
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private String obterDiretorio()
	{
		File root = android.os.Environment.getExternalStorageDirectory();
		return root.toString();
	}

	private String createListAnimais () throws IOException {
		String texto = "";

		try {
			File textfile = new File(Environment.getExternalStorageDirectory()+"/Prodap/","pasto.txt");
			BufferedReader br = new BufferedReader(new FileReader(textfile));

			texto = br.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto;
	}

	private void createFile() throws IOException {

		Date data = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		String filename = "backup.txt";
		String conteudo = "";

		File diretorio = new File(obterDiretorio(), "Prodap");

		if(!diretorio.exists()) {
			diretorio.mkdir();
		}
		File arquivo = new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);

		FileOutputStream outputStream = null;
		try
		{
			if(!arquivo.exists()) {
				outputStream = new FileOutputStream(arquivo);
				outputStream.write(conteudo.getBytes());
				outputStream.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createFileParto() throws IOException {

		Date data = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		String filename = "partos_enviados.txt";
		String conteudo = "";

		File diretorio = new File(obterDiretorio(), "Prodap");

		if(!diretorio.exists()) {
			diretorio.mkdir();
		}
		File arquivo = new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);

		FileOutputStream outputStream = null;
		try
		{
			if(!arquivo.exists()) {
				outputStream = new FileOutputStream(arquivo);
				outputStream.write(conteudo.getBytes());
				outputStream.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
