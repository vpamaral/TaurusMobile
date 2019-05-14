package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.tb.Relatorio_Parto;
import br.com.prodap.taurusmobile.tb.Parto;
import br.com.prodap.taurusmobile.tb.Parto_Cria;
import br.com.prodap.taurusmobile.tb.Vacas_Gestantes;

public class Parto_Adapter extends BaseAdapter
{
	private List<Parto> partos;
	private Activity activity;

	public Parto_Adapter() {

	}

	public Parto_Adapter(List<Parto> partos, Activity activity)
	{
		this.partos = partos;
		this.activity = activity;
	}
	
	public Parto PartoCursor(Cursor c)
	{
		Parto parto = new Parto();

		while (c.moveToNext())
		{
			parto.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			parto.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			parto.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			parto.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));
			parto.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
		}
		return parto;
	}

	@NonNull
	public ContentValues getDadosParto(Parto p_tb)
	{
		ContentValues p_dados = new ContentValues();
		p_dados.put("id_pk", p_tb.getId_pk());
		p_dados.put("id_fk_animal", p_tb.getId_fk_animal());
		p_dados.put("data_parto", p_tb.getData_parto());
		p_dados.put("perda_gestacao", p_tb.getPerda_gestacao());
		p_dados.put("sexo_parto", p_tb.getSexo_parto());
		p_dados.put("sync_status", p_tb.getSync_status());

		return p_dados;
	}

	public List<Parto> PartoPreencheArrayCursor(Cursor c)
	{
		List<Parto> listaParto = new ArrayList<Parto>();

		while (c.moveToNext())
		{
			Parto parto = new Parto();
			parto.setId_pk(c.getLong(c.getColumnIndex("id_pk")));
			parto.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			parto.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			parto.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
			parto.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));

			listaParto.add(parto);
		}

		return listaParto;
	}

	public List<Relatorio_Parto> RelatorioPartoPreencheArrayCursor(Cursor c)
	{
		List<Relatorio_Parto> listaParto = new ArrayList<Relatorio_Parto>();

		while (c.moveToNext())
		{
			Relatorio_Parto parto = new Relatorio_Parto();
			parto.setDataParto(c.getString(c.getColumnIndex("data_parto")));
			parto.setSexoParto(c.getString(c.getColumnIndex("sexo_parto")));
			parto.setQtdPartos(c.getInt(c.getColumnIndex("qtd_partos")));
			listaParto.add(parto);
		}

		return listaParto;
	}

	public List<Vacas_Gestantes> VacasGestantesPreencheArrayCursor(Cursor c)
	{
		List<Vacas_Gestantes> listaParto = new ArrayList<Vacas_Gestantes>();

		while (c.moveToNext())
		{
			Vacas_Gestantes parto = new Vacas_Gestantes();
			parto.setCodigo(c.getString(c.getColumnIndex("codigo")));
			parto.setData_ultimo_dg(c.getString(c.getColumnIndex("data_ultimo_dg")));
			parto.setData_parto_provavel(c.getString(c.getColumnIndex("data_parto_provavel")));
			listaParto.add(parto);
		}

		return listaParto;
	}

	public ArrayList<Parto> PartoPreencheArrayHelper(Parto[] PartoArray)
	{
		ArrayList<Parto> listaParto = new ArrayList<Parto>();

		for (int i = 0; i < PartoArray.length; i++)
		{
			Parto parto = new Parto();
			parto.setId_pk(PartoArray[i].getId_pk());
			parto.setId_fk_animal(PartoArray[i].getId_fk_animal());
			parto.setData_parto(PartoArray[i].getData_parto());
			parto.setSync_status(PartoArray[i].getSync_status());
			parto.setPerda_gestacao(PartoArray[i].getPerda_gestacao());
			parto.setSexo_parto(PartoArray[i].getSexo_parto());

			listaParto.add(parto);
		}

		return listaParto;
	}

	public Parto PartoHelper(Parto parto_tb)
	{
		Parto parto = new Parto();
		parto.setId_pk(parto_tb.getId_pk());
		parto.setId_fk_animal(parto_tb.getId_fk_animal());
		parto.setData_parto(parto_tb.getData_parto());
		parto.setSync_status(parto_tb.getSync_status());
		parto.setPerda_gestacao(parto_tb.getPerda_gestacao());
		parto.setSexo_parto(parto_tb.getSexo_parto());

		return parto;
	}

	public String PartoArqHelper(Parto parto_tb, Parto_Cria cria)
	{
		 String conteudo = "";

		conteudo = String.valueOf(parto_tb.getId_fk_animal()) + ","
		+ String.valueOf(parto_tb.getData_parto()) + ","
		+ String.valueOf(parto_tb.getSync_status()) + ","
		+ String.valueOf(parto_tb.getPerda_gestacao()) + ","
		+ String.valueOf(parto_tb.getSexo_parto()) + ","
		+ String.valueOf(cria.getId_fk_animal_mae()) + ","
		+ String.valueOf(cria.getSisbov()) + ","
		+ String.valueOf(cria.getIdentificador()) + ","
		+ String.valueOf(cria.getGrupo_manejo()) + ","
		+ String.valueOf(cria.getSync_status()) + ","
		+ String.valueOf(cria.getPeso_cria()) + ","
		+ String.valueOf(cria.getCodigo_cria()) + ","
		+ String.valueOf(cria.getSexo()) + ","
		+ String.valueOf(cria.getRaca_cria()) + ","
		+ String.valueOf(cria.getData_identificacao()) + ","
		//+ String.valueOf(cria.getRepasse()) + ","
		+ String.valueOf(cria.getTipo_parto())+ "|";
		//+ String.valueOf(cria.getPasto())+ "|";

		return conteudo;

	}

	@Override
	public int getCount()
	{
		return partos.size();
	}

	@Override
	public Object getItem(int position)
	{
		return partos.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return Long.parseLong(String.valueOf(partos.get(position).getId_fk_animal()));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}
}
