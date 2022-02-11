package com.example.ejerciciobasedatosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejerciciobasedatosqlite.db.DbContactos;

public class Vista_Contactos extends AppCompatActivity {

    EditText txtNombre,txtEdad;
    Button btGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_contactos);

        txtNombre= findViewById(R.id.txtNombreVer);
        txtEdad=findViewById(R.id.txtEdadVer);
        btGuardar=findViewById(R.id.btGuardarVer);

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbContactos bdContactos = new DbContactos(Vista_Contactos.this);
               long id= bdContactos.insertaContacto(txtNombre.getText().toString(),Integer.parseInt(txtEdad.getText().toString()));
               
               if(id>0){
                   Toast.makeText(Vista_Contactos.this, "Registro Guardado", Toast.LENGTH_SHORT).show();
                   limpiar();
               }else
               {
                   Toast.makeText(Vista_Contactos.this, "Error al Guardar Registro", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
    private void limpiar(){
        txtNombre.setText("");
        txtEdad.setText("");
    }
}