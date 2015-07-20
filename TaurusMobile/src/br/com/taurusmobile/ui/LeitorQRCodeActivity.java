package br.com.taurusmobile.ui;

import br.com.taurusmobile.TB.Configuracoes;
import br.com.taurusmobile.adapter.ConfiguracoesAdapter;
import br.com.taurusmobile.model.ConfiguracoesModel;
import br.com.taurusmobile.util.MensagemUtil;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * @author Prodap
 *
 */

public class LeitorQRCodeActivity extends Activity {
	
	private String dbResult = "";
	private IntentResult scanResult;
	Configuracoes qrConfiguracaoTB;
	ConfiguracoesModel qrConfiguracaoModel;
	ConfiguracoesAdapter qrConfiguracaoAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		existCelular();
	}

	private void existCelular() {
		carregaTela(dbResult);
	}

	private void carregaTela(String Result) {
		if (Result.equals("") || Result.equals(null)) {
			setContentView(R.layout.activity_leitor_qrcode);
			IntentIntegrator.initiateScan(this,
					R.layout.activity_leitor_qrcode,
					R.id_capture.viewfinder_view, R.id_capture.preview_view,
					true);
		} else {
			Intent intent = new Intent(LeitorQRCodeActivity.this,
					MenuPrincipalActivity.class);
			startActivity(intent);
			
			LeitorQRCodeActivity.this.finish();
		}
	}
	
	private void configuracoesInsert(String url){
		qrConfiguracaoTB = new Configuracoes();
		qrConfiguracaoAdapter = new ConfiguracoesAdapter();
		qrConfiguracaoModel = new ConfiguracoesModel(this);
		qrConfiguracaoTB.setEndereco(url);
		qrConfiguracaoTB.setTipo("terminal");
		
		qrConfiguracaoModel.insert(this, "Configuracao", qrConfiguracaoAdapter.configurarHelper(qrConfiguracaoTB));
		MensagemUtil.addMsg(LeitorQRCodeActivity.this, "Aguarde", "Salvando QR Code");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			
		
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE:
			scanResult = IntentIntegrator.parseActivityResult(requestCode,
					resultCode, data);
			final String result = scanResult.getContents();
			dbResult = result;
			configuracoesInsert("http://192.168.0.150/TaurusWebService/TaurusService.svc/");
			Intent intent = new Intent(LeitorQRCodeActivity.this,
					MenuPrincipalActivity.class);
			MensagemUtil.addMsg(LeitorQRCodeActivity.this, "Aguarde", "Salvando QR Code");
			startActivity(intent);
			
			/*if (result.equals(dbResult)
					&& (scanResult.getFormatName().toString()
							.contentEquals("QR_CODE"))) {
				carregaTela(result);
			}*/
			//break;
		default:
		}
		Log.i("Inseriu", "QR Code");
	  } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
