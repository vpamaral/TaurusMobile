package br.com.prodap.taurusmobile.TB;

import java.io.Serializable;

public class Configuracoes implements Serializable  {
	
	private String tipo;
	private String endereco;
	private String validaId;
	private String validaManejo;
	
	//public String autorizacao;
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getValidaId() {
		return validaId;
	}

	public void setValidaId(String validaId) {
		this.validaId = validaId;
	}

	public String getValidaManejo() {
		return validaManejo;
	}

	public void setValidaManejo(String validaManejo) {
		this.validaManejo = validaManejo;
	}


	//public String getAutorizacao() {
		//return autorizacao;
	//}

	//public void setAutorizacao(String autorizacao) {
		//this.autorizacao = autorizacao;
	//}
	
}
