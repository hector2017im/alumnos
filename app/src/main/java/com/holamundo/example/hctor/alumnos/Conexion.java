package com.holamundo.example.hctor.alumnos;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class Conexion
{
    public static String postDados (String urlUsuario, String parametrosUsuario){
        URL url;
        HttpURLConnection connection = null;
        try{

            url= new  URL(urlUsuario);
            connection =(HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Lenght",""+ Integer.toString(parametrosUsuario.getBytes().length));
            connection.setRequestProperty("Content-Language","pt-BR");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

           /* DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parametrosUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();*/

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametrosUsuario);
            outputStreamWriter.flush();
            // outputStreamWriter.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String linea;
            StringBuffer respuesta = new StringBuffer();
            while ((linea = bufferedReader.readLine()) != null){
                respuesta.append(linea);
                respuesta.append('\r');
            }
            bufferedReader.close();
            return respuesta.toString();


        }
        catch (Exception erro){
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}
