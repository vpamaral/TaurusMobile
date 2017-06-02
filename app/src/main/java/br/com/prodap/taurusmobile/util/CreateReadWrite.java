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
    private SimpleDateFormat date_format;
    private Date data;
    private Date data_atual;
    private Calendar calendar;
    private String arquivo_name;

    private String filename;
    private File file;// = null;
    private Context ctx;

    public CreateReadWrite(Context ctx)
    {
        this.ctx = ctx;

        source();
    }

    private void source()
    {
        date_format             = new SimpleDateFormat("yyyyMMdd_HHmm");
        data 					= new Date();
        calendar 				= Calendar.getInstance();

        calendar.setTime(data);

        data_atual 				= calendar.getTime();
        arquivo_name            = date_format.format(data_atual);
    }

    public boolean writeInFile(String text)
    {
        permission();
        BufferedReader input    = null;

        try
        {
            if (Constantes.TIPO_ENVIO == "arquivo")
            {
                filename = arquivo_name + "_.tpa";
                file     = new File(Environment.getExternalStorageDirectory()+"/Prodap/Arquivos_Mobile/A_Enviar", filename);
            }
            if (Constantes.TIPO_ENVIO == "bluetooth" || Constantes.TIPO_ENVIO == "web")
            {
                filename = "Enviado_" + arquivo_name + "_.tpa";
                file     = new File(Environment.getExternalStorageDirectory()+"/Prodap/Arquivos_Mobile/Enviados", filename);
            }

            validateFile(text, file);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private void permission()
    {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.CAMERA}, 123);
        }
    }

    public void createFile() throws IOException
    {
        permission();

        Date data           = new Date();
        final Calendar cal  = Calendar.getInstance();

        cal.setTime(data);

        String conteudo             = "";
        File dir_padrao             = new File(obterDiretorio(), "Prodap");
        File dir_mobile             = new File(obterDiretorio(), "Prodap/Arquivos_Mobile");
        File dir_mobile_enviar      = new File(obterDiretorio(), "Prodap/Arquivos_Mobile/A_Enviar");
        File dir_mobile_enviados    = new File(obterDiretorio(), "Prodap/Arquivos_Mobile/Enviados");
        File dir_server             = new File(obterDiretorio(), "Prodap/Arquivos_Servidor");

        if(!dir_padrao.exists() || dir_mobile.exists() || dir_server.exists()
                || dir_mobile_enviar.exists() || dir_mobile_enviados.exists())
        {
            dir_padrao.mkdir();
            dir_mobile.mkdir();
            dir_server.mkdir();
            dir_mobile_enviar.mkdir();
            dir_mobile_enviados.mkdir();
        }

        if (filename != null)
        {
            File arquivo = new File(Environment.getExternalStorageDirectory() + "/Prodap/Arquivos_Mobile/Enviados", filename);
            validateFile(conteudo, arquivo);
        }
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
