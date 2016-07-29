package br.com.prodap.taurusmobile.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto_Parto_Cria;

public class Parto_Parto_Cria_JSON {
	public String toJSON(List<Parto_Parto_Cria> partos_partoCria) {
		JSONStringer js = new JSONStringer();
		try {
			js.object().key("parto_partoCria").array();
			for (Parto_Parto_Cria p_partoCria : partos_partoCria) {
				js.object();
				js.key("id_fk_animal").value(p_partoCria.getId_fk_animal());
				js.key("data_parto").value(p_partoCria.getData_parto());
				js.key("sexo_parto").value(p_partoCria.getSexo_parto());
				js.key("perda_gestacao").value(p_partoCria.getPerda_gestacao());
				js.key("id_fk_animal_mae").value(p_partoCria.getId_fk_animal_mae());
				js.key("peso_cria").value(p_partoCria.getPeso_cria());
				js.key("grupo_manejo").value(p_partoCria.getGrupo_manejo());
				js.key("codigo_cria").value(p_partoCria.getCodigo_cria());
				js.key("sexo").value(p_partoCria.getSexo());
				js.key("sisbov").value(p_partoCria.getSisbov());
				js.key("identificador").value(p_partoCria.getIdentificador());
				js.key("sync_status").value(p_partoCria.getSync_status());
				js.key("raca_cria").value(p_partoCria.getRaca_cria());
				js.key("data_identificacao").value(p_partoCria.getData_identificacao());
				js.key("repasse").value(p_partoCria.getRepasse());
				js.key("tipo_parto").value(p_partoCria.getTipo_parto());
				js.key("cod_matriz_invalido").value(p_partoCria.getCod_matriz_invalido());
				js.key("pasto").value(p_partoCria.getPasto());
				js.key("criterio").value(p_partoCria.getCriterio());
				js.endObject();
			}
			js.endArray().endObject();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js.toString();
	}
}
