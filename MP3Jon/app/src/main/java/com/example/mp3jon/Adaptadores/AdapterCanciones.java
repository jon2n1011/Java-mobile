package com.example.mp3jon.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3jon.Objetos.Track;
import com.example.mp3jon.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCanciones extends BaseAdapter {
    Context contexto;
    List<Track> listatrack;
    ArrayList<Track> copyTrack = new ArrayList<>();

    public AdapterCanciones(Context contexto, List<Track> listacontacto) {
        this.contexto = contexto;
        this.listatrack = listacontacto;
        this.copyTrack.addAll(listacontacto);
    }

    @Override
    public int getCount() {
        return listatrack.size(); // Los elementos que hay en la lista seran return
    }

    @Override
    public Object getItem(int position) {
        return listatrack.get(position); // Returna de la lista la posicion que le pasemos
    }

    @Override
    public long getItemId(int position) {
        return listatrack.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista=convertView;
        LayoutInflater inflate=LayoutInflater.from(contexto);
        vista=inflate.inflate(R.layout.cancion_list,null);
        ImageView imagen=(ImageView) vista.findViewById(R.id.imageView2);
        TextView texto1=(TextView) vista.findViewById(R.id.textView3);
        texto1.setText(listatrack.get(position).getNombre().toString());
        if(listatrack.get(position).getImagen()!=null) imagen.setImageBitmap(listatrack.get(position).getImagen());
        else imagen.setImageResource(R.drawable.musica);
        return vista;

    }

}
