package br.com.prodap.taurusmobile.util;

import android.content.Context;

public class Auxiliar {
	String valor;
	Context ctx;
	
	public Auxiliar(String v){
		this.valor = v;
	}

	/*public void validateServer(String urlServer) {
		int count = 0;
		for (int i = 0; i < urlServer.length(); i++) {
			if (urlServer.charAt(i) == '/') {
				count++;
			}
		}
		if (count != 5) {
			MensagemUtil.addMsg(MessageDialog.Toast, ctx
					, "O URL do Servidor está inválido!" +
					"\nFavor configurar o servidor.");
		}
	}*/

}
