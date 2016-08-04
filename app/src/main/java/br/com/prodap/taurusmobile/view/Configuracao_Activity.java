package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.List;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.adapter.Configuracao_Adapter;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Leitor;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Configuracao_Activity extends Activity {

	private Button btnSalvar;
	private Button btnLeitorQRCode;
	private CheckBox cbIdentificador;
	private CheckBox cbManejo;
	private CheckBox cbSisbov;
	private CheckBox cbCodAlternativo;
	private EditText edtEndereco;

	private Configuracao_Adapter c_adapter;
	private Configuracao c_tb;
	private Configuracao_Model c_model;
	private List<Configuracao> c_list;
	private Leitor leitor;
	private String tipo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);

		source();

		loadLeitor();

		btnSalvarClick();
	}

	private void btnSalvarClick() {
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
			public void onClick(View v) {
				Intent intent = new Intent(Configuracao_Activity.this,
						Leitor_Activity.class);
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
	}

	private void insertConfiguracao(Configuracao c_tb) {
		c_model.insert(getBaseContext(), "Configuracao", c_adapter.getDadosConfig(c_tb));
	}

	private void updateConfiguracao(Configuracao c_tb) {
		c_model.update(getBaseContext(), "Configuracao", c_adapter.getDadosConfig(c_tb));
	}

	private void loadMenuPrincipal() {
		Intent intent = new Intent(Configuracao_Activity.this, Menu_Principal_Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
}
