package com.example.as_tp4_oyhambure;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AM extends BaseAdapter {
    private ArrayList<String> _Milista;
    private Context _Micontexto;
    private ArrayList<Bitmap> _Milistaposter;

    public AM(ArrayList<String> Listapeliculas, ArrayList<Bitmap> Listaposter,Context contextoausar){
        _Milista = Listapeliculas;
        Log.d("monki", "cantidad de resultades: " + _Milista.size());
        _Micontexto = contextoausar;
        _Milistaposter = Listaposter;
        Log.d("monki", "cantidad de imagenes traidas: " + _Milistaposter.size());
    }

    public int getCount(){
        return _Milista.size();
    }

    public String getItem(int posicion){
        String devolveme;
        devolveme = _Milista.get(posicion);
        return devolveme;
    }

    public long getItemId(int posicion) {
        return posicion;
    }

    public View getView(int posicion, View vistaactual, ViewGroup grupoactual){
        View devolveme;
        ArrayList<String> listaurls;
        devolveme = null;
        LayoutInflater layoutInflater;

        layoutInflater = (LayoutInflater)_Micontexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        devolveme = layoutInflater.inflate(R.layout.listview_movies, grupoactual, false);
        TextView titulopelicula;
        titulopelicula = (TextView)devolveme.findViewById(R.id.textview_TituloPelicula);
        String textoposicion;
        ImageView imagen;
        imagen = devolveme.findViewById(R.id.fotito);
        imagen.setImageBitmap(_Milistaposter.get(posicion));
        textoposicion = getItem(posicion);
        titulopelicula.setText(textoposicion);

        return devolveme;
    }
}
