package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.LinearLayout;
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
import java.util.StringTokenizer;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.prodap.taurusmobile.OpenFile.OnCloseListener;
import br.com.prodap.taurusmobile.OpenFile.OpenFileDialog;
import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.bluetooth.Bluetooth_Activity;
import br.com.prodap.taurusmobile.bluetooth.ConnectionThread;
import br.com.prodap.taurusmobile.bluetooth.DiscoveredDevices;
import br.com.prodap.taurusmobile.bluetooth.PairedDevices;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Criterio_Model;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.model.Parto_Cria_Model;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.task.Get_Animais_JSON;
import br.com.prodap.taurusmobile.task.Get_Criterios_JSON;
import br.com.prodap.taurusmobile.task.Get_Grupo_Manejo_JSON;
import br.com.prodap.taurusmobile.task.Get_Pastos_JSON;
import br.com.prodap.taurusmobile.task.Post_Animais_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.CreateReadWrite;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Menu_Principal_Activity extends Activity
{
	public static String JSONPASTO;
	private ProgressDialog dialog;
	private Handler mHandler;
	private OpenFileDialog openFileDialog;
	private CreateReadWrite crw;

	private Bluetooth_Activity b_activity;
	private String status_bluetooth;

	public static Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			resultHandleMessage(msg);
		}
	};

	private static void resultHandleMessage(Message msg)
	{
		Bundle bundle 		= msg.getData();
		byte[] data 		= bundle.getByteArray("data");
		String dataString 	= new String(data);

		if (dataString.equals("ERRO_CONEXAO"))
		{
			Constantes.STATUS_CONN = ("desconectado");
			Constantes.LBL_STATUS.setText("Desconectado");
		}
		else if (dataString.equals("CONECTADO"))
		{
			Constantes.STATUS_CONN = ("conectado");
			Constantes.LBL_STATUS.setText("Conectado");
		}
		else
		{
			data.toString().length();

			Constantes.JSON += new String(data);

			Constantes.GET_JSON.Get_JSON(Constantes.JSON);
		}
	}

	public static String old_identificador;
	public static String old_sisbov;
	public static String old_cod_matriz;
	public static String old_data_parto;
	public static String old_grupo_manejo;
	public static String old_criterio;
	public static String old_pasto;
	public static String old_raca_cria;
	public static String old_genetica;
	public static String old_sexo;

	private List<Configuracao> lista_conf;
	private Configuracao_Model configuracao_model;
	private Configuracao conf_tb;
	private String url;
	private Parto_Model parto_model;
	private Parto_Cria_Model p_cria_model;

    private int count;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_principal);

		loadVars();

		source();

		openFileDialog_Click();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// The activity is about to become visible.

		buscaPartosLancados();
		buscaDataAtualizacao();
	}

	public void btn_itens_atualizar_Click(View v) {
		LinearLayout be_w = (LinearLayout) findViewById(R.id.btn_enviar_web);
		LinearLayout be_b = (LinearLayout) findViewById(R.id.btn_enviar_bluetooth);
		LinearLayout be_a = (LinearLayout) findViewById(R.id.btn_enviar_arquivo);
		be_w.setVisibility(View.GONE);
		be_b.setVisibility(View.GONE);
		be_a.setVisibility(View.GONE);

        LinearLayout ba_w = (LinearLayout) findViewById(R.id.btn_atualiza_web);
		LinearLayout ba_b = (LinearLayout) findViewById(R.id.btn_atualiza_bluetooth);
		LinearLayout ba_a = (LinearLayout) findViewById(R.id.btn_atualiza_arquivo);

		if (ba_w.getVisibility() == View.GONE) {
			ba_w.setVisibility(View.VISIBLE);
			ba_b.setVisibility(View.VISIBLE);
			ba_a.setVisibility(View.VISIBLE);

			ImageButton i_select = (ImageButton) findViewById( R.id.imagebutton_open_enviar);
			i_select.setBackgroundColor(Color.parseColor("#5cb85c"));
			Button btn_select = (Button) findViewById( R.id.btn_open_enviar);
			btn_select.setBackgroundColor(Color.parseColor("#5cb85c"));
			btn_select.setTextColor(Color.WHITE);

			i_select = (ImageButton) findViewById( R.id.imagebutton_open_atualiza);
			//i_select.setBackgroundColor(Color.parseColor("#FF33B5E5"));
			btn_select = (Button) findViewById( R.id.btn_open_atualiza);
			//btn_select.setBackgroundColor(Color.parseColor("#FF33B5E5"));

			//btn_select.setTextColor(Color.BLACK);
			i_select.setBackgroundColor(Color.parseColor("#ffff8800"));

		} else {
			ba_w.setVisibility(View.GONE);
			ba_b.setVisibility(View.GONE);
			ba_a.setVisibility(View.GONE);

			ImageButton i_select = (ImageButton) findViewById( R.id.imagebutton_open_atualiza);
			i_select.setBackgroundColor(Color.parseColor("#f0ad4e"));

			Button btn_select = (Button) findViewById( R.id.btn_open_atualiza);
			btn_select.setBackgroundColor(Color.parseColor("#f0ad4e"));
			btn_select.setTextColor(Color.WHITE);
		}
	}

	public void btn_itens_enviar_Click(View v)
	{
        LinearLayout ba_w = (LinearLayout) findViewById(R.id.btn_atualiza_web);
		LinearLayout ba_b = (LinearLayout) findViewById(R.id.btn_atualiza_bluetooth);
		LinearLayout ba_a = (LinearLayout) findViewById(R.id.btn_atualiza_arquivo);
		ba_w.setVisibility(View.GONE);
		ba_b.setVisibility(View.GONE);
		ba_a.setVisibility(View.GONE);

		LinearLayout be_w = (LinearLayout) findViewById(R.id.btn_enviar_web);
		LinearLayout be_b = (LinearLayout) findViewById(R.id.btn_enviar_bluetooth);
		LinearLayout be_a = (LinearLayout) findViewById(R.id.btn_enviar_arquivo);

		if (be_w.getVisibility() == View.GONE) {
			be_w.setVisibility(View.VISIBLE);
			be_b.setVisibility(View.VISIBLE);
			be_a.setVisibility(View.VISIBLE);

			ImageButton i_select = (ImageButton) findViewById( R.id.imagebutton_open_atualiza);
			i_select.setBackgroundColor(Color.parseColor("#f0ad4e"));
			Button btn_select = (Button) findViewById( R.id.btn_open_atualiza);
			btn_select.setBackgroundColor(Color.parseColor("#f0ad4e"));
			btn_select.setTextColor(Color.WHITE);

			i_select = (ImageButton) findViewById( R.id.imagebutton_open_enviar);
			//i_select.setBackgroundColor(Color.parseColor("#FF33B5E5"));
			btn_select = (Button) findViewById( R.id.btn_open_enviar);
			//btn_select.setBackgroundColor(Color.parseColor("#FF33B5E5"));

			//btn_select.setTextColor(Color.BLACK);
            i_select.setBackgroundColor(Color.parseColor("#ff669900"));

		} else {
			be_w.setVisibility(View.GONE);
			be_b.setVisibility(View.GONE);
			be_a.setVisibility(View.GONE);

			ImageButton i_select = (ImageButton) findViewById( R.id.imagebutton_open_enviar);
			i_select.setBackgroundColor(Color.parseColor("#5cb85c"));

			Button btn_select = (Button) findViewById( R.id.btn_open_enviar);
			btn_select.setBackgroundColor(Color.parseColor("#5cb85c"));
			btn_select.setTextColor(Color.WHITE);
		}
	}

	private void loadVars()
	{
		old_sisbov = "";
		old_identificador = "";
		old_cod_matriz = "";
		old_data_parto = "";
		old_grupo_manejo = "";
		old_criterio = "";
		old_pasto = "";
		old_genetica = "";
		old_raca_cria = "";
		old_sexo = "";
	}

	private void openFileDialog_Click()
	{
		openFileDialog = new OpenFileDialog(this);
		openFileDialog.setOnCloseListener(new OnCloseListener()
		{
			@Override
			public void onCancel()
			{
			}

			@Override
			public void onOk(String selectedFile)
			{
				try
				{
					Constantes.ARQUIVO = createList(selectedFile).toString();

					if (Constantes.ARQUIVO != null)
					{
						Constantes.GET_JSON.Get_JSON(Constantes.ARQUIVO);

						updateViaArquivo();
					}
					else
					{
						Mensagem_Util.addMsg(Message_Dialog.Toast, Menu_Principal_Activity.this, "Aquivos sem dados");
					}
				}
				catch (IOException e)
				{
					Log.i("OPENFILE", e.toString());
					e.printStackTrace();
				}
			}
		});
	}

	private void source()
	{
		try
		{
			crw = new CreateReadWrite(this);
			crw.createFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Constantes.LBL_STATUS 	= (TextView) findViewById(R.id.lbl_status_MSG);
		Constantes.GET_JSON 	= new Get_JSON();
		mHandler 				= new Handler();
		configuracao_model 		= new Configuracao_Model(this);
		conf_tb 				= new Configuracao();
		parto_model 			= new Parto_Model(this);
		p_cria_model			= new Parto_Cria_Model(this);

		b_activity				= new Bluetooth_Activity();

		lista_conf = configuracao_model.selectAll(this, "Configuracao", conf_tb);

		for (Configuracao conf : lista_conf)
		{
			url = conf.getEndereco();
		}

		isEnableBluetooth();

		if(lista_conf.size() == 0){

            verificaConfig();
        }

	}

	private void verificaConfig(){

		lista_conf = configuracao_model.selectAll(this, "Configuracao", conf_tb);
		if(lista_conf.size() == 0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal_Activity.this);
				builder.setTitle("Aviso").setMessage("Favor informar as configurações antes de iniciar os lançamentos!")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								loadConfiguracao();
								verificaConfig();
							}
						})
						.show();
		}
	}

    public void buscaDataAtualizacao() {

        try {
            lista_conf = configuracao_model.selectAll(this, "Configuracao", conf_tb);
            if(lista_conf.size() > 0){
                conf_tb = lista_conf.get(0);

                String ult_at = String.valueOf(conf_tb.getUltimaAtualizacao());
                TextView tv = (TextView) findViewById(R.id.lbl_ult_atualizacao);
                tv.setText(ult_at);

				LinearLayout linear = (LinearLayout) findViewById(R.id.linear_ult_atualizacao);
				if(ult_at == "null")
					linear.setVisibility(View.GONE);
				else
					linear.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            throw e;
        }
    }

	public void buscaPartosLancados() {

		try {

			Parto_Cria p_cria_tb = new Parto_Cria();
			List<Parto_Cria> p_cria_list = p_cria_model.selectAll(getBaseContext(), "Parto_Cria", p_cria_tb);
			int quantdPartos = p_cria_list.size();
			TextView tv = (TextView) findViewById(R.id.lbl_partos_lancados);
			tv.setText( String.valueOf(quantdPartos));

            LinearLayout linear = (LinearLayout) findViewById(R.id.linear_partos_lancados);
			if(quantdPartos == 0)
				linear.setVisibility(View.GONE);
			else
                linear.setVisibility(View.VISIBLE);

		} catch (Exception e) {
			throw e;
		}
	}

	//metodo para selecionar o arquivo para atualizar o banco de dados do aparelho
	private String createList(String path_file) throws IOException
	{
		String dados_file = "";

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(path_file));

			dados_file = br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return dados_file;
	}

	public void btn_atualiza_via_web_Click(View v)
	{
		updateViaWeb();
	}

	public void btn_atualiza_via_bluetooth_Click(View v)
	{
		Constantes.TIPO_ENVIO = "bluetooth";

		if (checksConnectionBluetooth())
		{
			dialog = ProgressDialog.show(this,"Sincronisando dados...","Aguarde ...", false, true);
			Constantes.JSON = "";
			sendMessage("GET");

			Constantes.BLUETOOTH_TESTE = false;
			mHandler.postDelayed(mRun, 5000);
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "O dispositivo não esta conectado ao servidor!");
			return;
		}
	}

	private Runnable mRun = new Runnable()
	{
		@Override
		public void run()
		{
			if (Constantes.TIPO_ENVIO == "bluetooth")
			{
				String validate_bluetooth = Constantes.JSON.toString();

				finishConnection(validate_bluetooth, "|", ";");

				if (count > 3) {
					Constantes.BLUETOOTH_TESTE = true;
					count = 0;
				}

				if (Constantes.BLUETOOTH_TESTE) {
					dialog.dismiss();
					updateViaBluetooth();
				} else {
					mHandler.postDelayed(mRun, 1000);
				}
			}
			if (Constantes.TIPO_ENVIO == "web")
			{
				if(Constantes.SERVER_RESULT_GET == 200)
				{
					dialog.dismiss();
					//updateViaWeb();
				}
				else
				{
					mHandler.postDelayed(mRun, 1000);
				}
			}
		}
	};

	public void finishConnection(String str, String charsRemove, String delimiter)
	{
		if (charsRemove != null && charsRemove.length() > 0 && str != null)
		{
			String[] remover = charsRemove.split(delimiter);

			for(int i = 0; i < remover.length; i++)
			{
				if (str.indexOf(remover[i]) != -1)
				{
					str = str.replace(remover[i], "");
					count++;
				}
			}
		}
	}

    public static void delay ()
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {

                }
            }
        }.start();
    }

	public void btn_open_arquivo_Click (View v)
	{
		openFileDialog_Click();
		openFileDialog.setTitle("Seleciona Arquivo");
		openFileDialog.setPath(Environment.getExternalStorageDirectory() + "/Prodap/Arquivos_Servidor");
		openFileDialog.show();
	}

	public void btn_list_animais_Click (View v)
	{
		animaisList();
	}

	public void btn_cadastro_parto_Click (View v)
	{
		partosList();
	}

	public void btn_relatorio_parto_Click (View v)
	{
		Intent intent = new Intent(Menu_Principal_Activity.this, Relatorio_Partos_Activity.class);
		startActivity(intent);
	}

	public void btn_enviar_via_web_Click (View v)
	{
		Constantes.TIPO_ENVIO = "web";
		postViaWeb();
	}

	public void btn_enviar_via_bluetooth_Click (View v)
	{
		Constantes.TIPO_ENVIO = "bluetooth";
		postViaBluetooth();
	}

	public void btn_create_file_Click (View v)
	{
		Constantes.TIPO_ENVIO = "arquivo";
		postViaArquivo();
	}

	private void animaisList()
    {
		Intent intent = new Intent(Menu_Principal_Activity.this, Lista_Animais_Activity.class);
		startActivity(intent);
	}
	
	private void partosList()
	{
		Intent intent = new Intent(Menu_Principal_Activity.this, Lista_Partos_Cria_Activity.class);
		startActivity(intent);
	}
	
	private void loadConfiguracao()
	{
		Intent intent = new Intent(Menu_Principal_Activity.this, Configuracao_Activity.class);
		startActivity(intent);
	}

	private void updateViaBluetooth()
	{
		if (checksConnectionBluetooth())
		{
			Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja atualizar os dados via Bluetooth?", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Constantes.TIPO_ENVIO = "bluetooth";
					updatePastos();
					updateGrupoManejo();
					updateCriterios();
					updateAnimais();
				}
			});
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro o dispositivo não esta conectado ao servidor!");
			return;
		}
	}

	private void updateViaWeb()
	{
		Constantes.TIPO_ENVIO = "web";

		if (checksConnectionWeb())
		{
			if (validateServer(url))
			{
				updatePastos();
				updateGrupoManejo();
				updateCriterios();
				updateAnimais();

				dialog = ProgressDialog.show(this,"Sincronisando dados...","Aguarde ...", false, true);

				mHandler.postDelayed(mRun, 5000);
			}
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}

//		if (checksConnectionWeb())
//		{
//			if (validateServer(url))
//			{
//				Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja atualizar os dados via Web?", new DialogInterface.OnClickListener()
//				{
//					@Override
//					public void onClick(DialogInterface dialog, int which)
//					{
//						Constantes.TIPO_ENVIO = "web";
//						updatePastos();
//						updateGrupoManejo();
//						updateCriterios();
//						updateAnimais();
//					}
//				});
//			}
//		}
//		else
//		{
//			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro ao conectar ao servidor!");
//			return;
//		}
	}

	private void updateViaArquivo()
	{
		Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja atualizar os dados via arquivo?", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Constantes.TIPO_ENVIO = "arquivo";
				updatePastos();
				updateGrupoManejo();
				updateCriterios();
				updateAnimais();
			}
		});
	}

	private void updateAnimais()
	{
		Animal_Model objModelAnimal = new Animal_Model(Menu_Principal_Activity.this);
		objModelAnimal.delete(Menu_Principal_Activity.this, "Animal");
		new Get_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL, this).execute();
	}

	private void updatePastos()
	{
		Pasto_Model pasto_model = new Pasto_Model(Menu_Principal_Activity.this);
		pasto_model.delete(Menu_Principal_Activity.this, "Pasto");
		new Get_Pastos_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
	}

	private void updateGrupoManejo()
	{
		Grupo_Manejo_Model grupo_model = new Grupo_Manejo_Model(Menu_Principal_Activity.this);
		grupo_model.delete(Menu_Principal_Activity.this, "Grupo_Manejo");
		new Get_Grupo_Manejo_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
	}

	private void updateCriterios()
	{
		Criterio_Model c_model = new Criterio_Model(Menu_Principal_Activity.this);
		c_model.delete(Menu_Principal_Activity.this, "Criterio");
		new Get_Criterios_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
	}

	private void post()
    {
        new Post_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL, this).execute();
    }

	private void postViaWeb()
	{
		Constantes.CREATE_ARQUIVO = false;

		if (checksConnectionWeb())
		{
			if (validateServer(url))
			{
				Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja enviar os dados?", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						post();
					}
				});
			}
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro ao conectar ao servidor!");
			return;
		}
	}

	private void postViaBluetooth()
	{
		Constantes.CREATE_ARQUIVO = false;

		if (checksConnectionBluetooth())
		{
			Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja enviar os dados?", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
                    post();
				}
			});
		}
		else
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro o dispositivo não esta conectado ao servidor!");
			return;
		}
	}

	private void postViaArquivo()
	{
		Constantes.CREATE_ARQUIVO = true;

		Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja gerar o Arquivo?", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
                post();
			}
		});
	}

	public boolean checksConnectionWeb()
	{
		boolean connected = false;

		ConnectivityManager conectivtyManager 	= (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		lista_conf 								= configuracao_model.selectAll(this, "Configuracao", conf_tb);

		if (lista_conf.size() > 0)
		{
			if (conectivtyManager.getActiveNetworkInfo() != null
					&& conectivtyManager.getActiveNetworkInfo().isAvailable()
					&& conectivtyManager.getActiveNetworkInfo().isConnected())
			{
				connected = true;
			}
			else
			{
				connected = false;
			}
		}
		return connected;
	}

	private boolean checksConnectionBluetooth()
	{
		boolean connected;

		if (Constantes.STATUS_CONN == "conectado")
		{
			connected = true;
		}
		else
		{
			connected = false;
		}
		return connected;
	}

	public boolean validateServer(String urlServer)
	{
		int count = 0;

		for (int i = 0; i < urlServer.length(); i++)
		{
			if (urlServer.charAt(i) == '/')
			{
				count++;
			}
		}

		if (count != 5)
		{
			Mensagem_Util.addMsg(Message_Dialog.Toast, this, "O URL do Servidor está inválido!" +
					"\nFavor configurar o servidor.");
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_principal, menu);

		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Constantes.CALL_BLUETOOTH = "Menu_Principal";

		switch (item.getItemId())
		{
			case R.id.menu_dispositivos_pariados:
				b_activity.searchPairedDevices(this);
				return false;

			case R.id.menu_habilitar_visibilidade:
				b_activity.enableVisibility(this);
				return false;

			case R.id.menu_descoberta_dispositivo:
				b_activity.discoverDevices(this);
				return false;

			case R.id.menu_esperar_conexao:
				b_activity.waitConnection();
				return false;

			case R.id.menu_QRCode:
				loadConfiguracao();
				return false;

			case R.id.menu_about:
				about();
				return false;

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void sendMessage(String json)
	{
		byte[] data = json.getBytes();
		Constantes.CONNECT.write(data);
	}

	public void about()
	{


		AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal_Activity.this);

		builder.setMessage("Versão do Sistema: " + getString(R.string.release_apk) +
				"\n\nSuporte: (31) 3555-0800\n"+
				"www.prodap.com.br\n"+
				"prodap@prodap.com.br\n\n"+
				"© 2017 Prodap\n"+
				"Todos os direitos reservados.")
				.setTitle(" ")
				.setIcon(R.drawable.prodap_logo)
				.setPositiveButton("OK", null)
				.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == Constantes.ENABLE_BLUETOOTH)
		{
			if(resultCode == RESULT_OK)
			{
				Constantes.LBL_STATUS.setText("Bluetooth ativado");
			}
			else
			{
				Constantes.LBL_STATUS.setText("Bluetooth não ativado");
			}
		}
		else if(requestCode == Constantes.SELECT_PAIRED_DEVICE || requestCode == Constantes.SELECT_DISCOVERED_DEVICE)
		{
			if(resultCode == RESULT_OK)
			{
				Constantes.LBL_STATUS.setText("Aguarde conectando ao servidor...");

				b_activity.connection(data.getStringExtra("btDevAddress"));
			}
			else
			{
				Constantes.LBL_STATUS.setText("Nenhum dispositivo selecionado");
			}
		}
	}

	private void isEnableBluetooth()
	{
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

		if (btAdapter == null)
		{
			Constantes.LBL_STATUS.setText("Hardware Bluetooth não está funcionando");
		}
		else
		{
			Constantes.LBL_STATUS.setText("Hardware Bluetooth está funcionando");
		}

		if(btAdapter != null) {
			if (!btAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, Constantes.ENABLE_BLUETOOTH);
				Constantes.LBL_STATUS.setText("Solicitando ativação do Bluetooth...");
			} else {
				Constantes.LBL_STATUS.setText("Bluetooth já ativado");
			}
		}
	}
}
