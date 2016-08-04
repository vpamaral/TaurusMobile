package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.adapter.Parto_Cria_Adapter;
import br.com.prodap.taurusmobile.bluetooth.Bluetooth_Activity;
import br.com.prodap.taurusmobile.model.Criterio_Model;
import br.com.prodap.taurusmobile.model.Grupo_Manejo_Model;
import br.com.prodap.taurusmobile.model.Parto_Cria_Model;
import br.com.prodap.taurusmobile.service.Banco_Service;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.tb.Configuracao;
import br.com.prodap.taurusmobile.tb.Criterio;
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

public class Parto_Activity extends Activity
{
    private static final String[] PERDA = new String[]{"NENHUMA", "ABORTO", "NATIMORTO"
            , "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO"
            , "F.MUMIFICADO", "OUTRA"};

    private static final String[] SEXO = new String[]{"FÊMEA", "MACHO"};

    private static final String[] RACACRIA = new String[]{"Nelore", "Composto"};

    public String msg = "";
    private LinearLayout ll_identificador;
    private LinearLayout ll_sisbov;
    private LinearLayout ll_manejo;
    private LinearLayout ll_cod_alternativo;

    private Calendar calendario;
    private static EditText editMatriz;
    private EditText editDataParto;
    private AutoCompleteTextView editBuscaPasto;
    private EditText editCodCria;
    private EditText editCodAlternativo;
    private EditText editIdentificador;
    private EditText editSisbov;
    private AutoCompleteTextView editGrupoManejo;
    private AutoCompleteTextView editCriterio;
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

    private Animal_Model ani_model;
    private Animal animal_tb;
    private Parto_Model parto_model;
    private Parto_Cria_Model cria_model;
    private Configuracao_Model conf_model;
    private Configuracao conf_tb;
    private Parto parto_tb;
    private Parto_Cria cria_tb;
    private Parto_Adapter p_helper;
    private Parto_Cria_Adapter pc_helper;
    private List<Configuracao> listConf;
    private List<Parto_Cria> listaCria;
    private static List<String> listaMatriz;
    private static List<Animal> listaAnimal;

    private String strSisbov;
    private String strIdentificador;
    private String strCod_matriz;
    private String strDataParto;
    private String strGrupo_manejo;
    private String strCriterio;
    private String strPasto;
    private String strRaca_cria;
    private String strGenetica;
    private String strSexo;


    public static boolean validaSisbov;
    public static boolean validaIdentificador;
    public static boolean validaManejo;
    public static boolean validaCodAlternativo;

    private String cod_matriz_invalido;
    private Mensagem_Util md;

    public static boolean FLAG_CODIGO_MATRIZ_DUPLICADO;
    public static boolean FLAG_ID_FK_MAE_DUPLICADO;

    private Date data;
    private SimpleDateFormat dateFormat;
    private Calendar cal;
    private Date data_atual;
    private String data_completa;

    private long id_animal;
    private long id_pk;

