package br.com.prodap.taurusmobile.TB;

import java.io.Serializable;

public class Configuracoes implements Serializable  {
	
	public String tipo;
	
	public String endereco;
	
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

	//public String getAutorizacao() {
		//return autorizacao;
	//}

	//public void setAutorizacao(String autorizacao) {
		//this.autorizacao = autorizacao;
	//}
	
}
