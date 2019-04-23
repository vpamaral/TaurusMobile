package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.view.View;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.model.Parto_Model;
import br.com.prodap.taurusmobile.tb.Parto;
import java.util.List;

public class Relatorio_Partos_Activity extends Activity
{
	private Parto_Model p_model;
	private Parto p_tb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_relatorio_partos);

		p_model = new Parto_Model(this);
		p_tb	= new Parto();
		setTitle("Partos Gerados");
		displayHistory();
	}


	public void displayHistory(){

		List<Relatorio_Parto> list = p_model.selectRelatorioPartos(this, "Parto", p_tb);

		TableLayout showRow = (TableLayout) findViewById(R.id.history_table);
		int count = 0;
		for(Relatorio_Parto hl : list) {
			count++;
			TableRow tr = new TableRow(this);
			tr.setBackgroundColor(getResources().getColor(R.color.bootstrap_gray_lighter));
			tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
			tr.setOrientation(TableRow.HORIZONTAL);
			tr.setPadding(0,5,0,0);

			//LinearLayout ll = new LinearLayout(this);
			//ll.setLayoutParams(new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 30));

			TextView tv_data = new TextView(this);
			TextView tv_sexo = new TextView(this);
			TextView tv_Qtd = new TextView(this);

			tv_data.setGravity(Gravity.CENTER);
			tv_sexo.setGravity(Gravity.CENTER);
			tv_Qtd.setGravity(Gravity.CENTER);

			tv_data.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
			tv_sexo.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
			tv_Qtd.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));

			tv_data.setTextSize(18);
			tv_sexo.setTextSize(18);
			tv_Qtd.setTextSize(18);

			tv_data.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_sexo.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));
			tv_Qtd.setTextColor(getResources().getColor(R.color.bootstrap_gray_dark));

			tv_data.setText(hl.getDataParto());
			tv_sexo.setText(hl.getSexoParto());
			tv_Qtd.setText(Integer.toString(hl.getQtdPartos()));

			//ll.addView(tv_sync_no);
			//ll.addView(tv_sync_date);
			//ll.addView(tv_sync_orderid);

			//tr.addView(ll);
			tr.addView(tv_data);
			tr.addView(tv_sexo);
			tr.addView(tv_Qtd);


			showRow.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		}


	}

}
