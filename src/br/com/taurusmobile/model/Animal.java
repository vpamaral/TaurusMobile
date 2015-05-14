package br.com.taurusmobile.model;

import java.io.Serializable;

public class Animal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sisbov;
	private String codigo;
	private String codigo_ferro;
	private String identificador;

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
