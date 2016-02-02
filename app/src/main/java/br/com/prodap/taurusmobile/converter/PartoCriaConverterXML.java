package br.com.prodap.taurusmobile.converter;

import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto_Cria;

import com.thoughtworks.xstream.XStream;

public class PartoCriaConverterXML {
	private String resultXML;

	public String toXML(List<Parto_Cria> partos_cria) {
		XStream xml = new XStream();
		for (Parto_Cria parto_cria : partos_cria) {
			parto_cria.getId_fk_animal_mae();
			parto_cria.getPeso_cria();
			parto_cria.getCodigo_cria();
			parto_cria.getSexo();
			
			resultXML = xml.toXML(partos_cria);
		}
		return resultXML;
	}
}
