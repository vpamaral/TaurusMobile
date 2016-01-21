package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Leitor;
import br.com.prodap.taurusmobile.util.Constantes;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;


public class LeitorActivity extends Activity {
    private Leitor leitor;
    private MensagemUtil md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitor);
        leitor = new Leitor();
        IntentIntegrator.initiateScan(this, R.layout.activity_leitor, R.id.viewfinder_view, R.id.preview_view, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                leitor.setTipo(scanResult.getFormatName());
                leitor.setScanResult(scanResult.getContents());
                if (leitor.getTipo() != null) {
                    if (leitor.getTipo().equals("CODE_39") || leitor.getTipo().equals("ITF")) {
                        sendResultCodBarras(leitor);
                    } else if (leitor.getTipo().contentEquals("QR_CODE")) {
                        sendResultQRCode(leitor);
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

    public void sendResultCodBarras(Leitor leitor) {
        if (leitor.getTipo() != null) {
            if (leitor.getTipo().contentEquals("CODE_39") || leitor.getTipo().contentEquals("ITF")) {
                if (leitor.getScanResult().length() > 0 && leitor.getScanResult().length() < 15) {
                    Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                                        "O leitor não conseguir ler todo o código de barras!");
                } else {
                    if (!validaIdentificador(leitor.getScanResult())) {
                        Intent intent = new Intent(getBaseContext(), PartoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("leitor", leitor);
                        startActivity(intent);
                        finish();
                    }
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

    public boolean validaIdentificador(String result){
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '%') {
                Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                MensagemUtil.addMsg(MessageDialog.Toast, LeitorActivity.this,
                        "O código de barras do Identificador da cria " + result + " é inválido.");
                return true;
                //break;
            }
        }
        return false;
    }

    //http://192.168.1.19/TaurusWebService/TaurusService.svc/
    public void sendResultQRCode(Leitor leitor) {
        String result = "";
        int count = 0;
        List<char[]> val = new ArrayList<>();
        if (leitor.getScanResult() != null) {
            if (leitor.getTipo().contentEquals("QR_CODE")) {
                for (int i = 0; i < leitor.getScanResult().length(); i++) {
                    if (leitor.getScanResult().charAt(i) == '/') {
                        count++;
                    }
                    if (count >= 3) {
                        char[] url = new char[]{leitor.getScanResult().charAt(i)};
                        val.add(url);
                    }
                }

                for (char[] c : val) {
                    result += String.valueOf(c);
                }

                if (count == 5 && result.equals(Constantes.TAURUS_URL)) {
                    Intent intent = new Intent(getBaseContext(), ConfiguracoesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("leitor", leitor);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    md.addMsg(MessageDialog.Toast, LeitorActivity.this, "O URL do Servidor está inválido!");
                }
            } else {
                Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                md.addMsg(MessageDialog.Toast, LeitorActivity.this, "Código inválido!\nÉ esperado um QR_CODE!");
            }
        } else {
            onBackPressed();
        }
    }
}
