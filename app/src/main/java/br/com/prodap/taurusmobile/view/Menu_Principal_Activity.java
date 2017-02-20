package br.com.prodap.taurusmobile.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.OpenFile.OnCloseListener;
import br.com.prodap.taurusmobile.OpenFile.OpenFileDialog;
import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.bluetooth.ConnectionThread;
import br.com.prodap.taurusmobile.bluetooth.DiscoveredDevices;
import br.com.prodap.taurusmobile.bluetooth.PairedDevices;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Criterio_Model;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.service.Get_JSON;
import br.com.prodap.taurusmobile.task.Get_Animais_JSON;
import br.com.prodap.taurusmobile.task.Get_Criterios_JSON;
import br.com.prodap.taurusmobile.task.Get_Grupo_Manejo_JSON;
import br.com.prodap.taurusmobile.task.Get_Pastos_JSON;
import br.com.prodap.taurusmobile.task.Post_Animais_JSON;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Pasto;
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
		String dataString	= new String(data);

		if(dataString.equals("ERRO_CONEXAO"))
		{
			Constantes.STATUS_CONN = ("desconectado");
			Constantes.LBL_STATUS.setText("Desconectado");
		}
		else if(dataString.equals("CONECTADO"))
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_menu_principal);

		loadVars();

		source();

		openFileDialog_Click();

		//parto_model.recoverDescarte(getBaseContext());
		//atualizarBotoes();
		//loadListener();
	}

	private void loadVars()
	{
		old_sisbov 			= "";
		old_identificador	= "";
		old_cod_matriz		= "";
		old_data_parto		= "";
		old_grupo_manejo	= "";
		old_criterio		= "";
		old_pasto			= "";
		old_genetica		= "";
		old_raca_cria		= "";
		old_sexo			= "";
	}

	private void openFileDialog_Click()
	{
		openFileDialog.setOnCloseListener(new OnCloseListener()
		{
			@Override
			public void onCancel() {}

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
		openFileDialog 			= new OpenFileDialog(this);

		configuracao_model 		= new Configuracao_Model(this);
		conf_tb 				= new Configuracao();
		parto_model				= new Parto_Model(this);

		lista_conf 				= configuracao_model.selectAll(this, "Configuracao", conf_tb);

		for (Configuracao conf : lista_conf)
		{
			url = conf.getEndereco();
		}

        isEnableBluetooth();
		//existCelular(lista_conf, conf_tb);

		parto_model.recoverSentPartos(this);
	}

	//metodo para selecionar o arquivo para atualizar o banco de dados do aparelho
	private String createList (String path_file) throws IOException
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

	public void btn_atualiza_via_web_Click (View v)
	{
		updateViaWeb();
	}

	public void btn_atualiza_via_bluetooth_Click (View v)
	{
		Constantes.TIPO_ENVIO = "bluetooth";

        if (checksConnectionBluetooth())
        {
            sendMessage("GET");

            delay();
            mHandler.postDelayed(mRun, 10000);
        }
        else
        {
            Mensagem_Util.addMsg(Message_Dialog.Toast, this, "O dispositivo não esta conectado ao servidor!");
            return;
        }
	}

    private Runnable mRun = new Runnable ()
    {
        @Override
        public void run()
        {
            updateViaBluetooth();
        }
    };

    private void delay ()
    {
        dialog = ProgressDialog.show(this,"","Aguarde ...", false, true);

        new Thread()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(10000);
                    dialog.dismiss();
                }
                catch (Exception e)
                {

                }
            }
        }.start();
    }

	public void btn_open_arquivo_Click (View v)
	{
		openFileDialog.setTitle("Seleciona Arquivo");
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

	private void lancaParto()
	{
		Intent intent = new Intent(Menu_Principal_Activity.this, Parto_Activity.class);
		startActivity(intent);
	}

	private void novoPasto()
    {
		Intent pasto = new Intent(Menu_Principal_Activity.this, Pasto_Activity.class);
		startActivity(pasto);
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
			Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja atualizar os dados via Bluetooth?", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
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
		if (checksConnectionWeb())
		{
			if (validateServer(url))
			{
				Mensagem_Util.addMsg(Menu_Principal_Activity.this, "Aviso", "Deseja atualizar os dados via Web?", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Constantes.TIPO_ENVIO = "web";
						updatePastos();
						updateGrupoManejo();
						updateCriterios();
						updateAnimais();
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
		new Get_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
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
						new Post_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
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
					new Post_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
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
				new Post_Animais_JSON(Menu_Principal_Activity.this, ProgressDialog.STYLE_HORIZONTAL).execute();
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

	private void existCelular(List<Configuracao> listQRCode, Configuracao qrcode_tb)
	{
		if (listQRCode.size() != 0)
		{
			for (Configuracao conf_tb : listQRCode)
			{
				if (conf_tb.getTipo().equals(qrcode_tb.getTipo())
						&& conf_tb.getEndereco().equals(qrcode_tb.getEndereco()))
				{
					break;
				}
			}
		}
		else
		{
			loadLeitor();
		}
	}

	private void loadLeitor()
	{
		Intent intent = new Intent(Menu_Principal_Activity.this, Leitor_Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
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
		switch (item.getItemId())
		{
			case R.id.menu_dispositivos_pariados:
				searchPairedDevices();
				return false;

			case R.id.menu_habilitar_visibilidade:
				enableVisibility();
				return false;

			case R.id.menu_descoberta_dispositivo:
				discoverDevices();
				return false;

			case R.id.menu_esperar_conexao:
				waitConnection();
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

	public void searchPairedDevices()
	{
		Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
		startActivityForResult(searchPairedDevicesIntent,Constantes. SELECT_PAIRED_DEVICE);
	}

	public void discoverDevices()
	{
		Intent searchPairedDevicesIntent = new Intent(this, DiscoveredDevices.class);
		startActivityForResult(searchPairedDevicesIntent, Constantes.SELECT_DISCOVERED_DEVICE);
	}

	public void enableVisibility()
	{
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
		startActivity(discoverableIntent);
	}

	private String obterDiretorio()
	{
		File root = android.os.Environment.getExternalStorageDirectory();
		return root.toString();
	}

    private String createListAnimais () throws IOException
    {
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

    private void createFile() throws IOException
	{
        Date data = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        String filename = "backup.txt";
        String conteudo = "";

        File diretorio = new File(obterDiretorio(), "Prodap");

        if(!diretorio.exists())
		{
            diretorio.mkdir();
        }
        File arquivo = new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);

        FileOutputStream outputStream = null;

        try
        {
            if(!arquivo.exists())
			{
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

	public void waitConnection()
	{
		Constantes.CONNECT = new ConnectionThread();
		Constantes.CONNECT.start();
	}

    public void connection(String device)
    {
        Constantes.CONNECT = new ConnectionThread(device);
        Constantes.CONNECT.start();
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

        if(!btAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constantes.ENABLE_BLUETOOTH);
            Constantes.LBL_STATUS.setText("Solicitando ativação do Bluetooth...");
        }
        else
        {
            Constantes.LBL_STATUS.setText("Bluetooth já ativado");
        }
    }

	public void about()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Menu_Principal_Activity.this);
		builder.setMessage("Versão do Sistema: 06022017\n\n"+
				"Suporte: (31) 3555-0800\n"+
				"www.prodap.com.br\n"+
				"prodap@prodap.com.br\n\n"+
				"© 2016 Prodap\n"+
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

                connection(data.getStringExtra("btDevAddress"));
            }
            else
            {
                Constantes.LBL_STATUS.setText("Nenhum dispositivo selecionado");
            }
        }
    }
}
