package br.com.prodap.taurusmobile.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import java.util.Calendar;

import android.text.Editable;
import android.widget.DatePicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.prodap.taurusmobile.TB.Animal;
import br.com.prodap.taurusmobile.TB.Configuracoes;
import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;
import br.com.prodap.taurusmobile.adapter.PartoAdapter;
import br.com.prodap.taurusmobile.model.AnimalModel;
import br.com.prodap.taurusmobile.model.ConfiguracoesModel;
import br.com.prodap.taurusmobile.model.PartoModel;
import br.com.prodap.taurusmobile.model.Parto_CriaModel;
import br.com.prodap.taurusmobile.util.MensagemUtil;
import br.com.prodap.taurusmobile.util.MessageDialog;

public class PartoActivity extends Activity {

	private static final String[] PERDA = new String[] { "NENHUMA", "ABORTO",
			"NATIMORTO", "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO",
			"F.MUMIFICADO", "OUTRA" };
	private static final String[] SEXO = new String[] { "FÊMEA", "MACHO" };

	private static final String[] RACACRIA = new String[] {"Nelore", "Composto", "Hereford", "Braford", "Angus", "Brangus", "Gir"};

	public String msg = "";
	public String msgCampoVazio = "";
	private LinearLayout ll_identificador;
	private LinearLayout ll_sisbov;
	private LinearLayout ll_manejo;

	private Calendar calendario = Calendar.getInstance();
	private int year_x, month_x, day_x;

