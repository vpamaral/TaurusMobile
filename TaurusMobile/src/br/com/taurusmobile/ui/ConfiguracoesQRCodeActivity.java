package br.com.taurusmobile.ui;
import br.com.taurusmobile.task.CadastrarQRCode;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConfiguracoesQRCodeActivity extends Activity {
	
	public Button btnSalvar;
	public static EditText edtTipo;
	public static EditText edtEndereco;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracao);
		
		btnSalvar = (Button) findViewById(R.id.btn_salvar);
		edtTipo = (EditText) findViewById(R.id.edtTipo);
		edtEndereco = (EditText) findViewById(R.id.edtEndereco);
		
		
		
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cadastrarQRCode();
			}
		});	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return super.onCreateOptionsMenu(menu);
	}
	
	private void cadastrarQRCode() {
		new CadastrarQRCode(this).execute();
		zerarInterface();
	}
	
	public void zerarInterface() {
		edtTipo.setText("");
		edtEndereco .setText("");
		edtTipo.requestFocus();
	}
}
