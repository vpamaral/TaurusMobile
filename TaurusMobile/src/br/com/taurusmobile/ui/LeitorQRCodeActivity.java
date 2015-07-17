package br.com.taurusmobile.ui;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author Prodap
 *
 */

public class LeitorQRCodeActivity extends Activity {
	private String dbResult = "";
	private IntentResult scanResult;

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE:
			scanResult = IntentIntegrator.parseActivityResult(requestCode,
					resultCode, data);
			final String result = scanResult.getContents();
			dbResult = result;
			if (result.equals(dbResult)
					&& (scanResult.getFormatName().toString()
							.contentEquals("QR_CODE"))) {
				carregaTela(result);
			}
			break;
		default:
		}
	}
}
