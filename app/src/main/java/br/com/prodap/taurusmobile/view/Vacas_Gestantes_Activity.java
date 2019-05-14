package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.ParseException;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.tb.Vacas_Gestantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Vacas_Gestantes_Activity extends Activity
{
	private Parto_Model p_model;
	private Parto p_tb;
	private EditText editDataFiltro;
	private Calendar calendario;
	private String filtro;
	private Mensagem_Util md;
	boolean init = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_vacas_gestantes);

		p_model = new Parto_Model(this);
		p_tb	= new Parto();
		setTitle("Vacas Gestantes");
		//displayHistory();
		filtro = "";

		calendario = Calendar.getInstance();

		init = true;
		editDataFiltro = (EditText) findViewById(R.id.edtDataFiltro);
		changeDataFiltro();

		Date currentTime = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(currentTime);
		filtro = strDate;

		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		strDate = dateFormat.format(currentTime);
		editDataFiltro.setText(strDate);


		findViewById(R.id.btnFiltrar).requestFocus();

		btn_filtro_Click(null);
	}

	private void changeDataFiltro()
	{
		editDataFiltro.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus && !init)
				{
					showCalendar();
				}
			}
		});
		init = false;
	}

	public void showCalendar()
	{
		new DatePickerDialog(Vacas_Gestantes_Activity.this, listner, calendario.get(Calendar.YEAR)
				, calendario.get(Calendar.MONTH)
				, calendario.get(Calendar.DAY_OF_MONTH)).show();

		findViewById(R.id.btnFiltrar).requestFocus();

	}

	private DatePickerDialog.OnDateSetListener listner = new DatePickerDialog.OnDateSetListener()
	{
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day)
		{
			String date = String.format("%s-%s-%d", day <= 9 ? "0" + (day) : (day)
					, (month + 1) <= 9 ? "0" + (month + 1): (month + 1)
					, year);

			String dateFiltro = String.format("%d-%s-%s", year,
					(month + 1) <= 9 ? "0" + (month + 1) : (month + 1),
					day <= 9 ? "0" + (day) : (day));

			editDataFiltro.setText(date);
			filtro = dateFiltro;
		}
	};

	public void btn_filtro_Click (View view)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try
		{
			Date date = sdf.parse(editDataFiltro.getText().toString());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
            c.add(Calendar.DATE, -15);
			String dt_inicio = sdf.format(c.getTime());  // dt is now the new date

			c.add(Calendar.DATE, 30);
			String dt_fim = sdf.format(c.getTime());  // dt is now the new date

			md.addMsg(Message_Dialog.Toast, Vacas_Gestantes_Activity.this,
					"Foi(ram) buscada(s) a(s) vaca(s) com data de parto provável entre '"+dt_inicio+"' e '"+dt_fim+"'!",
					"Aviso",
					1);
		}
		catch (ParseException error){
			error.printStackTrace();
		}


		displayHistory();
	}


	public void displayHistory(){

		List<Vacas_Gestantes> list = p_model.selectVacasGestantes(this, "Parto", p_tb, filtro);

		TableLayout showRow = (TableLayout) findViewById(R.id.history_table);
		showRow.removeAllViews();

		int count = 0;
		for(Vacas_Gestantes hl : list) {
			count++;
			TableRow tr = new TableRow(this);
			tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
			tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
			tr.setOrientation(TableRow.HORIZONTAL);
			tr.setPadding(5,5,5,5);

			//LinearLayout ll = new LinearLayout(this);
			//ll.setLayoutParams(new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 30));

			TextView tv_codigo = new TextView(this);
			TextView tv_data_dg = new TextView(this);
			TextView tv_data_parto = new TextView(this);

			tv_codigo.setGravity(Gravity.CENTER);
			tv_data_dg.setGravity(Gravity.CENTER);
			tv_data_parto.setGravity(Gravity.CENTER);

			tv_codigo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
			tv_data_dg.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
			tv_data_parto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));

			tv_codigo.setTextSize(18);
			tv_data_dg.setTextSize(18);
			tv_data_parto.setTextSize(18);

			tv_codigo.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_data_dg.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_data_parto.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));

			tv_codigo.setText(hl.getCodigo());
			tv_data_dg.setText(hl.getData_ultimo_dg());
			tv_data_parto.setText(hl.getData_parto_provavel());

			//ll.addView(tv_sync_no);
			//ll.addView(tv_sync_date);
			//ll.addView(tv_sync_orderid);

			//tr.addView(ll);
			tr.addView(tv_codigo);
			tr.addView(tv_data_parto);
			tr.addView(tv_data_dg);

			showRow.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

			showRow.setFocusable(true);
		}


	}

}
