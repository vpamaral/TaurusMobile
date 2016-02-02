package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Configuracoes;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.tb.Leitor;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class ConfiguracoesActivity extends Activity {

	private static final String[] Validar = new String[] { "SIM", "NÃO" };

	private Button btnSalvar;
	private Button btnLeitorQRCode;
	private Spinner spnIdentificador;
	private Spinner spnManejo;
	private Spinner spnSisbov;
	private EditText edtEndereco;
	private ConfiguracoesAdapter c_helper;
	private Configuracoes c_tb;
	private ConfiguracoesModel qrcode_model;
	private ConfiguracoesModel configuracaoModel;
	private ConfiguracoesAdapter config_adapter;
	//private Configuracoes configuracaoTB;
	private List<Configuracoes> lista_conf;
	private List<String> configuracoes;
	private Leitor leitor;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);
		source();

		lista_conf = configuracaoModel.selectAll(getBaseContext(), "Configuracao", c_tb);

		for (Configuracoes conf_tb : lista_conf) {
			url = conf_tb.getEndereco();
			edtEndereco.setText(url);
			spnIdentificador.setSelection(conf_tb.getValida_identificador().equals("S") ? 0 : 1);
			spnManejo.setSelection(conf_tb.getValida_manejo().equals("S") ? 0 : 1);
			spnSisbov.setSelection(conf_tb.getValida_sisbov().equals("S") ? 0 : 1);
		}

		if (lista_conf.size() == 0) {
			edtEndereco.setText(leitor.getScanResult());
			c_tb.setEndereco(leitor.getScanResult());
			c_tb.setTipo(leitor.getTipo());
			c_tb.setValida_identificador("S");
			c_tb.setValida_manejo("S");
			c_tb.setValida_sisbov("S");

			insertConfiguracoes(c_tb);
		}

		if (leitor != null) {
			if (leitor.getScanResult().equals(url)) {
				edtEndereco.setText(url);
			} else {
				edtEndereco.setText(leitor.getScanResult());
			}
		}
		btnLeitorQRCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ConfiguracoesActivity.this,
						LeitorActivity.class);
				startActivity(intent);
			}
		});

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (edtEndereco.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesActivity.this
										,"É necessário preencher o endereço do servidor.");
					edtEndereco.requestFocus();
				}

				else {
					if (leitor != null) {
						c_tb.setEndereco(edtEndereco.getText().toString());
						c_tb.setTipo(leitor.getTipo());
						c_tb.setValida_identificador(spnIdentificador.getSelectedItem() == "SIM" ? "S" : "N");
						c_tb.setValida_manejo(spnManejo.getSelectedItem() == "SIM" ? "S" : "N");
						c_tb.setValida_sisbov(spnSisbov.getSelectedItem() == "SIM" ? "S" : "N");

						try {
							updateConfiguracoes(c_tb);
							MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesActivity.this, "Servidor atualizado com sucesso");
							carregaMenuPrincipal();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesActivity.this, "Servidor atualizado com sucesso");
					carregaMenuPrincipal();
				}
			}
		});
	}

	private void source() {
		c_tb 		 		= new Configuracoes();
		qrcode_model 	 	= new ConfiguracoesModel(this);
		c_helper 		 	= new ConfiguracoesAdapter();
		configuracaoModel 	= new ConfiguracoesModel(getBaseContext());
		config_adapter 		= new ConfiguracoesAdapter();
		configuracoes 		= new ArrayList<String>();
		spnIdentificador 	= (Spinner) findViewById (R.id.spnIdentificador);
		spnManejo 			= (Spinner) findViewById(R.id.spnManejo);
		spnSisbov        	= (Spinner) findViewById(R.id.spnSisbov);
		btnSalvar 			= (Button) findViewById(R.id.btn_salvar);
		btnLeitorQRCode 	= (Button) findViewById(R.id.btnLeitorQRCode);
		edtEndereco 		= (EditText) findViewById(R.id.edtEndereco);

		ArrayAdapter<String> adpValidaId = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Validar);
		adpValidaId.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnIdentificador.setAdapter(adpValidaId);

		ArrayAdapter<String> adpValidaManejo = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Validar);
		adpValidaManejo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnManejo.setAdapter(adpValidaManejo);

		ArrayAdapter<String> adpValidaSisbov = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Validar);
		adpValidaSisbov.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnSisbov.setAdapter(adpValidaSisbov);

		Intent intent  =  this.getIntent();
		leitor = (Leitor) intent.getSerializableExtra("leitor");

//		resultTipo   = intent.getStringExtra("tipo");
//		resultQRCode = intent.getStringExtra("QRCode");
	}

	private void insertConfiguracoes(Configuracoes c_tb) {
		qrcode_model.insert(getBaseContext(), "Configuracao", c_tb);
	}

	private void updateConfiguracoes(Configuracoes c_tb) {
		qrcode_model.update(getBaseContext(), "Configuracao", c_helper.configurarHelper(c_tb));
	}

	private void carregaMenuPrincipal() {
		Intent intent = new Intent(ConfiguracoesActivity.this, MenuPrincipalActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}*/
}
