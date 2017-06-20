package com.holamundo.example.hctor.alumnos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarAlumno extends AppCompatActivity {

    Bundle datosIntent;
    EditText txtNombre,txtApellidoP,txtApellidoM;
    Spinner spinner;
    TextView id;
    Button btnModificar, btnCancelar;
    ProgressDialog progeesDialog;
    String parametros = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alumno);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidoP = (EditText) findViewById(R.id.txtApellidoP);
        txtApellidoM = (EditText) findViewById(R.id.txtApellidom);
        spinner = (Spinner) findViewById(R.id.spn);
        id = (TextView) findViewById(R.id.id);
        btnModificar = (Button) findViewById(R.id.btnModicar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        //txtCurso = (EditText) findViewById(R.id.curso);

        datosIntent = getIntent().getExtras();

        final String idd = datosIntent.getString("id");
        id.setText(idd);

        String nombre = datosIntent.getString("nombre");
        txtNombre.setText(nombre);

        String ap = datosIntent.getString("apellidoP");
        txtApellidoP.setText(ap);

        String am = datosIntent.getString("apellidoM");
        txtApellidoM.setText(am);

        String curso = datosIntent.getString("curso");
        int pos = 0;
        if(curso.equals("Informática"))
            pos = 0;
        else if(curso.equals("Sistemas"))
            pos = 1;
        else if(curso.equals("Contabilidad"))
            pos = 2;
        else if(curso.equals("Diseño Gráfico"))
            pos = 3;
        else if(curso.equals("Cocina"))
            pos = 4;

        spinner.setSelection(pos);


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ida = id.getText().toString();
                String nombre =  txtNombre.getText().toString();
                String apellidoP =  txtApellidoP.getText().toString();
                String apellidoM =  txtApellidoM.getText().toString();
                String curso =  spinner.getSelectedItem().toString();

                if(ida.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()){
                    Toast.makeText(getApplicationContext(), "CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                }else{

                    progeesDialog =  new ProgressDialog(ModificarAlumno.this);
                    progeesDialog.setMessage("Modificando Datos, Espere...");
                    progeesDialog.setCancelable(false);
                    progeesDialog.show();

                    url = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/modificarAlumnos.php";
                    parametros = "id=" + ida + "&nombre=" + nombre + "&apellidoP=" + apellidoP + "&apellidoM=" + apellidoM + "&curso=" + curso;
                    new ModificarAlumno.SolicitaDados().execute(url);
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreInicio = new Intent(ModificarAlumno.this,listaAlumnos.class);
                startActivity(abreInicio);
                finish();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(ModificarAlumno.this);
                builder.setMessage("Alumno Modificado Con Éxito")
                        .setTitle("Éxito");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int iddd) {
                        dialog.dismiss();
                        txtNombre.setText("");
                        txtApellidoP.setText("");
                        txtApellidoM.setText("");
                        id.setText("");
                        Intent abreInicio = new Intent(ModificarAlumno.this,listaAlumnos.class);
                        startActivity(abreInicio);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificarAlumno.this);
                builder.setMessage("Datos no Insertados papú")
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
