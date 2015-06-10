package br.com.taurusmobile.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class MensagemUtil {
	
	/**
	 * M�todo de cria��o de mensagens r�pidas.
	 * 
	 * @param activity
	 * @param msg
	 * 
	 */
	public static void addMsg(MessaDialog window, Activity activity, String msg) {
		
		if(window == MessaDialog.Toast){
			Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * M�todo de cria��o de mensagens com bot�o "Ok"
	 * 
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 */
	public static void addMsg(MessaDialog window, Activity activity, String msg, String titulo, int icone){
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setNeutralButton("Ok", null);
		builderDialog.setIcon(icone);
		builderDialog.show();
	}
	
	/**
	 * M�todo para cria��o de uma mensagem de di�logo com op��es de sim ou n�o
	 * 
	 * @param activity
	 * @param titulo
	 * @param msg
	 * @param icone
	 * @param listener
	 */
	public static void addMsg(Activity activity, String titulo, 
		String msg, int icone, OnClickListener listener) {
		AlertDialog.Builder builderDialog = new AlertDialog.Builder(activity);
		builderDialog.setTitle(titulo);
		builderDialog.setMessage(msg);
		builderDialog.setPositiveButton("Sim", listener);
		builderDialog.setNegativeButton("N�o", null);
		builderDialog.setIcon(icone);
		builderDialog.show();
	}

}
