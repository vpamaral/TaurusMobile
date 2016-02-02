package br.com.prodap.taurusmobile.converter;

import java.util.List;

import br.com.prodap.taurusmobile.tb.Parto;

import com.thoughtworks.xstream.XStream;

public class PartoConverterXML {
	private String resultXML;

	public String toXML(List<Parto> partos) {
		XStream xml = new XStream();
		for (Parto parto : partos) {
			parto.getId_fk_animal();
			parto.getData_parto();
			parto.getSexo_parto();
			parto.getPerda_gestacao();
			
			resultXML = xml.toXML(partos);
		}
		return resultXML;
	}
}
