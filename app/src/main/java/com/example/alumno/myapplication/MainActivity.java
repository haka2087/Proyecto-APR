package com.example.alumno.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button ingresar;
    EditText lectura, medidor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ingresar = (Button)findViewById(R.id.asdf);



        
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lectura.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "No has ingresado lectura", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(medidor.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "No has ingresado medidor", Toast.LENGTH_SHORT).show();
                    return;
                }

                guardarData(lectura.getText().toString(), medidor.getText().toString());

            }
        });
        
    }

    protected void guardarData(String lectura, String medidor) {
        try {
            Toast.makeText(getApplicationContext(), "¡LISTO!", Toast.LENGTH_SHORT).show();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("lectura", lectura)
                    .addFormDataPart("medidor", medidor)
                    .build();
            Request request = new Request.Builder()
                    .url("http://172.20.10.10/pro/lectura.php")
                    .post(requestBody)
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Response response = okHttpClient.newCall(request).execute();
            String responseStr = response.body().string();
            Toast.makeText(getApplication(), responseStr, Toast.LENGTH_LONG).show();
            Log.e("RESULT", responseStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//METODO PARA PASAR DE UNA ACTICITY A OTRO con boton "EXPORTAR LECTURAS":

    public void Exportar (View view) {
        Intent exportar = new Intent(this, ExportarActivity.class);
        startActivity(exportar);


    }

    public void Registrar (View view) {
        Intent registrar = new Intent(this, RegistrarLecturasActivity.class);
        startActivity(registrar);


    }


}