	private EditText editMatriz;
	private EditText editDataParto;
	private EditText editRacaPai;
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
	private TextView txtidanimal;
	String sisbov = null;
	String identificador = null;
	private AnimalModel ani_model;
	private PartoModel parto_model;
	private Parto_CriaModel cria_model;
	private ConfiguracoesModel conf_model;
	private Parto parto_tb;
	private Parto_Cria cria_tb;
	private PartoAdapter p_helper;
	private String strsis;
	private List<Configuracoes> listConf;
	Date dataParto = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parto);

		Spinner perdaGest = (Spinner) findViewById(R.id.spnPerda);
		ArrayAdapter<String> adpPerda = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, PERDA);
		adpPerda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		perdaGest.setAdapter(adpPerda);

		Spinner spnSexo = (Spinner) findViewById(R.id.spnSexo);
		ArrayAdapter<String> adpSexo = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, SEXO);
		adpSexo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnSexo.setAdapter(adpSexo);

		Spinner spnRaca = (Spinner) findViewById(R.id.spnRaca);
		ArrayAdapter<String> adpRaca = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, RACACRIA);
		adpRaca.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnRaca.setAdapter(adpRaca);

		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date data = new Date();

		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		final Date data_atual = cal.getTime();
		final String data_completa = dateFormat.format(data_atual);
		final long id_animal;
		ani_model = new AnimalModel(this);
		parto_model = new PartoModel(this);
		cria_model = new Parto_CriaModel(this);
		conf_model = new ConfiguracoesModel(this);
		final Parto parto_tb = new Parto();
		final Parto_Cria cria_tb = new Parto_Cria();

		editIdentificador = (EditText) findViewById(R.id.edtIdentificador);
		editSisbov = (EditText) findViewById(R.id.edtSisbov);
		editGrupoManejo = (EditText) findViewById(R.id.edtGrupoManejo);
		editMatriz = (EditText) findViewById(R.id.edtMatriz);
		editDataParto = (EditText) findViewById(R.id.edtDataParto);
		editRacaPai = (EditText) findViewById(R.id.edtRacaPai);
		spinPerda = (Spinner) findViewById(R.id.spnPerda);
		spinSexo = (Spinner) findViewById(R.id.spnSexo);
		editCodCria = (EditText) findViewById(R.id.edtCria);
		spinRacaCria =(Spinner) findViewById(R.id.spnRaca);
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
		final List<Parto_Cria> listaCria = cria_model.selectAll(this, "Parto_Cria", pc_tb);

		Configuracoes conf_tb = new Configuracoes();
		listConf = conf_model.selectAll(this, "Configuracao", conf_tb);

		Animal animal_tb = new Animal();
		final List<Animal> listaAnimal = ani_model.selectAll(this, "Animal", animal_tb);

		Intent intent  =  this.getIntent();

		String resultCodBarras;
		String tipo;

		tipo			= intent.getStringExtra("tipo");
		resultCodBarras = intent.getStringExtra("CodBarras");
		//editCodCria.setText(intent.getStringExtra("editCodBarras"));

		//if(listConf.get(0).getValidaId().equals("")) {
			//editIdentificador.setEnabled(true);
			//editSisbov.setEnabled(true);
			//ll_identificador.setVisibility(LinearLayout.VISIBLE);
			//ll_sisbov.setVisibility(LinearLayout.VISIBLE);
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
				} else if (tipo.equals("ITF")) {
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
		//}
		//else
		//{
		//	editIdentificador.setEnabled(false);
		//	editSisbov.setEnabled(false);
		//	ll_identificador.setVisibility(LinearLayout.GONE);
		//	ll_sisbov.setVisibility(LinearLayout.GONE);
	//	}

		//if(listConf.get(0).getValidaManejo().equals(""))
	//	{
			//editGrupoManejo.setEnabled(true);
	//		ll_manejo.setVisibility(LinearLayout.VISIBLE);
	//	}
	//	else
//		{
			//editGrupoManejo.setEnabled(false);
//			ll_manejo.setVisibility(LinearLayout.GONE);
	//	}

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
						}
					}
				}
			}
		});

		btnLeitorCodBarras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PartoActivity.this,
						LeitorCodBarrasActivity.class);
				startActivity(intent);
			}
		});

		btnLeitorCodBarra.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PartoActivity.this,
						LeitorCodBarrasActivity.class);
				startActivity(intent);
			}
		});

		//editDataParto.setText(data_completa);
		editDataIdentificacao.setText(data_completa);

		editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				final Animal animal;
			/*String matriz = editMatriz.getText().toString().trim();
			if (!hasFocus) {
				List<String> listaMatriz = new ArrayList<String>();
				for (Animal animalList : listaAnimal) {
					listaMatriz.add(animalList.getCodigo());
				}
				if (!listaMatriz.contains(matriz)) {
					MensagemUtil.addMsg(MessageDialog.Toast, PartoActivity.this, "Esse animal não existe no banco");
					//editMatriz.setText("");
					//editMatriz.requestFocus();
				}
			}*/
				if (editMatriz.getText().toString() != "") {
					animal = ani_model
							.selectByCodigo(PartoActivity.this, editMatriz
									.getText().toString());

					editRacaPai.setText(animal.getRaca_reprod());
					//editCodCria.setText(animal.getCodigo() + "/"
					// + cal.get(Calendar.YEAR));
					//editPeso.setText("40");
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

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			MenuPrincipalActivity.idold = null;
			Animal matriz;
			List<String> listaMatriz = new ArrayList<String>();
			for (Animal animalList : listaAnimal) {
				if(animalList.getCodigo().equals(editMatriz.getText().toString())) {
					listaMatriz.add(animalList.getCodigo());
				}
			}
			// validações temporarias
				try {
					dataParto = dateFormat.parse(editDataParto.getText().toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (editCodCria.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this, "É necessário preencher o código da cria.", "Aviso", 1);

					editCodCria.requestFocus();
				} else if (editMatriz.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this, "É necessário preencher o código matriz.", "Aviso", 1);

					editMatriz.requestFocus();
				} else if(dataParto.after(data_atual)) {
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this, "Data do parto não pode ser maior do que a data de identificação", "Aviso", 1);
				} else if (editDataParto.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this, "É necessário preencher a data do parto.", "Aviso", 1);

					editDataParto.requestFocus();
				} else if (editPeso.getText().toString().isEmpty()) {
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this,
							"É necessário preencher o peso de cria.", "Aviso", 1);
				}
				else if (editSisbov.getText().toString().isEmpty())	{
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this,
							"É necessário preencher o Sisbov de cria.", "Aviso", 1);
				} else if (editIdentificador.getText().toString().isEmpty())	{
					MensagemUtil.addMsg(MessageDialog.Yes,
							PartoActivity.this,
							"É necessário preencher o Identificador de cria.", "Aviso", 1);
				} else {
					parto_tb.setData_parto(editDataParto.getText().toString());
					parto_tb.setPerda_gestacao(spinPerda.getSelectedItem()
							.toString());
					if (spinSexo.getSelectedItem() == "FÊMEA") {
						parto_tb.setSexo_parto("FE");
					} else {
						parto_tb.setSexo_parto("MA");
					}
					parto_tb.setId_fk_animal(Long.parseLong(txtidanimal
							.getText().toString()));
					cria_tb.setCodigo_cria(editCodCria.getText().toString());
					cria_tb.setPeso_cria(editPeso.getText().toString());

					if(!editRacaPai.getText().equals(spinRacaCria.getSelectedItem().toString())) {
						cria_tb.setRepasse("SIM");
					}
					else {
						cria_tb.setRepasse("NAO");
					}

					if (spinSexo.getSelectedItem() == "FÊMEA") {
						cria_tb.setSexo("FE");
					} else {
						cria_tb.setSexo("MA");
					}
					cria_tb.setId_fk_animal_mae(Long.parseLong(txtidanimal.getText().toString()));

					cria_tb.setIdentificador(editIdentificador.getText().toString());
					long sis = Long.parseLong(editSisbov.getText().toString());
					String strsis = String.valueOf(sis);
					cria_tb.setSisbov(strsis);
					cria_tb.setGrupo_manejo(editGrupoManejo.getText().toString());

					cria_tb.setData_identificacao(editDataIdentificacao.getText().toString());
					cria_tb.setRaca_cria(spinRacaCria.getSelectedItem().toString());
					parto_tb.setFgStatus(0);
					cria_tb.setFgStatus(0);

					if (listaMatriz.size() == 0) {
						alertMsg();
						//MensagemUtil.addMsg(MessageDialog.Toast, PartoActivity.this, "Esse animal não existe no banco");
						//editMatriz.setText("");
						editMatriz.requestFocus();
						return;
					}

					if (validate(cria_tb, listaCria)) {
						parto_model.insert(PartoActivity.this, "Parto", parto_tb);
						cria_model.insert(PartoActivity.this, "Parto_Cria", cria_tb);

						zeraInterface();

						MensagemUtil.addMsg(MessageDialog.Toast, PartoActivity.this,
								"Parto cadastrado com sucesso!");
					} else {
						Toast.makeText(PartoActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				}
				}
			});
		}

		private void alertMsg(){
			MensagemUtil.addMsg(MessageDialog.Yes,
					PartoActivity.this, "Matriz não existe na base de dados!", "Aviso", 1);
		}

		public  Boolean sisbovCorreto(String sisbov)
		{

			int valor = 0;
			int ultimo_digito;
			String retorno = "";
			if (sisbov != "")
			{
				char[] cvetor = sisbov.toCharArray();


				sisbov = retorno;

				if (sisbov.length() < 15)
					return false;

				valor = ((Integer.valueOf(cvetor[0])  * 7) + (Integer.valueOf(cvetor[1])*  6) + (Integer.valueOf(cvetor[2]) * 5));
				valor += ((Integer.valueOf(cvetor[3]) * 4) + (Integer.valueOf(cvetor[4])*  3) + (Integer.valueOf(cvetor[5]) * 2));
				valor += ((Integer.valueOf(cvetor[6]) * 9) + (Integer.valueOf(cvetor[7]) * 8) + (Integer.valueOf(cvetor[8]) * 7));
				valor += ((Integer.valueOf(cvetor[9]) * 6) + (Integer.valueOf(cvetor[10]) * 5) + (Integer.valueOf(cvetor[11]) * 4));
				valor += ((Integer.valueOf(cvetor[12]) * 3) + (Integer.valueOf(cvetor[13]) * 2));

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

		private boolean validate(Parto_Cria cria_tb, List<Parto_Cria> listaCria)
		{
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
					return true;

					/*if (cria.getCodigo_cria().equals(cria_tb.getCodigo_cria())) {

						msg = "Codigo da Cria não pode ser duplicado.\n";
						return false;
					}*/
				}
				msg = "Nenhuma Cria foi lançada.";
				return false;
			} else {
				return true;
			}
		}

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
		}

		public void showCalendar() {
			new DatePickerDialog(PartoActivity.this, listner , calendario.get(Calendar.YEAR)
															, calendario.get(Calendar.MONTH)
															, calendario.get(Calendar.DAY_OF_MONTH)).show();
		}

		private DatePickerDialog.OnDateSetListener listner
				= new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				String date = String.format("%s-%s-%d"	, day < 10 ? "0" + (day) : (day)
														, month < 10 ? "0" + (++month) : (++month)
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
			editRacaPai.setText("");
			editCodCria.setText("");
			txtidanimal.setText("");
			editSisbov.setText("");
			editGrupoManejo.setText("");
			editDataIdentificacao.setText("");
			editIdentificador.setText("");
			editIdentificador.requestFocus();
		}
	}
