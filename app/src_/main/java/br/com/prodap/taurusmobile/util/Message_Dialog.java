package br.com.prodap.taurusmobile.util;

public enum Message_Dialog {
	
	Toast(1), Yes(2), YesOk(3); 
	
	private final int valor; 
	
	Message_Dialog(int valorOpcao){
		valor = valorOpcao; 
	} 
	
	public int getValor(){ 
		return valor; 
	}
	
}
