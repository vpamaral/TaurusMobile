package br.com.prodap.taurusmobile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.prodap.taurusmobile.TB.Parto_PartoCria;

public class Parto_PartoCriaAdapter {
	public Parto_PartoCriaAdapter() {

	}

	public List<Parto_PartoCria> P_PartoCriaPreencheArrayCursor(Cursor c) {
		List<Parto_PartoCria> listaPartoCria = new ArrayList<Parto_PartoCria>();
		while (c.moveToNext()) {

			Parto_PartoCria p_partoCria = new Parto_PartoCria();

			p_partoCria.setId_fk_animal_mae(c.getLong(c.getColumnIndex("id_fk_animal_mae")));
			p_partoCria.setPeso_cria(c.getString(c.getColumnIndex("peso_cria")));
			p_partoCria.setCodigo_cria(c.getString(c.getColumnIndex("codigo_cria")));
			p_partoCria.setSexo(c.getString(c.getColumnIndex("sexo")));
			p_partoCria.setId_fk_animal(c.getLong(c.getColumnIndex("id_fk_animal")));
			p_partoCria.setData_parto(c.getString(c.getColumnIndex("data_parto")));
			p_partoCria.setPerda_gestacao(c.getString(c.getColumnIndex("perda_gestacao")));
			p_partoCria.setSexo_parto(c.getString(c.getColumnIndex("sexo_parto")));
			p_partoCria.setSisbov(c.getString(c.getColumnIndex("sisbov")));
			p_partoCria.setIdentificador(c.getString(c.getColumnIndex("identificador")));

			listaPartoCria.add(p_partoCria);
		}

		return listaPartoCria;
	}

	public ArrayList<Parto_PartoCria> P_PartoCriaPreencheArrayHelper(
			Parto_PartoCria[] PartoCriaArray) {
		ArrayList<Parto_PartoCria> listaPartoCria = new ArrayList<Parto_PartoCria>();
		for (int i = 0; i < PartoCriaArray.length; i++) {

			Parto_PartoCria p_partoCria = new Parto_PartoCria();

			p_partoCria.setId_fk_animal_mae(PartoCriaArray[i].getId_fk_animal_mae());
			p_partoCria.setPeso_cria(PartoCriaArray[i].getPeso_cria());
			p_partoCria.setCodigo_cria(PartoCriaArray[i].getCodigo_cria());
			p_partoCria.setSexo(PartoCriaArray[i].getSexo());
			p_partoCria.setId_fk_animal(PartoCriaArray[i].getId_fk_animal());
			p_partoCria.setData_parto(PartoCriaArray[i].getData_parto());
			p_partoCria.setPerda_gestacao(PartoCriaArray[i].getPerda_gestacao());
			p_partoCria.setSexo_parto(PartoCriaArray[i].getSexo_parto());
			p_partoCria.setSisbov(PartoCriaArray[i].getSisbov());
			p_partoCria.setIdentificador(PartoCriaArray[i].getIdentificador());

			listaPartoCria.add(p_partoCria);
		}
		return listaPartoCria;
	}

	public Parto_PartoCria P_PartoCriaHelper(Parto_PartoCria p_partoCria_tb) {
		Parto_PartoCria p_partoCria = new Parto_PartoCria();

		p_partoCria.setId_fk_animal_mae(p_partoCria_tb.getId_fk_animal_mae());
		p_partoCria.setPeso_cria(p_partoCria_tb.getPeso_cria());
		p_partoCria.setCodigo_cria(p_partoCria_tb.getCodigo_cria());
		p_partoCria.setSexo(p_partoCria_tb.getSexo());
		p_partoCria.setId_fk_animal(p_partoCria_tb.getId_fk_animal());
		p_partoCria.setData_parto(p_partoCria_tb.getData_parto());
		p_partoCria.setPerda_gestacao(p_partoCria_tb.getPerda_gestacao());
		p_partoCria.setSexo_parto(p_partoCria_tb.getSexo_parto());
		p_partoCria.setSisbov(p_partoCria_tb.getSisbov());
		p_partoCria.setIdentificador(p_partoCria_tb.getIdentificador());

		return p_partoCria;
	}
}
