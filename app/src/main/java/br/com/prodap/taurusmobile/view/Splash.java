package br.com.prodap.taurusmobile.view;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import br.com.prodap.taurusmobile.R;

/**
 * Created by João on 1/24/2016.
 */
public class Splash extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();

                    Intent intent = new Intent();
                    intent.setClass(Splash.this, Menu_Principal_Activity.class);
                    startActivity(intent);

                }
            }, 2000);
        }
        catch (ActivityNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
