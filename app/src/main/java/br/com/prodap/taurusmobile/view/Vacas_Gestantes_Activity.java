package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.model.Animal_Model;
import br.com.prodap.taurusmobile.model.Parto_Cria_Model;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.tb.Animal;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.tb.Vacas_Gestantes;
import br.com.prodap.taurusmobile.util.Mensagem_Util;
import br.com.prodap.taurusmobile.util.Message_Dialog;

public class Vacas_Gestantes_Activity extends Activity
{
	private Parto_Model p_model;
	private Parto_Cria_Model pc_model;
	private Parto p_tb;
	private EditText editDataFiltro;
	private Calendar calendario;
	private String filtro;
	private Mensagem_Util md;
	boolean init = false;
	private List<Parto> parto_list;
	private List<Parto_Cria> parto_cria_list;

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		displayHistory();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_vacas_gestantes);

		p_model = new Parto_Model(this);
		pc_model = new Parto_Cria_Model(this);
		p_tb	= new Parto();
		setTitle("Vacas Gestantes");
		//displayHistory();
		filtro = "";

		calendario = Calendar.getInstance();

		init = true;
		editDataFiltro = (EditText) findViewById(R.id.edtDataFiltro);
		changeDataFiltro();

		//Calendar cal_data_parto = Calendar.getInstance();
		Date date = new Date();
		String strDateParto;
		try
		{
			strDateParto = p_model.maxDataPartoProvavel(this);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(strDateParto != null)
				date = sdf.parse(strDateParto);
			//cal_data_parto.setTime(date);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		//Date currentTime = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		filtro = strDate;

		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		strDate = dateFormat.format(date);
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

	TableRow tr;
	public void RowClick(View view) {

		//@drawable/gradientinvertido
		tr = (TableRow)view;
		tr.setBackgroundResource(R.drawable.gradientinvertido);

		long value = (long)view.getTag();

        Animal_Model a_model = new Animal_Model(this);
        final Animal a_tb = a_model.selectByIdPk(this, value);

		boolean perdaGestacao = false;
        boolean tem_parto = false;
        Parto p_tb = new Parto();
        Parto_Cria pc_tb = new Parto_Cria();
		for(Parto p : parto_list)
        {
            if(p.getId_fk_animal() == value) {
                p_tb = p;
                tem_parto = true;
				if(!p.getPerda_gestacao().equals("NENHUMA"))
					perdaGestacao = true;
                break;
            }
        }
        for(Parto_Cria pc : parto_cria_list)
        {
            if(pc.getId_fk_animal_mae() == value) {
                pc_tb = pc;
                break;
            }
        }

		String msg = "";
		if(tem_parto) {
            msg = "Dados da Matriz\n"
                    + "\n - Código da Matriz: " + a_tb.getCodigo()
                    + "\n - Descarte: " + pc_tb.getRepasse();

			if(!perdaGestacao)
				msg +=
                       "\n\nDados da Cria\n"
                    + "\n - Código da Cria: " + pc_tb.getCodigo_cria()
                    + "\n - Código Alternativo: " + pc_tb.getCodigo_ferro_cria()
                    + "\n - Identif.: " + pc_tb.getIdentificador()
                    + "\n - Sisbov: " + pc_tb.getSisbov()
                    + "\n - Data do Parto: " + p_tb.getData_parto()
                    + "\n - Data da Identif.: " + pc_tb.getData_identificacao()
                    + "\n - Tipo de Parto: " + pc_tb.getTipo_parto()
                    + "\n - Sexo: " + pc_tb.getSexo()
                    + "\n - Peso: " + pc_tb.getPeso_cria()
                    + "\n - Grupo de Manejo: " + pc_tb.getGrupo_manejo()
                    + "\n - Pasto: " + pc_tb.getPasto();
        }
        else
        {
            msg = "Não existe lançamento de parto para a matriz '" + a_tb.getCodigo() + "', deseja lançar agora?";
        }

        if(tem_parto) {
			Mensagem_Util.addMsg(Message_Dialog.Yes, Vacas_Gestantes_Activity.this, msg, "Parto", 1);
			tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			try {
				builder.setTitle("Aviso").setMessage(msg)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Vacas_Gestantes_Activity.this, Parto_Activity.class);
								intent.putExtra("data_parto_provavel", a_tb.getData_parto_provavel());
								intent.putExtra("matriz", a_tb.getCodigo());

								tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
								startActivity(intent);

							}
						})
						.setNegativeButton("Não", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
								return;
							}
						})
						.show();
			} catch (Exception e) {
				Log.i("Vacas gestantes", e.toString());
				e.printStackTrace();
			}
		}
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


	}

	public void displayHistory(){

		List<Vacas_Gestantes> list = p_model.selectVacasGestantes(this, "Parto", p_tb, filtro);

		TableLayout showRow = (TableLayout) findViewById(R.id.history_table);
		showRow.removeAllViews();

		Parto parto_tb = new Parto();
		Parto_Cria parto_cria_tb = new Parto_Cria();
		parto_list 	= p_model.selectAll(getBaseContext(), "Parto", parto_tb);
		parto_cria_list = pc_model.selectAllCrias(getBaseContext(), "Parto_Cria", parto_cria_tb, true);
		List<Long> list_ids = new ArrayList<Long>();

		for(Parto p : parto_list)
			list_ids.add(p.getId_fk_animal());

		int count = 0;
		for(Vacas_Gestantes hl : list) {
			count++;
			TableRow tr = new TableRow(this);
			tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
			tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
			tr.setOrientation(TableRow.HORIZONTAL);
			tr.setPadding(0, 20, 0, 20);
			tr.setTag(hl.getIdpk());
			tr.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					RowClick(v);
				}
			});

			//LinearLayout ll = new LinearLayout(this);
			//ll.setLayoutParams(new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 30));

			TextView tv_codigo = new TextView(this);
			TextView tv_data_dg = new TextView(this);
			TextView tv_data_parto = new TextView(this);
			ImageView im_status = new ImageView(this);

			tv_codigo.setGravity(Gravity.CENTER);
			tv_data_dg.setGravity(Gravity.CENTER);
			tv_data_parto.setGravity(Gravity.CENTER);

			tv_codigo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f));
			tv_data_dg.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.4f));
			tv_data_parto.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.3f));
			im_status.setLayoutParams(new TableRow.LayoutParams(60, 60, 0.2f));

			tv_codigo.setTextSize(18);
			tv_data_dg.setTextSize(18);
			tv_data_parto.setTextSize(18);

			tv_codigo.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_data_dg.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_data_parto.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			im_status.setMaxWidth(10);

			tv_codigo.setText(hl.getCodigo());
			tv_data_dg.setText(hl.getData_ultimo_dg());
			tv_data_parto.setText(hl.getData_parto_provavel());
			if(list_ids.contains(hl.getIdpk()))
				im_status.setImageResource(R.drawable.check);
			else
				im_status.setImageResource(R.drawable.close);

			//ll.addView(tv_sync_no);
			//ll.addView(tv_sync_date);
			//ll.addView(tv_sync_orderid);

			//tr.addView(ll);
			tr.addView(tv_codigo);
			tr.addView(tv_data_parto);
			//tr.addView(tv_data_dg);
			tr.addView(im_status);

			showRow.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 3);
			layoutParams.setMargins(15, 3, 15, 3);

			View v_line = new View(this);
			v_line.setBackgroundColor(Color.GRAY);
			showRow.addView(v_line, layoutParams);

			showRow.setFocusable(true);
		}


	}

}
