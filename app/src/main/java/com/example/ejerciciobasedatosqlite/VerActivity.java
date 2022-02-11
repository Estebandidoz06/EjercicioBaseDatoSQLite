package com.example.ejerciciobasedatosqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ejerciciobasedatosqlite.db.DbContactos;
import com.example.ejerciciobasedatosqlite.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad;
    Button btnGuardar;
    FloatingActionButton fabEditar, favEliminar;
    Contactos contacto;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre=findViewById(R.id.txtNombreVer);
        txtEdad=findViewById(R.id.txtEdadVer);
        btnGuardar=findViewById(R.id.btGuardarVer);
        fabEditar=findViewById(R.id.fabEditar);
        favEliminar=findViewById(R.id.faEliminar);
        btnGuardar.setVisibility(View.INVISIBLE);
        if(savedInstanceState == null){
           Bundle extras=getIntent().getExtras();
           if(extras==null) {
               id = Integer.parseInt(null);
           }
           else{
                   id=extras.getInt("ID");
           }
           }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
            Log.d("ID",String.valueOf(id));
            DbContactos dbContactos=new DbContactos(VerActivity.this);
            contacto=dbContactos.verContacto(id);
            if(contacto!= null){
                txtNombre.setText(contacto.getNombre());
                txtEdad.setText(String.valueOf(contacto.getEdad()));

                txtNombre.setInputType(InputType.TYPE_NULL);
                txtEdad.setInputType(InputType.TYPE_NULL);
            }

            fabEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(VerActivity.this,EditarActivity.class);
                    intent.putExtra("ID",id);
                    startActivity(intent);
                }
            });
            favEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                    builder.setMessage("Eliminar datos").setPositiveButton("si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Creamos el metodo Eliminar
                            if(dbContactos.eliminarContacto(id)){
                                listaDatos();
                            }
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            });
        }



    private void listaDatos(){
    Intent intent = new Intent(this,MainActivity.class);
    startActivity(intent);
    }
}