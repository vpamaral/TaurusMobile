package br.com.prodap.taurusmobile.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.prodap.taurusmobile.R;
import br.com.prodap.taurusmobile.bluetooth.ConnectionThread;
import br.com.prodap.taurusmobile.bluetooth.DiscoveredDevices;
import br.com.prodap.taurusmobile.bluetooth.PairedDevices;
import br.com.prodap.taurusmobile.util.Constantes;

public class Bluetooth_Activity extends Activity
{
    private ConnectionThread connect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Constantes.LBL_STATUS  = (TextView)findViewById(R.id.lblStatusMSG);

        isEnableBluetooth();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == Constantes.ENABLE_BLUETOOTH)
        {
            if(resultCode == RESULT_OK)
            {
                Constantes.LBL_STATUS.setText("Bluetooth ativado");
            }
            else
            {
                Constantes.LBL_STATUS.setText("Bluetooth não ativado");
            }
        }
        else if(requestCode == Constantes.SELECT_PAIRED_DEVICE || requestCode == Constantes.SELECT_DISCOVERED_DEVICE)
        {
            if(resultCode == RESULT_OK)
            {
                Constantes.LBL_STATUS.setText("Aguarde conectando ao servidor...");

                connection(data.getStringExtra("btDevAddress"));
            }
            else
            {
                Constantes.LBL_STATUS.setText("Nenhum dispositivo selecionado");
            }
        }
    }*/

    public void isEnableBluetooth()
    {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null)
        {
            Constantes.LBL_STATUS.setText("Hardware Bluetooth não está funcionando");
        }
        else
        {
            Constantes.LBL_STATUS.setText("Hardware Bluetooth está funcionando");
        }

        if(!btAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constantes.ENABLE_BLUETOOTH);
            Constantes.LBL_STATUS.setText("Solicitando ativação do Bluetooth...");
        }
        else
        {
            Constantes.LBL_STATUS.setText("Bluetooth já ativado");
        }
    }

    public void searchPairedDevices(Activity activity)
    {
        Intent searchPairedDevicesIntent = new Intent(activity, PairedDevices.class);
        activity.startActivityForResult(searchPairedDevicesIntent, Constantes.SELECT_PAIRED_DEVICE);
    }

    public void discoverDevices(Activity activity)
    {
        Intent searchPairedDevicesIntent = new Intent(activity, DiscoveredDevices.class);
        activity.startActivityForResult(searchPairedDevicesIntent, Constantes.SELECT_DISCOVERED_DEVICE);
    }

    public void enableVisibility(Activity activity)
    {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        activity.startActivity(discoverableIntent);
    }

    public void waitConnection()
    {
        connect = new ConnectionThread();
        connect.start();
    }

    public void connection(String device)
    {
        Constantes.CONNECT = new ConnectionThread(device);
        Constantes.CONNECT.start();
    }

    public void unconnection()
    {
        connect.cancel();
    }
}
