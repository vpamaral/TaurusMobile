package br.com.taurusmobile.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.adapter.PartoAdapter;
import br.com.taurusmobile.model.AnimalModel;

public class PartoActivity extends Activity {

	private static final String[] PERDA = new String[] { "NENHUMA", "ABORTO",
			"NATIMORTO", "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO",
			"F.MUMIFICADO", "OUTRA" };
	private static final String[] SEXO = new String[] { "", "FÊMEA", "MACHO" };

	private EditText editMatriz;
	private EditText editDataParto;
	private EditText editRacaPai;
	private EditText editCodCria;
	private EditText editPeso;
	private Button btnSalvar;
	private Spinner spinPerda;
	private Spinner spinSexo;

	private AnimalModel ani_model;
	private PartoAdapter p_helper;
	private Parto parto;
	private Parto_Cria cria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parto);

		Spinner perdaGest = (Spinner) findViewById(R.id.spnPerda);
		ArrayAdapter<String> adpPerda = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, PERDA);
		adpPerda.setDropDownViewResource(android.R.layout.simple_spinner_item);
		perdaGest.setAdapter(adpPerda);

		Spinner spnSexo = (Spinner) findViewById(R.id.spnSexo);
		ArrayAdapter<String> adpSexo = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, SEXO);
		adpSexo.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spnSexo.setAdapter(adpSexo);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date data = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		Date data_atual = cal.getTime();

		String data_completa = dateFormat.format(data_atual);

		ani_model = new AnimalModel(this);

		editMatriz = (EditText) findViewById(R.id.edtMatriz);
		editDataParto = (EditText) findViewById(R.id.edtDataParto);
		editRacaPai = (EditText) findViewById(R.id.edtRacaPai);
		spinPerda = (Spinner) findViewById(R.id.spnPerda);
		spinSexo = (Spinner) findViewById(R.id.spnSexo);
		editCodCria = (EditText) findViewById(R.id.edtCria);
		editPeso = (EditText) findViewById(R.id.edtPesoCria);

		btnSalvar = (Button) findViewById(R.id.btnSalvarParto);

		editDataParto.setText(data_completa);

		editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Parto parto_tb = new Parto();
				Parto_Cria cria_tb = new Parto_Cria();
				final Animal animal = ani_model.selectByCodigo(
						PartoActivity.this, editMatriz.getText().toString());
				editRacaPai.setText(animal.getRaca_reprod());
				parto_tb.setId_fk_animal(animal.getId_pk());
				cria_tb.setId_fk_animal_mae(animal.getId_pk());
				parto = parto_tb;
				cria = cria_tb;
			}
		});

		
		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				parto.setData_parto(editDataParto.getText().toString());
				parto.setPerda_gestacao(spinPerda.getSelectedItem()
						.toString());
				parto.setSexo_parto(spinSexo.getSelectedItem().toString());

				cria.setCodigo_cria(editCodCria.getText().toString());
				cria.setPeso_cria(Double.valueOf(editPeso.getText()
						.toString()));
				cria.setSexo(spinSexo.getSelectedItem().toString());

			}
		});

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

	public void zerarInterface() {

	}
}
