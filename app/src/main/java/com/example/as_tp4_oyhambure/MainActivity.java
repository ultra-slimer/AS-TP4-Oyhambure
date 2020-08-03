package com.example.as_tp4_oyhambure;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    FragmentManager adminfragments;
    FragmentTransaction transactionfragments;
    String nombre;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminfragments = getFragmentManager();

        Fragments.Name fragmentingreso;
        fragmentingreso = new Fragments.Name();
        transactionfragments=adminfragments.beginTransaction();
        transactionfragments.replace(R.id.alojafragment, fragmentingreso);
        transactionfragments.commit();
    }

    public void procesardatosrecibidos(String nombrercibido){
        Log.d("Datito", "Se busco: " +nombrercibido);
        nombre = nombrercibido;
        Fragments.Results fragmentresultados;
        fragmentresultados = new Fragments.Results();
        transactionfragments=adminfragments.beginTransaction();
        transactionfragments.replace(R.id.alojafragment, fragmentresultados);
        transactionfragments.commit();
    }

    public void procesardetalle(String idrecibida){
        id = idrecibida;
        Fragments.Detail fragmentDetalle;
        fragmentDetalle = new Fragments.Detail();
        transactionfragments=adminfragments.beginTransaction();
        transactionfragments.replace(R.id.alojafragment, fragmentDetalle);
        transactionfragments.commit();

    }
}
