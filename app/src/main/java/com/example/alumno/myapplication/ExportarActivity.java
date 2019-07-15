package com.example.alumno.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExportarActivity extends AppCompatActivity {

    private EditText et_numMedidor, et_numLectura;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporta);

        et_numMedidor =(EditText) findViewById(R.id.txt_numMedidor);
        et_numLectura =(EditText) findViewById(R.id.txt_numLectura);
        enviar = (Button)findViewById(R.id.btnGuardar);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guardarData(et_numMedidor.getText().toString(), et_numLectura.getText().toString());

            }
        });

    }

//Medtodo para realizar consulta a la tabla lecturas, para luego poder ser enviadas a Servidor.

    public void Buscar(View view){

        AdminSQliteOpenHelper admin= new AdminSQliteOpenHelper(this, "demo", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select numMedidor, lectura from lecturas", null);

        if (fila.moveToFirst() ){
            et_numMedidor.setText(fila.getString(0));
            et_numLectura.setText(fila.getString(1));
            BaseDeDatos.close();


            }else {

            Toast.makeText(this, "No se han podido consultar los datos", Toast.LENGTH_SHORT).show();

        }



    }


    protected void guardarData(String et_numMedidor, String et_numLectura) {
        try {
            Toast.makeText(getApplicationContext(), "¡LISTO!", Toast.LENGTH_SHORT).show();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("et_numMedidor", et_numMedidor)
                    .addFormDataPart("et_numLectura", et_numLectura)
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
}
