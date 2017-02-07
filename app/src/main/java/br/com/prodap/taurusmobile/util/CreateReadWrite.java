package br.com.prodap.taurusmobile.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Prodap on 19/10/2016.
 */

public class CreateReadWrite
{
    private SimpleDateFormat dateFormat;
    private Date data;
    private Date data_atual;
    private Calendar calendar;
    private String data_dd_mm_yyyy;

    private String filename;
    private Context ctx;

    public CreateReadWrite(Context ctx)
    {
        this.ctx = ctx;

        source();
    }

    private void source()
    {
        dateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
        data 					= new Date();
        calendar 				= Calendar.getInstance();

        calendar.setTime(data);

        data_atual 				= calendar.getTime();
        data_dd_mm_yyyy 		= dateFormat.format(data_atual);
    }

    public boolean writeInFile(String text)
    {
        permision();
        BufferedReader input    = null;
        File file               = null;

        try
        {
            if (Constantes.CREATE_ARQUIVO == "Arquivo")
                filename = "ProdapArquivo" + data_dd_mm_yyyy +".txt";

            if (Constantes.CREATE_ARQUIVO == "Bluetooth")
                filename = "ProdapBluetooth" + data_dd_mm_yyyy +".txt";

            if (Constantes.CREATE_ARQUIVO == "Web")
                filename = "ProdapWeb" + data_dd_mm_yyyy +".txt";

                //filename = Constantes.CREATE_ARQUIVO == "Arquivo" ? "ProdapArquivo" + data_dd_mm_yyyy +".txt" : "ProdapAtivoBluetooth" + data_dd_mm_yyyy +".txt";

            file = new File(Environment.getExternalStorageDirectory()+"/Prodap", filename);

            validateFile(text, file);
            /*FileOutputStream in = new FileOutputStream(file, true);
            in.write(text.getBytes());
            in.write("\n".getBytes());
            in.flush();
            in.close();*/

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private void permision()
    {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }
    }

    public void createFile() throws IOException
    {
        permision();

        Date data           = new Date();
        final Calendar cal  = Calendar.getInstance();

        cal.setTime(data);

        filename        = "backup.txt";
        String conteudo = "";
        File diretorio  = new File(obterDiretorio(), "Prodap");

        if(!diretorio.exists())
        {
            diretorio.mkdir();
        }

        File arquivo = new File(Environment.getExternalStorageDirectory() + "/Prodap", filename);

        validateFile(conteudo, arquivo);
    }

    private void validateFile(String conteudo, File arquivo)
    {
        FileOutputStream outputStream = null;
        try
        {
            if(!arquivo.exists())
            {
                outputStream = new FileOutputStream(arquivo);
                outputStream.write(conteudo.getBytes());
                outputStream.close();
            }
            else
            {
                outputStream = new FileOutputStream(arquivo, true);
                outputStream.write(conteudo.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private String obterDiretorio()
    {
        File root = Environment.getExternalStorageDirectory();
        return root.toString();
    }
}
