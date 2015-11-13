package br.com.prodap.taurusmobile.ui;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.TB.Pasto;
import br.com.prodap.taurusmobile.adapter.PartoAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.model.PastoModel;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class PartoActivity extends Activity {

    private static final String[] PERDA = new String[]{"NENHUMA", "ABORTO", "NATIMORTO"
            , "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO"
            , "F.MUMIFICADO", "OUTRA"};

    private static final String[] SEXO = new String[]{"FÊMEA", "MACHO"};

    private static final String[] RACACRIA = new String[]{"Nelore", "Composto"};//, "Hereford"
            //, "Braford", "Angus", "Brangus", "Gir"};

    //private static final String[] TIPOPARTO = new String[]{"ESTIMADO", "GENETICA"};

    //private static final String[] PASTO = new String[]{"ACURIZAL", "AROEIRAS", "BODOQUENA"
    //        , "BOQUERÃO", "CAIEIRAS", "CAPÃO DA BONECA", "CRISTO", "MORADA NOVA", "PULGA"
     //       , "UNIÃO", "VISTA ALEGRE"};

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
    private EditText editGrupoManejo;
    private EditText editPeso;
    private EditText editDataIdentificacao;
    private Button btnSalvar;
    private Button btnLeitorCodBarras;
    private Button btnLeitorCodBarra;
    private Spinner spinPerda;
    private Spinner spinSexo;
    private Spinner spinRacaCria;
    //private Spinner spinTipoParto;
    private CheckBox cbTipoParto;
    private CheckBox cbDescarte;
    //private Spinner spinPasto;
    private TextView txtidanimal;
    private String sisbov = null;
    private String identificador = null;
    private AnimalModel ani_model;
    private PartoModel parto_model;
    private Parto_CriaModel cria_model;
    private ConfiguracoesModel conf_model;
    private Parto parto_tb;
    private Parto_Cria cria_tb;
    private PartoAdapter p_helper;
    private String strsis;
    private List<Configuracoes> listConf;
    private List<Parto_Cria> listaCria;
    private Date dataParto = null;
    private boolean validaSisbov;
    private boolean validaIdentificador;
    private boolean validaManejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parto);
        buscaPasto();

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

        //spinTipoParto = (Spinner) findViewById(R.id.spnTipoParto);
        /*ArrayAdapter<String> adpTipoParto = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TIPOPARTO);
        adpTipoParto.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinTipoParto.setAdapter(adpTipoParto);*/

        cbTipoParto = (CheckBox) findViewById(R.id.cbTipoParto);
        cbDescarte = (CheckBox) findViewById(R.id.cbDescarte);
        /*spinPasto = (Spinner) findViewById(R.id.spnPasto);
        ArrayAdapter<String> adpPasto = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PASTO);
        adpPasto.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinPasto.setAdapter(adpPasto);*/

        calendario = Calendar.getInstance();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date data = new Date();

        final Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        final Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        final long id_animal;
        ani_model = new AnimalModel(this);
        parto_model = new PartoModel(this);
        cria_model = new Parto_CriaModel(this);
        conf_model = new ConfiguracoesModel(this);
        p_helper = new PartoAdapter();
        final Parto parto_tb = new Parto();
        final Parto_Cria cria_tb = new Parto_Cria();

        editIdentificador = (EditText) findViewById(R.id.edtIdentificador);
        editSisbov = (EditText) findViewById(R.id.edtSisbov);
        editGrupoManejo = (EditText) findViewById(R.id.edtGrupoManejo);
        editMatriz = (EditText) findViewById(R.id.edtMatriz);
        editDataParto = (EditText) findViewById(R.id.edtDataParto);
        editBuscaPasto = (AutoCompleteTextView) findViewById(R.id.edtBuscaPasto);
        editCodCria = (EditText) findViewById(R.id.edtCria);
        editPeso = (EditText) findViewById(R.id.edtPesoCria);
        txtidanimal = (TextView) findViewById(R.id.id_animal);
        btnSalvar = (Button) findViewById(R.id.btnSalvarParto);
        btnLeitorCodBarras = (Button) findViewById(R.id.btnLeitorCodBarras);
        btnLeitorCodBarra = (Button) findViewById(R.id.btnLeitorCodBarra);
        editDataIdentificacao = (EditText) findViewById(R.id.edtDataIdentificacao);

        ll_identificador = (LinearLayout) findViewById(R.id.ll_identificador);
        ll_sisbov = (LinearLayout) findViewById(R.id.ll_sisbov);
        ll_manejo = (LinearLayout) findViewById(R.id.ll_manejo);

        Parto_Cria pc_tb = new Parto_Cria();
        listaCria = cria_model.selectAll(this, "Parto_Cria", pc_tb);

        Configuracoes conf_tb = new Configuracoes();
        listConf = conf_model.selectAll(this, "Configuracao", conf_tb);

        Animal animal_tb = new Animal();
        final List<Animal> listaAnimal = ani_model.selectAll(this, "Animal", animal_tb);

        try {
            Intent intent = this.getIntent();

            String resultCodBarras;
            String tipo;

            tipo = intent.getStringExtra("tipo");
            resultCodBarras = intent.getStringExtra("CodBarras");
            editCodCria.setText(intent.getStringExtra("editCodBarras"));

            if (listConf.get(0).getValida_identificador().equals("S")) {
                editIdentificador.setEnabled(true);
                ll_identificador.setVisibility(LinearLayout.VISIBLE);
                validaIdentificador = true;
                ifIdentificadorAtivo(resultCodBarras, tipo);
            } else {
                editIdentificador.setEnabled(false);
                ll_identificador.setVisibility(LinearLayout.GONE);
                validaIdentificador = false;
            }

            if (listConf.get(0).getValida_sisbov().equals("S")) {
                editSisbov.setEnabled(true);
                ll_sisbov.setVisibility(LinearLayout.VISIBLE);
                validaSisbov = true;
                ifSisbov(resultCodBarras, tipo);
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
            MensagemUtil.addMsg(MessageDialog.Toast, this, "Não foi possível ler o código.");
        }
        editSisbov.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!editSisbov.getText().toString().equals("")) {
                        if (!sisbovCorreto(editSisbov.getText().toString()) || editSisbov.length() < 15) {

                            Toast.makeText(PartoActivity.this, "Sisbov inválido!", Toast.LENGTH_SHORT).show();
                            editSisbov.setFocusable(true);
                            return;

                        } else {
                            String codCria = editSisbov.getText().toString();
                            editCodCria.setText(codCria.substring(8, 14));
                            spinPerda.setEnabled(false);
                        }
                    }
                }
            }
        });

        btnLeitorCodBarras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartoActivity.this,
                        LeitorActivity.class);
                startActivity(intent);
            }
        });

        btnLeitorCodBarra.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartoActivity.this,
                        LeitorActivity.class);
                startActivity(intent);
            }
        });

        editDataIdentificacao.setText(data_completa);

        editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final Animal animal;
                if (editMatriz.getText().toString() != "") {
                    animal = ani_model
                            .selectByCodigo(PartoActivity.this, editMatriz
                                    .getText().toString());

                    //editRacaPai.setText(animal.getRaca_reprod());
                    txtidanimal.setText(String.valueOf(animal.getId_pk()));
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

        if (!editIdentificador.getText().equals("") || !editSisbov.getText().equals("")
                && !editIdentificador.getText().equals(null) || !editSisbov.getText().equals(null)) {
            spinPerda.setEnabled(false);
        }

        try {

            btnSalvar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //String pasto = spinPasto.getSelectedItem().toString();
                    MenuPrincipalActivity.idold = null;
                    List<String> listaMatriz = new ArrayList<String>();
                    for (Animal animalList : listaAnimal) {
                        if (animalList.getCodigo().equals(editMatriz.getText().toString())) {
                            listaMatriz.add(animalList.getCodigo());
                        }
                    }

                    if (listaAnimal.size() != 0) {

                        // validações temporarias
                        //ifIdentificadorSisbovManejoHide();
                        try {
                            dataParto = dateFormat.parse(editDataParto.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (editIdentificador.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this,
                                    "É necessário preencher o Identificador da cria.", "Aviso", 1);
                            editIdentificador.requestFocus();
                        } else if (editSisbov.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this,
                                    "É necessário preencher o Sisbov de cria.", "Aviso", 1);
                            editSisbov.requestFocus();
                        } else if (editCodCria.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher o código da cria.", "Aviso", 1);
                            editCodCria.requestFocus();
                        } else if (editMatriz.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher o código matriz.", "Aviso", 1);
                            editMatriz.requestFocus();
                        } else if (editDataParto.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher a data do parto.", "Aviso", 1);
                        } else if (editGrupoManejo.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher o Grupo de Manejo.", "Aviso", 1);
                            editGrupoManejo.requestFocus();
                        } else if (editBuscaPasto.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher o Pasto.", "Aviso", 1);
                            editBuscaPasto.requestFocus();
                        } else if (editPeso.getText().toString().isEmpty()) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "É necessário preencher o peso de cria.", "Aviso", 1);
                        } else if (dataParto.after(data_atual)) {
                            MensagemUtil.addMsg(MessageDialog.Yes,
                                    PartoActivity.this, "Data do parto não pode ser maior do que a data de identificação", "Aviso", 1);
                        } else {
                            parto_tb.setData_parto(editDataParto.getText().toString());
                            parto_tb.setPerda_gestacao(spinPerda.getSelectedItem()
                                    .toString());
                            if (spinSexo.getSelectedItem() == "FÊMEA") {
                                parto_tb.setSexo_parto("FE");
                            } else {
                                parto_tb.setSexo_parto("MA");
                            }
                            parto_tb.setId_fk_animal(Long.parseLong(txtidanimal.getText().toString()));
                            cria_tb.setCodigo_cria(editCodCria.getText().toString());
                            cria_tb.setPeso_cria(editPeso.getText().toString());

                            List<Long> lista_cria = new ArrayList<Long>();
                            for (Parto_Cria p_cria : listaCria) {
                                if (p_cria.getId_fk_animal_mae() == parto_tb.getId_fk_animal()) {
                                    lista_cria.add(p_cria.getId_fk_animal_mae());
                                }
                            }

                            //cria_tb.setTipo_parto(spinTipoParto.getSelectedItem().toString());

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

                            //String racaCria = spinRacaCria.getSelectedItem().toString();
                            //cria_tb.setRepasse(racaCria.equals(editRacaPai.getText().toString()) ? "NAO" : "SIM");

                            if (spinSexo.getSelectedItem() == "FÊMEA") {
                                cria_tb.setSexo("FE");
                            } else {
                                cria_tb.setSexo("MA");
                            }
                            cria_tb.setId_fk_animal_mae(Long.parseLong(txtidanimal.getText().toString()));
                            cria_tb.setData_identificacao(editDataIdentificacao.getText().toString());
                            cria_tb.setRaca_cria(spinRacaCria.getSelectedItem().toString());

                            cria_tb.setIdentificador(editIdentificador.getText().toString());
                            if (validaSisbov == true) {
                                long sis = Long.parseLong(editSisbov.getText().toString());
                                String strsis = String.valueOf(sis);
                                cria_tb.setSisbov(strsis);
                            } else {
                                cria_tb.setSisbov(editSisbov.getText().toString());
                            }
                            cria_tb.setGrupo_manejo(editGrupoManejo.getText().toString());

                            cria_tb.setSync_status(0);
                            parto_tb.setSync_status(0);
                            cria_tb.setPasto(editBuscaPasto.getText().toString());

                            if (listaMatriz.size() == 0) {
                                alertMsg();
                                editMatriz.requestFocus();
                                return;
                            } else if (cria_tb.getId_fk_animal_mae() == 0) {
                                editMatriz.clearFocus();
                                return;
                            }

                            if (lista_cria.size() != 0) {
                                if (validate(cria_tb, listaCria)) {
                                    criaGemelar(parto_tb, cria_tb);
                                } else {
                                    MensagemUtil.addMsg(MessageDialog.Toast, getBaseContext(), msg);
                                    //Toast.makeText(PartoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } else if (validate(cria_tb, listaCria)) {
                                insertParto(parto_tb, cria_tb);
                            } else {
                                MensagemUtil.addMsg(MessageDialog.Toast, getBaseContext(), msg);
                                //Toast.makeText(PartoActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        MensagemUtil.addMsg(MessageDialog.Yes, PartoActivity.this
                                , "O Aparelho não foi preparado para o lançamento de Partos, favor propara-lo!.", "Aviso", 1);
                        //MensagemUtil.addMsg(MessageDialog.Toast, getBaseContext(), "O Celular não foi preparado para o lançamentos de Partos, favor propara-lo!.");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buscaPasto () {
        final List<Pasto> pasto_list;
        final List<String> nome_pasto_list = new ArrayList<String>();
        final Pasto pasto_tb = new Pasto();
        PastoModel pasto_model = new PastoModel(getBaseContext());
        //PastoAdapter pasto_adapter = new PastoAdapter();

        pasto_list = pasto_model.selectAll(getBaseContext(), "Pasto", pasto_tb);
        //pasto_adapter = new PastoAdapter(pasto_list, this);

        for(Pasto p : pasto_list) {
                nome_pasto_list.add(p.getPasto().toString());
        }
        editBuscaPasto=(AutoCompleteTextView)findViewById(R.id.edtBuscaPasto);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nome_pasto_list);

        editBuscaPasto.setAdapter(adapter);
        editBuscaPasto.setThreshold(1);
    }

    private void ifSisbov(String resultCodBarras, String tipo) {
        if (resultCodBarras != null && resultCodBarras != "") {
            if (tipo.equals("ITF")) {
                sisbov = resultCodBarras;
                if (sisbov != null) {
                    long sis = Long.parseLong(sisbov);
                    String strsis = String.valueOf(sis);
                    editSisbov.setText(strsis);
                    editCodCria.setText(strsis.substring(8, 14));
                    if (MenuPrincipalActivity.idold == null || MenuPrincipalActivity.idold == "") {
                        MenuPrincipalActivity.idold = strsis;
                    } else {
                        editIdentificador.setText(MenuPrincipalActivity.idold);
                    }
                }
            }
        }
    }

    private void ifIdentificadorAtivo(String resultCodBarras, String tipo) {
        if (resultCodBarras != null && resultCodBarras != "") {
            if (tipo.equals("CODE_39")) {
                identificador = resultCodBarras;

                if (identificador != null) {
                    editIdentificador.setText(identificador);
                    if (MenuPrincipalActivity.idold == null || MenuPrincipalActivity.idold == "") {
                        MenuPrincipalActivity.idold = identificador;
                    } else {
                        long sis = Long.parseLong(MenuPrincipalActivity.idold);
                        strsis = String.valueOf(sis);
                        editSisbov.setText(MenuPrincipalActivity.idold);
                        editCodCria.setText(MenuPrincipalActivity.idold.substring(8, 14));
                    }
                }
            }
        }
    }

    private void ifIdentificadorSisbovManejoHide() {
        if (validaIdentificador == true) {
            if (editIdentificador.getText().toString().isEmpty()) {
                MensagemUtil.addMsg(MessageDialog.Yes,
                        PartoActivity.this,
                        "É necessário preencher o Identificador da cria.", "Aviso", 1);
                return;
            }
        }
        if (validaSisbov == true) {
            if (editSisbov.getText().toString().isEmpty()) {
                MensagemUtil.addMsg(MessageDialog.Yes,
                        PartoActivity.this,
                        "É necessário preencher o Sisbov de cria.", "Aviso", 1);
                return;
            }
        }
        if (validaManejo == true) {
            if (editGrupoManejo.getText().toString().isEmpty()) {
                MensagemUtil.addMsg(MessageDialog.Yes,
                        PartoActivity.this,
                        "É necessário preencher o Grupo de Manejo.", "Aviso", 1);
                return;
            }
        }
    }

    public void insertParto(Parto parto_tb, Parto_Cria cria_tb) {
        parto_model.insert(PartoActivity.this, "Parto", p_helper.PartoHelper(parto_tb));
        cria_model.insert(PartoActivity.this, "Parto_Cria", cria_tb);
        writeInFile(p_helper.PartoArqHelper(parto_tb, cria_tb));
            //MensagemUtil.addMsg(MessageDialog.Toast, PartoActivity.this, "Arquivo preenchido com sucesso!");
        MensagemUtil.addMsg(MessageDialog.Toast, PartoActivity.this, "Parto cadastrado com sucesso!");
        zeraInterface();
    }

    private void alertMsg() {
        MensagemUtil.addMsg(MessageDialog.Yes,
                PartoActivity.this, "Matriz não existe na base de dados!", "Aviso", 1);
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
        new DatePickerDialog(PartoActivity.this, listner, calendario.get(Calendar.YEAR)
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
        MenuPrincipalActivity.idold = null;
    }

    public void zeraInterface() {
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
