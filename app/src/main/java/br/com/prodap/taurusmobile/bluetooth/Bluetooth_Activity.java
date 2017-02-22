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

public class Bluetooth_Activity extends Activity
{
    private static int ENABLE_BLUETOOTH = 1;
    private static int SELECT_PAIRED_DEVICE = 2;
    private static int SELECT_DISCOVERED_DEVICE = 3;
    private ConnectionThread connect;
    private static TextView statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        statusMessage   = (TextView)findViewById(R.id.lblStatusMSG);

        setTitle("Conexão via Bluetooth");

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter == null)
        {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
        }
        else
        {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        if(!btAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
            statusMessage.setText("Solicitando ativação do Bluetooth...");
        }
        else
        {
            statusMessage.setText("Bluetooth já ativado :)");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == ENABLE_BLUETOOTH)
        {
            if(resultCode == RESULT_OK)
            {
                statusMessage.setText("Bluetooth ativado :D");
            }
            else
            {
                statusMessage.setText("Bluetooth não ativado :(");
            }
        }
        else if(requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE)
        {
            if(resultCode == RESULT_OK)
            {
                statusMessage.setText(data.getStringExtra("btDevName") + " - "
                        + data.getStringExtra("btDevAddress"));

                connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
                connect.start();
                finish();
            }
            else
            {
                statusMessage.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }

    public void searchPairedDevices(View view)
    {
        Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

    public void discoverDevices(View view)
    {
        Intent searchPairedDevicesIntent = new Intent(this, DiscoveredDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
    }

    public void enableVisibility(View view)
    {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        startActivity(discoverableIntent);
    }

    public void waitConnection(View view)
    {
        connect = new ConnectionThread();
        connect.start();
    }

    /*public void sendMessage(View view)
    {
        EditText messageBox = (EditText) findViewById(R.id.editText_MessageBox);
        String messageBoxString = messageBox.getText().toString();
        byte[] data =  messageBoxString.getBytes();
        connect.write(data);
        messageBox.setText("");
    }*/
}
