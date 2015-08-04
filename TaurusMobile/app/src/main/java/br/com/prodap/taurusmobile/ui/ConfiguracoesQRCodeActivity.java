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
import android.widget.Button;
import android.widget.EditText;

public class ConfiguracoesQRCodeActivity extends Activity {

	public Button btnSalvar;
	public static EditText edtTipo;
	public static EditText edtEndereco;
	ConfiguracoesModel configuracaoModel;
	ConfiguracoesAdapter config_adapter;
	Configuracoes configuracaoTB;
	List<Configuracoes> listaconf;
	List<String> configuracoes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);

		btnSalvar = (Button) findViewById(R.id.btn_salvar);
		edtEndereco = (EditText) findViewById(R.id.edtEndereco);
		configuracaoModel = new ConfiguracoesModel(getBaseContext());
		config_adapter = new ConfiguracoesAdapter();
		configuracaoTB = new Configuracoes();
		configuracoes = new ArrayList<String>();

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ConfiguracoesQRCodeActivity.edtEndereco.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Toast, ConfiguracoesQRCodeActivity.this,"É necessário preencher o endereço do servidor.");
					edtEndereco.requestFocus();
				}

				else {
					configuracaoTB.setTipo("terminal");
					configuracaoTB.setEndereco(edtEndereco.getText().toString());
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
		edtEndereco.setText("");
		edtEndereco.requestFocus();
	}
}
