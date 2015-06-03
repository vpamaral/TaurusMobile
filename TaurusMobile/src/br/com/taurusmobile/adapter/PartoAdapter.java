package br.com.taurusmobile.adapter;

import br.com.taurusmobile.TB.Parto;

public class PartoAdapter {

	
	public Parto PartoHelper(Parto parto_tb)
	{
		Parto parto = new Parto();
		
		parto.setId_fk_animal(parto_tb.getId_fk_animal());
		parto.setData_parto(parto_tb.getData_parto());
		parto.setPerda_gestacao(parto_tb.getPerda_gestacao());
		parto.setSexo_parto(parto_tb.getSexo_parto());
		
		
		return parto;
	}
	
}
