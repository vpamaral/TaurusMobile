package br.com.prodap.taurusmobile.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.prodap.taurusmobile.TB.Parto;

public class PartoAdapter extends BaseAdapter{

	private List<Parto> partos;
	private Activity activity;

	public PartoAdapter() {

	}

	public PartoAdapter(List<Parto> partos, Activity activity) {
		this.partos = partos;
		this.activity = activity;
	}
	
	public Parto PartoCursor(Cursor c) {
		Parto parto = new Parto();

		while (c.moveToNext()) {
			parto.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			parto.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			parto.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));
			parto.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
		}
		return parto;
	}

	public List<Parto> PartoPreencheArrayCursor(Cursor c) {
		List<Parto> listaParto = new ArrayList<Parto>();
		while (c.moveToNext()) {

			Parto parto = new Parto();

			parto.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			parto.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			parto.setSync_status(c.getInt(c.getColumnIndex("sync_status")));
			parto.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
			parto.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));

			listaParto.add(parto);
		}

		return listaParto;
	}

	public ArrayList<Parto> PartoPreencheArrayHelper(Parto[] PartoArray) {
		ArrayList<Parto> listaParto = new ArrayList<Parto>();
		for (int i = 0; i < PartoArray.length; i++) {

			Parto parto = new Parto();

			parto.setId_fk_animal(PartoArray[i].getId_fk_animal());
			parto.setData_parto(PartoArray[i].getData_parto());
			parto.setSync_status(PartoArray[i].getSync_status());
			parto.setPerda_gestacao(PartoArray[i].getPerda_gestacao());
			parto.setSexo_parto(PartoArray[i].getSexo_parto());

			listaParto.add(parto);
		}
		return listaParto;
	}

	public Parto PartoHelper(Parto parto_tb) {
		Parto parto = new Parto();

		parto.setId_fk_animal(parto_tb.getId_fk_animal());
		parto.setData_parto(parto_tb.getData_parto());
		parto.setSync_status(parto_tb.getSync_status());
		parto.setPerda_gestacao(parto_tb.getPerda_gestacao());
		parto.setSexo_parto(parto_tb.getSexo_parto());

		return parto;
	}

	@Override
	public int getCount() {
		return partos.size();
	}

	@Override
	public Object getItem(int position) {
		return partos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(String.valueOf(partos.get(position).getId_auto()));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}