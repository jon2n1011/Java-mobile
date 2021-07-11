package com.example.mp3jon.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3jon.Objetos.PlayList;
import com.example.mp3jon.Objetos.Track;
import com.example.mp3jon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterPlayList extends BaseAdapter {
    Context contexto;
    List<PlayList> listaPlay;
    ArrayList<PlayList> copyTrack = new ArrayList<>();

    public AdapterPlayList(Context contexto, List<PlayList> listacontacto) {
        this.contexto = contexto;
        this.listaPlay = listacontacto;
        this.copyTrack.addAll(listacontacto);
    }

    @Override
    public int getCount() {
        return listaPlay.size(); // Los elementos que hay en la lista seran return
    }

    @Override
    public Object getItem(int position) {
        return listaPlay.get(position); // Returna de la lista la posicion que le pasemos
    }

    @Override
    public long getItemId(int position) {
        return listaPlay.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista=convertView;
        LayoutInflater inflate=LayoutInflater.from(contexto);
        vista=inflate.inflate(R.layout.play_list,null);
        ImageView imagen=(ImageView) vista.findViewById(R.id.imageView2);
        TextView texto1=(TextView) vista.findViewById(R.id.textView3);
        TextView numcanciones=(TextView) vista.findViewById(R.id.textView);
        texto1.setText(listaPlay.get(position).getNom().toString());
        numcanciones.setText(String.valueOf(listaPlay.get(position).getNumcanciones()));
        imagen.setImageResource(R.drawable.playl);
        return vista;

    }
}