    //conexão bluetooth
    private static CharSequence result;
    private static String id;
    public static Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            resultHandleMessage(msg);
        }
    };

    private static void resultHandleMessage(Message msg)
    {
        Bundle bundle = msg.getData();
        byte[] data = bundle.getByteArray("data");
        String dataString= new String(data);

        if(dataString.equals("ERRO_CONEXAO"))
            editMatriz.setHint("Erro durante a conexão!");
        else if(dataString.equals("CONECTADO"))
            editMatriz.setHint("Batão Conectado!");
        else
        {
            data.toString().length();
            result = new String(data);

            String r = validaId(result.toString(), "1000000;\r\n", ";");
            id = r;
            buscaMatriz(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parto);

        FLAG_CODIGO_MATRIZ_DUPLICADO    = false;
        FLAG_ID_FK_MAE_DUPLICADO        = false;

        buscaPasto();

        buscaGrupoManejo();

        buscaCriterio();

        loadComboBox();

        loadData();

        source();

        leitorCodBarras();

        loadIdentificadorSisbovGrupoManejo();

        loadBotaoLeitor();

        changeSisbov();

        changeIdentificador();

        changeCodAlternativo();

        changeCodCria();

        changeDataParto();

        changeCodMatriz();

        loadOldValueVars();

        loadMatriz();

        btnSalvarClick();
    }

    public static void buscaMatriz (String id) {
        if (!id.equals("")) {
            loadMatriz();
        }
    }

    public static String validaId(String str, String charsRemove, String delimiter) {

        if (charsRemove!=null && charsRemove.length()>0 && str!=null) {

            String[] remover = charsRemove.split(delimiter);

            for(int i =0; i < remover.length ; i++) {
                //System.out.println("i: " + i + " ["+ remover[i]+"]");
                if (str.indexOf(remover[i]) != -1){
                    str = str.replace(remover[i], "");
                }
            }
        }
        return str;
    }

    private void btnSalvarClick() {
        btnSalvar.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                id_pk = data.getTime();

                parto_tb.setId_pk(id_pk);
                cria_tb.setId_fk_parto(id_pk);

                loadMatriz();

                if (listaAnimal.size() != 0)
                {
                    parto_tb.setData_parto(editDataParto.getText().toString());
                    strDataParto = editDataParto.getText().toString();

                    parto_tb.setPerda_gestacao(spinPerda.getSelectedItem().toString());
                    parto_tb.setSexo_parto(spinSexo.getSelectedItem() == "FÊMEA" ? "FE" : "MA");

                    if (!txtidanimal.getText().toString().equals(""))
                    {
                        parto_tb.setId_fk_animal(Long.parseLong(txtidanimal.getText().toString()));
                        cria_tb.setId_fk_animal_mae(Long.parseLong(txtidanimal.getText().toString()));
                    }
                    else
                    {
                        parto_tb.setId_fk_animal(1);
                        cria_tb.setId_fk_animal_mae(1);
                    }

                    cria_tb.setCodigo_cria(editCodCria.getText().toString());
                    cria_tb.setPeso_cria(editPeso.getText().toString());

                    /*List<Long> lista_cria = new ArrayList<Long>();
                    for (Parto_Cria p_cria : listaCria) {
                        if (p_cria.getId_fk_animal_mae() == parto_tb.getId_fk_animal()) {
                            lista_cria.add(p_cria.getId_fk_animal_mae());
                        }
                    }*/

                    cria_tb.setTipo_parto(cbTipoParto.isChecked() ? "GENETICA" : "ESTIMADO");
                    strGenetica = cbTipoParto.isChecked() ? "GENETICA" : "ESTIMADO";

                    cria_tb.setRepasse(cbDescarte.isChecked() ? "SIM" : "NAO");
                    cria_tb.setSexo(spinSexo.getSelectedItem() == "FÊMEA" ? "FE" : "MA");
                    strSexo = spinSexo.getSelectedItem() == "FÊMEA" ? "FE" : "MA";

                    cria_tb.setData_identificacao(editDataIdentificacao.getText().toString());

                    cria_tb.setRaca_cria(spinRacaCria.getSelectedItem().toString());
                    strRaca_cria = spinRacaCria.getSelectedItem().toString();

                    cria_tb.setIdentificador(editIdentificador.getText().toString());

                    if (validaSisbov == true)
                    {
                        if (!editSisbov.getText().toString().equals(""))
                        {
                            long sis = Long.parseLong(editSisbov.getText().toString());
                            String strsis = String.valueOf(sis);
                            cria_tb.setSisbov(strsis);
                        }
                    }
                    else
                    {
                        cria_tb.setSisbov(editSisbov.getText().toString());
                    }

                    cria_tb.setGrupo_manejo(editGrupoManejo.getText().toString());
                    strGrupo_manejo = editGrupoManejo.getText().toString();

                    cria_tb.setCriterio(editCriterio.getText().toString());
                    strCriterio = editCriterio.getText().toString();

                    cria_tb.setSync_status(0);
                    parto_tb.setSync_status(0);
                    cria_tb.setPasto(editBuscaPasto.getText().toString());
                    strPasto = editBuscaPasto.getText().toString();

                    cria_tb.setCodigo_ferro_cria(editCodAlternativo.getText().toString());
                    cria_tb.setCod_matriz_invalido(cod_matriz_invalido);
                    cria_tb.setCod_matriz_invalido(cria_tb.getId_fk_animal_mae() != 0 ? "0" : editMatriz.getText().toString());

                    strCod_matriz = editMatriz.getText().toString();

                    loadOldValueVars();

                    insertParto(parto_tb, cria_tb);

                } else {
                    Mensagem_Util.addMsg(Message_Dialog.Yes, Parto_Activity.this
                            , "O Aparelho não foi preparado para o lançamento de Partos, favor prepara-lo!.", "Aviso", 1);
                }
            }
        });
    }

    private static void loadMatriz()
    {
        listaMatriz = new ArrayList<String>();

        for (Animal animalList : listaAnimal)
        {
            /*if (animalList.getCodigo().equals(editMatriz.getText().toString())) {
                listaMatriz.add(animalList.getCodigo());
            }*/
            if (animalList.getIdentificador().equals(id))
            {
                listaMatriz.add(animalList.getCodigo());
                editMatriz.setText(animalList.getCodigo());
            }
        }
    }

    private void changeDataParto()
    {
        editDataParto.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    showCalendar();
                }
            }
        });
    }

    private void changeCodMatriz()
    {
        editMatriz.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                final Animal animal;
                if (!hasFocus)
                {
                    if (editMatriz.getText().toString() != "")
                    {
                        animal = ani_model.selectByCodigo(Parto_Activity.this, editMatriz.getText().toString());

                        txtidanimal.setText(String.valueOf(animal.getId_pk()));
                        listaMatriz = new ArrayList<String>();

                        for (Animal animalList : listaAnimal)
                        {
                            if (animalList.getCodigo().equals(editMatriz.getText().toString().toLowerCase())
                                || animalList.getCodigo().equals(editMatriz.getText().toString().toUpperCase())
                                || animalList.getCodigo_ferro().equals(editMatriz.getText().toString().toLowerCase())
                                || animalList.getCodigo_ferro().equals(editMatriz.getText().toString().toUpperCase()))
                            {
                                listaMatriz.add(animalList.getCodigo());
                                editMatriz.setText(animalList.getCodigo());
                            }
                        }
                    }
                    matrizInvalida();
                }
            }
        });
    }

    private void loadBotaoLeitor() {
        btnLeitorCodBarras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parto_Activity.this, Leitor_Activity.class);
                startActivity(intent);
            }
        });

        btnLeitorCodBarra.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parto_Activity.this, Leitor_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void changeCodCria()
    {
        editCodCria.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (editCodCria.getText().toString().equals(""))
                    {
                        spinPerda.setEnabled(true);
                    }
                    else
                    {
                        spinPerda.setEnabled(false);
                    }
                }
            }
        });
    }

    private void changeCodAlternativo()
    {
        editCodAlternativo.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (!editCodAlternativo.getText().toString().equals("")
                       && editCodCria.getText().toString().equals(""))
                    {
                        editCodCria.setText(editCodAlternativo.getText());
                    }
                }
            }
        });
    }

    private void changeSisbov()
    {
        editSisbov.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (!editSisbov.getText().toString().equals(""))
                    {
                        spinPerda.setEnabled(false);
                        if (!sisbovCorreto(editSisbov.getText().toString()) || editSisbov.length() < 15)
                        {
                            Toast.makeText(Parto_Activity.this, "Sisbov inválido!", Toast.LENGTH_SHORT).show();
                            editSisbov.setFocusable(true);
                            return;

                        }
                        else
                        {
                            String codCria = editSisbov.getText().toString();
                            editCodCria.setText(codCria.substring(8, 14));
                        }
                    }
                    else
                    {
                        spinPerda.setEnabled(true);
                        editCodCria.setText("");
                    }
                }
            }
        });
    }

    private void changeIdentificador()
    {
        editIdentificador.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (!editIdentificador.getText().toString().equals(""))
                    {
                        spinPerda.setEnabled(false);
                        if (editIdentificador.length() < 15)
                        {
                            Toast.makeText(Parto_Activity.this, "Identificador inválido!", Toast.LENGTH_SHORT).show();
                            editIdentificador.setFocusable(true);
                            return;
                        }
                        else
                        {
                            if (editSisbov.getText().toString().equals("") || !listConf.get(0).getValida_sisbov().equals("S"))
                            {
                                String codCria = editIdentificador.getText().toString();
                                editCodCria.setText(codCria.substring(7, 15));
                            }
                        }
                    }
                    else
                    {
                        spinPerda.setEnabled(true);
                        editCodCria.setText("");
                    }
                }
            }
        });
    }

    private void loadIdentificadorSisbovGrupoManejo()
    {
        if (!listConf.get(0).getValida_identificador().equals("S")
                && !listConf.get(0).getValida_sisbov().equals("S"))
        {
            editCodCria.setEnabled(true);
        }

        if (listConf.get(0).getValida_identificador().equals("S"))
        {
            editIdentificador.setEnabled(true);
            ll_identificador.setVisibility(LinearLayout.VISIBLE);
            validaIdentificador = true;
        }
        else
        {
            editIdentificador.setText("0");
            editIdentificador.setEnabled(false);
            ll_identificador.setVisibility(LinearLayout.GONE);
            validaIdentificador = false;
        }

        if (listConf.get(0).getValida_sisbov().equals("S"))
        {
            editSisbov.setEnabled(true);
            ll_sisbov.setVisibility(LinearLayout.VISIBLE);
            validaSisbov = true;
        }
        else
        {
            editSisbov.setText("0");
            editSisbov.setEnabled(false);
            ll_sisbov.setVisibility(LinearLayout.GONE);
            validaSisbov = false;
        }

        if (listConf.get(0).getValida_manejo().equals("S"))
        {
            editGrupoManejo.setEnabled(true);
            ll_manejo.setVisibility(LinearLayout.VISIBLE);
            validaManejo = true;
        }
        else
        {
            editGrupoManejo.setText("0");
            editGrupoManejo.setEnabled(false);
            ll_manejo.setVisibility(LinearLayout.GONE);
            validaManejo = false;
        }

        if (listConf.get(0).getValida_cod_alternativo().equals("S"))
        {
            editCodAlternativo.setEnabled(true);
            ll_cod_alternativo.setVisibility(LinearLayout.VISIBLE);
            validaCodAlternativo = true;
        }
        else
        {
            editCodAlternativo.setText("0");
            editCodAlternativo.setEnabled(false);
            ll_cod_alternativo.setVisibility(LinearLayout.GONE);
            validaCodAlternativo = false;
        }
    }

    private void source()
    {
        ani_model   = new Animal_Model(this);
        parto_model = new Parto_Model(this);
        cria_model  = new Parto_Cria_Model(this);
        conf_model  = new Configuracao_Model(this);
        p_helper    = new Parto_Adapter();
        pc_helper   = new Parto_Cria_Adapter();
        parto_tb    = new Parto();
        cria_tb     = new Parto_Cria();
        conf_tb     = new Configuracao();
        animal_tb   = new Animal();

        editIdentificador       = (EditText) findViewById(R.id.edtIdentificador);
        editSisbov              = (EditText) findViewById(R.id.edtSisbov);
        editMatriz              = (EditText) findViewById(R.id.edtMatriz);
        editDataParto           = (EditText) findViewById(R.id.edtDataParto);
        editCodCria             = (EditText) findViewById(R.id.edtCria);
        editCodAlternativo      = (EditText) findViewById(R.id.edtCodAlternativo);
        editPeso                = (EditText) findViewById(R.id.edtPesoCria);
        txtidanimal             = (TextView) findViewById(R.id.id_animal);
        btnSalvar               = (Button) findViewById(R.id.btnSalvarParto);
        btnLeitorCodBarras      = (Button) findViewById(R.id.btnLeitorCodBarras);
        btnLeitorCodBarra       = (Button) findViewById(R.id.btnLeitorCodBarra);
        editDataIdentificacao   = (EditText) findViewById(R.id.edtDataIdentificacao);

        ll_identificador        = (LinearLayout) findViewById(R.id.ll_identificador);
        ll_sisbov               = (LinearLayout) findViewById(R.id.ll_sisbov);
        ll_manejo               = (LinearLayout) findViewById(R.id.ll_manejo);
        ll_cod_alternativo      = (LinearLayout) findViewById(R.id.ll_cod_alternativo);

        listConf                = conf_model.selectAll(this, "Configuracao", conf_tb);
        listaAnimal             = ani_model.selectAll(this, "Animal", animal_tb);
        listaCria               = cria_model.selectAll(this, "Parto_Cria", cria_tb);

        editDataIdentificacao.setText(data_completa);
    }

    private void loadData()
    {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        data = new Date();

        cal = Calendar.getInstance();
        cal.setTime(data);
        data_atual = cal.getTime();
        data_completa = dateFormat.format(data_atual);
    }

    private void loadComboBox()
    {
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
    }

    private void buscaPasto()
    {
        List<Pasto> pasto_list;
        List<String> nome_pasto_list    = new ArrayList<String>();
        Pasto pasto_tb                  = new Pasto();
        Pasto_Model pasto_model         = new Pasto_Model(this);

        try
        {
            pasto_list = pasto_model.selectAll(this, "Pasto", pasto_tb);

            for(Pasto p : pasto_list)
            {
                nome_pasto_list.add(p.getNome().toString());
            }
        }
        catch (Exception e)
        {
            Log.i("BuscaPastos", e.toString());
            e.printStackTrace();
        }

        editBuscaPasto=(AutoCompleteTextView)findViewById(R.id.edtBuscaPasto);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, nome_pasto_list);

        editBuscaPasto.setAdapter(adapter);
        editBuscaPasto.setThreshold(1);
    }

    private void buscaGrupoManejo()
    {
        List<Grupo_Manejo> grupo_list;
        List<String> codigo_grupo_list  = new ArrayList<String>();
        Grupo_Manejo grupo_tb           = new Grupo_Manejo();
        Grupo_Manejo_Model grupo_model  = new Grupo_Manejo_Model(this);

        try
        {
            grupo_list = grupo_model.selectAll(this, "Grupo_Manejo", grupo_tb);

            for (Grupo_Manejo g_tb : grupo_list)
            {
                codigo_grupo_list.add(g_tb.getCodigo());
            }
        }
        catch (Exception e)
        {
            Log.i("BuscaGrupoManejo", e.toString());
            e.printStackTrace();
        }

        editGrupoManejo=(AutoCompleteTextView)findViewById(R.id.edtGrupoManejo);

        ArrayAdapter<String> grupo_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,codigo_grupo_list);

        editGrupoManejo.setAdapter(grupo_adapter);
        editGrupoManejo.setThreshold(1);
    }

    private void buscaCriterio()
    {
        List<Criterio> criterio_list;
        List<String> c_criterio_list    = new ArrayList<String>();
        Criterio criterio_tb            = new Criterio();
        Criterio_Model criterio_model   = new Criterio_Model(this);

        try
        {
            criterio_list = criterio_model.selectAll(this, "Criterio", criterio_tb);

            for (Criterio c_tb : criterio_list)
            {
                c_criterio_list.add(c_tb.getCriterio());
            }
        }
        catch (Exception e)
        {
            Log.i("BuscaBusca", e.toString());
            e.printStackTrace();
        }

        editCriterio=(AutoCompleteTextView)findViewById(R.id.edtCriterio);

        ArrayAdapter<String> criterio_adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, c_criterio_list);

        editCriterio.setAdapter(criterio_adapter);
        editCriterio.setThreshold(1);
    }

    private void leitorCodBarras()
    {
        Intent intent   = getIntent();
        Leitor leitor   = (Leitor) intent.getSerializableExtra("leitor");

        if (leitor != null)
        {
            if (leitor.getTipo().equals("CODE_39"))
            {
                strIdentificador = leitor.getScanResult();
                Menu_Principal_Activity.old_identificador = strIdentificador;
                long sis = Long.parseLong(Menu_Principal_Activity.old_identificador);
                String strsis = String.valueOf(sis);
                editIdentificador.setText(strsis);
                if (editSisbov.getText().toString().equals("") || !listConf.get(0).getValida_sisbov().equals("S"))
                {
                    editCodCria.setText(strsis.substring(7, 15));
                }
            }
            else if (leitor.getTipo().equals("ITF"))
            {
                strSisbov = leitor.getScanResult();
                Menu_Principal_Activity.old_sisbov = strSisbov;
                long sis = Long.parseLong(Menu_Principal_Activity.old_sisbov);
                String strsis = String.valueOf(sis);
                editSisbov.setText(strsis);
                editCodCria.setText(strsis.substring(8, 14));
            }

            if (editIdentificador.getText().toString().equals(""))
            {
                if (Menu_Principal_Activity.old_identificador != "")
                {
                    long sis = Long.parseLong(Menu_Principal_Activity.old_identificador);
                    String strsis = String.valueOf(sis);
                    editIdentificador.setText(strsis);
                    editCodCria.setText(strsis.substring(7, 15));
                }
            }

            if (editSisbov.getText().toString().equals(""))
            {
                if (Menu_Principal_Activity.old_sisbov != "")
                {
                    long sis = Long.parseLong(Menu_Principal_Activity.old_sisbov);
                    String strsis = String.valueOf(sis);
                    editSisbov.setText(strsis);
                    editCodCria.setText(strsis.substring(8, 14));
                }
            }

            if (editDataParto.getText().toString().equals(""))
            {
                editDataParto.setText(Menu_Principal_Activity.old_data_parto);
            }

            if (editMatriz.getText().toString().equals(""))
            {
                editMatriz.setText(Menu_Principal_Activity.old_cod_matriz);
            }

            if (editGrupoManejo.getText().toString().equals(""))
            {
                editGrupoManejo.setText(Menu_Principal_Activity.old_grupo_manejo);
            }

            if (editCriterio.getText().toString().equals(""))
            {
                editCriterio.setText(Menu_Principal_Activity.old_criterio);
            }

            if (editBuscaPasto.getText().toString().equals(""))
            {
                editBuscaPasto.setText(Menu_Principal_Activity.old_pasto);
            }

            /*Menu_Principal_Activity.old_raca_cria.toString());*/
            if (Menu_Principal_Activity.old_raca_cria != "")
            {
                for (int i = 0; i < spinRacaCria.getCount(); i++)
                {
                    if (spinRacaCria.getItemAtPosition(i).toString().equals(Menu_Principal_Activity.old_raca_cria))
                    {
                        spinRacaCria.setSelection(i);
                    }
                }
            }
            /*Menu_Principal_Activity.old_sexo;*/
            spinSexo.setSelection(Menu_Principal_Activity.old_sexo != "MA" ? 0 : 1);

            cbTipoParto.setChecked(Menu_Principal_Activity.old_genetica == "GENETICA" ? true : false);
        }
    }

    private void loadOldValueVars()
    {
        Menu_Principal_Activity.old_genetica        = strGenetica;
        Menu_Principal_Activity.old_sexo            = strSexo;
        Menu_Principal_Activity.old_raca_cria       = strRaca_cria;
        Menu_Principal_Activity.old_pasto           = strPasto;
        Menu_Principal_Activity.old_grupo_manejo    = strGrupo_manejo;
        Menu_Principal_Activity.old_criterio        = strCriterio;
        Menu_Principal_Activity.old_data_parto      = strDataParto;
        Menu_Principal_Activity.old_cod_matriz      = strCod_matriz;
    }

    public void insertParto(final Parto parto_tb, final Parto_Cria cria_tb)
    {
        try
        {
            cria_model.validate(this, "Parto_Cria", cria_tb, Banco_Service.VALIDATION_TYPE_INSERT);
            parto_model.validate(this, "Parto", parto_tb, Banco_Service.VALIDATION_TYPE_INSERT);
            parto_model.insert(Parto_Activity.this, "Parto", p_helper.getDadosParto(parto_tb));
            cria_model.insert(Parto_Activity.this, "Parto_Cria", pc_helper.getDadosCria(cria_tb));
            writeInFile(p_helper.PartoArqHelper(parto_tb, cria_tb));
            Mensagem_Util.addMsg(Message_Dialog.Toast, Parto_Activity.this, "Parto cadastrado com sucesso!");
            zeraInterface();
        }
        catch (final Validator_Exception e)
        {
            if (e.getException_code() == Validator_Exception.MESSAGE_TYPE_QUESTION)
            {
                md.addMsg(Parto_Activity.this, "Aviso" , e.getMessage(), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        validateFlags(e);
                        insertParto(parto_tb, cria_tb);
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

    private static void validateFlags(Validator_Exception e)
    {
        if (!e.getException_args().equals(0) && e.getException_args()[0].toString().equals("FLAG_CODIGO_MATRIZ_DUPLICADO"))
        {
            FLAG_CODIGO_MATRIZ_DUPLICADO = true;
        }

        if (!e.getException_args().equals(0) && e.getException_args()[0].toString().equals("FLAG_ID_FK_MAE_DUPLICADO"))
        {
            FLAG_ID_FK_MAE_DUPLICADO = true;
        }
    }

    private void matrizInvalida()
    {
        final boolean[] validaMatriz = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (listaMatriz.size() == 0)
        {
            builder.setTitle("Aviso").setMessage("Matriz não existe na base de dados. Deseja continuar?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            validaMatriz[0] = true;
                            cod_matriz_invalido = editMatriz.getText().toString();
                            cria_tb.setId_fk_animal_mae(0);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            validaMatriz[0] = false;
                            editMatriz.setText("");
                        }
                    })
                    .show();
            if (validaMatriz[0] == true)
            {
                cod_matriz_invalido = editMatriz.getText().toString();
            }
            else
            {
                cod_matriz_invalido = "0";
                return;
            }
        }
    }

    private boolean writeInFile(String text)
    {
        BufferedReader input = null;
        File file = null;

        try
        {
            file = new File(Environment.getExternalStorageDirectory()+"/Prodap","backup.txt");
            FileOutputStream in = new FileOutputStream(file, true);
            in.write(text.getBytes());
            in.write("\n".getBytes());
            in.flush();
            in.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean sisbovCorreto(String sisbov)
    {
        int valor = 0;
        int ultimo_digito;

        if (sisbov != "")
        {
            char[] cvetor = sisbov.toCharArray();

            if (cvetor.length < 15)
                return false;

            valor = ((Character.getNumericValue(sisbov.charAt(0)) * 7)) + (Character.getNumericValue(sisbov.charAt(1)) * 6) + (Character.getNumericValue(sisbov.charAt(2)) * 5);
            valor += ((Character.getNumericValue(sisbov.charAt(3)) * 4) + (Character.getNumericValue(sisbov.charAt(4)) * 3) + (Character.getNumericValue(sisbov.charAt(5)) * 2));
            valor += ((Character.getNumericValue(sisbov.charAt(6)) * 9) + (Character.getNumericValue(sisbov.charAt(7)) * 8) + (Character.getNumericValue(sisbov.charAt(8)) * 7));
            valor += ((Character.getNumericValue(sisbov.charAt(9)) * 6) + (Character.getNumericValue(sisbov.charAt(10)) * 5) + (Character.getNumericValue(sisbov.charAt(11)) * 4));
            valor += ((Character.getNumericValue(sisbov.charAt(12)) * 3) + (Character.getNumericValue(sisbov.charAt(13)) * 2));

            valor = valor % 11;

            if (valor <= 1)
            {
                ultimo_digito = 0;
            }
            else
            {
                ultimo_digito = (11 - valor);
            }

            if (ultimo_digito == Integer.valueOf(cvetor[sisbov.length() - 1] + "") && sisbov.length() == 15)
                return true;
        }
        return false;
    }

    /*private boolean validate(Parto_Cria cria_tb, List<Parto_Cria> listaCria) {
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
    }*/

    public void showCalendar()
    {
        new DatePickerDialog(Parto_Activity.this, listner, calendario.get(Calendar.YEAR)
                , calendario.get(Calendar.MONTH)
                , calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener listner = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            String date = String.format("%s-%s-%d", day <= 9 ? "0" + (day) : (day)
                    , ++month <= 9 ? "0" + (month) : (month)
                    , year);
            editDataParto.setText(date);
        }
    };

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        updateVars();
    }

    private void updateVars()
    {
        Menu_Principal_Activity.old_sisbov          = "";
        Menu_Principal_Activity.old_identificador   = "";
        Menu_Principal_Activity.old_cod_matriz      = "";
    }

    public void zeraInterface()
    {
        updateVars();
        editMatriz.setText("");
        editCodCria.setText("");
        editCodAlternativo.setText("");
        txtidanimal.setText("");
        editSisbov.setText("");
        editPeso.setText("");
        editIdentificador.setText("");
        editDataParto.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /*switch (item.getItemId())
        {
            /*case R.id.menu_bluetooth:
                configBluetooth();
                return false;
            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void configBluetooth()
    {
        Intent intent = new Intent(Parto_Activity.this, Bluetooth_Activity.class);
        startActivity(intent);
    }
}
