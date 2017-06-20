package com.holamundo.example.hctor.alumnos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.RequestQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class listaAlumnos extends AppCompatActivity {
    private  List<ProgramaResponse> listProgramas=new ArrayList<>();
    private  ProgramasAdapter mAdapter;
    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alumnos);

        mRecycler = (RecyclerView) findViewById(R.id.lvLista);

        String consulta = "http://isaactorressosa1993localizacion.esy.es/ALUMNOS/listarAlumons.php";
        EnviarRecibirDatos(consulta);

        mAdapter = new ProgramasAdapter(listaAlumnos.this, listProgramas);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter);
    }

    public void EnviarRecibirDatos(String URL) {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }//FFF

    public void CargarListView(JSONArray ja) {

        //ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i < ja.length(); i += 5) {

            try {
                ProgramaResponse item=new ProgramaResponse();
                item.setId(Integer.parseInt(ja.getString(i)));
                item.setNombre(ja.getString(i+1));
                item.setApellidoP(ja.getString(i + 2));
                item.setGetApellidoM(ja.getString(i + 3));
                item.setCurso(ja.getString(i + 4));
                listProgramas.add(item);
                //lista.add(ja.getString(i) + " " + ja.getString(i + 1) + " " + ja.getString(i + 2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mAdapter.notifyDataSetChanged();

    }
}
