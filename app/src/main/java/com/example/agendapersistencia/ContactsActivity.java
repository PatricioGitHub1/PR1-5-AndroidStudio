package com.example.agendapersistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    Button backButton;
    ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        listViewContacts = findViewById(R.id.listViewContacts);

        displayContacts();
    }

    private void displayContacts() {
        ArrayList<String> contactsList = readContactsFromFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactsList);
        listViewContacts.setAdapter(adapter);
    }

    private ArrayList<String> readContactsFromFile() {
        ArrayList<String> contactsList = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput("contactes.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] contactFields = line.split(";");
                String formattedContact = String.format(
                        "Name: %s\nSecond Name: %s\nPhone Number: %s\nEmail: %s",
                        contactFields[0], contactFields[1], contactFields[2], contactFields[3]);

                contactsList.add(formattedContact);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactsList;
    }
}