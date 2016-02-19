package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Parto_Cria_Model;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Grupo_Manejo;
import br.com.prodap.taurusmobile.tb.Leitor;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.tb.Pasto;
import br.com.prodap.taurusmobile.adapter.Parto_Adapter;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Configuracao_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.model.Pasto_Model;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;
import br.com.prodap.taurusmobile.util.Validator_Exception;

public class Parto_Activity extends Activity {

    private static final String[] PERDA = new String[]{"NENHUMA", "ABORTO", "NATIMORTO"
            , "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO"
            , "F.MUMIFICADO", "OUTRA"};

    private static final String[] SEXO = new String[]{"FÊMEA", "MACHO"};

    private static final String[] RACACRIA = new String[]{"Nelore", "Composto"};

    public String msg = "";
    private LinearLayout ll_identificador;
    private LinearLayout ll_sisbov;
    private LinearLayout ll_manejo;

    private Calendar calendario;
    private EditText editMatriz;
    private EditText editDataParto;
    private AutoCompleteTextView editBuscaPasto;
    private EditText editCodCria;
    private EditText editIdentificador;
    private EditText editSisbov;
    private AutoCompleteTextView editGrupoManejo;
    private EditText editPeso;
    private EditText editDataIdentificacao;
    private Button btnSalvar;
    private Button btnLeitorCodBarras;
    private Button btnLeitorCodBarra;
    private Spinner spinPerda;
    private Spinner spinSexo;
    private Spinner spinRacaCria;
    private CheckBox cbTipoParto;
    private CheckBox cbDescarte;
    private TextView txtidanimal;
    private String sisbov = null;
    private String identificador = null;
    private Animal_Model ani_model;
    private Animal animal_tb;
    private Parto_Model parto_model;
    private Parto_Cria_Model cria_model;
    private Configuracao_Model conf_model;
    private Configuracao conf_tb;
    private Parto parto_tb;
    private Parto_Cria cria_tb;
    private Parto_Adapter p_helper;
    private Parto_Cria_Adapter c_helper;
    private String strsis;
    private List<Configuracao> listConf;
    private List<Parto_Cria> listaCria;
    private Date dataParto = null;
    private boolean validaSisbov;
    private boolean validaIdentificador;
    private boolean validaManejo;
    private List<String> listaMatriz;
    private List<Animal> listaAnimal;
    private String cod_matriz_invalido;
    private Mensagem_Util md;

    public static boolean FLAG_CODIGO_MATRIZ_DUPLICADO;

