package br.com.taurusmobile.TB;

import java.io.Serializable;

public class Parto implements Serializable {

	private long id_fk_animal;
	
	private String data_parto;
	
	private String sexo_parto;
	
	private String perda_gestacao;

	public long getId_fk_animal() {
		return id_fk_animal;
	}

	public void setId_fk_animal(long id_pk) {
		this.id_fk_animal = id_pk;
	}

	public String getData_parto() {
		return data_parto;
	}

	public void setData_parto(String data_parto) {
		this.data_parto = data_parto;
	}

	public String getSexo_parto() {
		return sexo_parto;
	}

	public void setSexo_parto(String sexo_parto) {
		this.sexo_parto = sexo_parto;
	}

	public String getPerda_gestacao() {
		return perda_gestacao;
	}

	public void setPerda_gestacao(String perda_gestacao) {
		this.perda_gestacao = perda_gestacao;
	}

}
