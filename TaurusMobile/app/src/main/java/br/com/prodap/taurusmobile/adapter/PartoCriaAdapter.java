package br.com.prodap.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.prodap.taurusmobile.TB.Parto;
import br.com.prodap.taurusmobile.TB.Parto_Cria;

public class PartoCriaAdapter {
	public PartoCriaAdapter() {

	}
	
	public Parto_Cria PartoCriaCursor(Cursor c) {
		Parto_Cria parto_cria = new Parto_Cria();

		while (c.moveToNext()) {
			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setFgStatus(c.getInt(c.getColumnIndex("fgStatus")));
			parto_cria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			parto_cria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			parto_cria.setSexo(c.getString(c.getColumnIndex("sexo")));
		}
		return parto_cria;
	}

	public List<Parto_Cria> PartoCriaPreencheArrayCursor(Cursor c) {
		List<Parto_Cria> listaPartoCria = new ArrayList<Parto_Cria>();
		while (c.moveToNext()) {

			Parto_Cria parto_cria = new Parto_Cria();

			parto_cria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			parto_cria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			parto_cria.setIdentificador(c.getString(c.getColumnIndex("identificador")));
			parto_cria.setGrupo_manejo(c.getString(c.getColumnIndex("grupo_manejo")));
			parto_cria.setFgStatus(c.getInt(c.getColumnIndex("fgStatus")));
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
			parto_cria.setSisbov(PartoCriaArray[i].getSisbov());
			parto_cria.setIdentificador(PartoCriaArray[i].getIdentificador());
			parto_cria.setGrupo_manejo(PartoCriaArray[i].getGrupo_manejo());
			parto_cria.setFgStatus(PartoCriaArray[i].getFgStatus());
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
		parto_cria.setSisbov(parto_cria_tb.getSisbov());
		parto_cria.setIdentificador(parto_cria_tb.getIdentificador());
		parto_cria.setGrupo_manejo(parto_cria_tb.getGrupo_manejo());
		parto_cria.setFgStatus(parto_cria_tb.getFgStatus());
		parto_cria.setPeso_cria(parto_cria_tb.getPeso_cria());
		parto_cria.setCodigo_cria(parto_cria_tb.getCodigo_cria());
		parto_cria.setSexo(parto_cria_tb.getSexo());

		return parto_cria;
	}
}
