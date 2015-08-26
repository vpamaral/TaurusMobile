package br.com.prodap.taurusmobile.ui;

import java.util.List;

import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;

public class LeitorQRCodeActivity extends Activity {
	private IntentResult scanResult;
	private ConfiguracoesAdapter c_helper;
	private Configuracoes qrcode_tb;
	private ConfiguracoesModel qrcode_model;
	private String urlServidor;
	private String tipo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		source();
		//gravaConfiguracoes("http://192.168.0.150/TaurusWebService/TaurusService.svc/", "QR_CODE");
		getCelular();		
	}
	
	private void getCelular() {
		String urlServidor = null;
		String tipo = null;
		
		List<Configuracoes> listQRCode = qrcode_model.selectAll(this, "Configuracao", qrcode_tb);
		for (Configuracoes qrcode_tb : listQRCode){
			urlServidor = qrcode_tb.getEndereco();
			tipo 		= qrcode_tb.getTipo();
			break;
		}
		
		if (urlServidor != null && tipo != null){
			existCelular(urlServidor, tipo);
		} else {
			carregaLeitorQRCode();
		}
	}
	
	private void source() {
		qrcode_tb 		= new Configuracoes();
		qrcode_model 	= new ConfiguracoesModel(this);
		c_helper 		= new ConfiguracoesAdapter();
	}
	
	private void existCelular(String urlServidor, String tipo) {
		List<Configuracoes> listQRCode = qrcode_model.selectAll(this, "Configuracao", qrcode_tb);
		for (Configuracoes conf : listQRCode) {
			if (conf.getTipo().equals(tipo) && conf.getEndereco().equals(urlServidor)) {
				carregaMenuPrincipal();
			} 
		}
	}
	
	private void carregaLeitorQRCode() {
		setContentView(R.layout.activity_leitor);
		IntentIntegrator.initiateScan(this,
				R.layout.activity_leitor,
				R.id.viewfinder_view, R.id.preview_view,
				false);
	}

	private void carregaMenuPrincipal() {
		Intent intent = new Intent(LeitorQRCodeActivity.this,
				MenuPrincipalActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE:
			scanResult = IntentIntegrator.parseActivityResult(requestCode,
					resultCode, data);
			urlServidor = scanResult.getContents();
			tipo 		= scanResult.getFormatName();

			int count = 0;
			if (urlServidor != null && tipo != null) {
				if (tipo.contentEquals("QR_CODE") ) {
					for (int i = 0; i < urlServidor.length(); i++) {
						if (urlServidor.charAt(i) == '/') {
							count++;
						}
					}
					if (count != 5) {
						Intent intent = new Intent(getBaseContext(), LeitorQRCodeActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						MensagemUtil.addMsg(MessageDialog.Toast, LeitorQRCodeActivity.this,
								"O URL do Servidor está inválido!\nPosicione o leitor novamente.");
					} else {
						Intent intent = new Intent(getBaseContext(), MenuPrincipalActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//intent.putExtra("QRCode", urlServidor);
						//intent.putExtra("tipo", tipo);
						gravaConfiguracoes(urlServidor, tipo);
						existCelular(urlServidor, tipo);
						startActivity(intent);
						finish();
					}
				} else {
					Intent intent = new Intent(getBaseContext(), LeitorQRCodeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					MensagemUtil.addMsg(MessageDialog.Toast, LeitorQRCodeActivity.this,
							"Código inválido!\nÉ esperado um QR_CODE!");
				}
			} else {

				onBackPressed();
			}
			break;
		default:			
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	private void gravaConfiguracoes(String urlServidor, String tipo) {
		qrcode_tb.setEndereco(urlServidor);
		qrcode_tb.setTipo(tipo);
		qrcode_tb.setValidaId("S");
		qrcode_tb.setValidaManejo("S");

		qrcode_model.insert(this, "Configuracao",
				c_helper.configurarHelper(qrcode_tb));
	}
}
