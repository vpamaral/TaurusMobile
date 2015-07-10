package br.com.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.taurusmobile.TB.Animal;
import br.com.taurusmobile.TB.Parto;

public class PartoAdapter {

	public PartoAdapter() {

	}
	
	public Parto PartoCursor(Cursor c) {
		Parto parto = new Parto();

		while (c.moveToNext()) {
			parto.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			parto.setData_parto(c.getString(c.getColumnIndex("data_parto")));
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
			parto.setPerda_gestacao(c.getString(c
					.getColumnIndex("perda_gestacao")));
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
		parto.setPerda_gestacao(parto_tb.getPerda_gestacao());
		parto.setSexo_parto(parto_tb.getSexo_parto());

		return parto;
	}

}
