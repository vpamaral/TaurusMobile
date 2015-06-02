package br.com.taurusmobile.ui;

import java.util.List;

import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.service.ExecucaoProcesso;
import br.com.taurusmobile.service.ServicoRecebido;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_atualizar;
	private Button btn_animais;
	private Button btn_parto;

	List<Animal> objListaAnimal;
	ProgressDialog objProgressDialog;
	AnimalAdapter aniHelper;
	ServicoRecebido objServicoRecebido;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_atualizar = (Button) findViewById(R.id.btn_atualiza);

		btn_animais = (Button) findViewById(R.id.btn_animal);

		btn_parto = (Button) findViewById(R.id.btn_parto);

		btn_atualizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				objProgressDialog = new ProgressDialog(MainActivity.this);
				objProgressDialog.setMessage("Processando...");
				objProgressDialog.show();

				ExecucaoProcesso objProcessarDados = new ExecucaoProcesso(MainActivity.this);
				objProcessarDados.execute();

			}
		});

		btn_animais.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ListaAnimaisActivity.class);
				startActivity(intent);
			}
		});

		btn_parto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						PartoActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
