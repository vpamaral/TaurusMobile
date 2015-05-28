package br.com.taurusmobile.TB;

import java.io.Serializable;

import br.com.taurusmobile.Annotation.AColumn;

public class Animal implements Serializable {

	
	public String sisbov;
	public String codigo;
	public String codigo_ferro;
	public String identificador;

	
	public String getSisbov() {
		return sisbov;
	}

	public void setSisbov(String sisbov) {
		this.sisbov = sisbov;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo_ferro() {
		return codigo_ferro;
	}

	public void setCodigo_ferro(String codigo_ferro) {
		this.codigo_ferro = codigo_ferro;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
