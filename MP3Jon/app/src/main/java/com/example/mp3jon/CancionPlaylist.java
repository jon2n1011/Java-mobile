package com.example.mp3jon;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.mp3jon.Adaptadores.AdapterCanciones;
import com.example.mp3jon.Objetos.PlayList;
import com.example.mp3jon.Objetos.Track;
import com.example.mp3jon.SQL.Musica;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class CancionPlaylist extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView lista;
    private AdapterCanciones adaptador1;
    private View rootView;
    Context contexto =this;
    private ArrayList<Track> tracks= new ArrayList<>();
    private ArrayList<Integer> cancionesid=new ArrayList<>();
    private ArrayList<String> cancionestitulo=new ArrayList<>();
    private static final int MY_PERMISSION_REQUEST=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canciones);
        lista=findViewById(R.id.listac);
        Musica canciones = new Musica(this, "musica", null, 1);
        SQLiteDatabase db = canciones.getWritableDatabase();
        // Cursor donde tenemos una sentencia
        int idp=getIntent().getIntExtra("idplaylist",0);
        Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id in (select trackid from playlistmusic where playlistid="+idp+")",null);

        if (c5 != null && c5.moveToFirst()) {
            do {
                tracks.add(new Track((c5.getInt(0)), getAlbumImage(c5.getString(2)), c5.getString(1), c5.getString(2)));
                cancionesid.add(c5.getInt(0));
            } while (c5.moveToNext());
        }

        // Creamos un adaptador para enlazar el arraylist con los datos a la list view
        adaptador1 = new AdapterCanciones(this, tracks);
        lista.setAdapter(adaptador1);

        // Se usa cuando pulsemos un contacto en la list view
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Cogemos el contacto de la posicion clicada
                Track track = tracks.get(position);

                Intent intento = new Intent(view.getContext(), Cancion.class);
                intento.putExtra("idcancion", track.getId());
                intento.putExtra("titulo", track.getNombre());
                intento.putExtra("path", track.getPath());
                intento.putExtra("BitmapImage", track.getImagen());
                intento.putExtra("cancionesid",cancionesid);
                startActivity(intento);
            }
        });



    }


    // Menu de opciones despues de longclick de una entrada de el listview
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Escoja una opcion");
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_playlist, menu);

    }


    // Ahora programaremos lo que hara cada opcion de context menu longclick


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Track trac = tracks.get(info.position);
        switch (item.getItemId()) {
//Vercontacto
            case R.id.ap:

                cancionplaylist(trac);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }




    public void cancionplaylist(final Track trac){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.baseline_fast_rewind_black_18dp);
        builderSingle.setTitle("Seleccione lista de reproducción:");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        Musica canciones = new Musica(rootView.getContext(), "musica", null, 1);
        SQLiteDatabase db = canciones.getWritableDatabase();
        // Cursor donde tenemos una sentencia
        Cursor c5 = db.rawQuery("SELECT nombre FROM playlist", null);
        if (c5 != null && c5.moveToFirst()) {
            do {

                arrayAdapter.add(c5.getString(0));

            } while (c5.moveToNext());

        }
        db.close(); // Cerramos la BBDD

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                Musica canciones = new Musica(rootView.getContext(), "musica", null, 1);
                SQLiteDatabase db = canciones.getWritableDatabase();
                db.execSQL("INSERT INTO playlistmusic(playlistid,trackid) VALUES ("+which+","+trac.getId()+");");
                AlertDialog.Builder builderInner = new AlertDialog.Builder(getApplicationContext());
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();


    }
        private Bitmap getAlbumImage(String path) {
            android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            byte[] data = mmr.getEmbeddedPicture();
            if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
            return null;
        }

}
