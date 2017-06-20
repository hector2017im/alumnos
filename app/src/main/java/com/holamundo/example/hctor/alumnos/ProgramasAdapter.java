package com.holamundo.example.hctor.alumnos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class ProgramasAdapter extends RecyclerView.Adapter<ProgramasAdapter.ItemViewHolder> {

    private List<ProgramaResponse> list;
    private Context context;
    String url = "";
    String parametros = "";
    ProgressDialog progeesDialog;
    public ProgramasAdapter(Context context, List<ProgramaResponse> list) {
        this.list = list;
        this.context=context;
    }
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreateViewHolder(savedInstanceState);
        setContentView(R.layout.list_item);
       // etiNombre = (TextView) findViewById( R.id.eti_nombreA );
        //etiTelefono = (TextView) findViewById( R.id.eti_telefonoA );
        }*/

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_programa, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final ProgramaResponse item = list.get(position);

        holder.nombre.setText(item.getNombre());
        holder.apellidos.setText(item.getApellidoP() +" "+ item.getGetApellidoM());
        holder.curso.setText(item.getCurso());
        holder.idAlumno.setText(item.getId()+"");

        final String nombre, apellidoP, apellidoM, curso, idAlumno;
        nombre = holder.nombre.getText().toString();
        apellidoP = item.getApellidoP().toString();
        apellidoM = item.getGetApellidoM().toString();
        curso = holder.curso.getText().toString();
        idAlumno = holder.idAlumno.getText().toString();


        holder.btnModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreInicio = new Intent(context,ModificarAlumno.class);
                abreInicio.putExtra("id",idAlumno);
                abreInicio.putExtra("nombre",nombre);
                abreInicio.putExtra("apellidoP",apellidoP);
                abreInicio.putExtra("apellidoM",apellidoM);
                abreInicio.putExtra("curso",curso);
                context.startActivity(abreInicio);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("")
                        .setTitle("Eliminar Alumno");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //ELIMINAR
                        url = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/eliminarAlumno.php";
                        parametros = "idAlumno=" + idAlumno;

                        progeesDialog =  new ProgressDialog(ProgramasAdapter.this.context);
                        progeesDialog.setMessage("Eliminando, Espere...");
                        progeesDialog.setCancelable(false);
                        progeesDialog.show();


                        new ProgramasAdapter.SolicitaDados().execute(url);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context,"Cancelar", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                //Toast.makeText(v.getContext(),nombre + apellidoP+ apellidoM+ curso+ idAlumno, Toast.LENGTH_LONG).show();
            }
        });

        ///

    }

    ///ASS
    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {
            if (resultado.contains("OK")){
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("Alumno Eliminado")
                        .setTitle("Éxito");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }else {
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("Alumno NO Eliminado")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }
        }
    }
    ///ASS



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView idAlumno;
        public TextView nombre;
        public TextView apellidos;
        public TextView curso;
        public ImageButton btnModi;
        public ImageButton btnDelete;


        public ItemViewHolder(View v) {
            super(v);
            idAlumno = (TextView) v.findViewById(R.id.idAlumno);
            nombre = (TextView) v.findViewById(R.id.nombre);
            apellidos = (TextView) v.findViewById(R.id.apellidos);
            curso = (TextView) v.findViewById(R.id.curso);
            btnModi = (ImageButton) v.findViewById(R.id.btnmodi);
            btnDelete = (ImageButton) v.findViewById(R.id.btnDelete);
        }
    }
}