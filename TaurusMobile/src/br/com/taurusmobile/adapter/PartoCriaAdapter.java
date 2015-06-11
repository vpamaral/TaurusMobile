package br.com.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.taurusmobile.TB.Parto_Cria;

public class PartoCriaAdapter {
	public PartoCriaAdapter() {

	}

	public List<Parto_Cria> PartoCriaPreencheArrayCursor(Cursor c) {
		List<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		while (c.moveToNext()) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));			
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));

			listaPartoCria.add(parto_cria);
		}

		return listaPartoCria;
	}

	public ArrayList<Parto_Cria> PartoCriaPreencheArrayHelper(Parto_Cria[] PartoCriaArray) {
		ArrayList<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		for (int i = 0; i < PartoCriaArray.length; i++) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(PartoCriaArray[i].getId_fk_animal_mae());
			parto_cria.setPeso_cria(PartoCriaArray[i].getPeso_cria());
			parto_cria.setCodigo_cria(PartoCriaArray[i].getCodigo_cria());
			parto_cria.setSexo(PartoCriaArray[i].getSexo());			

			listaPartoCria.add(parto_cria);
		}
		return listaPartoCria;
	}

	public Parto_Cria PartoCriaHelper(Parto_Cria parto_cria_tb) {
		Parto_Cria parto_cria = new Parto_Cria();

		parto_cria.setId_fk_animal_mae(parto_cria_tb.getId_fk_animal_mae());
		parto_cria.setPeso_cria(parto_cria_tb.getPeso_cria());
		parto_cria.setCodigo_cria(parto_cria_tb.getCodigo_cria());
		parto_cria.setSexo(parto_cria_tb.getSexo());

		return parto_cria;
	}

}
