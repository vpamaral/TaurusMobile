package br.com.taurusmobile.converter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.taurusmobile.TB.Parto;

public class PartoConverterJSON {
	public String toJSON(List<Parto> partos) {
		JSONStringer js = new JSONStringer();
		try {
			js.object().key("list").array();
			js.object().key("parto").array();
			for (Parto parto : partos) {
				js.object();
				js.key("id_fk_animal").value(parto.getId_fk_animal());
				js.key("data_parto").value(parto.getData_parto());
				js.key("sexo_parto").value(parto.getSexo_parto());
				js.key("perda_gestacao").value(parto.getPerda_gestacao());
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