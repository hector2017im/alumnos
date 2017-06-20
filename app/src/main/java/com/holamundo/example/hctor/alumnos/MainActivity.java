package com.holamundo.example.hctor.alumnos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
    EditText txtUsuario, txtPass;
    String url = "";
    String parametros = "";
    ProgressDialog progeesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        txtPass = (EditText)findViewById(R.id.txtPass);
        btn = (Button)findViewById(R.id.buttonlogin);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            String usuario = txtUsuario.getText().toString();
            String contrasena = txtPass.getText().toString();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ningun campo debe estar vacio", Toast.LENGTH_LONG).show();
            }else{
                progeesDialog =  new ProgressDialog(MainActivity.this);
                progeesDialog.setMessage("Verificando datos, Espere...");
                progeesDialog.setCancelable(false);
                progeesDialog.show();

                //url = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/validarUsuario.php";
                url = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/validarUsuario.php";
                parametros = "usuario=" + usuario + "&contrasena=" + contrasena;
                new SolicitaDados().execute(url);

            }

        }else{
            Toast.makeText(getApplicationContext(), "no hay conexion", Toast.LENGTH_LONG).show();
        }


    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {
            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();
            int ini = resultado.length()-4;

            String rol = resultado.substring(ini,ini+1);
            Toast.makeText(getApplicationContext(),rol,Toast.LENGTH_LONG).show();
            if (resultado.contains("login_ok")){
                progeesDialog.dismiss();
                //progeesDialog.dismiss();
                String[] dados = resultado.split(",");
                // etEmail.setText(dados[0]+"-"+dados[1]+"+"+dados[2]);

                Intent abreInicio = new Intent(MainActivity.this,Main2Activity.class);

                abreInicio.putExtra("rol",rol);
                //abreInicio.putExtra("contrasena",dados[1]);
                //Toast.makeText(getApplicationContext(),"YEAH PAPU",Toast.LENGTH_LONG).show();
                startActivity(abreInicio);
            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Usuario o Contraseña Incorrectos")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                //Toast.makeText(getApplicationContext(),"Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();
                //progeesDialog.dismiss();
            }
        }
    }


}
