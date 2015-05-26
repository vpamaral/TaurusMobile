package br.com.taurusmobile.TB;

import java.io.Serializable;

import br.com.taurusmobile.Annotation.AColumn;

public class Animal implements Serializable {

	
	public String sisbov;
	public int codigo;
	public String codigo_ferro;
	public String identificador;

	
	@AColumn(position=0)
	public String getSisbov() {
		return sisbov;
	}

	public void setSisbov(String sisbov) {
		this.sisbov = sisbov;
	}

	@AColumn(position=1)
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@AColumn(position=2)
	public String getCodigo_ferro() {
		return codigo_ferro;
	}

	public void setCodigo_ferro(String codigo_ferro) {
		this.codigo_ferro = codigo_ferro;
	}

	@AColumn(position=3)
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
