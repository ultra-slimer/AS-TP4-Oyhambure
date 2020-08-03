package com.example.as_tp4_oyhambure;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Fragments {
    public static class Name extends Fragment implements View.OnClickListener{
        EditText nombre;
        Button btnbuscar;
        public View onCreateView(LayoutInflater infladornombre, ViewGroup gruponombre, Bundle datos){
            View vistadevuelta;
            vistadevuelta = infladornombre.inflate(R.layout.layout_input, gruponombre, false);
            nombre = vistadevuelta.findViewById(R.id.nombre);
            btnbuscar = vistadevuelta.findViewById(R.id.btnbuscar);
            btnbuscar.setOnClickListener(this);
            return vistadevuelta;
        }

        public void onClick(View vistarecibida){
            String mandame;
            mandame = nombre.getText().toString();

            MainActivity anfitriona;
            anfitriona=(MainActivity) getActivity();
            anfitriona.procesardatosrecibidos(mandame);
        }
    }
    public static class Results extends Fragment implements View.OnClickListener{
        TextView nombretraido;
        String monki;
        CharSequence dale;
        Context context;
        ArrayList<String> listapelis;
        ArrayList<Bitmap> listafotos;
        ArrayList<String> listaids;
        ListView lista;
        int i = 0;
        int x = 0;
        public View onCreateView(LayoutInflater infladornombre, ViewGroup gruponombre, Bundle datos){
            View vistadevuelta;
            vistadevuelta = infladornombre.inflate(R.layout.layout_results, gruponombre, false);
            nombretraido = vistadevuelta.findViewById(R.id.resultadito);
            lista = vistadevuelta.findViewById(R.id.lista);
            lista.setOnItemClickListener(escuchador);
            MainActivity anfitriona;
            anfitriona=(MainActivity)getActivity();
            monki = anfitriona.nombre + " Results:";
            dale = monki;
            Movies mispeliculas;
            mispeliculas = new Movies();
            listapelis = mispeliculas.Obtenertodas(anfitriona.nombre);
            listaids = mispeliculas.Traerids();
            Log.d("monki", "Tamanio: " + listapelis.size() );
            AM adaptador;
            context = getContext();
            listafotos = mispeliculas.Traerfotos();
            adaptador = new AM(listapelis, listafotos ,context);
            lista.setAdapter(adaptador);
            i = adaptador.getCount();
            Log.d("monki", "Tamanio: " + listapelis.size() );
            for (x = 0; x < i; x++)
            {
                Log.d("monki", "posicion: " + x );
                adaptador.getView(x, vistadevuelta, gruponombre);
            }

            nombretraido.setText(dale);

            return vistadevuelta;
        }

        public void onClick(View vistarecibida){
            String mandame;
            mandame = nombretraido.getText().toString();

            MainActivity anfitriona;
            anfitriona=(MainActivity) getActivity();
            anfitriona.procesardatosrecibidos(mandame);
        }

        AdapterView.OnItemClickListener escuchador = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Monki", "Posicion: " + position);
                Log.d("Monki", "Id de la posicion: " + listaids.get(position));
                String idelegido = listaids.get(position);
                MainActivity anfitriona;
                anfitriona=(MainActivity) getActivity();
                anfitriona.procesardetalle(idelegido);
            }
        };
    }
    public static class Detail extends Fragment{
        ImageView imagen;
        TextView titulofinal;
        TextView anio;
        TextView director;
        TextView plot;
        String id;
        Boolean termine = false;
        Boolean acabe = false;
        String traetitulo;
        String traeanio;
        String traedirector;
        String traeplot;
        String posterurl;

        public View onCreateView(LayoutInflater infladornombre, ViewGroup gruponombre, Bundle datos){
            View vistadevuelta;
            vistadevuelta = infladornombre.inflate(R.layout.layout_detail, gruponombre, false);
            imagen = vistadevuelta.findViewById(R.id.fotofinal);
            titulofinal = vistadevuelta.findViewById(R.id.titulofinal);
            anio = vistadevuelta.findViewById(R.id.anio);
            director = vistadevuelta.findViewById(R.id.director);
            plot = vistadevuelta.findViewById(R.id.plot);
            MainActivity anfitriona;
            anfitriona=(MainActivity)getActivity();
            id = anfitriona.id;
            Tareatraeporid tarea;
            tarea = new Tareatraeporid();
            tarea.execute();
            while(!termine)
            {
                /*Nada*/
            }
            Descargafoto descargate;
            descargate = new Descargafoto();
            descargate.execute();
            while(!acabe)
            {
                /* :D */
            }

            titulofinal.setText(traetitulo);
            anio.setText("Año publicada: " + traeanio);
            director.setText("Director: " + traedirector);
            plot.setText("Sinopsis: " + traeplot);

            return vistadevuelta;
        }


        private class Tareatraeporid extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... parametros) {

                try {
                    URL miRuta = new URL("http://www.omdbapi.com/?apikey=4716803d&i=" + id);
                    Log.d("Conexion", "Me voy a conectar");

                    HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                    Log.d("Conexion", "Ya establecí la conexión");

                    if (miConexion.getResponseCode() == 200) {
                        Log.d("Conexion", "Me pude conectar y todo OK");

                        InputStream lector = miConexion.getInputStream();
                        InputStreamReader lectorJSon = new InputStreamReader(lector, "utf-8");

                        JsonParser ProcesadorDeJSon = new JsonParser();
                        JsonObject objetoJSon = ProcesadorDeJSon.parse(lectorJSon).getAsJsonObject();

                    /*
                    int Cantidad=objetoJSon.get("cantidad_de_categorias").getAsInt();
                    Log.d("Conexion", "Cantidad: "+Cantidad);

                     */
                        JsonObject unaCategoriaCualquiera = objetoJSon.getAsJsonObject();

                        traetitulo = unaCategoriaCualquiera.get("Title").getAsString();
                        traeanio = unaCategoriaCualquiera.get("Year").getAsString();
                        traedirector = unaCategoriaCualquiera.get("Director").getAsString();
                        traeplot = unaCategoriaCualquiera.get("Plot").getAsString();
                        posterurl = unaCategoriaCualquiera.get("Poster").getAsString();



                        termine = true;


                    } else {
                        Log.d("Conexion", "Me pude conectar pero algo malo pasó");
                    }


                } catch (Exception ErrorOcurrido) {
                    Log.d("Conexion", "Al conectar o procesar ocurrió Error: " + ErrorOcurrido.getMessage());
                }

                return null;
            }
        }

        public class Descargafoto extends AsyncTask<String, Void, Bitmap>{
            protected Bitmap doInBackground(String... Ruta){
                Bitmap imagenconvertida;
                imagenconvertida = null;
                try {
                    URL miRuta;
                    String link = posterurl;
                    miRuta = new URL(link);
                    Log.d("Imagen", "Mi ruta: " + miRuta);
                    HttpURLConnection conexionurl;
                    conexionurl = (HttpURLConnection) miRuta.openConnection();
                    if(conexionurl.getResponseCode()==200){
                        InputStream cuerpodatos = conexionurl.getInputStream();
                        BufferedInputStream lectorEntrada = new BufferedInputStream(cuerpodatos);
                        imagenconvertida = BitmapFactory.decodeStream(lectorEntrada);

                        conexionurl.disconnect();
                        Log.d("Imagenes", "Imagen: " + imagenconvertida.toString());

                    }
                } catch (Exception error){ Log.d("Imagen", "Error: " + error.getMessage()); }
                acabe = true;
                imagen.setImageBitmap(imagenconvertida);
                return imagenconvertida;
            }

        }
    }
}
