package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.helper.Configuracao_Helper;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Configuracao_Activity extends Activity
{
	private Configuracao c_tb;
	private Configuracao_Helper c_helper;
	private Configuracao_Model c_model;
	private Mensagem_Util md;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_configuracao);

		source();

		setTitle("Configuração do Sistema");

		Intent intent   = getIntent();
		c_tb            = (Configuracao) intent.getSerializableExtra("c_tb");

		if (c_tb != null)
		{
			c_helper.FillForm(this, c_tb);
		}
	}

	private void source()
	{
		c_helper  = new Configuracao_Helper(this);
		c_tb      = new Configuracao();
		c_model   = new Configuracao_Model(this);
	}

	/*private void btnSalvarClick() {
		btnSalvar.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				try
				{
					if (c_list.size() == 0)
					{
						insertConfiguracao(getConfig());
						Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this, "Dados inserido com sucesso");
						loadMenuPrincipal();
					}
					else
					{
						updateConfiguracao(getConfig());
						Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this, "Dados atualizado com sucesso");
						loadMenuPrincipal();
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void loadLeitor()
	{
		btnLeitorQRCode.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Configuracao_Activity.this, Leitor_Activity.class);
				startActivity(intent);
			}
		});
	}

	private Configuracao getConfig()
	{
		c_tb.setEndereco(edtEndereco.getText().toString());
		c_tb.setTipo(tipo);
		c_tb.setValida_identificador(cbIdentificador.isChecked() ? "S" : "N");
		c_tb.setValida_manejo(cbManejo.isChecked() ? "S" : "N");
		c_tb.setValida_sisbov(cbSisbov.isChecked() ? "S" : "N");
		c_tb.setValida_cod_alternativo(cbCodAlternativo.isChecked() ? "S" : "N");

		return c_tb;
	}

	private void loadConfig(Configuracao config_tb)
	{
		edtEndereco.setText(config_tb.getEndereco().toString());
		tipo = config_tb.getTipo().toString();
		if (config_tb.getValida_identificador().equals("S")) cbIdentificador.setChecked(true);
		if (config_tb.getValida_sisbov().equals("S")) cbSisbov.setChecked(true);
		if (config_tb.getValida_manejo().equals("S")) cbManejo.setChecked(true);
		if (config_tb.getValida_cod_alternativo().equals("S")) cbCodAlternativo.setChecked(true);
	}

	private void source()
	{
		c_tb	 		 	= new Configuracao();
		c_model 	 		= new Configuracao_Model(this);
		c_adapter 		 	= new Configuracao_Adapter();

		cbIdentificador 	= (CheckBox) findViewById (R.id.cbIdentificador);
		cbManejo 			= (CheckBox) findViewById(R.id.cbManejo);
		cbSisbov        	= (CheckBox) findViewById(R.id.cbSisbov);
		cbCodAlternativo	= (CheckBox) findViewById(R.id.cbCodALternativo);
		btnSalvar 			= (Button) findViewById(R.id.btn_salvar);
		btnLeitorQRCode 	= (Button) findViewById(R.id.btnLeitorQRCode);
		edtEndereco 		= (EditText) findViewById(R.id.edtEndereco);

		Intent intent  		=  this.getIntent();
		leitor 				= (Leitor) intent.getSerializableExtra("leitor");
		c_list 				= c_model.selectAll(getBaseContext(), "Configuracao", c_tb);

		for (Configuracao config_tb : c_list) {
			loadConfig(config_tb);
		}

		if (leitor != null)
		{
			if (edtEndereco.getText().toString().equals(""))
			{
				btnSalvar.setText("Salvar Dados do Servidor");
			}

			edtEndereco.setText(leitor.getScanResult());
			tipo = leitor.getTipo();

			c_tb = getConfig();
		}
		//metodo para não deixar abrir o teclado do aparelho.
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}*/

	/*private void insertConfiguracao(Configuracao c_tb)
	{
		c_model.insert(getBaseContext(), "Configuracao", c_adapter.getDadosConfig(c_tb));
	}

	private void updateConfiguracao(Configuracao c_tb)
	{
		c_model.update(getBaseContext(), "Configuracao", c_adapter.getDadosConfig(c_tb));
	}*/

	private void loadMenuPrincipal()
	{
		Intent intent = new Intent(Configuracao_Activity.this, Menu_Principal_Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void btn_confirmar_Click(View v)
	{
		c_tb      = c_helper.getConfig(this);
		newConfiguracao(c_tb);
	}

	public void newConfiguracao(final Configuracao c_tb)
	{
		try
		{
			if (c_tb.getId_auto() != 0)
			{
				c_model.validate(this, "Configuracao", c_tb, Constantes.VALIDATION_TYPE_UPDATE);
				updateConfiguracao(c_tb);
				Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this
						, "Dados atualisados com sucesso.");
                loadMenuPrincipal();
			}
			else
			{
				c_tb.setId_auto(1);
				c_model.validate(this, "Configuracao", c_tb, Constantes.VALIDATION_TYPE_INSERT);
				insertConfiguracao(c_tb);
				Mensagem_Util.addMsg(Message_Dialog.Toast, Configuracao_Activity.this, "Dados inserido com sucesso");
                loadMenuPrincipal();
			}
		}
		catch (final Validator_Exception e)
		{
			if (e.getException_code() == Validator_Exception.MESSAGE_TYPE_QUESTION)
			{
				md.addMsg(Configuracao_Activity.this, "Aviso" , e.getMessage(), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

					}
				});
				return;
			}
			else
			{
				md.addMsg(Message_Dialog.Yes, Configuracao_Activity.this, e.getMessage(), "Aviso", 1);
				return;
			}
		}
	}

	private void insertConfiguracao(Configuracao c_tb)
	{
		c_model = new Configuracao_Model(this);
		c_model.insert(this, "Configuracao", c_helper.getDadosConfiguracao(c_tb));
	}

	private void updateConfiguracao(Configuracao c_tb)
	{
		c_model = new Configuracao_Model(this);
		c_model.update(this, "Configuracao", c_helper.getDadosConfiguracao(c_tb));
	}

    public void btn_leitor_qrcode_Click(View v)
    {
        Intent intent = new Intent(Configuracao_Activity.this, Leitor_Activity.class);
        startActivity(intent);
    }

	public void btn_cancelar_Click(View v)
	{
		finish();
	}
}
