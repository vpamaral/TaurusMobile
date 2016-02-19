package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.prodap.taurusmobile.adapter.Pasto_Adapter;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Pasto_Activity extends Activity {
    private EditText edt_pasto;
    private Button btn_salvar;
    private Pasto_Model pasto_model;
    private Pasto pasto_tb;
    private Pasto_Adapter p_helper;
    private Pasto_Adapter pasto_adapter;
    private List<Pasto> pasto_list;
    private Mensagem_Util md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pasto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            Log.i("PASTO", e.toString());
            Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Erro com a tela de Pasto.");
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
        pasto_model = new Pasto_Model(getBaseContext());
        pasto_tb    = new Pasto();
        pasto_adapter = new Pasto_Adapter();

    }

    private void insertPasto(Pasto pasto_tb) {
        try {
            //if (!edt_pasto.getText().toString().equals("") ) {
            pasto_tb.setNome(edt_pasto.getText().toString());
            pasto_model.validate(this, "Pasto", pasto_tb, Banco_Service.VALIDATION_TYPE_INSERT);
            pasto_model.insert(this, "Pasto", pasto_tb);
            Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Pasto cadastrado com sucesso.");
            edt_pasto.setText("");

        } catch (final Validator_Exception e) {
//            Log.i("PASTO", e.toString());
//            e.printStackTrace();
            if (e.getException_code() == Validator_Exception.MESSAGE_TYPE_QUESTION)
            {
                md.addMsg(Pasto_Activity.this, "Aviso" , e.getMessage(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (!e.getException_args().equals(0) && e.getException_args()[0].toString().equals("FLAG_")) {
//                        }
                    }
                });
                return;
            }
            else
            {
                md.addMsg(Message_Dialog.Yes, Pasto_Activity.this, e.getMessage(), "Aviso", 1);
                return;
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_pasto, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
