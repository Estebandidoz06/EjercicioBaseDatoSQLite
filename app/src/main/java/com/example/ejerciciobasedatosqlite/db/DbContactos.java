package com.example.ejerciciobasedatosqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ejerciciobasedatosqlite.MainActivity;
import com.example.ejerciciobasedatosqlite.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long insertaContacto(String nombre ,int edad) {
       long id=0;
        try {


        DbHelper dbHelper = new DbHelper((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("edad", edad);

         id = db.insert(TABLE_DATOS, null, values);
    }catch(Exception ex){
        ex.toString();


    }
        return id;
    }

public ArrayList<Contactos> mostrarContactos(){
    DbHelper dbHelper = new DbHelper((context));
    SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos= new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos= null;

        cursorContactos=db.rawQuery("SELECT * FROM "+TABLE_DATOS,null);
        if(cursorContactos.moveToFirst()){
            do{
                contacto= new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setEdad(cursorContactos.getInt(2));
                listaContactos.add(contacto);

                contacto.setId(cursorContactos.getInt(0));
            }while(cursorContactos.moveToNext());
        }

cursorContactos.close();
return listaContactos;
}

    public Contactos verContacto(int id){
        DbHelper dbHelper = new DbHelper((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Contactos contacto = null;
        Cursor cursorContactos= null;

        cursorContactos=db.rawQuery("SELECT * FROM "+TABLE_DATOS+ " WHERE id = "+ id + " LIMIT 1",null);
        if(cursorContactos.moveToFirst()){

                contacto= new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setEdad(cursorContactos.getInt(2));
               Log.d("Nombre",contacto.getNombre());

        }
        cursorContactos.close();
        return contacto;
    }

    public boolean editarContacto(int id,String nombre ,int edad) {
        boolean correcto=false;
        DbHelper dbHelper = new DbHelper((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
        db.execSQL("UPDATE "+TABLE_DATOS+" set nombre='"+nombre+"',edad='"+edad+"' Where id='"+id+"'");
        correcto=true;
        }catch(Exception ex){
            ex.toString();
        }
        finally{
            db.close();
        }
        return correcto;
    }
    public boolean eliminarContacto(int id) {
        boolean correcto=false;
        DbHelper dbHelper = new DbHelper((context));
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM "+TABLE_DATOS+" Where id='"+id+"'");
            correcto=true;
        }catch(Exception ex){
            ex.toString();
            correcto=false;
        }
        finally{
            db.close();
        }
        return correcto;
    }


}
