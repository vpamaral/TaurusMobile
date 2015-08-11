package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

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
                final String tipo   = scanResult.getFormatName();
                if ((result != null) && (scanResult.getFormatName().toString().contentEquals("CODE_39")) || (scanResult.getFormatName().toString().contentEquals("ITF")) ) {
                    Intent it = new Intent(getBaseContext(), PartoActivity.class);
                    it.putExtra("CodBarras", result);
                    it.putExtra("tipo", tipo);
                    startActivity(it);
                    finish();

                } else {
                    Toast.makeText(getBaseContext(), "Código inválido ou inexistente.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public void mataLeitor() {
        Intent newIntent = new Intent(LeitorActivity.this,PartoActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(newIntent);
        finish();
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
