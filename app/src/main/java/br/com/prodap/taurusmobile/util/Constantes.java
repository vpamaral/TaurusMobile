package br.com.prodap.taurusmobile.util;

import android.widget.TextView;

import br.com.prodap.taurusmobile.bluetooth.ConnectionThread;
import br.com.prodap.taurusmobile.service.Get_JSON;

public class Constantes
{
	//public static final String SERVER = "http://192.168.0.150/TaurusWebService/TaurusService.svc/";
	public static final String TAURUS_URL			= "/TaurusWebService/TaurusService.svc/";
	public static final String METHOD_GET_ANIMAIS 	= "listaAnimaisJson";
	public static final String METHOD_POST 			= "SendJson";
	public static final String METHOD_GET_PASTOS 	= "listaPastos";
	public static final String METHOD_GET_GRUPOS 	= "listaGrupos";
	public static final String METHOD_GET_CRITERIOS	= "listaCriterios";

	public static int VALIDATION_TYPE_INSERT = 0;
	public static int VALIDATION_TYPE_DELETE = 1;
	public static int VALIDATION_TYPE_UPDATE = 2;

	public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
	public static int ENABLE_BLUETOOTH = 1;
	public static int SELECT_PAIRED_DEVICE = 2;
	public static int SELECT_DISCOVERED_DEVICE = 3;
	public static final int READ_REQUEST_CODE = 42;

	public static String STATUS_CONN;
	public static TextView LBL_STATUS;
	public static String JSON;
	public static ConnectionThread CONNECT;
	public static Get_JSON GET_JSON;

	public static String ARQUIVO;
	public static boolean CREATE_ARQUIVO;
	public static String TIPO_ENVIO;

	public static String CALL_BLUETOOTH;
}
