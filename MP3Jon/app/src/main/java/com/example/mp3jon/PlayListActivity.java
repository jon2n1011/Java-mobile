package com.example.mp3jon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mp3jon.Adaptadores.AdapterPlayList;
import com.example.mp3jon.Objetos.PlayList;
import com.example.mp3jon.Objetos.Track;
import com.example.mp3jon.SQL.Musica;

import java.util.ArrayList;


public class PlayListActivity extends Fragment implements View.OnClickListener {

    private ListView lista;
    private AdapterPlayList adp;
    private ArrayList<PlayList> playlistas=new ArrayList<>();
    private EditText editText;
    private View rootView;
    private ImageButton boton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Para resolver el error de que en los fragments no deja inflar el menu
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.playlist, container, false);
        lista = (ListView) rootView.findViewById(R.id.playlist);
        boton=rootView.findViewById(R.id.afegir);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Crear Playlist");
                // Set up the input
                final EditText input = new EditText(getActivity());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cuando accionamos crear añadimos la play list a la vista
                        playlistas.clear();
                        Musica canciones = new Musica(getActivity(), "musica", null, 1);
                        SQLiteDatabase db = canciones.getWritableDatabase();
                        db.execSQL("INSERT INTO playlist (nombre) values ('"+input.getText()+"')");
                        Cursor c = db.rawQuery("SELECT playlistid,data,nombre FROM playlist", null);
                        if (c != null && c.moveToFirst()) {
                            do {
                                PlayList p1=new PlayList((c.getInt(0)), c.getString(2), null, 0, null);
                                //Contar canciones de la lista
                                Cursor c1 = db.rawQuery("SELECT count(trackid),playlistid FROM playlistmusic WHERE playlistid=" + c.getInt(0) + " group by playlistid;", null);
                                if (c1 != null && c1.moveToFirst()) {
                                    do {

                                        p1.setNumcanciones(c1.getInt(0));

                                    } while (c1.moveToNext());
                                }
                                playlistas.add(p1);

                            } while (c.moveToNext());


                            db.close(); // Cerramos la BBDD
                        }
                        adp = new AdapterPlayList(getContext(), playlistas);
                        lista.setAdapter(adp);
                        Toast.makeText(getActivity(),"Playlist creada.",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(getActivity(),"Playlist no creada.",Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();

            }
        });
        Musica canciones = new Musica(getActivity(), "musica", null, 1);
        SQLiteDatabase db = canciones.getWritableDatabase();
        //Primero pillo las id de las listas
        Cursor c = db.rawQuery("SELECT playlistid,data,nombre FROM playlist", null);

        if (c != null && c.moveToFirst()) {
            do {
                PlayList p1=new PlayList((c.getInt(0)), c.getString(2), null, 0, null);

                //Contar canciones de la lista
                Cursor c1 = db.rawQuery("SELECT count(trackid),playlistid FROM playlistmusic WHERE playlistid=" + c.getInt(0) + " group by playlistid;", null);
                if (c1 != null && c1.moveToFirst()) {
                    do {

                     p1.setNumcanciones(c1.getInt(0));

                    } while (c1.moveToNext());
                }
                playlistas.add(p1);

            } while (c.moveToNext());


            db.close(); // Cerramos la BBDD
        }
        adp = new AdapterPlayList(getContext(), playlistas);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cogemos la playlist
                PlayList pl = playlistas.get(position);
                Intent intento = new Intent(view.getContext(), CancionPlaylist.class);
                intento.putExtra("idplaylist", pl.getId());
                intento.putExtra("nom", pl.getNom());
                startActivity(intento);
            }
        });


        return rootView;


    }

    @Override
    public void onClick(View v) {

    }
}