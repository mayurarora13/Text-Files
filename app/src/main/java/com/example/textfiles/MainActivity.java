package com.example.textfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText mName, mSurname;
    Button mAdd, mSave;
    TextView mText;

    ArrayList<Person> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.etName);
        mSurname = findViewById(R.id.etSurName);
        mAdd = findViewById(R.id.btnAdd);
        mSave = findViewById(R.id.btnSave);
        mText = findViewById(R.id.tvTextView);

        people = new ArrayList<Person>();

        loadData();

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();

                people.add(new Person(name,surname));

                setTextToTextView();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveData();
            }
        });
    }

    private void setTextToTextView() {
        String text = "";

        for (int i=0; i < people.size(); i++) {
            text = text + people.get(i).getName() + "," + people.get(i).getSurname() + "\n";
        }

        mText.setText(text);
    }

    public void btnSaveData() {
        try {
            FileOutputStream file = openFileOutput("Data.txt",MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);

            for (int i=0; i < people.size(); i++) {
                outputFile.write(people.get(i).getName() + "," + people.get(i).getSurname() + "\n");
            }

            outputFile.flush();
            outputFile.close();

            Toast.makeText(MainActivity.this, "Succesfully Saved" ,Toast.LENGTH_SHORT).show();

        }
        catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadData() {
        people.clear();

        File file = getApplicationContext().getFileStreamPath("Data.txt");
        String lineFromFile;

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));

                while ((lineFromFile = reader.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(lineFromFile,",");

                    Person person = new Person(token.nextToken(),token.nextToken());
                    people.add(person);
                }

                reader.close();

                setTextToTextView();
            }
            catch (IOException e) {

            }
        }
    }
}
