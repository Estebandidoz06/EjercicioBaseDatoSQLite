package com.example.ejerciciobasedatosqlite;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SingleStatementInBlock")
public class logeo extends AppCompatActivity {
    private Button BT_Ingresar;
    private EditText TxT_Email;
    private EditText TxT_Password;
    private TextView TxT_Mensaje;
    private int Contador=4;

    /*private String Email="estebann.cc@gmail.com";
    private String Contraseña="5506Ester";*/
    boolean isValid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logeo);
        TxT_Email=findViewById(R.id.TxT_Email);

        TxT_Password=findViewById(R.id.TxT_Password);
        TxT_Mensaje=findViewById(R.id.TxT_Mensaje);
        BT_Ingresar=(Button) findViewById(R.id.BT_Ingresar);
        BT_Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarUsuario( "http://localhost/phpmyadmin/index.php?route=/table/sql&db=login&table=logeo");
                String inputEmail=TxT_Email.getText().toString();
                String inputPassword=TxT_Password.getText().toString();
                if (inputEmail.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(logeo.this,"Login Incorrecto",Toast.LENGTH_SHORT).show();
                }else {
                    isValid = validate(inputEmail, inputPassword);
                    if (isValid){
                        Contador--;
                        Toast.makeText(logeo.this, "Logeo incorrecto",Toast.LENGTH_SHORT).show();
                        TxT_Mensaje.setText("intento"+Contador);
                        if (Contador==0) {
                            BT_Ingresar.setEnabled(false);
                        }
                    }else{
                        Toast.makeText(logeo.this,"Logeo Correcto", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(logeo.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

    }
    private boolean validate( String Email, String Contraseña){
        if (Email.equals(TxT_Email) && Contraseña.equals(TxT_Password)){
            return true;
        }
        return false;
    }

    public Connection conexionBD(){
        Connection conexion=null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://74;databaseName=Logeo;user=sa;password=inacap-2021;");
            Toast.makeText(logeo.this,"Logeo Correcto", Toast.LENGTH_SHORT).show();


        } catch(Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
        }
        return conexion;
    }
    public void ValidarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(logeo.this, "Email o Password incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(logeo.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put( "Email", TxT_Email.getText().toString());
                parametros.put( "password", TxT_Password.getText().toString());
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    
}

