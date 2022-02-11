package com.example.ejerciciobasedatosqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ejerciciobasedatosqlite.adaptadores.ListaContactosAdapter;
import com.example.ejerciciobasedatosqlite.db.DbContactos;
import com.example.ejerciciobasedatosqlite.entidades.Contactos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContactos=findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        DbContactos dbContactos= new DbContactos(MainActivity.this);
       // listaArrayContactos=new ArrayList<>();

        ListaContactosAdapter adapter= new ListaContactosAdapter(dbContactos.mostrarContactos());

       listaContactos.setAdapter(adapter);
        Log.d("miLog",dbContactos.mostrarContactos().toString());

    }//! fin OnCreate

    // agregar el menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_primcipal,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this,Vista_Contactos.class);
        startActivity(intent);
    }


}