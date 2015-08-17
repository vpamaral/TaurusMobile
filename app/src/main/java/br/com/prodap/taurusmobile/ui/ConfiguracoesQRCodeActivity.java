package br.com.prodap.taurusmobile.ui;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ConfiguracoesQRCodeActivity extends Activity {

	private static final String[] Validar = new String[] { "SIM", "NÃO" };

	public Button btnSalvar;
	public static EditText edtTipo;
	public static Spinner spnIdentificador;
	public static Spinner spnManejo;
	public static EditText edtEndereco;
	ConfiguracoesModel configuracaoModel;
	ConfiguracoesAdapter config_adapter;
	Configuracoes configuracaoTB;
	List<Configuracoes> lista_conf;
	List<String> configuracoes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);

		spnIdentificador = (Spinner) findViewById (R.id.spnIdentificador);
		spnManejo = (Spinner) findViewById(R.id.spnManejo);

		ArrayAdapter<String> adpValidadaId = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Validar);
		adpValidadaId.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnIdentificador.setAdapter(adpValidadaId);


		ArrayAdapter<String> adpValidaManejo = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, Validar);
		adpValidaManejo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnManejo.setAdapter(adpValidaManejo);



		btnSalvar = (Button) findViewById(R.id.btn_salvar);
		edtEndereco = (EditText) findViewById(R.id.edtEndereco);
		configuracaoModel = new ConfiguracoesModel(getBaseContext());
		config_adapter = new ConfiguracoesAdapter();
		configuracaoTB = new Configuracoes();
		configuracoes = new ArrayList<String>();

		lista_conf = configuracaoModel.selectAll(getBaseContext(), "Configuracao", configuracaoTB);

		for (Configuracoes conf_tb : lista_conf) {
			edtEndereco.setText(conf_tb.getEndereco());
			//conf_tb.getValidaId();
			if (!conf_tb.getValidaManejo().equals("")) {
				if (conf_tb.getValidaManejo().equals("S")) {
					//spnManejo.setAdapter(adpValidadaId);
					spnManejo.setSelection(Integer.parseInt("SIM"));
				} else
					//spnManejo.setAdapter(adpValidadaId);
					spnManejo.setSelection(Integer.parseInt("NÂO"));
			}
			if (!conf_tb.getValidaId().equals("")) {
				if (conf_tb.getValidaManejo().equals("S")) {
					//spnManejo.setAdapter(adpValidadaId);
					spnManejo.setSelection(Integer.parseInt("SIM"));
				} else
					//spnManejo.setAdapter(adpValidadaId);
					spnManejo.setSelection(Integer.parseInt("NÂO"));
			}
		}

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			if (ConfiguracoesQRCodeActivity.edtEndereco.getText().toString().isEmpty()) {
				MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesQRCodeActivity.this,"É necessário preencher o endereço do servidor.");
				edtEndereco.requestFocus();
			}

			else {
				configuracaoTB.setEndereco(edtEndereco.getText().toString());
				configuracaoTB.setValidaId(spnIdentificador.getSelectedItem().toString());
				configuracaoTB.setValidaManejo((spnManejo.getSelectedItem().toString()));
				try {
					configuracaoModel.Update(ConfiguracoesQRCodeActivity.this, "Configuracao", config_adapter.configurarHelper(configuracaoTB));
					MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesQRCodeActivity.this, "Servidor atualizado com sucesso");
					zerarInterface();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}
	
	public void zerarInterface() {
		//edtEndereco.setText("");
		edtEndereco.requestFocus();
	}
}
