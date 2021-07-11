package com.example.mp3jon.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Musica extends SQLiteOpenHelper {

    String sqlCreacion ="CREATE TABLE musica (id integer primary key, " +
            "nom text  not null,path text not null);";
    String sqlcreacion2="CREATE TABLE playlist(playlistid integer primary key,data DATE, " +
            "nombre String);";
    String sqlcreacion3="CREATE TABLE playlistmusic(playlistid Integer,trackid Integer);";
    public Musica(Context context, String name, SQLiteDatabase.CursorFactory factory,
                     int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists musica");
        db.execSQL("drop table if exists playlist");
        db.execSQL("drop table if exists playlistmusic");
        db.execSQL(sqlCreacion);
        db.execSQL(sqlcreacion2);
        db.execSQL(sqlcreacion3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Només s'executa quan la versió de la BBDD amb la que cridem es superior a l'actual
        // S'hauria de fer una micració de dades. Nosaltres simplment esborrament les dades
        // antigues i crearem la nova estructura.

        db.execSQL("DROP TABLE IF EXISTS contactos");

        db.execSQL(sqlCreacion);
        db.execSQL(sqlcreacion2);
        // També és podria fer:
        // onCreate(db);
    }


}
