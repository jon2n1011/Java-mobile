package com.example.mp3jon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mp3jon.Objetos.Track;
import com.example.mp3jon.SQL.Musica;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class Cancion extends AppCompatActivity {
    MediaPlayer mp;
    boolean aleatorioc=false;
    boolean parado=false;
    ArrayList<Integer> canciones=new ArrayList();
    String pattern = "mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String path;
    SeekBar sb;
    boolean pausado=false;
    TextView duration;
    TextView ta;
    ImageButton pausa;
    private TextView titulo;
    private ImageView imagen;
    private Handler mHandler = new Handler();
    int currentp;
    private int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancion);
        titulo = findViewById(R.id.titulo);
        imagen = findViewById(R.id.imageView);
        pausa=findViewById(R.id.imageButton7);
        sb=findViewById(R.id.seekBar3);
        duration=findViewById(R.id.duracion);
        id=getIntent().getIntExtra("idcancion",1);
        canciones=getIntent().getIntegerArrayListExtra("cancionesid");
        String titulo2 = getIntent().getStringExtra("titulo");
        imagen.setImageBitmap((Bitmap) getIntent().getParcelableExtra("BitmapImage"));
        path=getIntent().getStringExtra("path");
        titulo.setText(titulo2);
        ta=findViewById(R.id.tiempoactual);
        Cancion.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
            if(!parado){
                if(mp != null){
                    int mCurrentPosition = mp.getCurrentPosition() / 1000;
                    sb.setProgress(mCurrentPosition);
                    String time = simpleDateFormat.format(mp.getCurrentPosition());
                    ta.setText(time);
                }
                mHandler.postDelayed(this, 1000);}
            else{ mHandler.removeCallbacks(this);}
            }
        });

        mp=new MediaPlayer();
            try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
            duration.setText(simpleDateFormat.format(mp.getDuration()));
            sb.setMax(mp.getDuration()/1000);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mp != null && fromUser){
                    mp.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        parado=true;
        mp.release();
    }

    // Pausa o play
    public void pausa(View view) throws IOException {
        if(!pausado){
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_play_arrow_black_18dp);
       pausa.setImageBitmap(bmp);
        mp.pause();
        pausado=true;
        }

        else {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_pause_black_18dp);
            pausa.setImageBitmap(bmp);
            mp.start();
            pausado=false;

        }
    }
    //Alternara imagebutton aleatorio


    public void aleatorio(View view){

        if (aleatorioc){ aleatorioc=false;
            Toast.makeText(getApplicationContext(), "Aleatoriedad desactivida", Toast.LENGTH_LONG).show();}
        else{ aleatorioc=true;
            Toast.makeText(getApplicationContext(), "Aleatoriedad activida", Toast.LENGTH_LONG).show();
        }
    }

    //Desplazara a la anterior cancion
    public void anterior(View view){
        Musica cancione = new Musica(getApplicationContext(), "musica", null, 1);
        SQLiteDatabase db = cancione.getWritableDatabase();
        if(!aleatorioc) {
            // Cursor donde tenemos una sentencia
            if (canciones.indexOf(id) == 0) {
                mp.release();
                try {
                    mp = new MediaPlayer();
                    mp.setDataSource(path);
                    mp.prepare();
                    mp.start();
                    duration.setText(simpleDateFormat.format(mp.getDuration()));
                    sb.setMax(mp.getDuration() / 1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                id = canciones.get((canciones.indexOf(id)) - 1);
                //Parte comun
                Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id=" + id + "", null);
                c5.moveToFirst();
                id = c5.getInt(0);
                titulo.setText(c5.getString(1));
                //Iniciamos otra vez release
                mp.release();
                try {
                    mp = new MediaPlayer();
                    String h = c5.getString(2);
                    mp.setDataSource(h);
                    mp.prepare();
                    mp.start();
                    duration.setText(simpleDateFormat.format(mp.getDuration()));
                    sb.setMax(mp.getDuration() / 1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else {


            Random r=new Random();
            int random=r.nextInt(canciones.size());
            id = canciones.get(random);
            //Parte comun
            Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id=" + id + "", null);
            c5.moveToFirst();
            id = c5.getInt(0);
            titulo.setText(c5.getString(1));
            //Iniciamos otra vez release
            mp.release();
            try {
                mp = new MediaPlayer();
                String h = c5.getString(2);
                mp.setDataSource(h);
                mp.prepare();
                mp.start();
                duration.setText(simpleDateFormat.format(mp.getDuration()));
                sb.setMax(mp.getDuration() / 1000);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_pause_black_18dp);
        pausa.setImageBitmap(bmp);
        pausado=false;

        }



    //Desplazara a la siguiente cancion
    public void siguiente(View view){
        Musica cancione = new Musica(getApplicationContext(), "musica", null, 1);
        SQLiteDatabase db = cancione.getWritableDatabase();
        // Cursor donde tenemos una sentencia
if(!aleatorioc) {
    if ((canciones.size() - 1) == (canciones.indexOf(id))) {
        id = canciones.get(0);
        //Parte comun
        Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id=" + id + "", null);
        c5.moveToFirst();
        id = c5.getInt(0);
        titulo.setText(c5.getString(1));
        //Iniciamos otra vez release
        mp.release();
        try {
            mp = new MediaPlayer();
            mp.setDataSource(c5.getString(2));
            mp.prepare();
            mp.start();
            duration.setText(simpleDateFormat.format(mp.getDuration()));
            sb.setMax(mp.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        id = canciones.get((canciones.indexOf(id)) + 1);
        //Parte comun
        Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id=" + id + "", null);
        c5.moveToFirst();
        id = c5.getInt(0);
        titulo.setText(c5.getString(1));
        //Iniciamos otra vez release
        mp.release();
        try {
            mp = new MediaPlayer();
            String h = c5.getString(2);
            mp.setDataSource(h);
            mp.prepare();
            mp.start();
            duration.setText(simpleDateFormat.format(mp.getDuration()));
            sb.setMax(mp.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

else {


        Random r=new Random();
        int random=r.nextInt(canciones.size());
        id = canciones.get(random);
        //Parte comun
        Cursor c5 = db.rawQuery("SELECT id,nom,path FROM musica WHERE id=" + id + "", null);
        c5.moveToFirst();
        id = c5.getInt(0);
        titulo.setText(c5.getString(1));
        //Iniciamos otra vez release
        mp.release();
        try {
            mp = new MediaPlayer();
            String h = c5.getString(2);
            mp.setDataSource(h);
            mp.prepare();
            mp.start();
            duration.setText(simpleDateFormat.format(mp.getDuration()));
            sb.setMax(mp.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }



}

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_pause_black_18dp);
        pausa.setImageBitmap(bmp);
        pausado=false;
    }




}
