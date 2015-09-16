package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;


public class LeitorActivity extends Activity {

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
                if (tipo != null) {
                    if (tipo.contentEquals("CODE_39") || tipo.contentEquals("ITF")) {
                        sendResultCodBarras(result, tipo);
                    } else if (tipo.contentEquals("QR_CODE")) {
                        sendResultQRCode(result, tipo);
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

    public void sendResultCodBarras(String result, String tipo) {
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

    public void sendResultQRCode(String urlServidor, String tipo) {
        int count = 0;
        if (urlServidor != null && tipo != null) {
            if (tipo.contentEquals("QR_CODE")) {
                for (int i = 0; i < urlServidor.length(); i++) {
                    if (urlServidor.charAt(i) == '/') {
                        count++;
                    }
                }
                if (count != 5) {
                    Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                            "O URL do Servidor está inválido!\nPosicione o leitor novamente.");
                } else {
                    Intent intent = new Intent(getBaseContext(), ConfiguracoesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("QRCode", urlServidor);
                    intent.putExtra("tipo", tipo);
                    startActivity(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                        "Código inválido!\nÉ esperado um QR_CODE!");
            }
        } else {
            onBackPressed();
        }
    }
    /*private boolean thereServer() {
        List<Configuracoes> lista_conf;
        ConfiguracoesModel configuracao_model 	= new ConfiguracoesModel(this);
        Configuracoes configuracao_tb 	= new Configuracoes();
        lista_conf = configuracao_model.selectAll(getBaseContext(), "Configuracao", configuracao_tb);

        if (lista_conf.size() != 0) {
            thereServer = true;
        } else {
            thereServer = false;
        }
        return thereServer;
    }*/
/*
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
    }*/
}
