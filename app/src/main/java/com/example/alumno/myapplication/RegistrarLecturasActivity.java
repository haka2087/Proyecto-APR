package com.example.alumno.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarLecturasActivity extends AppCompatActivity {

        private EditText et_numMedidor, et_numLectura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_lecturas);

        et_numMedidor = (EditText) findViewById(R.id.txt_medidor);
        et_numLectura = (EditText) findViewById(R.id.txt_lectura);
        }

        public void Registrar (View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "demo", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            String numMedior = et_numMedidor.getText().toString();
            String lectura = et_numLectura.getText().toString();
            if(!numMedior.isEmpty() && !lectura.isEmpty()){
                ContentValues registro = new ContentValues();
                registro.put("numMedidor", numMedior);
                registro.put("lectura", lectura);

                BaseDeDatos.insert("lecturas", null, registro);
                BaseDeDatos.close();

                et_numMedidor.setText("");
                et_numLectura.setText("");

                Toast.makeText(this, "Registro exitoso",Toast.LENGTH_SHORT).show();


                }else {
                Toast.makeText(this, "Debes llenar todos los campo", Toast.LENGTH_SHORT).show();
            }

        }
}
