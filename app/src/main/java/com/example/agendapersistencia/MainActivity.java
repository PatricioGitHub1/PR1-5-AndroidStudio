package com.example.agendapersistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a objetos de la View
        Button saveButton = (Button) findViewById(R.id.button);
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        EditText editTextPhone = (EditText) findViewById(R.id.editTextNumber);
        EditText editTextMail = (EditText) findViewById(R.id.editTextTextEmailAddress);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Info", "He presionado el  boton de guardar!");
                ArrayList<TextView> campos = new ArrayList<>();
                campos.add(editTextName);
                campos.add(editTextSurname);
                campos.add(editTextPhone);
                campos.add(editTextMail);

                boolean canSave = checkIfEmpty(campos);

                if (!canSave) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Detected empty field!", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    try {
                        FileOutputStream fos = getApplicationContext().openFileOutput("contactes.txt", MODE_APPEND);
                        StringBuilder text_original = new StringBuilder();
                        for (TextView txv : campos) {
                            text_original.append(txv.getText()).append(";");
                        }

                        try {
                            fos.write(text_original.toString().getBytes());
                            fos.write("\n".getBytes()); // Add a newline as a separator
                            fos.close(); // Close the stream to save changes
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private boolean checkIfEmpty(ArrayList<TextView> Views) {
        for (TextView txv: Views) {
            if (txv.getText().length() == 0) {
                Log.i("info", "Campo de texto vacio");
                return false;
            }
        }
        return true;
    }
}