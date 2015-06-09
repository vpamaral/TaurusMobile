package br.com.taurusmobile.ui;

import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.adapter.AnimalAdapter;
import br.com.taurusmobile.service.GetAnimaisTask;
import br.com.taurusmobile.service.ServicoRecebido;

public class MainActivity extends Activity {

	private Button btn_atualizar;
	private Button btn_animais;
	private Button btn_parto;
	protected List<Animal> objListaAnimal;
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
				atualizaDados();
			}
		});

		btn_animais.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listaAnimais();
			}
		});

		btn_parto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lancaParto();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_atualiza_dados:
			atualizaDados();
			return false;
		case R.id.menu_lista_animais:
			listaAnimais();
			return false;
		case R.id.menu_novo_parto:
			lancaParto();
			return false;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void lancaParto() {
		Intent intent = new Intent(MainActivity.this, PartoActivity.class);
		startActivity(intent);
	}

	private void listaAnimais() {
		Intent intent = new Intent(MainActivity.this,
				ListaAnimaisActivity.class);
		startActivity(intent);
	}

	private void atualizaDados() {
		new GetAnimaisTask(this).execute();
	}
}