    private Date data;
    private long id_pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parto);

        FLAG_CODIGO_MATRIZ_DUPLICADO = false;

        buscaPasto();
        buscaGrupoManejo();

        spinPerda = (Spinner) findViewById(R.id.spnPerda);
        ArrayAdapter<String> adpPerda = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PERDA);
        adpPerda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinPerda.setAdapter(adpPerda);

        spinSexo = (Spinner) findViewById(R.id.spnSexo);
        ArrayAdapter<String> adpSexo = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SEXO);
        adpSexo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinSexo.setAdapter(adpSexo);

        spinRacaCria = (Spinner) findViewById(R.id.spnRaca);
        ArrayAdapter<String> adpRaca = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, RACACRIA);
        adpRaca.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinRacaCria.setAdapter(adpRaca);

        cbTipoParto = (CheckBox) findViewById(R.id.cbTipoParto);
        cbDescarte = (CheckBox) findViewById(R.id.cbDescarte);

        calendario = Calendar.getInstance();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        data = new Date();

        final Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        final Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        final long id_animal;
        ani_model   = new Animal_Model(this);
        parto_model = new Parto_Model(this);
        cria_model  = new Parto_Cria_Model(this);
        conf_model  = new Configuracao_Model(this);
        p_helper    = new Parto_Adapter();
        c_helper    = new Parto_Cria_Adapter();
        parto_tb    = new Parto();
        cria_tb     = new Parto_Cria();
        conf_tb     = new Configuracao();
        animal_tb   = new Animal();

        editIdentificador       = (EditText) findViewById(R.id.edtIdentificador);
        editSisbov              = (EditText) findViewById(R.id.edtSisbov);
        editGrupoManejo         = (AutoCompleteTextView) findViewById(R.id.edtGrupoManejo);
        editMatriz              = (EditText) findViewById(R.id.edtMatriz);
        editDataParto           = (EditText) findViewById(R.id.edtDataParto);
        editBuscaPasto          = (AutoCompleteTextView) findViewById(R.id.edtBuscaPasto);
        editCodCria             = (EditText) findViewById(R.id.edtCria);
        editPeso                = (EditText) findViewById(R.id.edtPesoCria);
        txtidanimal             = (TextView) findViewById(R.id.id_animal);
        btnSalvar               = (Button) findViewById(R.id.btnSalvarParto);
        btnLeitorCodBarras      = (Button) findViewById(R.id.btnLeitorCodBarras);
        btnLeitorCodBarra       = (Button) findViewById(R.id.btnLeitorCodBarra);
        editDataIdentificacao   = (EditText) findViewById(R.id.edtDataIdentificacao);

        ll_identificador        = (LinearLayout) findViewById(R.id.ll_identificador);
        ll_sisbov               = (LinearLayout) findViewById(R.id.ll_sisbov);
        ll_manejo               = (LinearLayout) findViewById(R.id.ll_manejo);

        listaCria               = cria_model.selectAll(this, "Parto_Cria", cria_tb);
        listConf                = conf_model.selectAll(this, "Configuracao", conf_tb);
        listaAnimal             = ani_model.selectAll(this, "Animal", animal_tb);

        try {
            leitorCodBarras();

            if (listConf.get(0).getValida_identificador().equals("S")) {
                editIdentificador.setEnabled(true);
                ll_identificador.setVisibility(LinearLayout.VISIBLE);
                validaIdentificador = true;
                //ifIdentificadorAtivo(leitor);
            } else {
                editIdentificador.setEnabled(false);
                ll_identificador.setVisibility(LinearLayout.GONE);
                validaIdentificador = false;
            }

            if (listConf.get(0).getValida_sisbov().equals("S")) {
                editSisbov.setEnabled(true);
                ll_sisbov.setVisibility(LinearLayout.VISIBLE);
                validaSisbov = true;
                //ifSisbov(leitor);
            } else {
                editSisbov.setEnabled(false);
                ll_sisbov.setVisibility(LinearLayout.GONE);
                validaSisbov = false;
                editCodCria.setEnabled(true);
            }

            if (listConf.get(0).getValida_manejo().equals("S")) {
                editGrupoManejo.setEnabled(true);
                ll_manejo.setVisibility(LinearLayout.VISIBLE);
                validaManejo = true;
            } else {
                editGrupoManejo.setEnabled(false);
                ll_manejo.setVisibility(LinearLayout.GONE);
                validaManejo = false;
            }
        } catch (Exception e) {
            Log.i("LEITOR", e.toString());
            e.printStackTrace();
            Mensagem_Util.addMsg(Message_Dialog.Toast, this, "Não foi possível ler o código.");
        }
        editSisbov.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!editSisbov.getText().toString().equals("")) {
                        spinPerda.setEnabled(false);
                        if (!sisbovCorreto(editSisbov.getText().toString()) || editSisbov.length() < 15) {

                            Toast.makeText(Parto_Activity.this, "Sisbov inválido!", Toast.LENGTH_SHORT).show();
                            editSisbov.setFocusable(true);
                            return;

                        } else {
                            String codCria = editSisbov.getText().toString();
                            editCodCria.setText(codCria.substring(8, 14));
                        }
                    } else {
                        spinPerda.setEnabled(true);
                        editCodCria.setText("");
                    }
                }
            }
        });

        btnLeitorCodBarras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parto_Activity.this,
                        Leitor_Activity.class);
                startActivity(intent);
            }
        });

        btnLeitorCodBarra.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parto_Activity.this,
                        Leitor_Activity.class);
                startActivity(intent);
            }
        });

        editDataIdentificacao.setText(data_completa);

        editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final Animal animal;
                if (!hasFocus) {
                    if (editMatriz.getText().toString() != "") {
                        animal = ani_model.selectByCodigo(Parto_Activity.this, editMatriz.getText().toString());

                        //editRacaPai.setText(animal.getRaca_reprod());
                        txtidanimal.setText(String.valueOf(animal.getId_pk()));

                        listaMatriz = new ArrayList<String>();
                        for (Animal animalList : listaAnimal) {
                            if (animalList.getCodigo().equals(editMatriz.getText().toString())) {
                                listaMatriz.add(animalList.getCodigo());
                            }
                        }
                    }
                    matrizInvalida();
                    /*if (matrizInvalida().equals(true)) {
                        cod_matriz_invalido = editMatriz.getText().toString();
                    } else {
                        cod_matriz_invalido = "0";
                        editMatriz.clearFocus();
                        return;
                    }*/
                }
            }
        });

        editDataParto.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showCalendar();
                }
            }
        });

        try {
            btnSalvar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //String pasto = spinPasto.getSelectedItem().toString();
                    id_pk = data.getTime();

                    parto_tb.setId_pk(id_pk);
                    cria_tb.setId_fk_parto(id_pk);

                    listaMatriz = new ArrayList<String>();
                    for (Animal animalList : listaAnimal) {
                        if (animalList.getCodigo().equals(editMatriz.getText().toString())) {
                            listaMatriz.add(animalList.getCodigo());
                        }
                    }

                    if (listaAnimal.size() != 0) {

                        /*// validações temporarias
                        try {
                            dataParto = dateFormat.parse(editDataParto.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.i("PARTO_ACTIVITY", e.toString());
                        }
                        if (editIdentificador.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this,
                                    "É necessário preencher o Identificador da cria.", "Aviso", 1);
                            editIdentificador.requestFocus();
                        } else if (editSisbov.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this,
                                    "É necessário preencher o Sisbov de cria.", "Aviso", 1);
                            editSisbov.requestFocus();
                        } else if (editCodCria.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher o código da cria.", "Aviso", 1);
                            editCodCria.requestFocus();
                        } else if (editMatriz.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher o código matriz.", "Aviso", 1);
                            editMatriz.requestFocus();
                        } else if (editDataParto.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher a data do parto.", "Aviso", 1);
                        } else if (editGrupoManejo.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher o Grupo de Manejo.", "Aviso", 1);
                            editGrupoManejo.requestFocus();
                        } else if (editBuscaPasto.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher o Pasto.", "Aviso", 1);
                            editBuscaPasto.requestFocus();
                        } else if (editPeso.getText().toString().isEmpty()) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "É necessário preencher o peso de cria.", "Aviso", 1);
                        } else if (dataParto.after(data_atual)) {
                            Mensagem_Util.addMsg(Message_Dialog.Yes,
                                    Parto_Activity.this, "Data do parto não pode ser maior do que a data de identificação", "Aviso", 1);
                        } else {*/
                        parto_tb.setData_parto(editDataParto.getText().toString());
                        parto_tb.setPerda_gestacao(spinPerda.getSelectedItem().toString());
                        if (spinSexo.getSelectedItem() == "FÊMEA") {
                            parto_tb.setSexo_parto("FE");
                        } else {
                            parto_tb.setSexo_parto("MA");
                        }

                        if (!txtidanimal.getText().toString().equals("")) {
                            parto_tb.setId_fk_animal(Long.parseLong(txtidanimal.getText().toString()));
                            cria_tb.setId_fk_animal_mae(Long.parseLong(txtidanimal.getText().toString()));
                        }
                        cria_tb.setCodigo_cria(editCodCria.getText().toString());
                        cria_tb.setPeso_cria(editPeso.getText().toString());

                        List<Long> lista_cria = new ArrayList<Long>();
                        for (Parto_Cria p_cria : listaCria) {
                            if (p_cria.getId_fk_animal_mae() == parto_tb.getId_fk_animal()) {
                                lista_cria.add(p_cria.getId_fk_animal_mae());
                            }
                        }

                        if (cbTipoParto.isChecked()) {
                            cria_tb.setTipo_parto("GENETICA");
                        } else {
                            cria_tb.setTipo_parto("ESTIMADO");
                        }

                        if (cbDescarte.isChecked()) {
                            cria_tb.setRepasse("SIM");
                        } else {
                            cria_tb.setRepasse("NAO");
                        }

                        if (spinSexo.getSelectedItem() == "FÊMEA") {
                            cria_tb.setSexo("FE");
                        } else {
                            cria_tb.setSexo("MA");
                        }

                        cria_tb.setData_identificacao(editDataIdentificacao.getText().toString());
                        cria_tb.setRaca_cria(spinRacaCria.getSelectedItem().toString());

                        cria_tb.setIdentificador(editIdentificador.getText().toString());
                        if (validaSisbov == true) {
                            if (!editSisbov.getText().toString().equals("")) {
                                long sis = Long.parseLong(editSisbov.getText().toString());
                                String strsis = String.valueOf(sis);
                                cria_tb.setSisbov(strsis);
                            }
                        } else {
                            cria_tb.setSisbov(editSisbov.getText().toString());
                        }
                        cria_tb.setGrupo_manejo(editGrupoManejo.getText().toString());

                        cria_tb.setSync_status(0);
                        parto_tb.setSync_status(0);
                        cria_tb.setPasto(editBuscaPasto.getText().toString());

                        cria_tb.setCod_matriz_invalido(cod_matriz_invalido);

                        if (cria_tb.getId_fk_animal_mae() != 0) {
                            cria_tb.setCod_matriz_invalido("0");
                        } else {
                            cria_tb.setCod_matriz_invalido(editMatriz.getText().toString());
                        }

                        if (lista_cria.size() != 0) {
                            if (validate(cria_tb, listaCria)) {
                                criaGemelar(parto_tb, cria_tb);
                            } else {
                                Mensagem_Util.addMsg(Message_Dialog.Toast, getBaseContext(), msg);
                            }
                        } else if (validate(cria_tb, listaCria)) {
                            insertParto(parto_tb, cria_tb);
                        } else {
                            Mensagem_Util.addMsg(Message_Dialog.Toast, getBaseContext(), msg);
                        }
                    } else {
                        Mensagem_Util.addMsg(Message_Dialog.Yes, Parto_Activity.this
                                , "O Aparelho não foi preparado para o lançamento de Partos, favor propara-lo!.", "Aviso", 1);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buscaPasto() {
        List<Pasto> pasto_list;
        List<String> nome_pasto_list = new ArrayList<String>();
        Pasto pasto_tb = new Pasto();
        Pasto_Model pasto_model = new Pasto_Model(getBaseContext());
        try {
            pasto_list = pasto_model.selectAll(getBaseContext(), "Pasto", pasto_tb);

            for(Pasto p : pasto_list) {
                    nome_pasto_list.add(p.getNome().toString());
            }
        } catch (Exception e) {
            Log.i("BuscaPastos", e.toString());
            e.printStackTrace();
        }

        editBuscaPasto=(AutoCompleteTextView)findViewById(R.id.edtBuscaPasto);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nome_pasto_list);

        editBuscaPasto.setAdapter(adapter);
        editBuscaPasto.setThreshold(1);
    }

    private void buscaGrupoManejo() {
        List<Grupo_Manejo> grupo_list;
        List<String> codigo_grupo_list = new ArrayList<String>();
        Grupo_Manejo grupo_tb = new Grupo_Manejo();
        Grupo_Manejo_Model grupo_model = new Grupo_Manejo_Model(getBaseContext());
        try {
            grupo_list = grupo_model.selectAll(getBaseContext(), "Grupo_Manejo", grupo_tb);

            for (Grupo_Manejo g_tb : grupo_list) {
                codigo_grupo_list.add(g_tb.getCodigo().toString());
            }
        } catch (Exception e) {
            Log.i("BuscaGrupoManejo", e.toString());
            e.printStackTrace();
        }

        editGrupoManejo=(AutoCompleteTextView)findViewById(R.id.edtGrupoManejo);

        ArrayAdapter<String> grupo_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,codigo_grupo_list);

        editGrupoManejo.setAdapter(grupo_adapter);
        editGrupoManejo.setThreshold(1);
    }

    private void leitorCodBarras() {
        Intent intent   = getIntent();
        Leitor leitor   = (Leitor) intent.getSerializableExtra("leitor");

        if (leitor != null) {
            if (leitor.getTipo().equals("CODE_39")) {
                identificador = leitor.getScanResult();
                Menu_Principal_Activity.old_identificador = identificador;
                editIdentificador.setText(Menu_Principal_Activity.old_identificador);
            } else if (leitor.getTipo().equals("ITF")) {
                sisbov = leitor.getScanResult();
                Menu_Principal_Activity.old_sisbov = sisbov;
                long sis = Long.parseLong(Menu_Principal_Activity.old_sisbov);
                String strsis = String.valueOf(sis);
                editSisbov.setText(strsis);
                editCodCria.setText(strsis.substring(8, 14));
            }
            if (editIdentificador.getText().toString().equals("")) {
                editIdentificador.setText(Menu_Principal_Activity.old_identificador.toString());
            }
            if (editSisbov.getText().toString().equals("")) {
                if (Menu_Principal_Activity.old_sisbov != "") {
                    long sis = Long.parseLong(Menu_Principal_Activity.old_sisbov);
                    String strsis = String.valueOf(sis);
                    editSisbov.setText(strsis);
                    editCodCria.setText(strsis.substring(8, 14));
                }
            }
        }
    }

