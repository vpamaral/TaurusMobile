package br.com.prodap.taurusmobile.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.prodap.taurusmobile.TB.Pasto;
import br.com.prodap.taurusmobile.adapter.PastoAdapter;
import br.com.prodap.taurusmobile.model.PastoModel;
import br.com.prodap.taurusmobile.ui.R;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class PastoActivity extends Activity {
    private EditText edt_pasto;
    private Button btn_salvar;
    private PastoModel pasto_model;
    private Pasto pasto_tb;
    private PastoAdapter pasto_adapter;
    private List<Pasto> pasto_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pasto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.i("PASTO", e.toString());
            MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro com a tela de Pasto.");
        }
        source();
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPasto(pasto_tb);
            }
        });
    }

    private void source() {
        edt_pasto = (EditText) findViewById(R.id.edt_pasto);
        btn_salvar = (Button) findViewById(R.id.btn_salvar_pasto);
        pasto_model = new PastoModel(getBaseContext());
        pasto_tb    = new Pasto();
        pasto_adapter = new PastoAdapter();

    }

    private void insertPasto(Pasto pasto_tb) {
        try {
            if (!edt_pasto.getText().toString().equals("") ) {
                pasto_tb.setPasto(edt_pasto.getText().toString());
                pasto_model.insert(getBaseContext(), "Pasto", pasto_tb);
                MensagemUtil.addMsg(MessageDialog.Toast, this, "Pasto cadastrado com sucesso.");
            } else {
                MensagemUtil.addMsg(MessageDialog.Toast, this, "O Campo nome não pode ser vazio.");
                return;
            }
        } catch (Exception e) {
            Log.i("PASTO", e.toString());
            e.printStackTrace();
            MensagemUtil.addMsg(MessageDialog.Toast, this, "Erro ao Cadastrar o Pasto.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pasto, menu);
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
