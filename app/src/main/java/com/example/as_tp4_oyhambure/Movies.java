package com.example.as_tp4_oyhambure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Movies {
    String nombre;
    ArrayList<String> Listaadevolver = new ArrayList<>();
    ArrayList<JsonObject> arrayjsonobject = new ArrayList<>();
    Boolean termine = false;
    Boolean acabe = false;
    ArrayList<String> Linkimagenes = new ArrayList<>();
    ArrayList<String> Listaids = new ArrayList<>();
    ArrayList<Bitmap> noseusarte = new ArrayList<>();


    public ArrayList<String> Obtenertodas(String nombrepelicula) {
        nombre = nombrepelicula;
        Listaids = new ArrayList<>();
        TareaAsincronica tarea;
        tarea = new TareaAsincronica();
        tarea.execute();
        while(!termine)
        {
            /*Literalmente nada solo esperamos
             * */
        }
        return Listaadevolver;
    }

    public ArrayList<Bitmap> Damefotos()
    {
        noseusarte = new ArrayList<>();
        return noseusarte;
    }

    public ArrayList<String> Damelistafotos(String inncesarianoseparaquelapideparaelreturn){
        inncesarianoseparaquelapideparaelreturn = "";
        ArrayList<String> trampita;
        trampita = new ArrayList<>(Linkimagenes);
        return trampita;
    }

    private class TareaAsincronica extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... parametros) {

            try {
                URL miRuta = new URL("http://www.omdbapi.com/?apikey=4716803d&s=" + nombre);
                Log.d("Conexion", "Me voy a conectar");

                HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                Log.d("Conexion", "Ya establecí la conexión");

                if (miConexion.getResponseCode() == 200) {
                    Log.d("Conexion", "Me pude conectar y todo OK");

                    InputStream lector = miConexion.getInputStream();
                    InputStreamReader lectorJSon = new InputStreamReader(lector, "utf-8");

                    JsonParser ProcesadorDeJSon = new JsonParser();
                    JsonObject objetoJSon = ProcesadorDeJSon.parse(lectorJSon).getAsJsonObject();
                    if(objetoJSon.get("Search") == null){
                        Log.d("Conexion", "Llego vacio");
                    }
                    /*
                    int Cantidad=objetoJSon.get("cantidad_de_categorias").getAsInt();
                    Log.d("Conexion", "Cantidad: "+Cantidad);

                     */

                    else {
                    JsonArray arrResultados = objetoJSon.get("Search").getAsJsonArray();
                    Log.d("Conexion", "El array tiene " + arrResultados.size() + " elementos");

                        for (int i = 0; i < arrResultados.size(); i++) {
                            JsonObject unaCategoriaCualquiera = arrResultados.get(i).getAsJsonObject();

                            String Titulo = unaCategoriaCualquiera.get("Title").getAsString();
                            String Anio = unaCategoriaCualquiera.get("Year").getAsString();
                            String Id = unaCategoriaCualquiera.get("imdbID").getAsString();
                            String tipo = unaCategoriaCualquiera.get("Type").getAsString();
                            String url = unaCategoriaCualquiera.get("Poster").getAsString();
                            Listaadevolver.add(Titulo);
                            Linkimagenes.add(url);
                            Listaids.add(Id);
                            arrayjsonobject.add(unaCategoriaCualquiera);
                            Log.d("Conexion", "La lista tiene: " + Listaadevolver.size() + " elementos");
                            Log.d("Conexion", "Pos: " + i + " - Titulo: " + Titulo + " - Año: " + Anio + " Id: " + Id + "tipo: " + tipo + "url: " + url);
                            ;

                        }
                    }
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
                for (int i = 0; i < arrayjsonobject.size(); i++)
                {
                    JsonObject objetoactual = arrayjsonobject.get(i).getAsJsonObject();
                    String link = objetoactual.get("Poster").getAsString();
                    URL miRuta;
                    miRuta = new URL(link);
                    Log.d("Imagen", "Mi ruta: " + miRuta);
                    HttpURLConnection conexionurl;
                    conexionurl = (HttpURLConnection) miRuta.openConnection();
                    if(conexionurl.getResponseCode()==200){
                        InputStream cuerpodatos = conexionurl.getInputStream();
                        BufferedInputStream lectorEntrada = new BufferedInputStream(cuerpodatos);
                        imagenconvertida = BitmapFactory.decodeStream(lectorEntrada);

                        conexionurl.disconnect();
                        noseusarte.add(imagenconvertida);
                        Log.d("Imagenes", "Imagen: " + imagenconvertida.toString());
                        Log.d("Imagenes", "Tamanio Array: " + noseusarte.size());
                    }
                }
            } catch (Exception error){ Log.d("Imagen", "Error: " + error.getMessage()); }
            acabe = true;
            return imagenconvertida;
        }

    }

    public ArrayList<Bitmap> Traerfotos()
    {
        Descargafoto descargafoto = new Descargafoto();
        descargafoto.execute();
        while(!acabe)
        {
            /*Literalmente nada solo esperamos
             * */
        }
        return noseusarte;
    }

    public  ArrayList<String> Traerids()
    {
        return Listaids;
    }

}
