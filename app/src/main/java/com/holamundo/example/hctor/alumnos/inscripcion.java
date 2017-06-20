package com.holamundo.example.hctor.alumnos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class inscripcion extends AppCompatActivity {
    Button btn;
    EditText txtNombre, txtApellidoP, txtApellidoM;
    String url = "";
    String parametros = "";
    ProgressDialog progeesDialog;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripcion);

        btn = (Button) findViewById(R.id.btnRegistrar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidoP = (EditText) findViewById(R.id.txtApellidoP);
        txtApellidoM = (EditText) findViewById(R.id.txtApellidom);
        spinner = (Spinner) findViewById(R.id.spn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    String nombre = txtNombre.getText().toString();
                    String apellidoP = txtApellidoP.getText().toString();
                    String apellidoM = txtApellidoM.getText().toString();
                    String curso = spinner.getSelectedItem().toString();

                    if (nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "VACIOS", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progeesDialog =  new ProgressDialog(inscripcion.this);
                        progeesDialog.setMessage("Enviando datos, Espere Por Favor");
                        progeesDialog.setCancelable(false);
                        progeesDialog.show();

                        url = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/registrarAlumnos.php";
                        parametros = "nombre=" + nombre + "&apellidoP=" + apellidoP + "&apellidoM=" + apellidoM + "&curso=" + curso;
                        new inscripcion.SolicitaDados().execute(url);
                    }
                }
            }
        });
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {
            if (resultado.contains("OK")){
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(inscripcion.this);
                builder.setMessage("Inscripción Correcta")
                        .setTitle("Éxito");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        txtNombre.setText("");
                        txtApellidoP.setText("");
                        txtApellidoM.setText("");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(inscripcion.this);
                builder.setMessage("No se pudo hacer la inscripción...")
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
