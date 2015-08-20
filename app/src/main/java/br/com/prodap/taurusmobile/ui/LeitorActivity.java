package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;


public class LeitorActivity extends Activity {

    //private IntentResult scanResult;
    //private String tipo;
    //private String cod_barras;
    //private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitor);
        IntentIntegrator.initiateScan(this,
                R.layout.activity_leitor,
                R.id.viewfinder_view, R.id.preview_view,
                false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                final String result = scanResult.getContents();
                final String tipo = scanResult.getFormatName();
                    sendResult(result, tipo);
                break;
            default:
        }
    }

    public void sendResult(String result, String tipo) {
        if (result != null || tipo != null) {
            if (tipo.contentEquals("CODE_39") || tipo.contentEquals("ITF")) {
                if (result.length() > 0 && result.length() < 15) {
                    Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                                        "O leitor não conseguir ler todo o código de barras!");
                } else {
                    Intent intent = new Intent(getBaseContext(), PartoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("CodBarras", result);
                    intent.putExtra("tipo", tipo);
                    startActivity(intent);
                    finish();
                }
            } else {
                MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                                    "Código inválido!\nÉ esperado um Identificador ou Sisbov!");
            }
        } else {
            Intent intent = new Intent(getBaseContext(), PartoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
