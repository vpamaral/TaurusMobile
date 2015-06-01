package br.com.taurusmobile.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class PartoActivity extends Activity {

	// recebe os valores do banco para popular o array
	private static final String[] PERDA = new String[] { "NENHUM", "UM",
			"DOIS", "TRÊS" };
	private static final String[] SEXO = new String[] { "SEXO", "FÊMEA",
			"MACHO" };
	
	private EditText editMatriz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parto);

		Spinner perdaGest = (Spinner) findViewById(R.id.spnPerda);
		ArrayAdapter adpPerda = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, PERDA);
		adpPerda.setDropDownViewResource(android.R.layout.simple_spinner_item);
		perdaGest.setAdapter(adpPerda);

		Spinner spnSexo = (Spinner) findViewById(R.id.spnSexo);
		ArrayAdapter adpSexo = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, SEXO);
		adpSexo.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spnSexo.setAdapter(adpSexo);
		
		editMatriz = (EditText) findViewById(R.id.edtMatriz);
		
		editMatriz.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
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
}