//    private void ifSisbov(Leitor leitor) {
//        if (leitor != null) {
//            if (leitor.getTipo().equals("ITF")) {
//                sisbov = leitor.getScanResult();
//                if (sisbov != null) {
//                    long sis = Long.parseLong(sisbov);
//                    String strsis = String.valueOf(sis);
//                    editSisbov.setText(strsis);
//                    editCodCria.setText(strsis.substring(8, 14));
//                    if (MenuPrincipalActivity.idold == null || MenuPrincipalActivity.idold == "") {
//                        MenuPrincipalActivity.idold = strsis;
//                    } else {
//                        editIdentificador.setText(MenuPrincipalActivity.idold);
//                    }
//                }
//            }
//        }
//    }
//
//    private void ifIdentificadorAtivo(Leitor leitor) {
//        if (leitor != null) {
//            if (leitor.getTipo().equals("CODE_39")) {
//                //identificador = resultCodBarras;
//                identificador = leitor.getScanResult();
//                if (identificador != null) {
//                    editIdentificador.setText(identificador);
//                    if (MenuPrincipalActivity.idold == null || MenuPrincipalActivity.idold == "") {
//                        MenuPrincipalActivity.idold = identificador;
//                    } else {
//                        long sis = Long.parseLong(MenuPrincipalActivity.idold);
//                        strsis = String.valueOf(sis);
//                        editSisbov.setText(MenuPrincipalActivity.idold);
//                        editCodCria.setText(MenuPrincipalActivity.idold.substring(8, 14));
//                    }
//                }
//            }
//        }
//    }

    private void ifIdentificadorSisbovManejoHide() {
        if (validaIdentificador == true) {
            if (editIdentificador.getText().toString().isEmpty()) {
                Mensagem_Util.addMsg(Message_Dialog.Yes,
                        Parto_Activity.this,
                        "É necessário preencher o Identificador da cria.", "Aviso", 1);
                return;
            }
        }
        if (validaSisbov == true) {
            if (editSisbov.getText().toString().isEmpty()) {
                Mensagem_Util.addMsg(Message_Dialog.Yes,
                        Parto_Activity.this,
                        "É necessário preencher o Sisbov de cria.", "Aviso", 1);
                return;
            }
        }
        if (validaManejo == true) {
            if (editGrupoManejo.getText().toString().isEmpty()) {
                Mensagem_Util.addMsg(Message_Dialog.Yes,
                        Parto_Activity.this,
                        "É necessário preencher o Grupo de Manejo.", "Aviso", 1);
                return;
            }
        }
    }

    public void insertParto(Parto parto_tb, Parto_Cria cria_tb) {
        try {
            cria_model.validate(this, "Parto_Cria", cria_tb, Banco_Service.VALIDATION_TYPE_INSERT);
            parto_model.validate(this, "Parto", parto_tb, Banco_Service.VALIDATION_TYPE_INSERT);
            parto_model.insert(Parto_Activity.this, "Parto", parto_tb);
            cria_model.insert(Parto_Activity.this, "Parto_Cria", cria_tb);
            writeInFile(p_helper.PartoArqHelper(parto_tb, cria_tb));
            Mensagem_Util.addMsg(Message_Dialog.Toast, Parto_Activity.this, "Parto cadastrado com sucesso!");
            zeraInterface();
        }catch (final Validator_Exception e) {
            if (e.getException_code() == Validator_Exception.MESSAGE_TYPE_QUESTION)
            {
                md.addMsg(Parto_Activity.this, "Aviso" , e.getMessage(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnSalvar.getBottom();
                        validateFlags(e);
                    }
                });
                return;
            }
            else
            {
                md.addMsg(Message_Dialog.Yes, Parto_Activity.this, e.getMessage(), "Aviso", 1);
                return;
            }
        }
    }

    private static void validateFlags(Validator_Exception e) {
        if (!e.getException_args().equals(0) && e.getException_args()[0].toString().equals("FLAG_CODIGO_MATRIZ_DUPLICADO")) {
            FLAG_CODIGO_MATRIZ_DUPLICADO = true;
        }
    }

    private void matrizInvalida() {
        final boolean[] validaMatriz = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (listaMatriz.size() == 0) {
            builder.setTitle("Aviso").setMessage("Matriz não existe na base de dados. Deseja continuar?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //cria_tb.setCod_matriz_invalido(cod_matriz_invalido);
                            validaMatriz[0] = true;
                            cod_matriz_invalido = editMatriz.getText().toString();
                            cria_tb.setId_fk_animal_mae(0);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            validaMatriz[0] = false;
                            editMatriz.setText("");
                        }
                    })
                    .show();
            if (validaMatriz[0] == true) {
                cod_matriz_invalido = editMatriz.getText().toString();
            } else {
                cod_matriz_invalido = "0";
                return;
            }
        }
    }

    private boolean writeInFile(String text)
    {
        BufferedReader input = null;
        File file = null;

        try{
            file = new File(Environment.getExternalStorageDirectory()+"/Prodap","backup.txt");
            FileOutputStream in = new FileOutputStream(file, true);
            in.write(text.getBytes());
            in.write("\n".getBytes());
            in.flush();
            in.close();

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean sisbovCorreto(String sisbov) {
        int valor = 0;
        int ultimo_digito;
        if (sisbov != "") {
            char[] cvetor = sisbov.toCharArray();

            if (cvetor.length < 15)
                return false;

            valor = ((Character.getNumericValue(sisbov.charAt(0)) * 7)) + (Character.getNumericValue(sisbov.charAt(1)) * 6) + (Character.getNumericValue(sisbov.charAt(2)) * 5);
            valor += ((Character.getNumericValue(sisbov.charAt(3)) * 4) + (Character.getNumericValue(sisbov.charAt(4)) * 3) + (Character.getNumericValue(sisbov.charAt(5)) * 2));
            valor += ((Character.getNumericValue(sisbov.charAt(6)) * 9) + (Character.getNumericValue(sisbov.charAt(7)) * 8) + (Character.getNumericValue(sisbov.charAt(8)) * 7));
            valor += ((Character.getNumericValue(sisbov.charAt(9)) * 6) + (Character.getNumericValue(sisbov.charAt(10)) * 5) + (Character.getNumericValue(sisbov.charAt(11)) * 4));
            valor += ((Character.getNumericValue(sisbov.charAt(12)) * 3) + (Character.getNumericValue(sisbov.charAt(13)) * 2));

            valor = valor % 11;
            if (valor <= 1) {
                ultimo_digito = 0;
            } else {
                ultimo_digito = (11 - valor);
            }


            if (ultimo_digito == Integer.valueOf(cvetor[sisbov.length() - 1] + "") && sisbov.length() == 15)
                return true;
        }
        return false;
    }

    public void criaGemelar(final Parto parto_tb, final Parto_Cria cria_tb) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso").setMessage("Matriz com cria já cadastrada deseja cadastrar "
                + "outra cria para essa Matriz?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        insertParto(parto_tb, cria_tb);
                    }
                })
                .setNegativeButton("Não", null)
                .show();


    }

    private boolean validate(Parto_Cria cria_tb, List<Parto_Cria> listaCria) {
        if (listaCria.size() != 0) {
            for (Parto_Cria cria : listaCria) {
                if (cria.getIdentificador().equals(cria_tb.getIdentificador())) {
                    msg = "Identificador da Cria não pode ser duplicado.\n";
                    return false;
                }
                if (cria.getSisbov().equals(cria_tb.getSisbov())) {
                    msg = "Sisbov da Cria não pode ser duplicado.\n";
                    return false;
                }
                if (cria.getCodigo_cria().equals(cria_tb.getCodigo_cria())) {
                    msg = "Codigo da Cria não pode ser duplicado.\n";
                    return false;
                }
                return true;
            }
            msg = "Nenhuma Cria foi lançada.";
            return false;
        } else {
            return true;
        }
    }

    public void showCalendar() {
        new DatePickerDialog(Parto_Activity.this, listner, calendario.get(Calendar.YEAR)
                , calendario.get(Calendar.MONTH)
                , calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener listner = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = String.format("%s-%s-%d", day <= 9 ? "0" + (day) : (day)
                    , ++month <= 9 ? "0" + (month) : (month)
                    , year);
            editDataParto.setText(date);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateVars();
    }

    private void updateVars() {
        Menu_Principal_Activity.old_sisbov = "";
        Menu_Principal_Activity.old_identificador = "";
    }

    public void zeraInterface() {
        updateVars();
        editMatriz.setText("");
//        editRacaPai.setText("");
        editCodCria.setText("");
        txtidanimal.setText("");
        editSisbov.setText("");
        editPeso.setText("");
       // editGrupoManejo.setText("");
        editIdentificador.setText("");
        editDataParto.setText("");
        editIdentificador.requestFocus();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
