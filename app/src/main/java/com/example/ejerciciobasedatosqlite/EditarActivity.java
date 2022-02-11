package com.example.ejerciciobasedatosqlite;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejerciciobasedatosqlite.db.DbContactos;
import com.example.ejerciciobasedatosqlite.entidades.Contactos;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtEdad;
    Button btnGuardar;
    boolean correcto=false;
    Contactos contacto;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre=findViewById(R.id.txtNombreVer);
        txtEdad=findViewById(R.id.txtEdadVer);
        btnGuardar=findViewById(R.id.btGuardarVer);

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
        DbContactos dbContactos=new DbContactos(EditarActivity.this);
        contacto=dbContactos.verContacto(id);
        if(contacto!= null){
            txtNombre.setText(contacto.getNombre());
            txtEdad.setText(String.valueOf(contacto.getEdad()));
//Eliminar esta parte de el boton y de lo que permite abrir el teclado

        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("")&& !txtEdad.getText().toString().equals("")){
                   correcto= dbContactos.editarContacto(id,txtNombre.getText().toString(),Integer.parseInt(txtEdad.getText().toString()));
                   if(correcto){
                       Toast.makeText(EditarActivity.this,"Registro Modificado",Toast.LENGTH_SHORT).show();
                       verRegistro();
                   }else{
                       Toast.makeText(EditarActivity.this,"Error al  Modificar Registro",Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Toast.makeText(EditarActivity.this,"Ingrese Datos",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
private void verRegistro(){
        Intent intent= new Intent(this,VerActivity.class);
        intent.putExtra("ID",id);
        startActivity(intent);
}

}