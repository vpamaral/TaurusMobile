package br.com.taurusmobile.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.TB.Parto;
import br.com.taurusmobile.TB.Parto_Cria;
import br.com.taurusmobile.adapter.PartoAdapter;
import br.com.taurusmobile.model.AnimalModel;
import br.com.taurusmobile.model.PartoModel;
import br.com.taurusmobile.model.Parto_CriaModel;

public class PartoActivity extends Activity {

	private static final String[] PERDA = new String[] { "NENHUMA", "ABORTO",
			"NATIMORTO", "DESCONHECIDA", "F.AUTOLISADO", "F.MACERADO",
			"F.MUMIFICADO", "OUTRA" };
	private static final String[] SEXO = new String[] { "FÊMEA", "MACHO" };

	private EditText editMatriz;
	private EditText editDataParto;
	private EditText editRacaPai;
	private EditText editCodCria;
	private EditText editPeso;
	private Button btnSalvar;
	private Spinner spinPerda;
	private Spinner spinSexo;
	private TextView txtidanimal;

	private AnimalModel ani_model;
	private PartoModel parto_model;
	private Parto_CriaModel cria_model;
	private PartoAdapter p_helper;

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

		final Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		final Date data_atual = cal.getTime();
		String data_completa = dateFormat.format(data_atual);
		final long id_animal;
		ani_model = new AnimalModel(this);
		parto_model = new PartoModel(this);
		cria_model = new Parto_CriaModel(this);

		editMatriz = (EditText) findViewById(R.id.edtMatriz);
		editDataParto = (EditText) findViewById(R.id.edtDataParto);
		editRacaPai = (EditText) findViewById(R.id.edtRacaPai);
		spinPerda = (Spinner) findViewById(R.id.spnPerda);
		spinSexo = (Spinner) findViewById(R.id.spnSexo);
		editCodCria = (EditText) findViewById(R.id.edtCria);
		editPeso = (EditText) findViewById(R.id.edtPesoCria);
		txtidanimal = (TextView) findViewById(R.id.id_animal);
		btnSalvar = (Button) findViewById(R.id.btnSalvarParto);

		editDataParto.setText(data_completa);

		editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (editMatriz.getText().toString() != "") {
					final Animal animal = ani_model.selectByCodigo(PartoActivity.this, editMatriz.getText().toString());

					editRacaPai.setText(animal.getRaca_reprod());
					editCodCria.setText(animal.getCodigo() + "/"
							+ cal.get(Calendar.YEAR));
					editPeso.setText("40");
					txtidanimal.setText(String.valueOf(animal.getId_pk()));
				}
			}
		});

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Parto parto_tb = new Parto();
				Parto_Cria cria_tb = new Parto_Cria();

				parto_tb.setData_parto(editDataParto.getText().toString());
				parto_tb.setPerda_gestacao(spinPerda.getSelectedItem().toString());
				parto_tb.setSexo_parto(spinSexo.getSelectedItem().toString());
				parto_tb.setId_fk_animal(Long.parseLong(txtidanimal.getText().toString()));
				
				cria_tb.setCodigo_cria(editCodCria.getText().toString());
				cria_tb.setPeso_cria(editPeso.getText().toString());
				cria_tb.setSexo(spinSexo.getSelectedItem().toString());
				cria_tb.setId_fk_animal_mae(Long.parseLong(txtidanimal.getText().toString()));
				
				parto_model.insert(PartoActivity.this, "Parto", parto_tb);
				cria_model.insert(PartoActivity.this, "Parto_Cria", cria_tb);
				
				zerarInterface();
				
				Toast.makeText(PartoActivity.this, "Parto cadastrados com sucesso!", Toast.LENGTH_SHORT).show();
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
		editMatriz.setText("");
		editRacaPai.setText("");
		editCodCria.setText("");
		txtidanimal.setText("");
		editMatriz.requestFocus();
	}
}
