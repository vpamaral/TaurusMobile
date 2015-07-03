package br.com.taurusmobile.converter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.taurusmobile.TB.Parto_Cria;

public class PartoCriaConverterJSON {
	public String toJSON(List<Parto_Cria> partos_cria) {
		JSONStringer js = new JSONStringer();
		try {
			js.object().key("list").array();
			js.object().key("parto_cria").array();
			for (Parto_Cria parto : partos_cria) {
				js.object();
				js.key("id_fk_animal_mae").value(parto.getId_fk_animal_mae());
				js.key("peso_cria").value(parto.getPeso_cria());
				js.key("codigo_cria").value(parto.getCodigo_cria());
				js.key("sexo").value(parto.getSexo());
				js.endObject();
			}
			js.endArray().endObject();
			js.endArray().endObject();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js.toString();
	}
}